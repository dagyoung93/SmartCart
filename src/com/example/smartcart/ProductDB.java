package com.example.smartcart;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class ProductDB extends SQLiteOpenHelper {
	private final static String TAG = "ProductDB";

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Menu.db";
	private static final String TABLE_MENU = "Menu";

	private static final String KEY__ID = "_id";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PAY = "pay";
	private static final String KEY_NUM = "num";
	private static final String KEY_COM = "com";

	public ProductDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// ID, 사진, 이름, 가격, 수량, 제조사
		db.execSQL("create table Menu (" + KEY__ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID + " INTEGER, "
				+ KEY_NAME + " TEXT, " + KEY_PAY + " INTEGER, " + KEY_NUM + " INTEGER, " + KEY_COM + " TEXT);");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801062247196, '롯데샌드 깜뜨', 1200, 0, '롯데');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801062333295, '젤링 젤링', 1000, 0, '롯데');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(88002798, '바나나맛 우유 ', 4180, 0, '빙그레');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801111907767,'임금님표이천쌀20kg ', 59800, 0, '이천');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(56100101673,'패밀리케어2080 ', 4900, 0, '애경');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(50426416,'킷캣', 1200, 0, 'Nestle');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801094013905,'코카콜라', 890, 0, '코카콜라');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801007309705,'쁘띠첼 스윗 푸딩 레어치즈',1720, 0, '쁘띠첼');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801007309682,'쁘띠첼 스윗 푸딩 블루베리레어치즈', 1720, 0, '쁘띠첼');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801114101636,'풀무원국산콩두부', 3240, 0, '풀무원');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801117431914,'닥터유 에너지바', 1200, 0, '오리온');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801105912906,'코코팜 화이트요구르트', 800, 0, '');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801043032131,'짜왕', 1500, 0, '농심');");

		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801046245873,'2080칫솔', 4800, 0, '애경');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(3282779348850,'Avene cream',1720, 0, 'Avene');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(3401396605607,'BIODERMA세비엄세럼', 38000, 0, 'BIODERMA');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801047224341,'들기름향이가득한김', 3000, 0, '양반');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801619800720,'립톤 아이스티', 7800, 0, '립톤');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801221101185,'밴드닥터',1200, 0, '밴드닥터');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8808244201014,'삼다수 생수', 800, 0, '삼다수');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801074259637,'스팸', 4500, 0, 'cj');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801675574863,'아모스 트리트먼트', 30000, 0, '아모스');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801045890722,'옛날구수한 누룽지',6800, 0, '오뚜기');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(9788933830406,'오두막(도서)', 12800, 0, '세계사');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8809093198616,'오가닉스토리 물티슈', 3000, 0, '오가닉스토리');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801043015264,'짜파게티', 5600, 0, '농심');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801056055226,'트레비',1200, 0, '롯데');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(4902430111102,'페브리즈', 4500, 0, 'P&G');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8806109198622,'프리미엄 스피루리나', 63000, 0, '동국제약');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801065103994,'하인즈케찹', 7800, 0, '하인즈');");
		db.execSQL("INSERT INTO Menu (id, name, pay, num, com) values(8801069185446,'화이트 초코에몽',1000, 0, '남양유업');");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// 버전 숫자를 올릴 경우 테이블을 드랍하고 재생성 (내용들 지워짐)
		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
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
		return getReadableDatabase().query(TABLE_MENU, columns, selection, selectionArgs, groupBy, having, orderBy);
	}

	// 삽입 메소드
	public void insert(Product data) {
		Log.d(TAG, "Insert start!!");
		getWritableDatabase().execSQL("INSERT INTO " + TABLE_MENU + " (" + KEY_ID + ", " + KEY_NAME + ", " + KEY_PAY
				+ ", " + KEY_NUM + ", " + KEY_COM + ") VALUES ('" + data.getId() + "', '" + data.getName() + "', '"
				+ data.getPay() + "', '" + data.getNum() + "', '" + data.getCom() + "');");
		Log.d(TAG, "Insert ended!!");
	}

	// 전체 삭제 메소드
	public void delete() {
		Log.d(TAG, "Deleting start!!");
		getWritableDatabase().delete(TABLE_MENU, null, null);
		Log.d(TAG, "Deleting end!!");
	}

}
