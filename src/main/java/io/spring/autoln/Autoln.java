package io.spring.autoln;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Autoln {

	public void printLinks(PrintWriter writer, List<Ln> links) {
		links.forEach(link -> writer.println(link));
		writer.flush();
	}

	public void createLinks(List<Ln> links) {
		for (Ln link : links) {
			Path from = link.getFrom();
			Path to = link.getRelativeTo();
			try {
				deleteRecursively(from);
				Files.createSymbolicLink(from, to);
			}
			catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public List<Ln> findLinks(File path) {
		if (!path.exists()) {
			throw new IllegalArgumentException("Directory not found " + path);
		}
		if (!path.isDirectory()) {
			throw new IllegalArgumentException(path + " is not a Directory");
		}
		File[] dirs = path.listFiles(File::isDirectory);
		if (dirs == null) {
			return Collections.emptyList();
		}
		Map<String, Version> generationToMaxVersion = new HashMap<>();
		for (File dir : dirs) {
			if (!Version.isValid(dir.getName())) {
				continue;
			}
			Version version = Version.parse(dir.getName());
			String generation = version.getGeneration();
			generationToMaxVersion.compute(generation, (key, existingVersion) -> {
				if (existingVersion == null) {
					return version;
				}
				if (version.isGreaterThan(existingVersion)) {
					return version;
				}
				return existingVersion;
			});
		}
		List<Ln> results = new ArrayList<>();
		List<Version> versions = new ArrayList<>(generationToMaxVersion.values());
		Collections.sort(versions);
		Version current = null;
		Version currentSnapshot = null;
		for (Version version : versions) {
			if (version.isSnapshot()) {
				currentSnapshot = version;
			}
			else if (version.isRelease()) {
				current = version;
			}
			results.add(toLn(path, version, version.getGeneration()));
		}
		if (current != null) {
			results.add(toLn(path, current, "current"));
		}
		if (currentSnapshot != null) {
			results.add(toLn(path, currentSnapshot, "current-SNAPSHOT"));
		}
		return results;
	}

	private static Ln toLn(File path, Version version, String fromName) {
		File from = new File(path, fromName);
		File to = new File(path, version.getVersion());
		return new Ln(from, to);
	}

	/**
	 * Delete the supplied {@link File} &mdash; for directories, recursively delete any
	 * nested directories or files as well.
	 * @param root the root {@code File} to delete
	 * @return {@code true} if the {@code File} existed and was deleted, or {@code false}
	 * if it did not exist
	 * @throws IOException in the case of I/O errors
	 * @since 5.0
	 */
	static boolean deleteRecursively(Path root) throws IOException {
		if (root == null) {
			return false;
		}
		if (!Files.exists(root, LinkOption.NOFOLLOW_LINKS)) {
			return false;
		}

		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
		return true;
	}

}
