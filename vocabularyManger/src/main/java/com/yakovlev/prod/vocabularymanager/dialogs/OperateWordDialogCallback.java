package com.yakovlev.prod.vocabularymanager.dialogs;

import android.widget.TextView;

public interface OperateWordDialogCallback {

    public void onDeleteWordButtonPressed(int wordId);

    public void onEditWordButtonPressed(int wordId);

    public void onSetWordAsHardFirstRank(TextView tvKey, TextView tvValue, int wordId);

    public void onSetWordAsHardSecondRank(TextView tvKey, TextView tvValue, int wordId);

    public void onSetWordAsLearned(TextView tvKey, TextView tvValue, int wordId);
}
