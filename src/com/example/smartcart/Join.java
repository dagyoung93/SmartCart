package com.example.smartcart;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Join extends Activity implements OnClickListener {

	private final String URL = "http://youndk.dothome.co.kr/UML/join.php";
	
	AsyncHttpClient client;
	HttpResponse httpResponse;
	
	TextView tv_phone;
	
	EditText edt_id;
	EditText edt_password1;
	EditText edt_password2;
	EditText edt_name;
	
	Button btn_ok;
	Button btn_cancle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		
		client = new AsyncHttpClient();
		httpResponse = new HttpResponse();
		
		tv_phone = (TextView) findViewById(R.id.phone);
		
		edt_id = (EditText) findViewById(R.id.id);
		edt_password1 = (EditText) findViewById(R.id.password1);
		edt_password2 = (EditText) findViewById(R.id.password2);
		edt_name = (EditText) findViewById(R.id.name);
		
		btn_ok = (Button) findViewById(R.id.ok);
		btn_ok.setOnClickListener(this);
		btn_cancle = (Button) findViewById(R.id.cencle);
		btn_cancle.setOnClickListener(this);
		
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
		String phoneNum = tm.getLine1Number();
		
		tv_phone.setText(phoneNum);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.ok:
			if(edt_id.getText().toString().equals(""))
			{
				Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
				return;
			}
			if(edt_password1.getText().toString().equals(""))
			{
				Toast.makeText(this, "패스워드를 입력하세요.", Toast.LENGTH_LONG).show();
				return;
			}
			if(edt_password2.getText().toString().equals(""))
			{
				Toast.makeText(this, "패스워드를 입력하세요.", Toast.LENGTH_LONG).show();
				return;
			}
			if(!(edt_password1.getText().toString().equals(edt_password2.getText().toString())))
			{
				Toast.makeText(this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
				return;
			}
			
			if(edt_name.getText().toString().equals(""))
			{
				Toast.makeText(this, "이름를 입력하세요.", Toast.LENGTH_LONG).show();
				return;
			}
			
			RequestParams params = new RequestParams();
			params.put("id", edt_id.getText().toString().trim());
			params.put("pw", edt_password1.getText().toString().trim());
			params.put("name", edt_name.getText().toString().trim());
			params.put("phone", tv_phone.getText().toString().trim());
			
			client.post(URL, params, httpResponse);
			break;
		case R.id.cencle:
			break;
		}
	}
	
	public class HttpResponse extends AsyncHttpResponseHandler {

		ProgressDialog dialog;
		
		/* 통신 접속 실패시 호출된다.
		 * @param stateCode 상태코드
		 * @param header	HTTP Header
		 * @param body		HTTP Body
		 * @param error		에러정보 객체
		 */
		@Override
		public void onFailure(int stateCode, Header[] header, byte[] body,
				Throwable error) {
			// TODO Auto-generated method stub
			String errMsg = "State Code: " + stateCode + "\n";
			errMsg += "Error Message: " + error.getMessage();
			
//			tv1.setText(errMsg);
		}

		/* 통신 접속 성공시 호출된다.
		 * @param stateCode	상태코드 (정상결과일 경우 200)
		 * @param header	HTTP Header
		 * @param body		HTTP Body (브라우저에 보여지는 내용)
		 */
		@Override
		public void onSuccess(int stateCode, Header[] header, byte[] body) {
			// TODO Auto-generated method stub
			try {
				String result = new String(body, "UTF-8");
	//			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				if(result.equals("OK"))
				{
					finish();
					Toast.makeText(getApplicationContext(), "축하합니다. 가입되었습니다.", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "다시한번 확인해 주세요.", Toast.LENGTH_LONG).show();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

		/* 성공, 실패 여부에 상관 없이 통신이 종료되면 실행된다. */
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			
			dialog.dismiss();
			dialog = null;
			
			super.onFinish();
		}

		/* 통신 시작시에 실행된다. */
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			dialog = new ProgressDialog(Join.this);
			dialog.setMessage("잠시만 기다려주세요...");
			dialog.setCancelable(false);
			dialog.show();
			
			super.onStart();
		}
		
	} // end class
}
