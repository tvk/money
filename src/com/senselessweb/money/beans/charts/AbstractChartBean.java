package com.senselessweb.money.beans.charts;

import javax.faces.bean.ManagedProperty;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.senselessweb.money.storage.AccountStorage;

public class AbstractChartBean
{

	@ManagedProperty("#{accountStorage}")
	protected AccountStorage accountStorage;
	
	protected final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");


	public void setAccountStorage(AccountStorage accountStorage)
	{
		this.accountStorage = accountStorage;
	}
}
