package com.linking.words;

import java.io.IOException;

import com.linking.words.util.DBHelper;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.*;


public class Home extends Activity {
	
	MediaPlayer mp;
	
	DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ImageButton bthome = (ImageButton)findViewById(R.id.homebutton);
        bthome.setOnClickListener(bthomeOnClickListener);
        
        dbHelper = new DBHelper(this);
		try {
			dbHelper.createDataBase();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    private Button.OnClickListener bthomeOnClickListener = new Button.OnClickListener()
    {
    	@Override
    	public void onClick(View v){
    		Intent intent = new Intent();
    		intent.setClass(Home.this, Menuutama.class);
    		startActivityForResult(intent, 0);
    		finish();
    	}
    };

    
}
