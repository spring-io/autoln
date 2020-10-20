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

import java.io.PrintWriter;
import java.io.StringWriter;

import picocli.CommandLine;

public final class TestCommandLine {

	private final StringWriter stdout = new StringWriter();

	private final StringWriter stderr = new StringWriter();

	private final CommandLine commandLine;

	private TestCommandLine(CommandLine commandLine) {
		this.commandLine = commandLine;
		this.commandLine.setOut(new PrintWriter(this.stdout));
		this.commandLine.setErr(new PrintWriter(this.stderr));
	}

	public CommandLine getCommandLine() {
		return this.commandLine;
	}

	public String getStdOut() {
		return this.stdout.toString();
	}

	public String getStdErr() {
		return this.stderr.toString();
	}

	public static TestCommandLine create(Object application) {
		CommandLine commandLine = new CommandLine(application);
		return new TestCommandLine(commandLine);
	}

}
