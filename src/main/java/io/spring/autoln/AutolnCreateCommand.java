package io.spring.autoln;

import picocli.CommandLine;

import java.io.File;
import java.util.List;

@CommandLine.Command(name = "create")
class AutolnCreateCommand extends AbstractAutolnCommand implements Runnable {
	@Override
	public void run() {
		for(File project : getProjects()) {
			List<Ln> links = this.autoln.findLinks(project);
			stdout().println("");
			stdout().println("Creating Symlinks for project at '" + project + "'");
			stdout().println("");
			this.autoln.createLinks(links);
		}
	}
}
