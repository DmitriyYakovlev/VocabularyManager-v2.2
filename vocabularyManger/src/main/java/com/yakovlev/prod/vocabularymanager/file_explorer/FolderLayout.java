package com.yakovlev.prod.vocabularymanager.file_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.yakovlev.prod.vocabularymanger.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FolderLayout extends LinearLayout implements OnItemClickListener {

	// Views
	private TextView tvPath;
	private ListView lvFiles;

	// Vars
	private Context context;
	IFolderItemListener folderListener;
	private List<String> item = null;
	private List<String> path = null;
	private String root = "/";
	private String currentPath = null;

	public FolderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.file_explorer_view, this);

		tvPath = (TextView) findViewById(R.id.path);
		lvFiles = (ListView) findViewById(R.id.list);

		getDir(root, lvFiles);
	}

	public String getPathFromFolderLayout() {
		return currentPath;
	}

	public void setIFolderItemListener(IFolderItemListener folderItemListener) {
		this.folderListener = folderItemListener;
	}

	// Set Directory for view at anytime
	public void setDir(String dirPath) {
		getDir(dirPath, lvFiles);
	}

	private void getDir(String dirPath, ListView v) {

		String location = "Path: ";
		tvPath.setText(location + dirPath);

		currentPath = dirPath;
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();

		if (!dirPath.equals(root)) {

			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent());

		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			path.add(file.getPath());
			if (file.isDirectory())
				item.add(file.getName() + "/");
			else
				item.add(file.getName());
		}

		setItemList(item);
	}

	public void setItemList(List<String> item) {
		ArrayAdapter<String> fileList = new ArrayAdapter<String>(context, R.layout.file_explorer_row, item);

		lvFiles.setAdapter(fileList);
		lvFiles.setOnItemClickListener(this);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		if (path.get(position) == null)
			return;
		File file = new File(path.get(position));
		if (file.isDirectory()) {
			if (file.canRead())
				getDir(path.get(position), l);
			else {
				if (folderListener != null) {
					folderListener.OnCannotFileRead(file);
				}
			}
		} else {
			if (folderListener != null) {
				folderListener.OnFileClicked(file);
			}
		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		onListItemClick((ListView) arg0, arg0, arg2, arg3);
	}

}
