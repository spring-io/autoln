package io.spring.autoln;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScanAutoLnMain {

	/**
	 * Finds all directories containing a file named ".autoln-scan"
	 * @param root
	 * @return
	 * @throws IOException
	 */
	static List<File> findAutoLnScanParents(File root) throws IOException {
		return Files.walk(root.toPath())
			.filter(path -> path.toFile().getName().equals(".autoln-scan"))
			.map(marker -> marker.getParent().toFile())
			.collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		if (args.length == 0 || args.length > 2) {
			usage();
		}

		File root = null;
		boolean createLinks = false;
		if (args.length == 1) {
			root = new File(args[0]);
		} else if (args[0].equals("--create-links")){
			root = new File(args[1]);
			createLinks = true;
		} else {
			usage();
		}

		Autoln autoln = new Autoln();
		List<File> projects = findAutoLnScanParents(root);
		for(File project : projects) {
			List<Ln> links = autoln.findLinks(project);
			if (createLinks) {
				autoln.createLinks(links);
			} else {
				autoln.printLinks(new PrintWriter(System.out), links);
			}
		}
	}

	private static void usage() {
		throw new IllegalArgumentException("Expected arguments [--create-links] root-directory");
	}
}
