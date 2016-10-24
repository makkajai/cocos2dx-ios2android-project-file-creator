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

        if(args.length != 2) {
            System.out.println("Usage: java com.makkajai.ios2android.IOS2Android <root directory of cocos2dx project> <Project Name>");
            System.out.println("Example: java com.makkajai.ios2android.IOS2Android \"/Users/batman/demo-project\" \"monster-math-2\"");
            return;
        }

        String inputFile = args[0] + "/proj.ios_mac/" + args[1] + ".xcodeproj/project.pbxproj";
        String androidMKFile = args[0] + "/proj.android-studio/app/jni/Android.mk";
        String pathToStartLookingForFiles = args[0] + "/Classes";
        String valueToReplacePathWith = "../../../Classes";

        Scanner iOSProjectFileScanner = new Scanner(new File(inputFile));
        String androidMKFileContents = new String(Files.readAllBytes(Paths.get(androidMKFile)));

        String pattern = "/\\* (.*\\.cpp) in Sources \\*/";
        Pattern r = Pattern.compile(pattern);

        Set<String> alreadyProcessedFiles = new HashSet<String>();

        Find.Finder finder = new Find.Finder(pathToStartLookingForFiles, valueToReplacePathWith);
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
                finder.walkFileTree(fileNameToProcess);
            }
        }
        finder.done();
    }
}
