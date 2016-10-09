package com.yc.bean;

//用户选择的选项
public class VoteItem {
	private Integer viid; //编号
	private Integer vsid; //主题
	private Integer voteid;//用户选择的选项
	private Integer uid; //用户
	
	public Integer votecount;
	private String voteoption;
	
	
	
	public Integer getVotecount() {
		return votecount;
	}
	public void setVotecount(Integer votecount) {
		this.votecount = votecount;
	}
	public String getVoteoption() {
		return voteoption;
	}
	public void setVoteoption(String voteoption) {
		this.voteoption = voteoption;
	}
	public Integer getViid() {
		return viid;
	}
	public void setViid(Integer viid) {
		this.viid = viid;
	}
	public Integer getVsid() {
		return vsid;
	}
	public void setVsid(Integer vsid) {
		this.vsid = vsid;
	}
	public Integer getVoteid() {
		return voteid;
	}
	public void setVoteid(Integer voteid) {
		this.voteid = voteid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "VoteItem [viid=" + viid + ", vsid=" + vsid + ", voteid="
				+ voteid + ", uid=" + uid + ", votecount=" + votecount
				+ ", voteoption=" + voteoption + "]";
	}
	
	
	
	
}
