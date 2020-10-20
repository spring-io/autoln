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
import java.nio.file.Path;
import java.util.Objects;

public class Ln {

	private final Path from;

	private final Path to;

	public Ln(File from, File to) {
		this(from.toPath(), to.toPath());
	}

	public Ln(Path from, Path to) {
		this.from = from;
		this.to = to;
	}

	public Path getFrom() {
		return this.from;
	}

	public Path getTo() {
		return this.to;
	}

	public Path getRelativeTo() {
		return getFrom().getParent().relativize(getTo());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Ln ln = (Ln) o;
		return Objects.equals(this.from, ln.from) && Objects.equals(this.to, ln.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.from, this.to);
	}

	@Override
	public String toString() {
		return this.from + " => " + getRelativeTo();
	}

}
