package com.senselessweb.money.beans.charts;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.primefaces.model.chart.CartesianChartModel;

import com.senselessweb.money.storage.AccountStorage;

@ManagedBean
public class AccountBalanceAsLineChart
{

	@ManagedProperty("#{accountStorage}")
	private AccountStorage accountStorage;

	private Date startDate;

	private Date endDate;

	public CartesianChartModel getModel()
	{
		final CartesianChartModel model = new CartesianChartModel();
		final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

		return model;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getStartDate()
	{
		return this.startDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Date getEndDate()
	{
		return this.endDate;
	}

	public void setAccountStorage(AccountStorage accountStorage)
	{
		this.accountStorage = accountStorage;
	}
}
