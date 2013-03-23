package com.senselessweb.money;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

@ManagedBean
public class MySecondBean
{

	@ManagedProperty("#{userManager}")
	private UserManager userManager;

	public MySecondBean()
	{
		System.out.println("MySecondBean::init");
	}

	public String doSomething()
	{
		return this.userManager.getNextPage();
	}

	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}
}
