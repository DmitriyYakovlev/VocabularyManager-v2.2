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

    public HardWordsCursorLoader(Context context, HardWordMode mode) {
        super(context);
        this.context = context;
        this.mode = mode;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = WordTableHelper.getHardWordsCursorFromORM(context,mode);
        return cursor;
    }

}
