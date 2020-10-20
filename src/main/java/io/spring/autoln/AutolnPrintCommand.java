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

import java.io.File;
import java.util.List;

import picocli.CommandLine;

@CommandLine.Command(name = "print")
class AutolnPrintCommand extends AbstractAutolnCommand implements Runnable {

	@Override
	public void run() {
		for (File project : getProjects()) {
			List<Ln> links = this.autoln.findLinks(project);
			stdout().println("");
			stdout().println("Symlinks for project at '" + project + "'");
			stdout().println("");
			this.autoln.printLinks(stdout(), links);
		}
	}

}
