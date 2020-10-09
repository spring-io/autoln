package io.spring.autoln;

import picocli.CommandLine;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@CommandLine.Command(name = "print")
class AutolnPrintCommand extends AbstractAutolnCommand implements Runnable {
	@Override
	public void run() {
		for(File project : getProjects()) {
			List<Ln> links = this.autoln.findLinks(project);
			stdout().println("");
			stdout().println("Symlinks for project at '" + project + "'");
			stdout().println("");
			autoln.printLinks(stdout(), links);
		}
	}
}
