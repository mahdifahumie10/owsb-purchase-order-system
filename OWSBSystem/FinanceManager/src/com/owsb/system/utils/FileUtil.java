package com.owsb.system.utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class FileUtil {

    // Base path set to src folder to match your project structure
    private static final String BASE_PATH = "src/";

    // Read all lines from a text file inside src/
    public static List<String> readLines(String fileName) {
        try {
            Path path = Paths.get(BASE_PATH, fileName);
            System.out.println("Reading from: " + path.toAbsolutePath());
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Append a single line to a text file, create file if it does not exist
    public static void appendLine(String fileName, String line) {
        try {
            Path path = Paths.get(BASE_PATH, fileName);
            System.out.println("Writing to: " + path.toAbsolutePath());
            Files.write(path, (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + fileName);
            e.printStackTrace();
        }
    }

    // Return today's date as "YYYY-MM-DD"
    public static String today() {
        return LocalDate.now().toString();
    }
}
