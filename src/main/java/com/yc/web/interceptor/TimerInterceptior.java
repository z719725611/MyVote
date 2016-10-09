package com.yc.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class TimerInterceptior implements Interceptor {

	public void destroy() {
		
		
	}

	public void init() {
		
		
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		long startTime=System.currentTimeMillis();
		String result=invocation.invoke();  //运行action
		long exetime=System.currentTimeMillis()-startTime;
		System.out.println("Action:"+invocation.getAction().getClass().getName()+exetime);
		
		
		return result;
	}

}
