package com.shaktiprasadsen.mytodolist1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.R.anim;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lv;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        
        lv = (ListView) findViewById(R.id.lvItemList);
        loadItems();
        //items = new ArrayList<String>();
        //items.add("My First Item");
        //items.add("Second Item");
        
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(itemsAdapter);
        setupLVListener();
    }
	private void loadItems() {
		File filesDir = getFilesDir();
		File fHandle = new File(filesDir, "TodoList.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(fHandle));
		} catch (IOException e) {
			items = new ArrayList<String>();//At least to have empty list. Will help for 1st time creation/read.
			e.printStackTrace();
		}
		
		
	}
	private void setupLVListener() {
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
		
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		
		
	}
	protected void saveItems() {
		File filesDir = getFilesDir();
		File fHandle = new File(filesDir, "TodoList.txt");
		
		try {
			FileUtils.writeLines(fHandle, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void addItem(View v) {
		EditText etEnteredText = (EditText) findViewById(R.id.etNewItem);
		String str = etEnteredText.getText().toString();
		//Don't add any empty item.
		str = str.trim();
		if (str.length() > 0)
			itemsAdapter.add(str);
		//items.add(etEnteredText.getText().toString());
		etEnteredText.setText("");//After getting the text from Entry field, make it empty.
		saveItems();
	}
}
