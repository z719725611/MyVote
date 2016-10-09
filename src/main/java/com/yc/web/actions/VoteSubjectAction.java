package com.yc.web.actions;


import java.util.List;





import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.bean.VoteItem;
import com.yc.bean.VoteOption;
import com.yc.bean.VoteSubject;
import com.yc.bean.VoteUser;
import com.yc.dao.YcCommon;
import com.yc.service.VoteService;
import com.yc.services.impl.VoteServiceImpl;
import com.yc.web.model.JsonModel;

@Namespace("/")
@ParentPackage("mypackage")
@InterceptorRefs({ @InterceptorRef("defaultStack"),
	@InterceptorRef(value="relog",params={"includeMethods","findByVsid"})
})
public class VoteSubjectAction extends ActionSupport implements ModelDriven<VoteSubject>{
	private VoteService voteService = new VoteServiceImpl();
	private VoteSubject voteSubject;
	private JsonModel jsonModel;
	private HttpSession session;
	
	private static final long serialVersionUID = -2010534249945450348L;
	
	public VoteSubjectAction(){
		session=ServletActionContext.getRequest().getSession();
	}
	
	@Action(value = "/voteUser_findAll", results = @Result(type = "json", name = "success", params = {
			"root", "jsonModel", "excludeNullProperties", "true",
			"noCache", "true" }))
	public String findAll() {
		jsonModel = new JsonModel();
		try {
			List<VoteSubject> list=voteService.getAllSubjects();
			
			session.setAttribute("subjectlist", list);
			
			jsonModel.setCode(1);
			jsonModel.setObj(list);
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/voteSubject_getLoginUser", results = @Result(type = "json", name = "success", params = {
			"root", "jsonModel", "excludeNullProperties", "true",
			"noCache", "true" }))
	public String getLoginUser() {
		jsonModel = new JsonModel();
		if(session.getAttribute(YcCommon.User)!=null){
			VoteUser vu=(VoteUser) session.getAttribute(YcCommon.User);
			jsonModel.setCode(1);
			jsonModel.setObj(vu);
		}else{
			jsonModel.setCode(0);
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/voteSubject_findByVsid", results = @Result(type = "json", name = "success", params = {
			"root", "jsonModel", "excludeNullProperties", "true",
			"noCache", "true" }))
	public String findByVsid() {
		jsonModel = new JsonModel();
		List<VoteSubject> subjectlist=(List<VoteSubject>) session.getAttribute("subjectlist");
		Long vsid=voteSubject.getVsid();
		VoteSubject vs=null;
		
		for (VoteSubject voteSubject : subjectlist) {
			if(voteSubject.getVsid().longValue()==vsid.longValue()){
				vs=voteSubject;
				break;
			}
		}
		try{
			//查处所有的选项
			List<VoteOption> optionlist=voteService.findAllOption(vsid);
			jsonModel.setCode(1);
			vs.setOptions(optionlist);
			
			session.setAttribute(YcCommon.VOTESUBJECT, vs);
			
			jsonModel.setObj(vs);
		}catch (Exception e){
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setObj(e.getMessage());
		}
		
		return ActionSupport.SUCCESS;
	}
	
	
	
	@Action(value = "/voteSubject_findByVsidW", results = @Result(type = "json", name = "success", params = {
			"root", "jsonModel", "excludeNullProperties", "true",
			"noCache", "true" }))
	public String findByVsidW() {
		jsonModel = new JsonModel();
		try {
			//从session中取出subject
			VoteSubject vs=(VoteSubject) session.getAttribute(YcCommon.VOTESUBJECT);
			//根据vsid编号查询这个主题中所有的投票信息
			List<VoteItem> list=voteService.statVoteCountPerOptionOfSubject(vs.getVsid());
			for(VoteItem vi:list){
				for(int i=0;i<vs.getOptions().size();i++){
					VoteOption vo=vs.getOptions().get(i);
					if(vo.getVoteid()==vi.getVoteid()){
						vo.setVotecount(vi.getVotecount());
						vs.getOptions().set(i, vo);
					}
				}
			}
			
			jsonModel.setCode(1);
			jsonModel.setObj(vs);
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/voteSubject_vote", results = @Result(type = "json", name = "success", params = {
			"root", "jsonModel", "excludeNullProperties", "true",
			"noCache", "true" }))
	public String vote() {
		VoteSubject vs=(VoteSubject) session.getAttribute(YcCommon.VOTESUBJECT);
		VoteUser loginuser=(VoteUser) session.getAttribute(YcCommon.User);
		jsonModel = new JsonModel();
		try {
			//查看当前用户是否对这一项投过票了
			boolean flag=voteService.isUserVote(loginuser.getUid(),vs.getVsid());
			if(flag==true){
				jsonModel.setCode(0);
				jsonModel.setMsg("you have vote once");
				return ActionSupport.SUCCESS;
			}
			
			List<Long> chooseIds=voteSubject.getChooseIds();
			if(chooseIds==null|| chooseIds.size()<=0){
				jsonModel.setCode(0);
				jsonModel.setMsg("please choose at least ont choice");
				return ActionSupport.SUCCESS;
			}
			
			voteService.saveVoteItem(vs.getVsid(),chooseIds,loginuser.getUid());
			jsonModel.setCode(1);
			
			//再修改session中votesubject中的对应的用户数和每个选项的投票数
			vs.setUsercount(vs.getUsercount()+1);
			
			for (Long id : chooseIds) {
				for(int i=0;i<vs.getOptions().size();i++){
					VoteOption vo=vs.getOptions().get(i);
					if(id.longValue()==vo.getVoteid()){
						vo.setVotecount((vo.getVotecount()==null?0:vo.getVotecount())+1);
					}
					vs.getOptions().set(i, vo);
				}
			}
			session.setAttribute(YcCommon.VOTESUBJECT, vs);
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg("error"+e.getMessage());
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/voteSubject_add", results = @Result(type = "json", name = "success", params = {
			"root", "jsonModel", "excludeNullProperties", "true",
			"noCache", "true" }))
	public String add() {
		jsonModel = new JsonModel();
		try {
			voteService.saveOrUpdate(voteSubject);
			
			jsonModel.setCode(1);
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return ActionSupport.SUCCESS;
	}

	
	
	public JsonModel getJsonModel() {
		return jsonModel;
	}

	public void setJsonModel(JsonModel jsonModel) {
		this.jsonModel = jsonModel;
	}

	public VoteSubject getModel() {
		 voteSubject=new VoteSubject();
		return voteSubject;
	}
	
	
	
}
