package com.wushaoning.www.model;

import java.sql.Blob;

/**
 * 购物车实体类
 * @author 10620
 *
 */
public class BuyCar {
	private int id;
	private int myId;
	private int theGoodsId;
	private Blob theGoodsImg;
	private int myBuyNum;
	private int theShopId;
	private String theGoodsName;
	private int theGoodsPrice;
	private String myAlipay;
	private String myAddress;
	private String myPhoneNumber;
	private String situation;
	private int integral;
	
	public BuyCar(int alterGoodsid, int buyNum, String phone, String apay, String address) {
		super();
		this.id =  alterGoodsid;
		this.myBuyNum = buyNum;
		this.myPhoneNumber = phone;
		this.myAlipay = apay;
		this.myAddress = address;
	}
	
	public BuyCar(int myId, int theGoodsId, Blob theGoodsImg, int myBuyNum, int theShopId, String theGoodsName,
			int theGoodsPrice, String myAlipay, String myAddress, String myPhoneNumber, String situation, int id) {
		super();
		this.myId = myId;
		this.theGoodsId = theGoodsId;
		this.theGoodsImg = theGoodsImg;
		this.myBuyNum = myBuyNum;
		this.theShopId = theShopId;
		this.theGoodsName = theGoodsName;
		this.theGoodsPrice = theGoodsPrice;
		this.myAlipay = myAlipay;
		this.myAddress = myAddress;
		this.myPhoneNumber = myPhoneNumber;
		this.situation = situation;
		this.id = id;
	}
	public BuyCar(int myId, int theGoodsId, Blob theGoodsImg, int myBuyNum, int theShopId, String theGoodsName,
			int theGoodsPrice, String myAlipay, String myAddress, String myPhoneNumber, String situation) {
		super();
		this.myId = myId;
		this.theGoodsId = theGoodsId;
		this.theGoodsImg = theGoodsImg;
		this.myBuyNum = myBuyNum;
		this.theShopId = theShopId;
		this.theGoodsName = theGoodsName;
		this.theGoodsPrice = theGoodsPrice;
		this.myAlipay = myAlipay;
		this.myAddress = myAddress;
		this.myPhoneNumber = myPhoneNumber;
		this.situation = situation;
	}
	
	public BuyCar(int id, int theGoodsId, Blob theGoodsImg, int myBuyNum, String theGoodsName, int theGoodsPrice, int theShopId) {
		super();
		this.id = id;
		this.theGoodsId = theGoodsId;
		this.theGoodsImg = theGoodsImg;
		this.myBuyNum = myBuyNum;
		this.theGoodsName = theGoodsName;
		this.theGoodsPrice = theGoodsPrice;
		this.theShopId = theShopId;
	}
	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getMyId() {
		return myId;
	}
	public void setMyId(int myId) {
		this.myId = myId;
	}
	public int getTheGoodsId() {
		return theGoodsId;
	}
	public void setTheGoodsId(int theGoodsId) {
		this.theGoodsId = theGoodsId;
	}
	public Blob getTheGoodsImg() {
		return theGoodsImg;
	}
	public void setTheGoodsImg(Blob theGoodsImg) {
		this.theGoodsImg = theGoodsImg;
	}
	public int getMyBuyNum() {
		return myBuyNum;
	}
	public void setMyBuyNum(int myBuyNum) {
		this.myBuyNum = myBuyNum;
	}
	public int getTheShopId() {
		return theShopId;
	}
	public void setTheShopId(int theShopId) {
		this.theShopId = theShopId;
	}
	public String getTheGoodsName() {
		return theGoodsName;
	}
	public void setTheGoodsName(String theGoodsName) {
		this.theGoodsName = theGoodsName;
	}
	public int getTheGoodsPrice() {
		return theGoodsPrice;
	}
	public void setTheGoodsPrice(int theGoodsPrice) {
		this.theGoodsPrice = theGoodsPrice;
	}
	public String getMyAlipay() {
		return myAlipay;
	}
	public void setMyAlipay(String myAlipay) {
		this.myAlipay = myAlipay;
	}
	public String getMyAddress() {
		return myAddress;
	}
	public void setMyAddress(String myAddress) {
		this.myAddress = myAddress;
	}
	public String getMyPhoneNumber() {
		return myPhoneNumber;
	}
	public void setMyPhoneNumber(String myPhoneNumber) {
		this.myPhoneNumber = myPhoneNumber;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	
}
