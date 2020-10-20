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