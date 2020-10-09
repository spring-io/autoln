package io.spring.autoln;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutolnCommandTest {
	private final int SUCCESS_STATUS = 0;

	@Test
	void noargumentsThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute();

		assertThat(status).isNotEqualTo(SUCCESS_STATUS);
		assertThat(test.getStdErr()).isNotEmpty();
		assertThat(test.getStdOut()).isEmpty();
	}

	@Test
	void printWhenNoArgumentThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute("print");

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
		assertThat(test.getStdErr()).isEqualTo("is not a Directory");
		assertThat(test.getStdOut()).isEmpty();
	}
}