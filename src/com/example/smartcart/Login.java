package com.example.smartcart;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Login extends Activity implements OnClickListener {
	
	private final String URL = "http://youndk.dothome.co.kr/UML/login.php";
	
	AsyncHttpClient client;
	HttpResponse httpResponse;
	
	Member member;
	
	EditText edt_id;
	EditText edt_password;
	
	Button btn_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		client = new AsyncHttpClient();
		httpResponse = new HttpResponse();
		
		member = (Member) getApplicationContext();
		
		edt_id = (EditText) findViewById(R.id.id);
		edt_password = (EditText) findViewById(R.id.password);
		
		btn_ok = (Button) findViewById(R.id.ok);
		btn_ok.setOnClickListener(this);
		
	}
	
	/* 통신 결과를 받아서 처리할 클래스 - inner class 형태로 정의한다. */
	public class HttpResponse extends JsonHttpResponseHandler {
		
		ProgressDialog dialog;
		
		/* 통신은 이루어 졌으나 서버에서 에러코드를 반환할 경우 호출된다.
		 * @param stateCode	상태코드 (HTTP 상태코드 값이 전달된다. 404, 500)
		 * @param header	HTTP Header
		 * @param error		에러정보 객체
		 */
		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			// TODO Auto-generated method stub
			String errMsg = "State Code: " + statusCode + "\n";
			errMsg += "Error Message: " + throwable.getMessage();
			
//			tv1.setText(errMsg);
			Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
			super.onFailure(statusCode, headers, responseString, throwable);
		}

		/* 통신 접속 실패시 호출된다.
		 * @param stateCode	상태코드 (0이 전달된다.)
		 * @param header	HTTP Header
		 * @param error		에러정보 객체
		 */
		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, JSONObject errorResponse) {
			// TODO Auto-generated method stub
			String errMsg = "State Code: " + statusCode + "\n";
			errMsg += "Error Message: " + throwable.getMessage();
			
//			tv1.setText(errMsg);
			Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}

		/* 통신 성공시 호출된다.
		 * @param stateCode	상태코드
		 * @param header	HTTP Header
		 * @param response	서버의 응답 내용
		 */
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			
			try {
				
				JSONArray results = response.getJSONArray("results");
				JSONObject re = results.getJSONObject(0);
				String name = re.getString("name");
				String phone = re.getString("phone");
				int point = Integer.parseInt(re.getString("point"));
				member.setId(edt_id.getText().toString());
				member.setName(name);
				member.setPhone(phone);
				member.setPoint(point);
				Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
				finish();
	//			tv1.setText(statusCode+"/"+name+"/"+type);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/* 성공, 실패 여부에 상관 없이 통신이 종료되면 실행된다. */
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
			dialog.dismiss();
			dialog = null;
		}

		/* 통신 시작시에 실행된다. */
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			dialog = new ProgressDialog(Login.this);
			dialog.setMessage("잠시만 기다려주세요...");
			dialog.setCancelable(false);
			dialog.show();
			super.onStart();
		}
		
	} // end class

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.ok:
			RequestParams params = new RequestParams();
			params.put("id", edt_id.getText().toString().trim());
			params.put("pw", edt_password.getText().toString().trim());
			
			client.post(URL, params, httpResponse);
			break;
		}
	}
}
