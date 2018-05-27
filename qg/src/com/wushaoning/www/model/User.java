package com.wushaoning.www.model;

/**
 * ”√ªß¿‡
 * 
 * @author 10620
 *
 */
public class User {
	private int id;
	private String userName;
	private String password;
	private String gender;
	private String phoneNumber;
	private String qq;
	private String wechat;
	private String address;
	private String email;
	private String role;
	private String pwdPtd;
	private String pwdQue;
	private int myBuyCarNum;
	private int integral;
	private int experience;
	private String degree;

	public User() {
		super();
	}

	public User(String email, String pwdPtd, String pwdQue) {
		super();
		this.email = email;
		this.pwdPtd = pwdPtd;
		this.pwdQue = pwdQue;
	}

	public User(int id, String userName, String gender, String phoneNumber, String qq, String wechat, String address,
			String email) {
		super();
		this.id = id;
		this.userName = userName;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.qq = qq;
		this.wechat = wechat;
		this.address = address;
		this.email = email;
	}

	public User(String userName, String password, String gender, String phoneNumber, String qq, String wechat,
			String address, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.qq = qq;
		this.wechat = wechat;
		this.address = address;
		this.email = email;
	}

	public User(int id, String userName, String password, String gender, String phoneNumber, String qq, String wechat,
			String address, String email) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.qq = qq;
		this.wechat = wechat;
		this.address = address;
		this.email = email;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPwdPtd() {
		return pwdPtd;
	}

	public int getMyBuyCarNum() {
		return myBuyCarNum;
	}

	public void setMyBuyCarNum(int myBuyCarNum) {
		this.myBuyCarNum = myBuyCarNum;
	}

	public void setPwdPtd(String pwdPtd) {
		this.pwdPtd = pwdPtd;
	}

	public String getPwdQue() {
		return pwdQue;
	}

	public void setPwdQue(String pwdQue) {
		this.pwdQue = pwdQue;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getqq() {
		return qq;
	}

	public void setqq(String qQ) {
		qq = qQ;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
