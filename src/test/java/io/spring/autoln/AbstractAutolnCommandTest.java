package io.spring.autoln;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractAutolnCommandTest {

	@Test
	void findAutoLnScanParents() throws IOException  {
		List<File> parents = AbstractAutolnCommand.scanForProjects(new File("src/test/resources/"), null);
		assertThat(parents).containsExactly(new File("src/test/resources/docs/spring-framework"));
	}
}