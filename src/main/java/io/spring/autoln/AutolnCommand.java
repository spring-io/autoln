package io.spring.autoln;

import picocli.CommandLine;

@CommandLine.Command(name = "autoln", mixinStandardHelpOptions = true,
		subcommands = { AutolnPrintCommand.class, AutolnCreateCommand.class })
public class AutolnCommand {

	public static void main(String... args) {
		System.exit(new CommandLine(AutolnCommand.class).execute(args));
	}

}
