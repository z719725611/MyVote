package com.yc.web.actions;

import java.sql.SQLException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.bean.VoteUser;
import com.yc.dao.YcCommon;
import com.yc.service.VoteService;
import com.yc.services.impl.VoteServiceImpl;
import com.yc.web.model.JsonModel;

@Namespace("/")
@ParentPackage("mypackage")
@InterceptorRefs({ @InterceptorRef("defaultStack"), @InterceptorRef("time"),
		@InterceptorRef(value = "log", params = { "includeMethods", "login" }) })
public class VoteUserAction extends ActionSupport implements
		ModelDriven<VoteUser> {
	private VoteService voteService = new VoteServiceImpl();
	private VoteUser voteUser;
	private JsonModel jsonModel;
	private static final long serialVersionUID = -2010534249945450348L;

	@Action(value = "/voteUser_register", results = @Result(type = "json", name = "res", params = {
			"root", "jsonModel", "excludeNullProperties", "true", "noCache",
			"true" }))
	public String register() {
		if (!voteUser.getPwd().equals(voteUser.getConfirmPwd())) {
			jsonModel.setCode(0);
			jsonModel.setMsg("pwd not equals confirmpwd");
			return "res";
		}

		jsonModel = new JsonModel();
		try {
			voteService.saveOrUpdate(voteUser);
			jsonModel.setCode(1);
		} catch (SQLException e) {
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg("user does not exist");
		}
		return "res";
	}

	@Action(value = "/voteUser_login", results = @Result(type = "json", name = "loginSuccess", params = {
			"root", "jsonModel", "excludeNullProperties", "true", "noCache",
			"true" }))
	public String login() {
		jsonModel = new JsonModel();
		try {
			VoteUser user = voteService.findUserById(voteUser.getUname());
			if (user == null) {
				jsonModel.setCode(0);
				jsonModel.setMsg("user does not exist");
			} else {
				if (user.getPwd().equals(voteUser.getPwd())) {
					ActionContext.getContext().getSession()
							.put(YcCommon.User, user);
					jsonModel.setCode(1);
				} else {
					jsonModel.setCode(0);
					jsonModel.setMsg("user does not exist");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return "loginSuccess";

	}

	public VoteUser getModel() {
		voteUser = new VoteUser();
		return voteUser;
	}

	public JsonModel getJsonModel() {
		return jsonModel;
	}

	public void setJsonModel(JsonModel jsonModel) {
		this.jsonModel = jsonModel;
	}

	public VoteUser getVoteUser() {
		return voteUser;
	}
}
