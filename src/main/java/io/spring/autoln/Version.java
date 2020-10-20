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

import java.util.Objects;
import java.util.regex.Pattern;

final class Version implements Comparable<Version> {

	private static final Pattern VERSION_PATTERN = Pattern.compile(
			"^(?<major>\\d+)\\.(?<minor>\\d+)\\.\\d+(?:(?:\\.BUILD|\\.CI)?-SNAPSHOT|[\\.\\-]M\\d+|[\\.\\-]RC\\d+|\\.RELEASE)?");

	private static final Pattern LEGACY_RELEASE_TRAIN_VERSION_PATTERN = Pattern.compile(
			"^(?<name>[A-Z][a-zA-Z]+)(?:(?:\\.BUILD|\\.CI)?-SNAPSHOT|[\\.\\-]M\\d+|[\\.\\-]RC\\d+|\\.RELEASE|[\\.\\-]SR\\d+)");

	private static final Pattern ALL_DIGITS_PATTERN = Pattern.compile("^\\d+$");

	private final String version;

	private Version(String version) {
		if (!isValid(version)) {
			throw new IllegalArgumentException("version '" + version + "' is invalid");
		}
		this.version = version;
	}

	boolean isGreaterThan(Version version) {
		boolean isLegacy = isLegacyReleaseTrain(this.version);
		boolean isThatLegacy = isLegacyReleaseTrain(version.version);
		if (isLegacy && !isThatLegacy) {
			return false;
		}
		if (!isLegacy && isThatLegacy) {
			return true;
		}
		String[] otherParts = version.getVersion().split("[\\.\\-]");
		String[] parts = getVersion().split("[\\.\\-]");
		for (int i = 0; i < parts.length; i++) {
			if (i > otherParts.length - 1) {
				return false;
			}
			if (i == parts.length - 1 && !parts[i].equals(otherParts[i])) {
				if ("RELEASE".equals(parts[i])) {
					return true;
				}
				if ("RELEASE".equals(otherParts[i])) {
					return false;
				}
			}
			int match = numericalAwareCompare(parts[i], otherParts[i]);
			if (match != 0) {
				return match > 0;
			}
		}
		return otherParts.length > parts.length;
	}

	private int numericalAwareCompare(String lhs, String rhs) {
		if (isDigits(lhs) && isDigits(rhs)) {
			int l = Integer.parseInt(lhs);
			int r = Integer.parseInt(rhs);
			return l - r;
		}
		return lhs.compareTo(rhs);
	}

	private boolean isDigits(String v) {
		return ALL_DIGITS_PATTERN.matcher(v).matches();
	}

	@Override
	public int compareTo(Version rhs) {
		Version lhs = this;
		if (lhs.isGreaterThan(rhs)) {
			return 1;
		}
		else if (rhs.isGreaterThan(lhs)) {
			return -1;
		}
		return 0;
	}

	String getVersion() {
		return this.version;
	}

	String getGeneration() {
		return this.version.replaceFirst("^(\\d+\\.\\d+|[A-Z][a-zA-Z]+)\\..*?(\\-SNAPSHOT)?$", "$1.x$2");
	}

	boolean isSnapshot() {
		return this.version.endsWith("-SNAPSHOT");
	}

	boolean isReleaseCandidate() {
		return this.version.matches(".*?[\\.\\-]RC\\d+$");
	}

	boolean isMilestone() {
		return this.version.matches(".*?[\\.\\-]M\\d+$");
	}

	boolean isRelease() {
		return !isSnapshot() && !isMilestone() && !isReleaseCandidate();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Version version1 = (Version) o;
		return Objects.equals(this.version, version1.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.version);
	}

	@Override
	public String toString() {
		return this.version;
	}

	static boolean isValid(String version) {
		if (version == null) {
			return false;
		}
		if (version.isEmpty()) {
			return false;
		}
		return VERSION_PATTERN.matcher(version).matches() || isLegacyReleaseTrain(version);
	}

	private static boolean isLegacyReleaseTrain(String version) {
		return LEGACY_RELEASE_TRAIN_VERSION_PATTERN.matcher(version).matches();
	}

	static Version parse(String version) {
		return new Version(version);
	}

}
