package com.yakovlev.prod.vocabularymanager.file_explorer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;

public class FileWorkHelper {

	public static List<WordTable> parseVocabFile(String tempVocabFile, int vocabId) {

		File file = new File(tempVocabFile);
		String reqKeyVal = "(.+)[-|=](.+)";
		Pattern pattern = Pattern.compile(reqKeyVal);
		List<WordTable> wordList = new ArrayList<WordTable>();

		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufRead = new BufferedReader(reader);

			String line;
			while ((line = bufRead.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					String key = matcher.group(1);
					String val = matcher.group(2);

					WordTable word = new WordTable(key, val, vocabId);
					wordList.add(word);
				}
			}
			reader.close();
			return wordList;

		} catch (IOException e) {
			e.printStackTrace();
			return wordList;
		}
	}

	public static boolean isExtentionFalse(String path, String extentionValue) {

		String extention = getFileExtension(path);

		if (path.contains("."))
			extention = getFileExtension(path);
		else
			return true;

		if (extention.equals(extentionValue))
			return false;
		return true;
	}

	public static String getFileExtension(String path) {
		return path.substring((path.lastIndexOf(".") + 1), path.length());
	}
	
	
}
