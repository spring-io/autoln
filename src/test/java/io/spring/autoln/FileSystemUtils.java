package io.spring.autoln;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

public class FileSystemUtils {

	/**
	 * Recursively copy the contents of the {@code src} file/directory
	 * to the {@code dest} file/directory.
	 * @param src the source directory
	 * @param dest the destination directory
	 * @throws IOException in the case of I/O errors
	 * @since 5.0
	 */
	public static void copyRecursively(Path src, Path dest) throws IOException {
		if (src == null) {
			throw new IllegalArgumentException("Source Path must not be null");
		}
		if (dest == null) {
			throw new IllegalArgumentException("Destination Path must not be null");
		}
		BasicFileAttributes srcAttr = Files.readAttributes(src, BasicFileAttributes.class);

		if (srcAttr.isDirectory()) {
			Files.walkFileTree(src, EnumSet.of(FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					Files.createDirectories(dest.resolve(src.relativize(dir)));
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.copy(file, dest.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
					return FileVisitResult.CONTINUE;
				}
			});
		}
		else if (srcAttr.isRegularFile()) {
			Files.copy(src, dest);
		}
		else {
			throw new IllegalArgumentException("Source File must denote a directory or file");
		}
	}
}
