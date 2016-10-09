package com.yc.bean;

//主题下的选项
public class VoteOption {
	private Integer voteid;//编号
	private String voteoption;//选项名   如：北京
	private Integer voteorder;//显示顺序
	private Integer vsid;//对应的主题  如：中国的首都是？
	
	public Integer votecount;
	
	public Integer getVotecount() {
		return votecount;
	}
	public void setVotecount(Integer votecount) {
		this.votecount = votecount;
	}
	public Integer getVoteid() {
		return voteid;
	}
	public void setVoteid(Integer voteid) {
		this.voteid = voteid;
	}
	public String getVoteoption() {
		return voteoption;
	}
	public void setVoteoption(String voteoption) {
		this.voteoption = voteoption;
	}
	public Integer getVoteorder() {
		return voteorder;
	}
	public void setVoteorder(Integer voteorder) {
		this.voteorder = voteorder;
	}
	public Integer getVsid() {
		return vsid;
	}
	public void setVsid(Integer vsid) {
		this.vsid = vsid;
	}
	@Override
	public String toString() {
		return "VoteOption [voteid=" + voteid + ", voteoption=" + voteoption
				+ ", voteorder=" + voteorder + ", vsid=" + vsid
				+ ", votecount=" + votecount + "]";
	}
	
	
}
