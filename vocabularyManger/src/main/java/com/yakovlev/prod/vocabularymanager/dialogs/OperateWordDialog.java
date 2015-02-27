package com.yakovlev.prod.vocabularymanager.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yakovlev.prod.vocabularymanger.R;

public class OperateWordDialog extends Dialog implements View.OnClickListener {

    private OperateWordDialogCallback callback;
    private ImageButton btnDelete, btnEdit, btnHardWordsFirstRank, btnHardWordsSecondRank, btnLearnedWordsStatus;
    private TextView tvValue, tvKey;
    private int wordId;

    public OperateWordDialog(Context context, OperateWordDialogCallback callback, TextView tvKey, final TextView tvValue, int wordId ) {
        super(context);
        this.callback = callback;
        this.tvKey = tvKey;
        this.tvValue = tvValue;
        this.wordId = wordId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_operate_word);
        findViews();
    }

    private void findViews() {
        btnDelete = (ImageButton) findViewById(R.id.btnDeleteWord);
        btnEdit = (ImageButton) findViewById(R.id.btnEditWord);
        btnHardWordsFirstRank = (ImageButton) findViewById(R.id.btnHardWordsFirstRank);
        btnHardWordsSecondRank = (ImageButton) findViewById(R.id.btnHardWordsSecondRank);
        btnLearnedWordsStatus = (ImageButton) findViewById(R.id.btnLearnedWord);

        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnHardWordsFirstRank.setOnClickListener(this);
        btnHardWordsSecondRank.setOnClickListener(this);
        btnLearnedWordsStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDeleteWord:
                callback.onDeleteWordButtonPressed(wordId);
                break;
            case R.id.btnEditWord:
                callback.onEditWordButtonPressed(wordId);
                break;
            case R.id.btnHardWordsFirstRank:
                callback.onSetWordAsHardFirstRank(tvKey, tvValue, wordId);
                break;
            case R.id.btnHardWordsSecondRank:
                callback.onSetWordAsHardSecondRank(tvKey, tvValue, wordId);
                break;
            case R.id.btnLearnedWord:
                callback.onSetWordAsLearned(tvKey, tvValue, wordId);
                break;
        }
        dismiss();

    }

}