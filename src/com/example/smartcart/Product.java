package com.example.smartcart;

public class Product {

	private String id;
	private String img;
	private String name;
	private int pay;
	private int num;
	private String com;
	private boolean temp;
	private int money;
	private boolean own;
	private boolean order;

	public Product(String id, String name, int money, boolean own, boolean order, int num) {
		super();
		this.id = id;
		this.name = name;
		this.money = money;
		this.own = own;
		this.order = order;
		this.num = num;
	}

	public boolean getTemp() {
		return temp;
	}

	public void setTemp(boolean temp) {
		this.temp = temp;
	}

	public String getId() {
		return id;
	}

	public int getMoney() {
		return money;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getOwn() {
		return own;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", img=" + img + ", name=" + name + ", pay=" + pay + ", num=" + num + ", com="
				+ com + "]";
	}

	public Product(String id, String img, String name, int pay, int num, String com) {
		super();
		this.id = id;
		this.img = img;
		this.name = name;
		this.pay = pay;
		this.num = num;
		this.com = com;
	}

}
