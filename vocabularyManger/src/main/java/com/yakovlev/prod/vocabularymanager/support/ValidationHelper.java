package com.yakovlev.prod.vocabularymanager.support;

import com.yakovlev.prod.vocabularymanager.exceptions.ExceptionHelper;
import com.yakovlev.prod.vocabularymanger.R;

import android.widget.EditText;

public class ValidationHelper {

	public static boolean isStringEmpy(String str) {
		if (str == null || str.isEmpty())
			return true;
		return false;
	}

	public static void isEditTextNotEmpy(EditText edt, String message) throws ExceptionHelper {
		String editTextValue = edt.getText().toString();
		if (isStringEmpy(editTextValue))
			throw new ExceptionHelper(message);
	}

	public static int getIntegerFromEditText(EditText edt) {
		return Integer.valueOf(edt.getText().toString());
	}

	public static float getFloatFromEditText(EditText edt) {
		return Float.valueOf(edt.getText().toString());
	}

}
