package ru.IJackDaniel.InfS.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserFileWriter {

    private static final String DATA_FILE_PATH = "data/data.txt";

    public static void writeData(List<String> data) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(DATA_FILE_PATH))) {

            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}