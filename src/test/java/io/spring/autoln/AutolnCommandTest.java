package io.spring.autoln;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutolnCommandTest {

	private final int SUCCESS_STATUS = 0;

	@Test
	void noargumentsThenError() {
		TestCommandLine test = TestCommandLine.create(new AutolnCommand());

		int status = test.getCommandLine().execute();

		assertThat(status).isNotEqualTo(this.SUCCESS_STATUS);
		assertThat(test.getStdErr()).isNotEmpty();
		assertThat(test.getStdOut()).isEmpty();
	}

}