package com.linking.words.util;

import java.util.ArrayList;
import java.util.List;

import com.linking.words.R;
import com.linking.words.R.id;
import com.linking.words.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HighScoreAdapter extends BaseAdapter{
	Context ctx ;
	//ArrayList<ItemBean> arrItem ;
	List<ItemBean> list;
	LayoutInflater inflater ;
	
	public HighScoreAdapter(Context ctx,List<ItemBean> list) {
		this.ctx = ctx;
		this.list = list ;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		v = inflater.inflate(R.layout.listview_item_row, null, true);
		
		TextView txtName = (TextView) v.findViewById(R.id.txtName);
		TextView txtScore = (TextView) v.findViewById(R.id.txtScore);
		
		txtName.setText(list.get(position).getName());
		txtScore.setText(list.get(position).getScore());
		
		return v;
	}
	
}
