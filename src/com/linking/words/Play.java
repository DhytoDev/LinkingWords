package com.linking.words;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.linking.words.util.DBHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Play extends Activity implements OnClickListener {

	private DBHelper dbHelper;
	private String idx;
	private static char[] karakter;
	private int i = 0;
	private int last, first;
	private int skor = 0;
	private MediaPlayer mp, mp1;

	EditText input;
	TextView score;
	TextView timer;
	EditText edtJwbn;
	LinearLayout LLjwbn;
	TextView txtShowScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		CountDown count = new CountDown(30000, 1000);
		count.start();

		score = (TextView) findViewById(R.id.txtScore);
		timer = (TextView) findViewById(R.id.timer);

		input = (EditText) findViewById(R.id.input);
		Button btnSubmit = (Button) findViewById(R.id.btnSend);
		btnSubmit.setOnClickListener(this);

		dbHelper = new DBHelper(this);
		try {
			dbHelper.createDataBase();

		} catch (IOException e) {
			e.printStackTrace();
		}

		final String alphabet = "abcdefghijklmnopqrstuvwxyz";
		final int N = alphabet.length();

		Random r = new Random();
		char id = alphabet.charAt(r.nextInt(N));
		String idx = Character.toString(id);

		Cursor c = dbHelper.getData(idx);
		if (c.moveToFirst()) {
			int index = c.getColumnIndex("_" + idx);
			String kata = c.getString(index);

			try {
				LLjwbn = (LinearLayout) findViewById(R.id.LLjwbn2);
				edtJwbn = new EditText(this);
				// edtJwbn.setId(i);
				edtJwbn.setEms(10);
				edtJwbn.setEnabled(false);
				edtJwbn.setText(kata);
				LLjwbn.addView(edtJwbn);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public char checkCharLast(String str1) {
		karakter = new char[str1.length()];

		for (int i = 0; i < str1.length(); i++) {

			karakter[i] = str1.charAt(i);

		}

		last = str1.length() - 1;
		System.out.println(karakter[last]);
		return 0;
	}

	public char checkCharFirst(String str1) {
		karakter = new char[str1.length()];
		
		for (int i = 0; i < str1.length(); i++) {

			karakter[i] = str1.charAt(i);

		}

		first = 0;
		System.out.println(karakter[first]);
		return 0;
	}

	@Override
	public void onClick(View v) {
		try {
			String inputUser = input.getText().toString();
			String jwbn1 = edtJwbn.getText().toString();
			String kata = "";
			checkCharLast(jwbn1);
			char lastChar = karakter[last];
			checkCharFirst(inputUser);
			char firstChar = karakter[first];

			Log.d("Last", Character.toString(lastChar));
			Log.d("First", Character.toString(firstChar));

			Cursor c = dbHelper
					.getWord(Character.toString(lastChar), inputUser);
			if (c.moveToFirst()) {
				int index = c.getColumnIndex("_" + lastChar);
				kata = c.getString(index);

			}

			if (lastChar == firstChar && kata.equals(inputUser)) {

				try {
					correctSound();
					edtJwbn = new EditText(this);
					edtJwbn.setEms(10);
					edtJwbn.setEnabled(false);
					edtJwbn.setText(inputUser);
					LLjwbn.addView(edtJwbn);

					input.setText("");
					input.requestFocus();

					skor = skor + 10;
					Log.d("Score", String.valueOf(skor));
					score.setText("Score : " + String.valueOf(skor));

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (lastChar != firstChar) {
				wrongSound();

				Toast.makeText(getBaseContext(),
						"Your type mismatch.. Try Again!!", Toast.LENGTH_LONG)
						.show();
				input.setText("");
				input.requestFocus();
			} else if (kata != inputUser) {
				wrongSound();
				Toast.makeText(getBaseContext(),
						"Your type not available.. Try Again!!",
						Toast.LENGTH_LONG).show();
				input.setText("");
				input.requestFocus();
			}
		} catch (Exception e) {
			Toast.makeText(getBaseContext(),
					"Input something please.. Try Again!!", Toast.LENGTH_LONG)
					.show();
			input.setText("");
			input.requestFocus();
		}

	}

	public void wrongSound() {
		mp = MediaPlayer.create(Play.this, R.raw.salah);
		mp.setLooping(false);
		mp.start();
	}

	public void correctSound() {
		mp = MediaPlayer.create(Play.this, R.raw.benar);
		mp.setLooping(false);
		mp.start();
	}

	public class CountDown extends CountDownTimer {

		// TODO Auto-generated constructor stub

		public CountDown(long millisInFuture, long countDownInterval) {

			super(millisInFuture, countDownInterval);

		}

		public void onFinish() {
			final Dialog dialog = new Dialog(Play.this);
			dialog.getLayoutInflater();
			dialog.setTitle("Congratulation");

			dialog.setContentView(R.layout.win_dialog);
			txtShowScore = (TextView) dialog.findViewById(R.id.textScore);
			final EditText edtInputName = (EditText) dialog
					.findViewById(R.id.textName);

			txtShowScore.setText(String.valueOf(skor));

			ImageButton dialogButton = (ImageButton) dialog
					.findViewById(R.id.button1);

			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String name = edtInputName.getText().toString();
					String score2 = txtShowScore.getText().toString();

					dbHelper.saveScore(name, score2);

					Intent i = new Intent(Play.this, Menuutama.class);
					startActivity(i);
					finish();

				}
			});
			dialog.show();

		}

		@Override
		public void onTick(long millisUntilFinished) {

			long second = (int) (millisUntilFinished / 1000);
			timer.setText(String.format("%02d", second / 60) + ":"
					+ String.format("%02d", second % 60));

		}
	}

	@Override
	protected void onDestroy() {
		if (dbHelper != null) {
			dbHelper.close();
		}
		super.onDestroy();

	}

}
