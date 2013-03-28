package com.senselessweb.money.beans.accounts;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.senselessweb.money.storage.AccountActivity;
import com.senselessweb.money.storage.AccountStorage;

@ManagedBean
@ViewScoped
public class ActivitiesBean implements Serializable
{

	private static final long serialVersionUID = 3513326152700187999L;
	
	@ManagedProperty("#{accountStorage}")
	protected AccountStorage accountStorage;
	
	protected static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");


	public void setAccountStorage(AccountStorage accountStorage)
	{
		this.accountStorage = accountStorage;
	}
	
	private Long account;

	public Long getAccount()
	{
		return this.account;
	}

	public void setAccount(final Long account)
	{
		this.account = account;
	}
	
	public Collection<AccountActivity> getActivities()
	{
		return Lists.reverse(this.accountStorage.getAccountActivities(this.account, null, null));
	}
}
