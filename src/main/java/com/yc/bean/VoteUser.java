package com.yc.bean;

import java.io.Serializable;

public class VoteUser implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	
	private Integer uid;//用户编号
	private String uname;//用户名
	private String pwd;//用户密码
	
	private String confirmPwd;
	
	
	
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "VoteUser [uid=" + uid + ", uname=" + uname + ", pwd=" + pwd
				+ "]";
	}
	
	
}
