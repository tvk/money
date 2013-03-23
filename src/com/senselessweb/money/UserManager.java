package com.senselessweb.money;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="userManager")
@SessionScoped
public class UserManager implements Serializable
{

	public UserManager()
	{
		System.out.println("UserManager::init");
	}
	
	public String getNextPage()
	{
		return "page2";
	}
	
}
