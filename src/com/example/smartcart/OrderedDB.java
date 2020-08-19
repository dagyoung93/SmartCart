package com.example.smartcart;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class OrderedDB extends SQLiteOpenHelper {
	private final static String TAG = "OrderedDB";

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Order.db";
	private static final String TABLE_ORDERED = "Ordered";

	private static final String KEY__ID = "_id";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PAY = "pay";
	private static final String KEY_NUM = "num";
	private static final String KEY_COM = "com";

	public OrderedDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// ID, 사진, 이름, 가격, 수량, 제조사
		db.execSQL("create table Ordered (" + KEY__ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID + " TEXT, "
				+ KEY_NAME + " TEXT, " + KEY_PAY + " INTEGER, " + KEY_NUM + " INTEGER, " + KEY_COM + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// 버전 숫자를 올릴 경우 테이블을 드랍하고 재생성 (내용들 지워짐)
		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERED);
			onCreate(db);
		}
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	// String[] columns에 있는 테이블 항목들을 순차적으로 가져옴.
	public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		Log.d(TAG, "querying start!!");
		return getReadableDatabase().query(TABLE_ORDERED, columns, selection, selectionArgs, groupBy, having, orderBy);
	}

	// 삽입 메소드
	public void insert(Product data) {
		Log.d(TAG, "Insert start!!");
		getWritableDatabase().execSQL("INSERT INTO " + TABLE_ORDERED + " (" + KEY_ID + ", " + KEY_NAME + ", " + KEY_PAY
				+ ", " + KEY_NUM + ", " + KEY_COM + ") VALUES ('" + data.getId() + "', '" + data.getName() + "', '"
				+ data.getPay() + "', '" + data.getNum() + "', '" + data.getCom() + "');");
		Log.d(TAG, "Insert ended!!");
	}

	// 전체 삭제 메소드
	public void delete() {
		Log.d(TAG, "Deleting start!!");
		getWritableDatabase().delete(TABLE_ORDERED, null, null);
		Log.d(TAG, "Deleting end!!");
	}

}
