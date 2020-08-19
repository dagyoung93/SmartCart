package com.example.smartcart;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Order extends Activity implements OnClickListener, OnItemClickListener {
	private final static String TAG = "Order";

	// 바코드
	public static final int REQUEST_CODE_SCAN = 1001;
	public static final int DIALOG_SCANNER_NEEDED = 1002;
	public static final int DIALOG_SHOW_URL = 1003;

	public static final String PRODUCT_CODE_TYPES = "UPC_A,UPC_E,EAN_8,EAN_13";
	public static final String ONE_D_CODE_TYPES = PRODUCT_CODE_TYPES + ",CODE_39,CODE_93,CODE_128";
	public static final String QR_CODE_TYPES = "QR_CODE";
	public static final String ALL_CODE_TYPES = null;
	//

	private TextView contentsText;
	private String scannedUrl;

	ImageButton btn_scan;
	Button btn_del;
	TextView totalCostView;
	Button btn_order;

	ListView listView1;
	ArrayList<Product> data;
	ProductArrayAdapter adapter;

	ProductDB productDB;
	OrderedDB orderedDB;
	SQLiteDatabase sqlDB;
	Cursor cursor;

	int select;

	ArrayList<String> existsIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		btn_scan = (ImageButton) findViewById(R.id.scan);
		btn_scan.setOnClickListener(this);

		listView1 = (ListView) findViewById(R.id.orderListView);
		totalCostView = (TextView) findViewById(R.id.totalOrderCost);
		btn_order = (Button) findViewById(R.id.submitOrder);
		btn_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (data.size() > 0) {
					orderedDB.delete();
					for (int i = 0; i < data.size(); i++)
						orderedDB.insert(data.get(i));

					Intent intent = new Intent(Order.this, OrderedResult.class);
					startActivity(intent);
				}
			}
		});

		productDB = new ProductDB(this);
		orderedDB = new OrderedDB(this);
		data = new ArrayList<Product>(); // ArrayList 데이터를 초기화
		existsIds = new ArrayList<String>();
		fillListArray();
	}

	// ArrayList 데이터를 초기화 해주는 메소드
	private void fillListArray() {
		adapter = new ProductArrayAdapter(this, R.layout.list_item_order, data);
		listView1.setAdapter(adapter); // 리스트뷰에 반영
	}

	// 주문 화면의 리스트뷰에 띄울 내용 및 수량 조절 버튼 클릭 시 화면이 업데이트 되게끔 하는 내부클래스
	public class ProductArrayAdapter extends ArrayAdapter<Product> {

		// 내부클래스 생성자
		public ProductArrayAdapter(Context context, int textViewResourceId, ArrayList<Product> _data) {
			super(context, textViewResourceId, _data);
			data = _data;
			totalCostView.setText(" 총 가격 : 0 원 ");
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View itemLayout = convertView;
			if (itemLayout == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				itemLayout = inflater.inflate(R.layout.list_item_order, null);

			}

			// 리스트뷰 내부 객체 하나하나 초기화 해주는 부분
			TextView productName;
			ImageView removeBtn;
			Button increaseBtn;
			Button decreaseBtn;
			final TextView quantityView;
			final TextView costView;

			productName = (TextView) itemLayout.findViewById(R.id.product_name);
			removeBtn = (ImageView) itemLayout.findViewById(R.id.remove_btn);
			increaseBtn = (Button) itemLayout.findViewById(R.id.increase_quantity_btn);
			decreaseBtn = (Button) itemLayout.findViewById(R.id.decrease_quantity_btn);
			quantityView = (TextView) itemLayout.findViewById(R.id.quantity_view);
			costView = (TextView) itemLayout.findViewById(R.id.product_cost_view);

			// ArrayList에 저장된 Product 데이터들을 읽어와서 뷰의 텍스트를 설정해주는 부분
			productName.setText(getItem(position).getName());
			Log.d(TAG, "getItem(position) : " + getItem(position).getName());
			quantityView.setText(getItem(position).getNum() + "");
			Log.d(TAG, "getItem(position) : " + getItem(position).getNum() + "");
			getItem(position).setPay(getItem(position).getMoney() * getItem(position).getNum());
			costView.setText(getItem(position).getPay() + "원");

			// 리스트뷰 내부 객체의 삭제 버튼을 누를 시, 총가격에서 금액 빼주고, 리스트뷰에서 삭제하기
			removeBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					remove(getItem(position));
					int totalCost = 0;
					for (int i = 0; i < getCount(); i++) {
						totalCost += getItem(i).getPay();
					}
					totalCostView.setText("총 가격 : " + totalCost + "원");
				}
			});

			// 리스트뷰 내부 객체의 수량 빼기 버튼을 누를 시, 누르기 직전 수량이 1보다 클 경우에만 1씩 감소. 감소 즉시 총
			// 금액에 반영
			decreaseBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (getItem(position).getNum() > 1) {
						getItem(position).setNum(getItem(position).getNum() - 1);
						getItem(position).setPay(getItem(position).getMoney() * getItem(position).getNum());
						quantityView.setText(getItem(position).getNum() + "");
						costView.setText(getItem(position).getPay() + "원");
						int totalCost = 0;
						for (int i = 0; i < getCount(); i++) {
							totalCost += getItem(i).getPay();
						}
						totalCostView.setText("총 가격 : " + totalCost + "원");
					}
				}
			});

			// 리스트뷰 내부 객체의 수량 더하기 버튼을 누를 시, 누르기 직전 수량이 99보다 작은 경우에만 1씩 증가. 증가 즉시
			// 총 금액에 반영
			increaseBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (getItem(position).getNum() < 99) {
						getItem(position).setNum(getItem(position).getNum() + 1);
						Log.d(TAG, "onClick inscreaseBtn. getNum : " + getItem(position).getNum());
						Log.d(TAG, "onClick inscreaseBtn. getMoney : " + getItem(position).getMoney());
						getItem(position).setPay(getItem(position).getMoney() * getItem(position).getNum());
						quantityView.setText(getItem(position).getNum() + "");
						costView.setText(getItem(position).getPay() + "원");
						int totalCost = 0;
						for (int i = 0; i < getCount(); i++) {
							totalCost += getItem(i).getPay();
						}
						totalCostView.setText("총 가격: " + totalCost + "원");
					}
				}
			});

			return itemLayout;
		}
	}
	public void addProduct() {
		for (int i = 0; i < data.size(); i++) {
		}

		cursor = productDB.query(new String[] { "id", "img", "name", "pay", "num", "com" }, "id=?",
				new String[] { "id" }, null, null, null);

		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String img = cursor.getString(cursor.getColumnIndex("img"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int pay = cursor.getInt(cursor.getColumnIndex("pay"));
			int num = cursor.getInt(cursor.getColumnIndex("num"));
			String com = cursor.getString(cursor.getColumnIndex("com"));
			adapter.add(new Product(id, img, name, pay, num, com));
		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Product choice = adapter.getItem(arg2);
		// Toast.makeText(this, choice.toString(), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, Menu.class);
		intent.putExtra("ID", choice.getId());
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.scan:
			scan();
			break;

		}
	}

	private void scan() {
		scan(ALL_CODE_TYPES);
	}

	private void scan(String formats) {
		Intent intentScan = new Intent("com.google.zxing.client.android.SCAN");
		intentScan.addCategory(Intent.CATEGORY_DEFAULT);
		if (formats != null) {
			intentScan.putExtra("SCAN_FORMATS", formats);
		}
		try {
			startActivityForResult(intentScan, REQUEST_CODE_SCAN);
		} catch (ActivityNotFoundException e) {
			showDialog(DIALOG_SCANNER_NEEDED);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		Log.d(TAG, "REQUEST_CODE_SCAN start!");
		if (requestCode == REQUEST_CODE_SCAN) {
			Log.d(TAG, "REQUEST_CODE_SCAN Ok!");
			Toast toast = Toast.makeText(getBaseContext(), "onActivityResult called with code : " + resultCode,
					Toast.LENGTH_LONG);
			toast.show();
			boolean isExistedItem = false;
			if (resultCode == Activity.RESULT_OK) {
				Log.d(TAG, "RESULT_OK! " + resultCode);
				String contents = intent.getStringExtra("SCAN_RESULT");
				String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");

				Log.d(TAG, "SCAN_RESULT: " + contents);

				for (int i = 0; i < existsIds.size(); i++) {
					if (existsIds.get(i).equals(contents)) {
						Log.d(TAG, "RESULT ALREADY ON LISTARRAY!");
						isExistedItem = true;
					}
				}
				if (!isExistedItem) {
					Log.d(TAG, "RESULT search on DB!");
					cursor = productDB.query(new String[] { "id", "name", "pay", "num", "com" }, "id=?",
							new String[] { contents }, null, null, null);
					Log.d(TAG, "getCount = " + cursor.getCount());

					if (cursor != null && cursor.getCount() != 0) {
						Log.d(TAG, "Found query result!!");
						cursor.moveToFirst();
						data.add(new Product(cursor.getString(0), cursor.getString(1), cursor.getInt(2), false, false,
								cursor.getInt(3)));
						fillListArray();
						for (int i = 0; i < data.size(); i++) {
							Log.d(TAG, data.get(i).getId() + ", " + data.get(i).getName() + ", " + data.get(i).getNum()
									+ ", " + data.get(i).getPay());
						}
					}
					existsIds.add(contents);
				}

			} else {

			}

		}

	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = null;

		switch (id) {
		case DIALOG_SCANNER_NEEDED:
			builder = new AlertDialog.Builder(this);
			builder.setTitle("바코드 스캐너 앱 설치");
			builder.setMessage("바코드 스캐너 앱이 필요합니다. 자동 설치할까요?");
			builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Uri uri = Uri.parse("market://details?id=com.google.zxing.client.android");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);

					startActivity(intent);
				}
			});

			builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

				}
			});

			break;
		case DIALOG_SHOW_URL:
			builder = new AlertDialog.Builder(this);
			builder.setTitle("웹으로 보기");
			builder.setMessage("스캔한 결과를 웹으로 보시겠습니까?");
			builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedUrl));
					startActivity(intent);
				}
			});

			builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

				}
			});
			break;
		default:
			break;
		}
		return builder.create();
	}
}