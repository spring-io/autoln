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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateLinks {

	public static void main(String[] args) throws Exception {
		// mkDirsForProject("spring-framework");
		// mkDirsForProject("spring-boot");
		mkDirsForProject("spring-cloud");
		// mkDirsForProjectUrl("spring-security",
		// "https://docs.spring.io/spring-security/site/docs/");
	}

	static void mkDirsForProject(String project) throws IOException {
		String url = "https://docs.spring.io/" + project + "/docs/";
		mkDirsForProjectUrl(project, url);
	}

	static void mkDirsForProjectUrl(String project, String url) throws IOException {
		List<String> versions = getDirsFromUrl(url);
		for (String version : versions) {
			File file = new File("src/test/resources/docs/" + project, version);
			file.mkdirs();
			new File(file, ".empty_dir").createNewFile();
		}
	}

	static List<Version> getVersionsFromUrl(String url) throws IOException {
		// @formatter:off
		return getDirsFromUrl(url).stream()
				.map(d -> new File(d).getName())
				.filter(Version::isValid)
				.map(Version::parse)
				.sorted()
				.collect(Collectors.toList());
		// @formatter:on
	}

	static List<String> getDirsFromUrl(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		List<String> result = new ArrayList<>();
		Elements links = doc.select("body a");
		for (Element l : links) {
			String href = l.attr("href");
			if (href.equals(l.text())) {
				result.add(href);
			}
		}
		Collections.sort(result);
		return result;
	}

}
