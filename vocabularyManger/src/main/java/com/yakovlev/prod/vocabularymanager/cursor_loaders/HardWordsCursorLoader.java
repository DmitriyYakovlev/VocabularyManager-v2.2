package com.yakovlev.prod.vocabularymanager.cursor_loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.yakovlev.prod.vocabularymanager.ormlite.WordTableHelper;
import com.yakovlev.prod.vocabularymanager.support.HardWordMode;

import java.sql.SQLException;

public class HardWordsCursorLoader  extends CursorLoader {

    private Context context;
    private HardWordMode mode;
    private String text;
    private boolean isSearchFromStart;

    public HardWordsCursorLoader(Context context, HardWordMode mode, String text, boolean isSearchFromStart) {
        super(context);
        this.context = context;
        this.mode = mode;
        this.text = text;
        this.isSearchFromStart = isSearchFromStart;
    }

    @Override
    public Cursor loadInBackground() {
        if (mode.equals(HardWordMode.ALL_WORDS))
            return WordTableHelper.getAllWordsCursorFromORM(context, text, isSearchFromStart);
        else
            return WordTableHelper.getHardWordsCursorFromORM(context, mode, text, isSearchFromStart);
    }

}
