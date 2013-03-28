package com.senselessweb.money.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.senselessweb.money.storage.AccountStorage;

@ManagedBean
public class SupportBean
{

	@ManagedProperty("#{accountStorage}")
	protected AccountStorage accountStorage;
	
	protected static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");


	public void setAccountStorage(AccountStorage accountStorage)
	{
		this.accountStorage = accountStorage;
	}
	
	public List<Long> getAccountNumbers()
	{
		return this.accountStorage.getAccountNumbers();
	}
}
