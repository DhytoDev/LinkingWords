package com.linking.words;

import java.util.List;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.view.View;
import android.widget.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Intent;
import android.app.*;
import android.view.*;
import android.content.*;
import android.content.res.AssetFileDescriptor;

public class Menuutama extends Activity {
	MediaPlayer mp, mp1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_utama);

		mp = MediaPlayer.create(Menuutama.this, R.raw.bgm);
		mp.setLooping(true);
		mp.start();

		ImageButton a, b, c, d;

		a = (ImageButton) findViewById(R.id.button1);
		a.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menuutama.this, Play.class);
				startActivity(intent);
				finish();
				buttonSound();
				// To change body of implemented methods use File | Settings |
				// File Templates.
			}
		});

		b = (ImageButton) findViewById(R.id.button2);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menuutama.this,
						HighScoreActivity.class);
				startActivity(intent);
				buttonSound();
			}
		});

		c = (ImageButton) findViewById(R.id.button3);
		c.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonSound();

				final Dialog dialog = new Dialog(Menuutama.this);
				dialog.setContentView(R.layout.win_tutorial);
				dialog.setTitle("How To Play");
				ImageButton dialogButton = (ImageButton) dialog
						.findViewById(R.id.back);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});

		d = (ImageButton) findViewById(R.id.button4);
		d.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonSound();
				final Dialog dialog = new Dialog(Menuutama.this);
				dialog.getLayoutInflater();
				dialog.setTitle("ABOUT US");

				dialog.setContentView(R.layout.win_about);
				ImageButton dialogButton = (ImageButton) dialog
						.findViewById(R.id.back);

				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
	};

	@Override
	protected void onPause() {
		mp.stop();

		/*
		 * if (this.isFinishing()) { mp.stop(); }
		 * 
		 * Context context = getApplicationContext(); ActivityManager am =
		 * (ActivityManager) context
		 * .getSystemService(Context.ACTIVITY_SERVICE); List<RunningTaskInfo>
		 * taskInfo = am.getRunningTasks(1); if (!taskInfo.isEmpty()) {
		 * ComponentName topActivity = taskInfo.get(0).topActivity; if
		 * (!topActivity.getPackageName().equals(context.getPackageName())) {
		 * mp.stop(); } else {
		 * 
		 * } }
		 */
		super.onPause();
	}

	public void buttonSound() {
		mp1 = MediaPlayer.create(Menuutama.this, R.raw.button);
		mp1.setLooping(false);
		mp1.start();
	}
}
