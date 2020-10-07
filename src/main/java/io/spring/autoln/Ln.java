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
		return from;
	}

	public Path getTo() {
		return to;
	}

	public Path getRelativeTo() {
		return getFrom().getParent().relativize(getTo());
	}

	@Override
	public String toString() {
		return from + " => " + getRelativeTo();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ln ln = (Ln) o;
		return Objects.equals(from, ln.from) &&
				Objects.equals(to, ln.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
