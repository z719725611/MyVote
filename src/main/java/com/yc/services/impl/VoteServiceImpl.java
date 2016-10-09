package com.yc.services.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.bean.VoteItem;
import com.yc.bean.VoteOption;
import com.yc.bean.VoteSubject;
import com.yc.bean.VoteUser;
import com.yc.dao.DBUtil;
import com.yc.service.VoteService;
import com.yc.utils.LogUtils;

public class VoteServiceImpl implements VoteService {
	private DBUtil db = new DBUtil();

	public List getUserCountPerSubject(Long id) {
		StringBuffer sb = new StringBuffer();

		List<Object> params = new ArrayList<Object>();
		sb.append(" select a.vsid,a.title,a.stype,a.usercount ,ifnull(b.optioncount,0) as optioncount from( ");
		sb.append(" select votesubject.vsid,votesubject.title,votesubject.stype,count(DISTINCT(uid)) as usercount from voteitem ");
		sb.append(" right join votesubject on voteitem.vsid=votesubject.vsid group by voteitem.vsid, votesubject.title ");
		sb.append(" ) a left join( ");
		sb.append(" select votesubject.vsid, count( * ) as optioncount from votesubject inner join voteoption on voteoption.vsid=votesubject.vsid ");
		sb.append(" group by votesubject.vsid ");
		sb.append(" ) b on a.vsid=b.vsid  ");

		if (id != null && id > 0) {
			sb.append(" where a.vsid=? ");
			params.add(id);
		}

		List<VoteSubject> list = null;
		try {
			list = db.find(sb.toString(), params, VoteSubject.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		LogUtils.logger.debug(sb.toString());
		return list;
	}

	/**
	 * 获取所有投票的主题
	 * 
	 * @return
	 */
	public List<VoteSubject> getAllSubjects() {
		return this.getUserCountPerSubject(null);
	}

	public List<VoteSubject> getSubjectsByTitle(String title) throws Exception {

		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append(" select a.vsid,a.title,a.stype,a.countoption countoption ,ifnull(b.countuser,0) countuser ");
		sb.append(" from ( select vs.vsid ,count(vo.voteid) countoption ,vs.title,vs.stype ");
		sb.append(" from votesubject vs left join voteoption vo on ");
		sb.append(" vo.vsid = vs.vsid group by vo.vsid ,vs.title,vs.stype ");
		sb.append(") a left join (	");
		sb.append(" select vsid,count(distinct(uid)) countuser from  voteitem ");
		sb.append(" group by vsid  )b ");
		sb.append(" on a.vsid = b.vsid ");
		if (title != null && !"".equals(title)) {
			sb.append(" where a.title like %?%");
			params.add(title);
		}
		System.out.println(sb.toString());
		return db.find(sb.toString(), params, VoteSubject.class);
	}

	public VoteSubject findSubjectById(Long id) {
		String sql = "select * from votesubject where vsid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		List<VoteSubject> list = null;
		try {
			list = db.find(sql, params, VoteSubject.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public VoteOption findOptionById(Long id) {
		String sql = "select * from voteoption where voteid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		List<VoteOption> list = null;
		try {
			list = db.find(sql, params, VoteOption.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public void saveOrUpdate(VoteSubject subject) throws SQLException, IOException {
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		if (subject != null && subject.getVsid() != null) {
			params.add(subject.getTitle());
			params.add(subject.getStype());
			params.add(subject.getVsid());
			sql= "update votesubject set title=?,stype=? where vsid=?";
			db.doUpdate(sql, params);
		} else {//先查主题编号
			sql="select max(vsid) as maxvsid from votesubject";
			Map<String,Object> map=db.doQueryOne(sql, null);
			Integer vsid=1;
			if(map.get("maxvsid")!=null){
				vsid=(Integer)map.get("maxvsid")+1;
			}
			List<String> sqls=new ArrayList<String>();
			List<List<Object>> paramsList=new ArrayList<List<Object>>();
			sql = "insert into votesubject(title,stype) values(?,?)";
			sqls.add(sql);
			
			params.add(subject.getTitle());
			params.add(subject.getStype());
			paramsList.add(params);
			
			int order=0;
			for (String option: subject.getVoteoptions()) {
				order++;
				sql="insert into voteoption ( voteoption,voteorder,vsid) values(?,?,?)";
				sqls.add(sql);
				
				List<Object> p=new ArrayList<Object>();
				p.add(option);
				p.add(order);
				p.add(vsid);
				
				paramsList.add(p);
 			}
			db.doUpdate(sqls, paramsList);
		}
		
	}

	public void deleteSubject(Long id) throws SQLException {
		List<String> sqls = new ArrayList<String>();
		List<List<Object>> params = new ArrayList<List<Object>>();
		sqls.add("delete from vateitem where vsid=?");
		List<Object> p = new ArrayList<Object>();
		p.add(id);
		params.add(p);

		sqls.add("delete from votesubject where vsid=?");
		p = new ArrayList<Object>();
		p.add(id);
		params.add(p);

		db.doUpdate(sqls, params);

	}

	public VoteUser findUserById(String uname) throws Exception {
		String sql = "select * from voteuser where uname=?";
		List<Object> params = new ArrayList<Object>();
		params.add(uname);
		List<VoteUser> list = db.find(sql, params, VoteUser.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public void saveOrUpdate(VoteItem item) throws SQLException {
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		if (item != null && item.getViid() != null) {
			sql = "update voteitem set vsid=?,uid=?,voteid=? where viid=?";
			params.add(item.getVsid());
			params.add(item.getUid());
			params.add(item.getVoteid());
			params.add(item.getViid());
		} else {
			sql = "insert into voteitem (vsid,uid,voteid) values(?,?,?)";
			params.add(item.getVsid());
			params.add(item.getUid());
			params.add(item.getVoteid());
		}
		DBUtil.doUpdate(sql, params);

	}

	public List statVoteCountPerOptionOfSubject(Long id)
			throws Exception {
		/*List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();

		if (id == null || id <= 0) {
			return null;
		}
		sb.append("select vo.voteid,vo.voteoption,vo.vsid,vo.voteorder,ifnull(count(distinct(vi.uid)),0) countoption ");
		sb.append(" from voteoption vo left join voteitem vi ");
		sb.append("	on vo.voteid = vi.voteid ");
		sb.append(" where vo.vsid = ? group by vo.voteid");

		params.add(id);*/
		StringBuffer sql=new StringBuffer("select a.voteid,a.voteoption,ifnull(b.votecount,0) as votecount from ");
		sql.append("(select vsid,voteid,voteoption from voteoption where vsid="+id+")a ");
		sql.append(" left join ");
		sql.append("(select vsid,voteid,count(voteid) as votecount from voteitem ");
		sql.append(" where vsid="+id);
		sql.append(" group by voteid ");
		sql.append(" )b ");
		sql.append(" on a.voteid=b.voteid ");
		return db.find(sql.toString(), null, VoteItem.class);
	}

	public void saveOrUpdate(VoteUser user) throws SQLException {
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		if (user != null && user.getUid() != null) {
			params.add(user.getUname());
			params.add(user.getPwd());
			params.add(user.getUid());
			sql = "update voteuser set uname=?,pwd =? where uid=?";
		} else {
			sql = "insert into voteuser (uname,pwd) values(?,?)";
			params.add(user.getUname());
			params.add(user.getPwd());
		}
		db.doUpdate(sql, params);
	}

	public List<VoteOption> findAllOption(Long vsid) throws Exception {
		String sql="select * from voteoption where vsid="+vsid;
		List<VoteOption> optionlist=db.find(sql, null, VoteOption.class);
		return optionlist;
	}

	public boolean isUserVote(Integer uid, Long vsid) throws Exception{
		String sql="select count(*) as num from voteitem where vsid="+vsid+" and uid="+uid;
		Map<String,Object> map=db.doQueryOne(sql, null);
		if(map!=null&&Long.parseLong(map.get("num").toString())>=1){
			return true;
		}else{
			return false;
		}
	}

	public void saveVoteItem(Long vsid, List<Long> votelist, int uid) throws SQLException {
		List<String> sqls=new ArrayList<String>();
		List<List<Object>> params=new ArrayList<List<Object>>();
		
		for (Long voteid : votelist) {
			String sql="insert into voteitem(voteid,vsid,uid) values(?,?,?)";
			List<Object> p=new ArrayList<Object>();
			p.add(voteid);
			p.add(vsid);
			p.add(uid);
			
			sqls.add(sql);
			params.add(p);
		}
		db.doUpdate(sqls, params);
	}

}
