package com.yakovlev.prod.vocabularymanager.ormlite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;


public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	
	private static final Class<?>[] clases = new Class[]{Vocabulary.class, WordTable.class}; 
			
	public static void main(String[] args) throws SQLException, IOException {
//		writeConfigFile("ormlite_configs.txt",clases);
		writeConfigFile(new File("vocabularyManger/src/main/res/raw/ormlite_config.txt"),clases);
	}
}
