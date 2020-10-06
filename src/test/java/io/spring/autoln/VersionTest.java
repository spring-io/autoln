package io.spring.autoln;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

	@ParameterizedTest
	// @formatter:off
	@CsvSource(value = {
			"1.0.0.BUILD-SNAPSHOT,true",
			"1.0.0-SNAPSHOT,true",
			"1.0.0.M1,false",
			"1.0.0-M1,false",
			"1.0.0.RC1,false",
			"1.0.0-RC1,false",
			"1.0.0.RELEASE,false",
			"1.0.0,false"
	})
		// @formatter:on
	void isSnapshotWhenVersionThenIsEqualToExpected(String version, boolean expected) {
		assertThat(Version.parse(version).isSnapshot()).isEqualTo(expected);
	}

	@ParameterizedTest
	// @formatter:off
	@CsvSource(value = {
			"1.0.0.BUILD-SNAPSHOT,false",
			"1.0.0-SNAPSHOT,false",
			"1.0.0.M1,true",
			"1.0.0-M1,true",
			"1.0.0.RC1,false",
			"1.0.0-RC1,false",
			"1.0.0.RELEASE,false",
			"1.0.0,false"
	})
		// @formatter:on
	void isMilestoneWhenVersionThenIsEqualToExpected(String version, boolean expected) {
		assertThat(Version.parse(version).isMilestone()).isEqualTo(expected);
	}

	@ParameterizedTest
	// @formatter:off
	@CsvSource(value = {
			"1.0.0.BUILD-SNAPSHOT,false",
			"1.0.0-SNAPSHOT,false",
			"1.0.0.M1,false",
			"1.0.0-M1,false",
			"1.0.0.RC1,true",
			"1.0.0-RC1,true",
			"1.0.0.RELEASE,false",
			"1.0.0,false"
	})
		// @formatter:on
	void isReleaseCandidateWhenVersionThenIsEqualToExpected(String version, boolean expected) {
		assertThat(Version.parse(version).isReleaseCandidate()).isEqualTo(expected);
	}

	@ParameterizedTest
	// @formatter:off
	@CsvSource(value = {
			"1.0.0.BUILD-SNAPSHOT,false",
			"1.0.0-SNAPSHOT,false",
			"1.0.0.M1,false",
			"1.0.0-M1,false",
			"1.0.0.RC1,false",
			"1.0.0-RC1,false",
			"1.0.0.RELEASE,true",
			"1.0.0,true"
	})
		// @formatter:on
	void isReleaseWhenVersionThenIsEqualToExpected(String version, boolean expected) {
		assertThat(Version.parse(version).isRelease()).isEqualTo(expected);
	}

	@ParameterizedTest
	// @formatter:off
	@CsvSource(value = {
			"1.0.0.BUILD-SNAPSHOT,1.0.x-SNAPSHOT",
			"1.0.0-SNAPSHOT,1.0.x-SNAPSHOT",
			"1.0.0.M1,1.0.x",
			"1.0.0-M1,1.0.x",
			"1.0.0.RC1,1.0.x",
			"1.0.0-RC1,1.0.x",
			"1.0.0.RELEASE,1.0.x",
			"1.0.0,1.0.x"
	})
		// @formatter:on
	void getGeneration(String version, String expectedGeneration) {
		assertThat(Version.parse(version).getGeneration()).isEqualTo(expectedGeneration);
	}

//	@ParameterizedTest
//	// @formatter:off
//	@CsvSource(value = {
//			"1.0.1.BUILD-SNAPSHOT,1.0.0.BUILD-SNAPSHOT",
//			"1.1.0.BUILD-SNAPSHOT,1.0.0.BUILD-SNAPSHOT",
//			"1.1.1.BUILD-SNAPSHOT,1.0.0.BUILD-SNAPSHOT",
//			"1.2.0.BUILD-SNAPSHOT,1.0.0.BUILD-SNAPSHOT",
//			"2.0.0.BUILD-SNAPSHOT,1.0.0.BUILD-SNAPSHOT",
//
//			"1.0.0-SNAPSHOT,1.0.0.BUILD-SNAPSHOT",
//			"1.0.1-SNAPSHOT,1.0.0-SNAPSHOT",
//			"1.1.0-SNAPSHOT,1.0.0-SNAPSHOT",
//			"1.1.1-SNAPSHOT,1.0.0-SNAPSHOT",
//			"1.2.0-SNAPSHOT,1.0.0-SNAPSHOT",
//			"2.0.0-SNAPSHOT,1.0.0-SNAPSHOT",
//
//			"1.0.0.M1,1.0.0.BUILD-SNAPSHOT",
//			"1.0.0.M2,1.0.0.M1",
//			"1.1.0.M1,1.0.0.M2",
//			"2.0.0.M1,1.0.0.M2",
//
//			"1.0.0,1.0.0.BUILD-SNAPSHOT",
//			"1.0.0,1.0.0-SNAPSHOT",
//			"1.0.0,1.0.0-M1",
//			"1.0.0,1.0.0-M2",
//			"1.0.0,1.0.0-RC1",
//			"1.0.0,1.0.0-RC2",
//
//			"1.0.0.RELEASE,1.0.0.BUILD-SNAPSHOT",
//			"1.0.0.RELEASE,1.0.0-SNAPSHOT",
//			"1.0.0.RELEASE,1.0.0-M1",
//			"1.0.0.RELEASE,1.0.0-M2",
//			"1.0.0.RELEASE,1.0.0-RC1",
//			"1.0.0.RELEASE,1.0.0-RC2",
//	})
//	// @formatter:on
//	void isGreaterThanBoot(String greater, String smaller) {
//		assertThat(Version.parse(greater).isGreaterThan(Version.parse(smaller))).isTrue();
//		assertThat(Version.parse(smaller).isGreaterThan(Version.parse(greater))).isFalse();
//	}

	@ParameterizedTest
	@CsvSource(value = {
			"1.0.10.RELEASE,1.0.9.RELEASE"
	})
	void greaterThanWhenMoreDigits(String greater, String smaller) {
		assertThat(Version.parse(greater).isGreaterThan(Version.parse(smaller))).isTrue();
		assertThat(Version.parse(smaller).isGreaterThan(Version.parse(greater))).isFalse();
	}

	@ParameterizedTest
	@CsvSource(value = {
			"1.0.0.BUILD-SNAPSHOT,true",
			"1.0.0-SNAPSHOT,true",
			"1.0.0.M1,true",
			"1.0.0-M1,true",
			"1.0.0.RC1,true",
			"1.0.0-RC1,true",
			"1.0.0.RELEASE,true",
			"1.0.0,true",

			"1.0.x-SNAPSHOT,false",
			"1.0.x,false",
			"1.1.x-SNAPSHOT,false",
			"1.1.x,false",
			"2.0.x-SNAPSHOT,false",
			"2.0.x,false",
	})
	void isValid(String version, boolean expected) {
		assertThat(Version.isValid(version)).isEqualTo(expected);
	}
}