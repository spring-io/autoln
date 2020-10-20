package io.spring.autoln;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.spring.autoln.ExtendedPathAssert.assertThatPath;
import static org.assertj.core.api.Assertions.assertThat;

class AutolnCreateCommandTest {

	private final int SUCCESS_STATUS = 0;

	@Test
	void createWhenNoArgumentThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("create");

		assertThat(status).isNotEqualTo(this.SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("Missing required argument");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void createWhenMaxDepthThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("create", "--maxdepth=1");

		assertThat(status).isNotEqualTo(this.SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("Missing required argument");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void createWhenProjectDirAndScanDirThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("create", "--scan-dir=src/test/resources/docs/spring-boot",
				"--project-dir=src/test/resources/docs/spring-boot");

		assertThat(status).isNotEqualTo(this.SUCCESS_STATUS);
		assertThat(test.getStdErr()).isNotEmpty();
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void createWhenProjectDirAndMaxDepthThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("create", "--project-dir=src/test/resources/docs/spring-boot",
				"--maxdepth=1");

		assertThat(status).isNotEqualTo(this.SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("Missing required argument");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void createWhenProjectDirNotFoundThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--project-dir=/not/found");

		assertThat(status).isNotEqualTo(this.SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("Directory not found /not/found");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void createWhenProjectDirNotDirThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("create",
				"--project-dir=src/test/resources/docs/spring-framework/.autoln-scan");

		assertThat(status).isNotEqualTo(this.SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("is not a Directory");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void createWhenValidSuccess(@TempDir Path tempDir) throws IOException {
		Path p = Paths.get("src/test/resources");
		Path projectDir = tempDir.resolve(p.getFileName());
		FileSystemUtils.copyRecursively(p, projectDir);
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());
		Path springFrameworkProject = projectDir.resolve("docs/spring-framework");

		int status = test.getCommandLine().execute("create",
				"--project-dir=" + springFrameworkProject.toFile().getAbsolutePath());

		assertThat(status).isEqualTo(this.SUCCESS_STATUS);

		assertThatPath(springFrameworkProject.resolve("1.0.x")).isRelativeSymlinkTo("1.0.2");
		assertThatPath(springFrameworkProject.resolve("1.1.x")).isRelativeSymlinkTo("1.1.5");
		assertThatPath(springFrameworkProject.resolve("1.2.x")).isRelativeSymlinkTo("1.2.9");
		assertThatPath(springFrameworkProject.resolve("2.0.x")).isRelativeSymlinkTo("2.0.8");
		assertThatPath(springFrameworkProject.resolve("2.5.x")).isRelativeSymlinkTo("2.5.6");
		assertThatPath(springFrameworkProject.resolve("3.0.x")).isRelativeSymlinkTo("3.0.7.RELEASE");
		assertThatPath(springFrameworkProject.resolve("3.1.x")).isRelativeSymlinkTo("3.1.4.RELEASE");
		assertThatPath(springFrameworkProject.resolve("3.2.x")).isRelativeSymlinkTo("3.2.18.RELEASE");
		assertThatPath(springFrameworkProject.resolve("3.2.x-SNAPSHOT")).isRelativeSymlinkTo("3.2.19.BUILD-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("3.3.x-SNAPSHOT")).isRelativeSymlinkTo("3.3.0.BUILD-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("4.0.x")).isRelativeSymlinkTo("4.0.9.RELEASE");
		assertThatPath(springFrameworkProject.resolve("4.1.x")).isRelativeSymlinkTo("4.1.9.RELEASE");
		assertThatPath(springFrameworkProject.resolve("4.1.x-SNAPSHOT")).isRelativeSymlinkTo("4.1.10.BUILD-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("4.2.x")).isRelativeSymlinkTo("4.2.9.RELEASE");
		assertThatPath(springFrameworkProject.resolve("4.2.x-SNAPSHOT")).isRelativeSymlinkTo("4.2.10.BUILD-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("4.3.x")).isRelativeSymlinkTo("4.3.29.RELEASE");
		assertThatPath(springFrameworkProject.resolve("4.3.x-SNAPSHOT")).isRelativeSymlinkTo("4.3.30.BUILD-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("5.0.x")).isRelativeSymlinkTo("5.0.19.RELEASE");
		assertThatPath(springFrameworkProject.resolve("5.0.x-SNAPSHOT")).isRelativeSymlinkTo("5.0.20.BUILD-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("5.1.x")).isRelativeSymlinkTo("5.1.18.RELEASE");
		assertThatPath(springFrameworkProject.resolve("5.1.x-SNAPSHOT")).isRelativeSymlinkTo("5.1.19.BUILD-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("5.2.x")).isRelativeSymlinkTo("5.2.9.RELEASE");
		assertThatPath(springFrameworkProject.resolve("5.2.x-SNAPSHOT")).isRelativeSymlinkTo("5.2.10.BUILD-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("5.3.x")).isRelativeSymlinkTo("5.3.0-RC1");
		assertThatPath(springFrameworkProject.resolve("5.3.x-SNAPSHOT")).isRelativeSymlinkTo("5.3.0-SNAPSHOT");
		assertThatPath(springFrameworkProject.resolve("current")).isRelativeSymlinkTo("5.2.9.RELEASE");
		assertThatPath(springFrameworkProject.resolve("current-SNAPSHOT")).isRelativeSymlinkTo("5.3.0-SNAPSHOT");
	}

}