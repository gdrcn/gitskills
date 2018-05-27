package com.wushaoning.www.model;

import java.sql.Blob;

/**
 * 交易的实体类
 * @author 10620
 *
 */
public class Transaction {
	private int id;
	private int userId;
	private int businessmanId;
	private int goodsId;
	private int shopId;
	private int starLevel;
	private String comment;
	private String reply;
	private String userName;
	private Blob evidence;
	private int complaintId;
	private String complaintTxt;
	private String completed;
	

	public Transaction() {
		super();
	}
	public Transaction(int complaintId, int id, int userId, Blob evidence, String complaintTxt) {
		super();
		this.complaintId = complaintId;
		this.id = id;
		this.userId = userId;
		this.evidence = evidence;
		this.complaintTxt = complaintTxt;
	}
	public Transaction(int complaintId, int id, int userId, Blob evidence, String complaintTxt, String completed) {
		super();
		this.complaintId = complaintId;
		this.id = id;
		this.userId = userId;
		this.evidence = evidence;
		this.complaintTxt = complaintTxt;
		this.completed = completed;
	}
	public Transaction(int id, int userId, String comment, int starLevel, String reply, String userName) {
		super();
		this.id = id;
		this.userId = userId;
		this.comment = comment;
		this.starLevel = starLevel;
		this.reply = reply;
		this.userName = userName;
	}
	public int getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}
	public int getUserId() {
		return userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBusinessmanId() {
		return businessmanId;
	}
	public void setBusinessmanId(int businessmanId) {
		this.businessmanId = businessmanId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Blob getEvidence() {
		return evidence;
	}
	public void setEvidence(Blob evidence) {
		this.evidence = evidence;
	}
	public String getComplaintTxt() {
		return complaintTxt;
	}
	public void setComplaintTxt(String complaintTxt) {
		this.complaintTxt = complaintTxt;
	}
	public int getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}
	public String getcompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
}
