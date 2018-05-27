package com.wushaoning.www.model;

import java.sql.Blob;

/**
 * 商品实体类
 * 
 * @author 10620
 *
 */
public class Goods {
	private String goodsName;
	private int goodsId;
	private String function;
	private int price;
	private Blob picture;
	private String tags;
	private int shopId;
	private int num;
	private int salesVolume;
	private String goodsComment;
	private String discount;
	private float starLevel;
	
	
	public Goods() {
		super();
	}
	
	public Goods(String tags, String price, String num, String function, String goodsName, int shopId) {
		super();
		this.tags = tags;
		this.price = Integer.parseInt(price);
		this.num = Integer.parseInt(num);
		this.function = function;
		this.shopId = shopId;
		this.goodsName = goodsName;
	}

	public Goods(int goodsId, String function, String tags, int price, Blob picture, int shopId, int num,
			String goodsName, int salesVolume) {
		super();
		this.goodsName = goodsName;
		this.goodsId = goodsId;
		this.function = function;
		this.price = price;
		this.picture = picture;
		this.tags = tags;
		this.shopId = shopId;
		this.num = num;
		this.salesVolume = salesVolume;
	}
	
	public Goods(String goodsName, int goodsId, String function, int price, Blob picture, String tags, int shopId,
			int num, int salesVolume) {
		super();
		this.goodsName = goodsName;
		this.goodsId = goodsId;
		this.function = function;
		this.price = price;
		this.picture = picture;
		this.tags = tags;
		this.shopId = shopId;
		this.num = num;
		this.salesVolume = salesVolume;
	}

	public Goods(String goodsName, int goodsId, String function, int price, Blob picture, String tags, int shopId,
			int num) {
		super();
		this.goodsName = goodsName;
		this.goodsId = goodsId;
		this.function = function;
		this.price = price;
		this.picture = picture;
		this.tags = tags;
		this.shopId = shopId;
		this.num = num;
	}
	
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public float getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(float starLevel) {
		this.starLevel = starLevel;
	}



	public String getGoodsName() {
		return goodsName;
	}

	public String getGoodsComment() {
		return goodsComment;
	}

	public void setGoodsComment(String goodsComment) {
		this.goodsComment = goodsComment;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

}
