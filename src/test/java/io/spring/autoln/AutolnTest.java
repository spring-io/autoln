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
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

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
		expected.add(new Ln(new File(path, "2.1.x"), new File(path, "2.1.17.RELEASE")));
		expected.add(new Ln(new File(path, "2.1.x-SNAPSHOT"), new File(path, "2.1.18.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "2.2.x"), new File(path, "2.2.10.RELEASE")));
		expected.add(new Ln(new File(path, "2.2.x-SNAPSHOT"), new File(path, "2.2.11.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "2.3.x"), new File(path, "2.3.4.RELEASE")));
		expected.add(new Ln(new File(path, "2.3.x-SNAPSHOT"), new File(path, "2.3.5.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "2.4.x"), new File(path, "2.4.0-M3")));
		expected.add(new Ln(new File(path, "2.4.x-SNAPSHOT"), new File(path, "2.4.0-SNAPSHOT")));
		expected.add(new Ln(new File(path, "current"), new File(path, "2.3.4.RELEASE")));
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
		expected.add(new Ln(new File(path, "4.3.x"), new File(path, "4.3.29.RELEASE")));
		expected.add(new Ln(new File(path, "4.3.x-SNAPSHOT"), new File(path, "4.3.30.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.0.x"), new File(path, "5.0.19.RELEASE")));
		expected.add(new Ln(new File(path, "5.0.x-SNAPSHOT"), new File(path, "5.0.20.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.1.x"), new File(path, "5.1.18.RELEASE")));
		expected.add(new Ln(new File(path, "5.1.x-SNAPSHOT"), new File(path, "5.1.19.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.2.x"), new File(path, "5.2.9.RELEASE")));
		expected.add(new Ln(new File(path, "5.2.x-SNAPSHOT"), new File(path, "5.2.10.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.3.x"), new File(path, "5.3.0-RC1")));
		expected.add(new Ln(new File(path, "5.3.x-SNAPSHOT"), new File(path, "5.3.0-SNAPSHOT")));
		expected.add(new Ln(new File(path, "current"), new File(path, "5.2.9.RELEASE")));
		expected.add(new Ln(new File(path, "current-SNAPSHOT"), new File(path, "5.3.0-SNAPSHOT")));

		List<Ln> actual = autoln.findLinks(new File(path));

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	void frameworkWithSupplimental() {
		String path = "src/test/resources/docs/spring-framework-with-supplemental";
		Autoln autoln = new Autoln();
		List<Ln> expected = new ArrayList<>();
		expected.add(new Ln(new File(path, "2.0.x"), new File(path, "2.0.8.RELEASE")));
		expected.add(new Ln(new File(path, "3.0.x"), new File(path, "3.0.8.RELEASE")));
		expected.add(new Ln(new File(path, "3.1.x"), new File(path, "3.1.7.RELEASE")));
		expected.add(new Ln(new File(path, "3.2.x"), new File(path, "3.2.10.RELEASE")));
		expected.add(new Ln(new File(path, "4.0.x"), new File(path, "4.0.4.RELEASE")));
		expected.add(new Ln(new File(path, "4.1.x"), new File(path, "4.1.5.RELEASE")));
		expected.add(new Ln(new File(path, "4.2.x"), new File(path, "4.2.20.RELEASE")));
		expected.add(new Ln(new File(path, "5.0.x"), new File(path, "5.0.19.RELEASE")));
		expected.add(new Ln(new File(path, "5.1.x"), new File(path, "5.1.13.RELEASE")));
		expected.add(new Ln(new File(path, "5.2.x"), new File(path, "5.2.15.RELEASE")));
		expected.add(new Ln(new File(path, "5.2.x-SNAPSHOT"), new File(path, "5.2.16.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.3.x"), new File(path, "5.3.13.RELEASE")));
		expected.add(new Ln(new File(path, "5.3.x-SNAPSHOT"), new File(path, "5.3.14.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.4.x"), new File(path, "5.4.11")));
		expected.add(new Ln(new File(path, "5.4.x-SNAPSHOT"), new File(path, "5.4.12-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.5.x"), new File(path, "5.5.8")));
		expected.add(new Ln(new File(path, "5.5.x-SNAPSHOT"), new File(path, "5.5.9-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.6.x"), new File(path, "5.6.10")));
		expected.add(new Ln(new File(path, "5.6.x-SNAPSHOT"), new File(path, "5.6.11-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.7.x"), new File(path, "5.7.8")));
		expected.add(new Ln(new File(path, "5.7.x-SNAPSHOT"), new File(path, "5.7.9-SNAPSHOT")));
		expected.add(new Ln(new File(path, "5.8.x"), new File(path, "5.8.3")));
		expected.add(new Ln(new File(path, "5.8.x-SNAPSHOT"), new File(path, "5.8.4-SNAPSHOT")));
		expected.add(new Ln(new File(path, "6.0.x"), new File(path, "6.0.3")));
		expected.add(new Ln(new File(path, "6.0.x-SNAPSHOT"), new File(path, "6.0.4-SNAPSHOT")));
		expected.add(new Ln(new File(path, "6.1.x"), new File(path, "6.1.0-RC1")));
		expected.add(new Ln(new File(path, "6.1.x-SNAPSHOT"), new File(path, "6.1.0-SNAPSHOT")));
		expected.add(new Ln(new File(path, "current"), new File(path, "6.0.3")));
		expected.add(new Ln(new File(path, "current-SNAPSHOT"), new File(path, "6.1.0-SNAPSHOT")));
		expected.add(
				new Ln(new File(path, "current-SNAPSHOT/reference"), new File(path, ".current-SNAPSHOT/reference")));

		List<Ln> actual = autoln.findLinks(new File(path));

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	void cloud() {
		String path = "src/test/resources/docs/spring-cloud";
		Autoln autoln = new Autoln();
		List<Ln> expected = new ArrayList<>();
		expected.add(new Ln(new File(path, "Hoxton.x-SNAPSHOT"), new File(path, "Hoxton.BUILD-SNAPSHOT")));
		expected.add(new Ln(new File(path, "Hoxton.x"), new File(path, "Hoxton.SR8")));
		expected.add(new Ln(new File(path, "2020.0.x"), new File(path, "2020.0.0-M4")));
		expected.add(new Ln(new File(path, "2020.0.x-SNAPSHOT"), new File(path, "2020.0.0-SNAPSHOT")));
		expected.add(new Ln(new File(path, "current"), new File(path, "Hoxton.SR8")));
		expected.add(new Ln(new File(path, "current-SNAPSHOT"), new File(path, "2020.0.0-SNAPSHOT")));

		List<Ln> actual = autoln.findLinks(new File(path));

		assertThat(actual).containsExactlyElementsOf(expected);
	}

	@Test
	void findLinksWhenEmpty() {
		Autoln autoln = new Autoln();
		assertThatIllegalArgumentException().isThrownBy(() -> autoln.findLinks(new File("src/test/resources/missing")));
	}

	@Test
	void printLinks() {
		String path = "path";
		Autoln autoln = new Autoln();
		List<Ln> links = new ArrayList<>();
		links.add(new Ln(new File(path, "1.0.x"), new File(path, "1.0.2")));
		StringWriter writer = new StringWriter();
		autoln.printLinks(new PrintWriter(writer), links);
		assertThat(writer.toString()).contains("path/1.0.x => 1.0.2");
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

			assertThat(Files.readSymbolicLink(from.toPath())).isRelative();
			assertThat(Files.readSymbolicLink(from.toPath()))
					.isEqualTo(from.toPath().getParent().relativize(to.toPath()));
		}
		finally {
			from.delete();
		}
	}

	@Test
	void deleteRecursivelySymlinkToMissing(@TempDir Path tempDir) throws IOException {
		Path link = tempDir.resolve("1.1.x");
		Files.createSymbolicLink(link, tempDir.resolve("missing"));
		assertThat(Autoln.deleteRecursively(link)).isTrue();
		assertThat(link).doesNotExist();
	}

	@Test
	void deleteRecursivelyDoesNotDeleteContentOfSymlinkTo(@TempDir Path tempDir) throws IOException {
		Path link = tempDir.resolve("1.1.x");
		Path to = tempDir.resolve("to");
		to.toFile().mkdirs();
		Path file = to.resolve("file");
		file.toFile().createNewFile();
		Files.createSymbolicLink(link, to);
		assertThat(Autoln.deleteRecursively(link)).isTrue();
		assertThat(to).exists();
		assertThat(file).exists();
	}

	@Test
	void deleteRecursivelyDoesNotExist(@TempDir Path tempDir) throws IOException {
		Path missing = tempDir.resolve("missing");
		assertThat(missing).doesNotExist();
		assertThat(Autoln.deleteRecursively(missing)).isFalse();
		assertThat(missing).doesNotExist();
	}

	private void printExpectedLinks(List<Ln> links) {
		for (Ln ln : links) {
			System.out.println("expected.add(new Ln(new File(path, \"" + ln.getFrom().toFile().getName()
					+ "\"), new File(path, \"" + ln.getTo().toFile().getName() + "\")));");
		}
	}

}
