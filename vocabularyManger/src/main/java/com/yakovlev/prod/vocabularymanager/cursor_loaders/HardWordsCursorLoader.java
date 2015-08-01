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

    public HardWordsCursorLoader(Context context, HardWordMode mode,String text) {
        super(context);
        this.context = context;
        this.mode = mode;
        this.text = text;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = WordTableHelper.getHardWordsCursorFromORM(context,mode,text);
        return cursor;
    }

}
