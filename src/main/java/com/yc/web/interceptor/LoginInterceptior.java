package com.yc.web.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.yc.bean.VoteUser;
import com.yc.web.actions.VoteUserAction;

public class LoginInterceptior extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		String t=sdf.format(d);
		
		//从invocation中取出登录名
		Object obj=invocation.getAction();
		VoteUserAction vua=(VoteUserAction) obj;
		VoteUser user= vua.getVoteUser();
		String uname=user.getUname();
		
		System.out.println("--------------------");
		System.out.println("用户"+uname+"于"+t+"登录系统");
		System.out.println("--------------------");
		return invocation.invoke();//通过这里 action才会调用对应的方法
	}

}
