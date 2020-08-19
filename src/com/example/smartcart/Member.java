package com.example.smartcart;

import android.app.Application;

public class Member extends Application {
	
	private String id;
	private String name = "noname";
	private String phone;
	private int point;
	
	public Member()
	{
		
	}
	public Member(String id, String name, String phone, int point) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.point = point;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	public void logout()
	{
		this.id = "";
		this.name = "noname";
		this.phone = "";
		this.point = 0;
	}
}
