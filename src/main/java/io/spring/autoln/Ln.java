package io.spring.autoln;

import java.io.File;
import java.util.Objects;

public class Ln {
	private final File from;

	private final File to;

	public Ln(File from, File to) {
		this.from = from;
		this.to = to;
	}

	public File getFrom() {
		return from;
	}

	public File getTo() {
		return to;
	}

	@Override
	public String toString() {
		return from + " => " + to;
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
