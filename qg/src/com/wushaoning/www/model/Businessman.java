package com.wushaoning.www.model;

import java.sql.Blob;

/**
 * 商家的实体类
 * @author 10620
 *
 */
public class Businessman extends User{
	private String identification;
	private String alipay;
	private Blob idphoto;
	private String promotion;
	private int shop;
	
	public Businessman(String identification, String alipay, Blob idphoto, String promotion) {
		super();
		this.identification = identification;
		this.alipay = alipay;
		this.idphoto = idphoto;
		this.promotion = promotion;
	}
	
	public Businessman() {
		super();
	}

	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	public Blob getIdphoto() {
		return idphoto;
	}
	public void setIdphoto(Blob idphoto) {
		this.idphoto = idphoto;
	}
	public int getShop() {
		return shop;
	}
	public void setShop(int shop) {
		this.shop = shop;
	}


	
}
