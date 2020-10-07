package io.spring.autoln;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScanAutoLnMainTest {

	@Test
	void findAutoLnScanParents() throws IOException  {
		List<File> parents = ScanAutoLnMain.findAutoLnScanParents(new File("src/test/resources/"), null);
		assertThat(parents).containsExactly(new File("src/test/resources/docs/spring-framework"));
	}
}