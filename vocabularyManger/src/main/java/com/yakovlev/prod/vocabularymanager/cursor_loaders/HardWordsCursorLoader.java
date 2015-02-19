package com.yakovlev.prod.vocabularymanager.cursor_loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.yakovlev.prod.vocabularymanager.ormlite.WordTableHelper;

import java.sql.SQLException;

public class HardWordsCursorLoader  extends CursorLoader {

    private Context context;

    public HardWordsCursorLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = WordTableHelper.getHardWordsCursorFromORM(context);
        return cursor;
    }

}
