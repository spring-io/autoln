package io.spring.autoln;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Autoln {
	public void printLinks(PrintWriter writer, List<Ln> links) {
		links.forEach(link ->
			writer.println(link)
		);
		writer.flush();
	}

	public void createLinks(List<Ln> links) throws IOException {
		for (Ln link : links) {
			Path from = link.getFrom().toPath();
			Path to = link.getTo().toPath();
			Files.deleteIfExists(from);
			Files.createSymbolicLink(from, to);
		}
	}

	public List<Ln> findLinks(File path) {
		File[] dirs = path.listFiles(File::isDirectory);
		Map<String,Version> generationToMaxVersion = new HashMap<>();
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
			else {
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
}
