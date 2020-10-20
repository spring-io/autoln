package io.spring.autoln;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommandLine.Command(mixinStandardHelpOptions = true)
class AbstractAutolnCommand {

	@CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
	private AutolnOptions options;

	@CommandLine.Spec
	private CommandLine.Model.CommandSpec commandSpec;

	protected Autoln autoln = new Autoln();

	/**
	 * Finds all directories containing a file named ".autoln-scan"
	 * @param root
	 * @return
	 */
	static List<File> scanForProjects(File root, Integer maxDepth) {
		Stream<Path> files;
		try {
			files = maxDepth == null ? Files.walk(root.toPath()) : Files.walk(root.toPath(), maxDepth);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		// @formatter:off
		return files.filter(path -> path.toFile().getName().equals(".autoln-scan"))
				.map(marker -> marker.getParent().toFile())
				.collect(Collectors.toList());
		// @formatter:on
	}

	PrintWriter stdout() {
		return commandSpec.commandLine().getOut();
	}

	PrintWriter stderr() {
		return commandSpec.commandLine().getErr();
	}

	List<File> getProjects() {
		ScanOptions scanOptions = this.options.scanOptions;
		if (scanOptions == null) {
			return Collections.singletonList(this.options.projectDir);
		}
		List<File> projects = scanForProjects(scanOptions.scanDir, scanOptions.maxDepth);
		if (projects.isEmpty()) {
			stdout().println("No projects contained .autoln-scan within " + scanOptions.scanDir + "with maxdepth of "
					+ scanOptions.maxDepth);
		}
		return projects;
	}

	static class AutolnOptions {

		@CommandLine.ArgGroup(exclusive = false, multiplicity = "1")
		private ScanOptions scanOptions;

		@CommandLine.Option(names = "--project-dir",
				description = "The directory that should be processed. No marker file is necessary", required = true)
		private File projectDir;

	}

	static class ScanOptions {

		@CommandLine.Option(names = "--scan-dir",
				description = "Indicates that each subdirectory of the scanDir that contains .autoln-scan should be processed as a project-dir",
				required = true)
		private File scanDir;

		@CommandLine.Option(names = "--maxdepth",
				description = "the maxdepth levels of a directories to scan when using scan-dir option")
		private Integer maxDepth;

	}

}
