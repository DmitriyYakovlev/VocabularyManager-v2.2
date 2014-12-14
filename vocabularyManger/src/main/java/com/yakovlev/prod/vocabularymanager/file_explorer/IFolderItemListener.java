package com.yakovlev.prod.vocabularymanager.file_explorer;

import java.io.File;

public interface IFolderItemListener {
	
	void OnCannotFileRead(File file);

	void OnFileClicked(File file);

}
