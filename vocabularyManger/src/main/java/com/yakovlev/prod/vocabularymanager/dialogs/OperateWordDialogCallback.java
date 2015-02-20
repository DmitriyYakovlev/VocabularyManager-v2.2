package com.yakovlev.prod.vocabularymanager.dialogs;

import android.widget.TextView;

public interface OperateWordDialogCallback {

    public void onDeleteWordButtonPressed(int wordId);

    public void onEditWordButtonPressed(int wordId);

    public void onChangeStatusButtonPressed(TextView tvKey, TextView tvValue, int wordId);
}
