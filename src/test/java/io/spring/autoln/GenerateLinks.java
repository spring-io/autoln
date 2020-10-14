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
//		mkDirsForProject("spring-framework");
//		mkDirsForProject("spring-boot");
		mkDirsForProject("spring-cloud");
//		mkDirsForProjectUrl("spring-security", "https://docs.spring.io/spring-security/site/docs/");
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
		return getDirsFromUrl(url).stream()
				.map(d -> new File(d).getName())
				.filter(Version::isValid)
				.map(Version::parse)
				.sorted()
				.collect(Collectors.toList());
	}

	static List<String> getDirsFromUrl(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		List<String> result = new ArrayList<>();
		Elements links = doc.select("body a");
		for(Element l : links) {
			String href = l.attr("href");
			if (href.equals(l.text())) {
				result.add(href);
			}
		}
		Collections.sort(result);
		return result;
	}
}
