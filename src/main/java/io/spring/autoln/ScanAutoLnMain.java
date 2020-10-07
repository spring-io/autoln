package io.spring.autoln;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommandLine.Command(defaultValueProvider = ScanAutoLnMain.CurrentDirectoryDefaultValueProvider.class)
public class ScanAutoLnMain {
	@CommandLine.Option(names = "--create-links", description = "flag to indicate that the links should be created rather than just printed out", defaultValue = "false")
	private boolean createLinks;

	@CommandLine.Parameters(paramLabel = "ROOT_DIR", description = "the directory to scan for creating symlinks")
	private File root;

	@CommandLine.Option(names = "--maxdepth", description = "descend at most maxdepth levels of a directories to find .autoln-scan")
	private Integer maxDepth;

	@CommandLine.Option(names = {"-h", "--help"}, description = "display help")
	private boolean help;

	/**
	 * Finds all directories containing a file named ".autoln-scan"
	 * @param root
	 * @return
	 */
	static List<File> findAutoLnScanParents(File root, Integer maxDepth)  {
		Stream<Path> files;
		try {
			files = maxDepth == null ?
					Files.walk(root.toPath()) :
					Files.walk(root.toPath(), maxDepth);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return files
			.filter(path -> path.toFile().getName().equals(".autoln-scan"))
			.map(marker -> marker.getParent().toFile())
			.collect(Collectors.toList());
	}

	public void run() {
		Autoln autoln = new Autoln();
		List<File> projects = findAutoLnScanParents(root, maxDepth);
		if (projects.isEmpty()) {
			System.out.println("No projects contained .autoln-scan within " + root + "with max-depth of " + maxDepth);
		}
		for(File project : projects) {
			System.out.println("Finding symlinks for " + project);
			List<Ln> links = autoln.findLinks(project);
			if (createLinks) {
				autoln.createLinks(links);
			} else {
				autoln.printLinks(new PrintWriter(System.out), links);
			}
		}
	}

	public static void main(String[] args) {
		ScanAutoLnMain main = CommandLine.populateCommand(new ScanAutoLnMain(), args);
		if (main.help) {
			CommandLine.usage(main, System.out);
		} else {
			System.out.println("Running...");
			main.run();
		}
	}

	static class CurrentDirectoryDefaultValueProvider implements CommandLine.IDefaultValueProvider {

		@Override
		public String defaultValue(CommandLine.Model.ArgSpec argSpec) throws Exception {
			if (argSpec.type().equals(File.class)) {
				return System.getProperty("user.dir");
			}
			else {
				return argSpec.defaultValue();
			}
		}
	}
}
