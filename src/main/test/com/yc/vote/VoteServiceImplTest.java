package com.yc.vote;

import java.util.List;

import com.yc.bean.VoteItem;
import com.yc.bean.VoteUser;
import com.yc.service.VoteService;
import com.yc.services.impl.VoteServiceImpl;

import junit.framework.TestCase;

public class VoteServiceImplTest extends TestCase {

	public void testGetUserCountPerSubject() {
		VoteService vs=new VoteServiceImpl();
		List list=vs.getUserCountPerSubject(null);
		System.out.println(list);
	}
	/**
	 * 获取所有投票的主题
	 * @return
	 */
	public void testGetAllSubjects() {
		VoteService vs=new VoteServiceImpl();
		List list=vs.getAllSubjects();
		System.out.println(list);
		
	}
		//TODO:sql语句还有问题
	public void testGetSubjectsByTitle() throws Exception {
		VoteService vs=new VoteServiceImpl();
		List list=vs.getSubjectsByTitle("中国的首都是?");
		System.out.println(list);
	}

	public void testFindSubjectById() {
		
	}

	public void testFindOptionById() {
		fail("Not yet implemented");
	}

	public void testSaveOrUpdateVoteSubject() {
		fail("Not yet implemented");
	}

	public void testDeleteSubject() {
		fail("Not yet implemented");
	}

	public void testFindUserById() {
		fail("Not yet implemented");
	}

	public void testSaveOrUpdateVoteItem() {
		fail("Not yet implemented");
	}

	public void testStatVoteCountPerOptionOfSubject() throws Exception {
		VoteService vs=new VoteServiceImpl();
		List<VoteItem> list=vs.statVoteCountPerOptionOfSubject(3L);
		System.out.println(list);
	}

	public void testSaveOrUpdateVoteUser() {
		VoteService vs=new VoteServiceImpl();
		
	/*	List<VoteUser> list=vs.saveOrUpdate();
		System.out.println(list);*/
	}

}
