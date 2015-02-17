package com.yakovlev.prod.vocabularymanager.adapters;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yakovlev.prod.vocabularymanager.ormlite.CursorHelper;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTable;
import com.yakovlev.prod.vocabularymanager.ormlite.WordTableHelper;
import com.yakovlev.prod.vocabularymanager.support.ToastHelper;
import com.yakovlev.prod.vocabularymanger.R;

public class LearnWordsCursorAdapter extends CursorAdapter{

	private LayoutInflater inflater;
	private TextView tvKey, tvValue;
	public Set<Integer> checkedItemsList = new HashSet<Integer>();
    private boolean hideRightSide = true;
    private Context context;
	
	public LearnWordsCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		this.inflater = LayoutInflater.from(context);
        this.context = context;
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final int id = CursorHelper.getNumberByField(cursor, "_id");
		String key = CursorHelper.getStringByField(cursor, "wKey");
		String value = CursorHelper.getStringByField(cursor, "wValue");
		String wTranscription = CursorHelper.getStringByField(cursor, "wTranscription");

		tvKey = (TextView) view.findViewById(R.id.tvKey);
		tvValue = (TextView) view.findViewById(R.id.tvValue);
		
		tvKey.setText(key);
		tvValue.setText(value);

		final View viewRightHide =  (View) view.findViewById(R.id.hideViewRight);
		final View viewLeftHide =  (View) view.findViewById(R.id.hideViewLeft);

		if (checkedItemsList.contains(id)) {
            setVisibilityForLeftHideViewAndRightHideView(viewLeftHide, viewRightHide, View.GONE, View.GONE);
        }
		else {
            if (hideRightSide)
                setVisibilityForLeftHideViewAndRightHideView(viewLeftHide, viewRightHide, View.GONE, View.VISIBLE);
            else
                setVisibilityForLeftHideViewAndRightHideView(viewLeftHide, viewRightHide, View.VISIBLE, View.GONE);
        }
		
		LinearLayout itemParent = (LinearLayout) view.findViewById(R.id.itemWordLearn);
		itemParent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                processLinearLayoutProcessing(viewLeftHide, viewRightHide, id);
            }
        });

        setOnLongClickListenerForItem(id, itemParent);
    }



    private void setOnLongClickListenerForItem(final int wordId, View itemParent){


        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastHelper.doInUIThread(Integer.toString(wordId),context );


                return false;
            }
        };
        itemParent.setOnLongClickListener(onLongClickListener);
    }

    private void processLinearLayoutProcessing(View viewLeftHide, View viewRightHide, int id){
        if (hideRightSide) {
            if (viewRightHide.getVisibility() == View.VISIBLE)
                setVisibilityForLeftHideViewAndRightHideView(viewLeftHide, viewRightHide, View.GONE, View.GONE);
            else
                setVisibilityForLeftHideViewAndRightHideView(viewLeftHide, viewRightHide, View.GONE, View.VISIBLE);
        }
        else {
            if (viewLeftHide.getVisibility() == View.VISIBLE)
                setVisibilityForLeftHideViewAndRightHideView(viewLeftHide, viewRightHide, View.GONE, View.GONE);
            else
                setVisibilityForLeftHideViewAndRightHideView(viewLeftHide, viewRightHide, View.VISIBLE, View.GONE);
        }
        processCheckedItemList(id);
    }

    private void setVisibilityForLeftHideViewAndRightHideView(View viewLeftHide, View viewRightHide, int leftVisibility, int rightVisibility){
        viewLeftHide.setVisibility(leftVisibility);
        viewRightHide.setVisibility(rightVisibility);
    }

    private void processCheckedItemList(int id){
        if (checkedItemsList.contains(id))
            checkedItemsList.remove(id);
        else
            checkedItemsList.add(id);
    }

    public void changeSwitcher(){
        if (hideRightSide)
            hideRightSide = false;
        else
            hideRightSide = true;
        notifyDataSetChanged();
    }

	public static String getFirstNSymbols(String s, int n){
		return s.substring(0, Math.min(s.length(), n));
	}
	
	@Override
	public View newView(Context arg0, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.item_learn_words_adapter, parent, false);
	}

}
