package com.example.smartcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private final static String TAG = "MainActivity";
	
	LinearLayout back;
	ImageButton btn_order, btn_payment;
	TextView hello;
	Button btn_join;
	Button btn_login;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(TAG, "onCreate");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        back = (LinearLayout) findViewById(R.id.back);
        
        btn_order = (ImageButton) findViewById(R.id.cart);
        btn_order.setOnClickListener(this);
        
        hello = (TextView) findViewById(R.id.hello);
        
        btn_join = (Button) findViewById(R.id.join);
        btn_join.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.login);
        btn_login.setOnClickListener(this);        
    }
  	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.cart:
			if(!IsLogin()) return;
			Intent cart = new Intent(this, Order.class);
			startActivity(cart);
			break;
		case R.id.join:
			Intent join = new Intent(this, Join.class);
			startActivity(join);
			break;
		case R.id.login:
			Intent login = new Intent(this, Login.class);
			startActivity(login);
			break;
		}
	}
	public boolean IsLogin() {
		// TODO Auto-generated method stub
		Member member = (Member) getApplicationContext();
		if(member.getName().equals("noname"))
		{
			Toast.makeText(this, "로그인을 먼저 해주세요.", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Member member = (Member) getApplicationContext();
		if(!member.getName().equals("noname"))
			hello.setText(member.getName()+"님 환영합니다.");
	}
}
