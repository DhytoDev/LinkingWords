package com.linking.words.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	ArrayList<ItemBean> highscore = new ArrayList<ItemBean>();

	private static String DB_NAME = "db_linkingwords";
	private SQLiteDatabase db;
	private final Context context;
	private String DB_PATH;
	public static final String TAG = "DBAdapter";

	private static final String DATABASE_TABLE = "tabel_skor";
	public static final String KEY_NAME = "name";
	public static final String KEY_SCORE = "score";
	private static final String DATABASE_CREATE = "create table IF NOT EXISTS tabel_skor (_id integer primary key autoincrement, "
			+ "name text not null, score text not null);";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
		DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();
		if (dbExist) {

		} else {
			this.getReadableDatabase();
			this.getWritableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private boolean checkDataBase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	private void copyDataBase() throws IOException {

		InputStream myInput = context.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	private SQLiteDatabase openOrCreateDatabase(String dB_NAME2,
			int modePrivate, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveScore(String nama, String skor) {

		db = this.getWritableDatabase();

		String sql = "Insert into tabel_skor (name,score) values ('" + nama
				+ "'," + "'" + skor + "')";
		db.execSQL(sql);
		db.close();

		Log.d("TAG", sql);

	}

	public ArrayList<ItemBean> getScore() {

		db = this.getReadableDatabase();
		// List<String> data = new ArrayList<String>();
		String sql = "SELECT * from tabel_skor ORDER BY score DESC LIMIT 5";
		Log.d("TAG", sql);
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			ItemBean hs = new ItemBean();
			hs.setName(c.getString(1));
			hs.setScore(c.getString(2));

			highscore.add(hs);

		}

		c.close();
		db.close();
		return highscore;

	}

	public Cursor getData(String id) {
		String myPath = DB_PATH + DB_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
		String sql = "SELECT _" + id
				+ " FROM tabel_katabenda ORDER BY RANDOM() LIMIT 1";
		Log.d("TAG", sql);
		Cursor c = db.rawQuery(sql, null);

		// Note: Master is the one table in External db. Here we trying to
		// access the records of table from external db.
		return c;
	}

	public Cursor getWord(String id, String text) {
		String myPath = DB_PATH + DB_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
		String sql = "SELECT _" + id + " FROM tabel_katabenda WHERE _" + id
				+ "=" + "'" + text + "'";
		Log.d("TAG", sql);
		Cursor c = db.rawQuery(sql, null);

		// Note: Master is the one table in External db. Here we trying to
		// access the records of table from external db.
		return c;
	}

	public void close() {
		if (db != null) {
			db.close();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
