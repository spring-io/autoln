package io.spring.autoln;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AutolnTest {

	@Test
	void security() {
		String path = "src/test/resources/docs/spring-security";
		Autoln autoln = new Autoln();
		List<Ln> expected = new ArrayList<>();
		expected.add(new Ln(new File(path, "2.0.x"), new File(path, "2.0.8.RELEASE")));
		expected.add(new Ln(new File(path, "3.0.x"), new File(path, "3.0.8.RELEASE")));
		expected.add(new Ln(new File(path, "3.0.x-SNAPSHOT"), new File(path, "3.0.9.CI-SNAPSHOT")));
		expected.add(new Ln(new File(path, "3.1.x"), new File(path, "3.1.7.RELEASE")));
		expected.add(new Ln(new File(path, "3.1.x-SNAPSHOT"), new File(path, "3.1.8.CI-SNAPSHOT")));
		expected.add(new Ln(new File(path, "3.2.x"), new File(path, "3.2.10.RELEASE")));
		expected.add(new Ln(new File(path, "3.2.x-SNAPSHOT"), new File(path, "3.2.11.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "4.0.x"), new File(path, "4.0.4.RELEASE")));
		expected.add(new Ln(new File(path, "4.0.x-SNAPSHOT"), new File(path, "4.0.5.CI-SNAPSHOT")));
		expected.add(new Ln(new File(path, "4.1.x"), new File(path, "4.1.5.RELEASE")));
		expected.add(new Ln(new File(path, "4.1.x-SNAPSHOT"), new File(path, "4.1.6.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "4.2.x"), new File(path, "4.2.18.RELEASE")));
		expected.add(new Ln(new File(path, "4.2.x-SNAPSHOT"), new File(path, "4.2.19.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.0.x"), new File(path, "5.0.18.RELEASE")));
		expected.add(new Ln(new File(path, "5.0.x-SNAPSHOT"), new File(path, "5.0.19.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.1.x"), new File(path, "5.1.12.RELEASE")));
		expected.add(new Ln(new File(path, "5.1.x-SNAPSHOT"), new File(path, "5.1.13.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.2.x"), new File(path, "5.2.6.RELEASE")));
		expected.add(new Ln(new File(path, "5.2.x-SNAPSHOT"), new File(path, "5.2.7.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.3.x"), new File(path, "5.3.4.RELEASE")));
		expected.add(new Ln(new File(path, "5.3.x-SNAPSHOT"), new File(path, "5.3.5.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.4.x"), new File(path, "5.4.0")));
		expected.add(new Ln(new File(path, "5.4.x-SNAPSHOT"), new File(path, "5.4.1-SNAPSHOT")));
		expected.add(new Ln(new File(path, "current"), new File(path, "5.4.0")));
		expected.add(new Ln(new File(path, "current-SNAPSHOT"), new File(path, "5.4.1-SNAPSHOT")));

		List<Ln> actual = autoln.findLinks(new File(path));

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	void boot() {
		String path = "src/test/resources/docs/spring-boot";
		Autoln autoln = new Autoln();
		List<Ln> expected = new ArrayList<>();
		expected.add(new Ln(new File(path, "0.0.x"), new File(path, "0.0.9.RELEASE")));
		expected.add(new Ln(new File(path, "0.0.x-SNAPSHOT"), new File(path, "0.0.10.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "0.5.x-SNAPSHOT"), new File(path, "0.5.0.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "0.5.x"), new File(path, "0.5.0.M7")));
		expected.add(new Ln(new File(path, "1.0.x"), new File(path, "1.0.2.RELEASE")));
		expected.add(new Ln(new File(path, "1.0.x-SNAPSHOT"), new File(path, "1.0.3.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "1.1.x"), new File(path, "1.1.12.RELEASE")));
		expected.add(new Ln(new File(path, "1.2.x"), new File(path, "1.2.8.RELEASE")));
		expected.add(new Ln(new File(path, "1.2.x-SNAPSHOT"), new File(path, "1.2.9.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "1.3.x"), new File(path, "1.3.8.RELEASE")));
		expected.add(new Ln(new File(path, "1.3.x-SNAPSHOT"), new File(path, "1.3.9.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "1.4.x"), new File(path, "1.4.7.RELEASE")));
		expected.add(new Ln(new File(path, "1.4.x-SNAPSHOT"), new File(path, "1.4.8.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "1.5.x"), new File(path, "1.5.22.RELEASE")));
		expected.add(new Ln(new File(path, "1.5.x-SNAPSHOT"), new File(path, "1.5.23.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "2.0.x"), new File(path, "2.0.9.RELEASE")));
		expected.add(new Ln(new File(path, "2.0.x-SNAPSHOT"), new File(path, "2.0.10.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "2.1.x"), new File(path, "2.1.16.RELEASE")));
		expected.add(new Ln(new File(path, "2.1.x-SNAPSHOT"), new File(path, "2.1.17.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "2.2.x"), new File(path, "2.2.9.RELEASE")));
		expected.add(new Ln(new File(path, "2.2.x-SNAPSHOT"), new File(path, "2.2.10.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "2.3.x"), new File(path, "2.3.3.RELEASE")));
		expected.add(new Ln(new File(path, "2.3.x-SNAPSHOT"), new File(path, "2.3.4.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "2.4.x"), new File(path, "2.4.0-M2")));
		expected.add(new Ln(new File(path, "2.4.x-SNAPSHOT"), new File(path, "2.4.0-SNAPSHOT")));
		expected.add(new Ln(new File(path, "current"), new File(path, "2.4.0-M2")));
		expected.add(new Ln(new File(path, "current-SNAPSHOT"), new File(path, "2.4.0-SNAPSHOT")));

		List<Ln> actual = autoln.findLinks(new File(path));

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	void framework() {
		String path = "src/test/resources/docs/spring-framework";
		Autoln autoln = new Autoln();
		List<Ln> expected = new ArrayList<>();
		expected.add(new Ln(new File(path, "1.0.x"), new File(path, "1.0.2")));
		expected.add(new Ln(new File(path, "1.1.x"), new File(path, "1.1.5")));
		expected.add(new Ln(new File(path, "1.2.x"), new File(path, "1.2.9")));
		expected.add(new Ln(new File(path, "2.0.x"), new File(path, "2.0.8")));
		expected.add(new Ln(new File(path, "2.5.x"), new File(path, "2.5.6")));
		expected.add(new Ln(new File(path, "3.0.x"), new File(path, "3.0.7.RELEASE")));
		expected.add(new Ln(new File(path, "3.1.x"), new File(path, "3.1.4.RELEASE")));
		expected.add(new Ln(new File(path, "3.2.x"), new File(path, "3.2.18.RELEASE")));
		expected.add(new Ln(new File(path, "3.2.x-SNAPSHOT"), new File(path, "3.2.19.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "3.3.x-SNAPSHOT"), new File(path, "3.3.0.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "4.0.x"), new File(path, "4.0.9.RELEASE")));
		expected.add(new Ln(new File(path, "4.1.x"), new File(path, "4.1.9.RELEASE")));
		expected.add(new Ln(new File(path, "4.1.x-SNAPSHOT"), new File(path, "4.1.10.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "4.2.x"), new File(path, "4.2.9.RELEASE")));
		expected.add(new Ln(new File(path, "4.2.x-SNAPSHOT"), new File(path, "4.2.10.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "4.3.x"), new File(path, "4.3.28.RELEASE")));
		expected.add(new Ln(new File(path, "4.3.x-SNAPSHOT"), new File(path, "4.3.29.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.0.x"), new File(path, "5.0.18.RELEASE")));
		expected.add(new Ln(new File(path, "5.0.x-SNAPSHOT"), new File(path, "5.0.19.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.1.x"), new File(path, "5.1.17.RELEASE")));
		expected.add(new Ln(new File(path, "5.1.x-SNAPSHOT"), new File(path, "5.1.18.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.2.x"), new File(path, "5.2.8.RELEASE")));
		expected.add(new Ln(new File(path, "5.2.x-SNAPSHOT"), new File(path, "5.2.9.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.3.x"), new File(path, "5.3.0-M2")));
		expected.add(new Ln(new File(path, "5.3.x-SNAPSHOT"), new File(path, "5.3.0-SNAPSHOT")));
		expected.add(new Ln(new File(path, "current"), new File(path, "5.3.0-M2")));
		expected.add(new Ln(new File(path, "current-SNAPSHOT"), new File(path, "5.3.0-SNAPSHOT")));

		List<Ln> actual = autoln.findLinks(new File(path));

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	void findLinksWhenEmpty() {
		Autoln autoln = new Autoln();
		List<Ln> actual = autoln.findLinks(new File("src/test/resources/missing"));
		assertThat(actual).isEmpty();
	}

	@Test
	void printLinks() {
		String path = "path";
		Autoln autoln = new Autoln();
		List<Ln> links = new ArrayList<>();
		links.add(new Ln(new File(path, "1.0.x"), new File(path, "1.0.2")));
		StringWriter writer = new StringWriter();
		autoln.printLinks(new PrintWriter(writer), links);
		assertThat(writer.toString()).contains("path/1.0.x => path/1.0.2");
	}

	@Test
	void ln(@TempDir Path tempDir) throws IOException {
		Autoln autoln = new Autoln();
		List<Ln> links = new ArrayList<>();
		File from = new File(tempDir.getParent().toFile(), "current");
		File to = tempDir.toFile();
		links.add(new Ln(from, to));

		try {
			autoln.createLinks(links);

			assertThat(Files.readSymbolicLink(from.toPath())).isEqualByComparingTo(to.toPath());
		}
		finally {
			from.delete();
		}
	}

	private void printExpectedLinks(List<Ln> links) {
		for(Ln ln : links) {
			System.out.println("expected.add(new Ln(new File(path, \"" + ln.getFrom().getName() + "\"), new File(path, \"" + ln.getTo().getName() + "\")));");
		}
	}
}