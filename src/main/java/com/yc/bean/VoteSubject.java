package com.yc.bean;

import java.util.ArrayList;
import java.util.List;

public class VoteSubject {
	public final static int TYPE_SINCE=1; //单选
	public final static int TYPE_MULTIPE=2;//多选
	
	private Long vsid;//编号
	private String title;//标题
	private int stype=1; //类型
	private Integer optioncount;
	private Integer usercount;
	
	private List<VoteOption> options=new ArrayList<VoteOption>();
	private List<Long> chooseIds=new ArrayList<Long>();
	
	private List<String> voteoptions=new ArrayList<String>();
	
	
	
	public List<String> getVoteoptions() {
		return voteoptions;
	}
	public void setVoteoptions(List<String> voteoptions) {
		this.voteoptions = voteoptions;
	}
	public List<VoteOption> getOptions() {
		return options;
	}
	public void setOptions(List<VoteOption> options) {
		this.options = options;
	}
	public List<Long> getChooseIds() {
		return chooseIds;
	}
	public void setChooseIds(List<Long> chooseIds) {
		this.chooseIds = chooseIds;
	}
	public Long getVsid() {
		return vsid;
	}
	public void setVsid(Long vsid) {
		this.vsid = vsid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStype() {
		return stype;
	}
	public void setStype(int stype) {
		this.stype = stype;
	}
	
	public int getOptioncount() {
		return optioncount;
	}
	public void setOptioncount(Integer optioncount) {
		this.optioncount = optioncount;
	}
	public int getUsercount() {
		return usercount;
	}
	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}
	@Override
	public String toString() {
		return "VoteSubject [vsid=" + vsid + ", title=" + title + ", stype="
				+ stype + ", optioncount=" + optioncount + ", usercount="
				+ usercount + ", options=" + options + ", chooseIds="
				+ chooseIds + ", voteoptions=" + voteoptions + "]";
	}
	
	
	
	
}
