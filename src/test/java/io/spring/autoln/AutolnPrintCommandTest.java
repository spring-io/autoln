package io.spring.autoln;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutolnPrintCommandTest {
	private final int SUCCESS_STATUS = 0;

	@Test
	void printWhenNoArgumentThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print");

		assertThat(status).isNotEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("Missing required argument");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void printWhenMaxDepthThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--maxdepth=1");

		assertThat(status).isNotEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("Missing required argument");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void printWhenProjectDirAndScanDirThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--scan-dir=src/test/resources/docs/spring-boot", "--project-dir=src/test/resources/docs/spring-boot");

		assertThat(status).isNotEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).isNotEmpty();
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void printWhenProjectDirAndMaxDepthThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--project-dir=src/test/resources/docs/spring-boot", "--maxdepth=1");

		assertThat(status).isNotEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("Missing required argument");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void printWhenProjectDirNotFoundThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--project-dir=/not/found");

		assertThat(status).isNotEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("Directory not found /not/found");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void printWhenProjectDirNotDirThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--project-dir=src/test/resources/docs/spring-framework/.autoln-scan");

		assertThat(status).isNotEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).contains("is not a Directory");
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void printWhenValidSuccess() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--project-dir=src/test/resources/docs/spring-framework/");

		assertThat(status).isEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).isEmpty();
		assertSpringFrameworkStdOut(test.getStdOut());
	}

	@Test
	void printWhenScanDir() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--scan-dir=src/test/resources/docs/spring-framework/");


		assertThat(status).isEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).isEmpty();
		assertSpringFrameworkStdOut(test.getStdOut());
	}

	@Test
	void printWhenScanDirAndMaxDepthFindsResults() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--scan-dir=src/test/resources/docs/", "--maxdepth=2");

		assertThat(status).isEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).isEmpty();
		assertSpringFrameworkStdOut(test.getStdOut());
	}

	@Test
	void printWhenScanDirAndMaxDepthNoResults() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print", "--scan-dir=src/test/resources/docs/", "--maxdepth=1");

		assertThat(status).isEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).isEmpty();
		assertThat(test.getStdOut()).contains("No projects contained .autoln-scan within src/test/resources/docswith maxdepth of 1");
	}

	private static void assertSpringFrameworkStdOut(String stdout) {
		// @formatter:off
		assertThat(stdout).isEqualTo(
				"\n" +
						"Symlinks for project at 'src/test/resources/docs/spring-framework'\n" +
						"\n" +
						"src/test/resources/docs/spring-framework/1.0.x => 1.0.2\n" +
						"src/test/resources/docs/spring-framework/1.1.x => 1.1.5\n" +
						"src/test/resources/docs/spring-framework/1.2.x => 1.2.9\n" +
						"src/test/resources/docs/spring-framework/2.0.x => 2.0.8\n" +
						"src/test/resources/docs/spring-framework/2.5.x => 2.5.6\n" +
						"src/test/resources/docs/spring-framework/3.0.x => 3.0.7.RELEASE\n" +
						"src/test/resources/docs/spring-framework/3.1.x => 3.1.4.RELEASE\n" +
						"src/test/resources/docs/spring-framework/3.2.x => 3.2.18.RELEASE\n" +
						"src/test/resources/docs/spring-framework/3.2.x-SNAPSHOT => 3.2.19.BUILD-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/3.3.x-SNAPSHOT => 3.3.0.BUILD-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/4.0.x => 4.0.9.RELEASE\n" +
						"src/test/resources/docs/spring-framework/4.1.x => 4.1.9.RELEASE\n" +
						"src/test/resources/docs/spring-framework/4.1.x-SNAPSHOT => 4.1.10.BUILD-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/4.2.x => 4.2.9.RELEASE\n" +
						"src/test/resources/docs/spring-framework/4.2.x-SNAPSHOT => 4.2.10.BUILD-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/4.3.x => 4.3.29.RELEASE\n" +
						"src/test/resources/docs/spring-framework/4.3.x-SNAPSHOT => 4.3.30.BUILD-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/5.0.x => 5.0.19.RELEASE\n" +
						"src/test/resources/docs/spring-framework/5.0.x-SNAPSHOT => 5.0.20.BUILD-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/5.1.x => 5.1.18.RELEASE\n" +
						"src/test/resources/docs/spring-framework/5.1.x-SNAPSHOT => 5.1.19.BUILD-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/5.2.x => 5.2.9.RELEASE\n" +
						"src/test/resources/docs/spring-framework/5.2.x-SNAPSHOT => 5.2.10.BUILD-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/5.3.x => 5.3.0-RC1\n" +
						"src/test/resources/docs/spring-framework/5.3.x-SNAPSHOT => 5.3.0-SNAPSHOT\n" +
						"src/test/resources/docs/spring-framework/current => 5.2.9.RELEASE\n" +
						"src/test/resources/docs/spring-framework/current-SNAPSHOT => 5.3.0-SNAPSHOT\n");
		// @formatter:on
	}
}