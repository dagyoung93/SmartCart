package com.example.smartcart;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderedResult extends Activity {
	private final static String TAG = "OrderedResult";

	OrderedDB orderedDB;
	ListView listView;
	ArrayList<Product> data;
	ProductArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_own);

		orderedDB = new OrderedDB(this);

		listView = (ListView) findViewById(R.id.orderedListView);
		fillListArray();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ordered_result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void fillListArray() {
		data = new ArrayList<Product>();
		int i=0;

		Cursor c = orderedDB.query(new String[] { "id", "name", "pay", "num", "com" }, null, null, null, null,
				null);
		Log.d(TAG, "getCount: " + c.getCount());
		while (c.moveToNext()) {
			data.add(
					new Product(c.getString(0), c.getString(1), c.getInt(2), true, false, c.getInt(3)));
			        data.get(i++).setPay(c.getInt(2));
		}

		adapter = new ProductArrayAdapter(this, R.layout.list_item_own, data);
		listView.setAdapter(adapter); // 리스트뷰에 반영
	}

	// 주문 화면의 리스트뷰에 띄울 내용 및 수량 조절 버튼 클릭 시 화면이 업데이트 되게끔 하는 내부클래스
	public class ProductArrayAdapter extends ArrayAdapter<Product> {

		// 내부클래스 생성자
		public ProductArrayAdapter(Context context, int textViewResourceId, ArrayList<Product> _data) {
			super(context, textViewResourceId, _data);
			data = _data;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View itemLayout = convertView;
			if (itemLayout == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				itemLayout = inflater.inflate(R.layout.list_item_own, null);

			}

			// 리스트뷰 내부 객체 하나하나 초기화 해주는 부분
			TextView productName;
			ImageView productImage;
			TextView productCost;

			productName = (TextView) itemLayout.findViewById(R.id.productOrderedName);
			productImage = (ImageView) itemLayout.findViewById(R.id.productImage);
			productCost = (TextView) itemLayout.findViewById(R.id.productOrderedCost);

			// ArrayList에 저장된 Product 데이터들을 읽어와서 뷰의 텍스트를 설정해주는 부분
			productName.setText(getItem(position).getName() + " " + getItem(position).getNum() + "개");
			Log.d(TAG, "getItem(position) : " + getItem(position).getName());
			productCost.setText("₩" + getItem(position).getPay());
			Log.d(TAG, "getItem(position) : " + getItem(position).getNum() + "");
			Log.d(TAG, "getItem(position) : " + getItem(position).getId() + "");
			Drawable drawable = null;
			// 데이터 아이디에 따라 출력할 이미지 설정
			if (getItem(position).getId().equals("8801062247196")) {
				Log.d(TAG, "c001");
				drawable = getResources().getDrawable(R.drawable.c001);
			} else if (getItem(position).getId().equals("8801062333295"+"")) {
				Log.d(TAG, "c002");
				drawable = getResources().getDrawable(R.drawable.c002);
			}
			else if (getItem(position).getId().equals("88002798"+"")) {
				Log.d(TAG, "c003");
				drawable = getResources().getDrawable(R.drawable.c003);
			}
			else if (getItem(position).getId().equals("8801111907767"+"")) {
				Log.d(TAG, "c004");
				drawable = getResources().getDrawable(R.drawable.c004);
			}
			else if (getItem(position).getId().equals("56100101673"+"")) {
				Log.d(TAG, "c005");
				drawable = getResources().getDrawable(R.drawable.c005);
			}	
            else if (getItem(position).getId().equals("50426416"+"")) {
				Log.d(TAG, "c006");
				drawable = getResources().getDrawable(R.drawable.c006);
			}
            else if (getItem(position).getId().equals("8801094013905"+"")) {
				Log.d(TAG, "c007");
				drawable = getResources().getDrawable(R.drawable.c007);
	        }
            else if (getItem(position).getId().equals("8801007309705"+"")) {
				Log.d(TAG, "c008");
				drawable = getResources().getDrawable(R.drawable.c008);
	        }
            else if (getItem(position).getId().equals("8801007309682"+"")) {
  				Log.d(TAG, "c009");
  				drawable = getResources().getDrawable(R.drawable.c009);
  	        }
            else if (getItem(position).getId().equals("8801114101636"+"")) {
  				Log.d(TAG, "c010");
  				drawable = getResources().getDrawable(R.drawable.c010);
  	        }
            else if (getItem(position).getId().equals("8801117431914"+"")) {
  				Log.d(TAG, "c011");
  				drawable = getResources().getDrawable(R.drawable.c011);
  	        }
            else if (getItem(position).getId().equals("8801105912906"+"")) {
  				Log.d(TAG, "c012");
  				drawable = getResources().getDrawable(R.drawable.c012);
  	        }
            else if (getItem(position).getId().equals("8801043032131"+"")) {
  				Log.d(TAG, "c013");
  				drawable = getResources().getDrawable(R.drawable.c013);
  	        }
            else if (getItem(position).getId().equals("8801046245873"+"")) {
				Log.d(TAG, "c014");
				drawable = getResources().getDrawable(R.drawable.c014);
			}	
            else if (getItem(position).getId().equals("3282779348850"+"")) {
				Log.d(TAG, "c015");
				drawable = getResources().getDrawable(R.drawable.c015);
			}	
            else if (getItem(position).getId().equals("3401396605607"+"")) {
				Log.d(TAG, "c016");
				drawable = getResources().getDrawable(R.drawable.c016);
			}
            else if (getItem(position).getId().equals("8801047224341"+"")) {
				Log.d(TAG, "c017");
				drawable = getResources().getDrawable(R.drawable.c017);
	        }
            else if (getItem(position).getId().equals("8801619800720"+"")) {
				Log.d(TAG, "c018");
				drawable = getResources().getDrawable(R.drawable.c018);
	        }
            else if (getItem(position).getId().equals("8801221101185"+"")) {
  				Log.d(TAG, "c019");
  				drawable = getResources().getDrawable(R.drawable.c019);
  	        }
            else if (getItem(position).getId().equals("8808244201014"+"")) {
  				Log.d(TAG, "c020");
  				drawable = getResources().getDrawable(R.drawable.c020);
  	        }
			else if (getItem(position).getId().equals("8801074259637"+"")) {
				Log.d(TAG, "c021");
				drawable = getResources().getDrawable(R.drawable.c021);
			}
 		   else if (getItem(position).getId().equals("8801675574863"+"")) {
				Log.d(TAG, "c022");
				drawable = getResources().getDrawable(R.drawable.c022);
			}
            else if (getItem(position).getId().equals("8801045890722"+"")) {
				Log.d(TAG, "c023");
				drawable = getResources().getDrawable(R.drawable.c023);
	        }
            else if (getItem(position).getId().equals("9788933830406"+"")) {
				Log.d(TAG, "c024");
				drawable = getResources().getDrawable(R.drawable.c024);
	        }
            else if (getItem(position).getId().equals("8809093198616"+"")) {
  				Log.d(TAG, "c025");
  				drawable = getResources().getDrawable(R.drawable.c025);
  	        }
            else if (getItem(position).getId().equals("8801043015264"+"")) {
  				Log.d(TAG, "c026");
  				drawable = getResources().getDrawable(R.drawable.c026);
  	        }
            else if (getItem(position).getId().equals("8801056055226"+"")) {
				Log.d(TAG, "c027");
				drawable = getResources().getDrawable(R.drawable.c027);
			}
            else if (getItem(position).getId().equals("4902430111102"+"")) {
				Log.d(TAG, "c028");
				drawable = getResources().getDrawable(R.drawable.c028);
	        }
            else if (getItem(position).getId().equals("8806109198622"+"")) {
				Log.d(TAG, "c029");
				drawable = getResources().getDrawable(R.drawable.c029);
	        }
            else if (getItem(position).getId().equals("8801065103994"+"")) {
  				Log.d(TAG, "c030");
  				drawable = getResources().getDrawable(R.drawable.c030);
  	        }
            else if (getItem(position).getId().equals("8801069185446"+"")) {
  				Log.d(TAG, "c031");
  				drawable = getResources().getDrawable(R.drawable.c031);
  	        }
			if (drawable != null)
				productImage.setImageDrawable(drawable);
  
			return itemLayout;
		}
	}
}
