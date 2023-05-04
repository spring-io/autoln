/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.autoln;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Autoln {

	public void printLinks(PrintWriter writer, List<Ln> links) {
		links.forEach((link) -> writer.println(link));
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
		for (Ln ln : new ArrayList<>(results)) {
			Path from = ln.getFrom();
			String fromName = from.toFile().getName();
			Path supplemental = from.resolveSibling("." + fromName);
			File supplementalDir = supplemental.toFile();
			if (!(supplementalDir.exists() || supplementalDir.isDirectory())) {
				continue;
			}
			for (Path supplementalTo : listChildren(supplemental)) {
				String supplementalChildName = supplementalTo.toFile().getName();
				Path supplementalFrom = from.resolve(supplementalChildName);
				results.add(new Ln(supplementalFrom, supplementalTo));
			}
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

	private List<Path> listChildren(Path path) {
		try {
			try (Stream<Path> walk = Files.walk(path, 1)) {
				List<Path> result = walk.collect(Collectors.toList());
				result.remove(path);
				return result;
			}
		}
		catch (IOException ex) {
			throw new RuntimeException("Could not walk path", ex);
		}
	}

}
