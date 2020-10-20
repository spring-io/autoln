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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.assertj.core.api.AbstractPathAssert;

public class ExtendedPathAssert extends AbstractPathAssert<ExtendedPathAssert> {

	public ExtendedPathAssert(Path actual) {
		super(actual, ExtendedPathAssert.class);
	}

	public static ExtendedPathAssert assertThatPath(Path path) {
		return new ExtendedPathAssert(path);
	}

	public ExtendedPathAssert isRelativeSymlinkTo(String path) throws IOException {
		isSymbolicLink();
		assertThatPath(Files.readSymbolicLink(this.actual)).isRelative()
				.isEqualTo(this.actual.getParent().relativize(this.actual.getParent().resolve(path)));
		return this;
	}

}
