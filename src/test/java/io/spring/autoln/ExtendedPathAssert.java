package io.spring.autoln;

import org.assertj.core.api.AbstractPathAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExtendedPathAssert extends AbstractPathAssert<ExtendedPathAssert> {
	public ExtendedPathAssert(Path actual) {
		super(actual, ExtendedPathAssert.class);
	}

	public static ExtendedPathAssert assertThatPath(Path path) {
		return new ExtendedPathAssert(path);
	}

	public ExtendedPathAssert isRelativeSymlinkTo(String path) throws IOException {
		isSymbolicLink();
		assertThatPath(Files.readSymbolicLink(this.actual)).isRelative().isEqualTo(this.actual.getParent().relativize(this.actual.getParent().resolve(path)));
		return this;
	}
}
