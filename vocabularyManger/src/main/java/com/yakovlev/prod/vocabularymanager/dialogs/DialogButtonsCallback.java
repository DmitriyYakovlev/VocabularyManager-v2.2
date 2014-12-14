package com.yakovlev.prod.vocabularymanager.dialogs;

import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;

public interface DialogButtonsCallback {

	public void possitiveButtonPress(WordTable wTable);

	public void negativeButtonPress(String key, String value);

}
