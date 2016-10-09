package com.yc.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.yc.bean.VoteItem;
import com.yc.bean.VoteOption;
import com.yc.bean.VoteSubject;
import com.yc.bean.VoteUser;

public interface VoteService {
	
	public List getUserCountPerSubject(Long id);
	
	
	
	/**
	 * 获取所有投票的主题
	 * @return
	 */
	public List<VoteSubject> getAllSubjects();
	/**
	 * 根据主题的标题查找主题
	 * @return
	 * @throws Exception 
	 */
	public List<VoteSubject> getSubjectsByTitle(String title) throws Exception;
	/**
	 * 根据主题的编号查主题的内容
	 * @param id
	 * @param lock
	 * @return
	 */
	public VoteSubject findSubjectById(Long id);
	
	/**
	 * 根据id查选项
	 * @param id
	 * @return
	 */
	public VoteOption findOptionById(Long id);
	
	/**
	 * 更新或保存主题
	 * @param subject
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void saveOrUpdate(VoteSubject subject) throws SQLException, IOException;
	
	/**
	 * 根据编号删除主题(投票->选项->主题)
	 * @param id
	 * @throws SQLException 
	 */
	public void deleteSubject(Long id) throws SQLException;
	/**
	 * 根据id查找用户
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public VoteUser findUserById(String uname) throws Exception;
	
	/**
	 *更新或保存投票项
	 * @param voteitem
	 * @throws SQLException 
	 */
	public void saveOrUpdate(VoteItem voteitem) throws SQLException;
	/**
	 * 某个主题的每个投票项的投票数
	 * @param entityId
	 * @return
	 * @throws Exception 
	 */
	public List statVoteCountPerOptionOfSubject(Long entityId) throws Exception;
	
	/**
	 * 更新或保存用户
	 * @param user
	 * @throws SQLException 
	 */
	public void saveOrUpdate(VoteUser user) throws SQLException;



	public List<VoteOption> findAllOption(Long vsid) throws Exception;



	public boolean isUserVote(Integer uid, Long vsid) throws Exception;



	public void saveVoteItem(Long vsid, List<Long> chooseIds, int uid) throws SQLException;
}
