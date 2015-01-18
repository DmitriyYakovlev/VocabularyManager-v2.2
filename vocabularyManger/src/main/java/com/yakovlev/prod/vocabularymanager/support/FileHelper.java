package com.yakovlev.prod.vocabularymanager.support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {

    public static void saveStringInFile(String text, String path) {

        File fileForSaving = new File(path);
        if (!fileForSaving.exists()) {
            try {
                fileForSaving.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(fileForSaving, true));
            buf.append(text);
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String loadStringFromFile(String path) {

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(new File(path)));
            while ((line = in.readLine()) != null)
                stringBuilder.append(line);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }

}
