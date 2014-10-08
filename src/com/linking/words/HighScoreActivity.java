package com.linking.words;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.linking.words.util.DBHelper;
import com.linking.words.util.HighScoreAdapter;
import com.linking.words.util.ItemBean;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighScoreActivity extends Activity {
	DBHelper dbhelper;
	ListView mylist;
	HighScoreAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscore);

		dbhelper = new DBHelper(this);
		try {
			dbhelper.createDataBase();
		} catch (IOException e) {
			Log.d("Err", e.getMessage());
		}

		ArrayList<String> highscore = new ArrayList<String>();
		List<ItemBean> list = dbhelper.getScore();
		for (ItemBean hs : list) {
			
			
			Log.d("name", hs.getName());
			Log.d("Score", hs.getScore());
		}
		adapter = new HighScoreAdapter(this, list);
		Log.d("List", list.toString());
		mylist = (ListView) findViewById(R.id.listView1);

		View header = (View) getLayoutInflater().inflate(
				R.layout.listview_header_row, null);
		mylist.addHeaderView(header);

		mylist.setAdapter(adapter);

	}
}
