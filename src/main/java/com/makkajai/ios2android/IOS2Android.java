package com.makkajai.ios2android;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOS2Android {

    public static void main(String[] args) throws IOException {
        String inputFile = "/Users/batman/playground/projarea/monster-math-cross-platform/monster-math-2/proj.ios_mac/monster-math-2.xcodeproj/project.pbxproj";
        String androidMKFile = "/Users/batman/playground/projarea/monster-math-cross-platform/monster-math-2/proj.android-studio/app/jni/Android.mk";
        String pathToStartLookingForFiles = "/Users/batman/playground/projarea/monster-math-cross-platform/monster-math-2/Classes";
        String valueToReplacePathWith = "../../../Classes";

        Scanner iOSProjectFileScanner = new Scanner(new File(inputFile));
        String androidMKFileContents = new String(Files.readAllBytes(Paths.get(androidMKFile)));

        String pattern = "/\\* (.*\\.cpp) in Sources \\*/";
        Pattern r = Pattern.compile(pattern);

        Set<String> alreadyProcessedFiles = new HashSet<String>();

        while (iOSProjectFileScanner.hasNextLine()) {
            String line = iOSProjectFileScanner.nextLine();
            Matcher m = r.matcher(line);
            if (!m.find( )) {
                continue;
            }
            String fileNameToProcess = m.group(1);
            if(alreadyProcessedFiles.contains(fileNameToProcess))
                continue;
            alreadyProcessedFiles.add(fileNameToProcess);
            if(!androidMKFileContents.contains(fileNameToProcess)) {
                Find.Finder finder = new Find.Finder(fileNameToProcess, pathToStartLookingForFiles, valueToReplacePathWith);
                Files.walkFileTree(Paths.get(pathToStartLookingForFiles), finder);
                finder.done();
            }

        }
    }
}
