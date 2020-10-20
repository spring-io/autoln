package io.spring.autoln;

import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestCommandLine {

	private StringWriter stdout = new StringWriter();

	private StringWriter stderr = new StringWriter();

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
