package com.makkajai.ios2android;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Sample code that finds files that match the specified glob pattern.
 * For more information on what constitutes a glob pattern, see
 * https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob
 * <p>
 * The file or directories that match the pattern are printed to
 * standard out.  The number of matches is also printed.
 * <p>
 * When executing this application, you must put the glob pattern
 * in quotes, so the shell will not expand any wild cards:
 * java Find . -name "*.java"
 */
public class Find {

    public static class Finder
            extends SimpleFileVisitor<Path> {

        private PathMatcher matcher;
        private final String pathPrefix;
        private final String replacePrefixWith;
        private int numMatches = 0;
        private final StringBuffer buffer = new StringBuffer();

        Finder(String pathPrefix, String replacePrefixWith) {
            this.pathPrefix = pathPrefix;
            this.replacePrefixWith = replacePrefixWith;
        }

        // Compares the glob pattern against
        // the file or directory name.
        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                numMatches++;
                String filePath = file.toString();
                buffer
                        .append(filePath.replaceAll(pathPrefix, replacePrefixWith))
                        .append(" ");
            }
        }

        // Prints the total number of
        // matches to standard out.
        void done() {
            System.out.println(buffer.toString() + " \\ ");
        }

        // Invoke the pattern matching
        // method on each file.
        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }

        // Invoke the pattern matching
        // method on each directory.
        @Override
        public FileVisitResult preVisitDirectory(Path dir,
                                                 BasicFileAttributes attrs) {
            find(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file,
                                               IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }

        public void walkFileTree(String fileNameToProcess) throws IOException {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + fileNameToProcess);
            Files.walkFileTree(Paths.get(pathPrefix), this);
        }
    }
}
