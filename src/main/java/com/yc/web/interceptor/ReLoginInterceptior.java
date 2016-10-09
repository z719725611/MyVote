package com.yc.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.yc.bean.VoteUser;
import com.yc.dao.YcCommon;
import com.yc.web.actions.VoteSubjectAction;
import com.yc.web.actions.VoteUserAction;
import com.yc.web.model.JsonModel;


public class ReLoginInterceptior extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		/*Object obj=invocation.getAction();
		VoteSubjectAction vua=(VoteSubjectAction) obj;
		VoteUser user= vua.getVoteUser();
		if(user!=null&&"".equals(user)){
			
			return invocation.invoke();//通过这里 action才会调用对应的方法
		}else{
		System.out.println("请登录");
		return null;
		}*/
							//ActionContext.getContext().getSession()
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute(YcCommon.User)==null){
			HttpServletResponse response=ServletActionContext.getResponse();
			JsonModel jm=new JsonModel();
			jm.setCode(0);
			jm.setObj("you have not been logined");
			
			response.setContentType("text/plain;charset=utf-8");
			
			PrintWriter pw=response.getWriter();
			
			Gson g=new Gson();
			String jsonString=g.toJson(jm);
			pw.println(jsonString);
			pw.println();
			pw.close();
		}
		
		return invocation.invoke();//通过这里 action才会调用对应的方法
		
	}

}
