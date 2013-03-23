package com.senselessweb.money.beans.charts;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.senselessweb.money.storage.AccountStorage;

@ManagedBean
public class AccountBalanceAsLineChart
{

	@ManagedProperty("#{accountStorage}")
	private AccountStorage accountStorage;
	
	private final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

	private Date startDate;

	private Date endDate;

	public CartesianChartModel getModel()
	{
		final CartesianChartModel model = new CartesianChartModel();
		final DateTime start = this.startDate != null ? 
				new DateTime(this.startDate.getTime()) : this.accountStorage.getEarliestEntryTime();
		final DateTime end = this.endDate != null ? 
				new DateTime(this.endDate.getTime()) : this.accountStorage.getLatestEntryTime();
		final List<DateMidnight> representiveDates = RepresentiveDates.getRepresentiveDates(start, end, 20);		
		
		for (final Long accountNumber : this.accountStorage.getAccountNumbers())
		{
			final ChartSeries chartSeries = new ChartSeries(String.valueOf(accountNumber));
			for (final DateMidnight date : representiveDates)
			{
				chartSeries.set(formatter.print(date), this.accountStorage.getBalance(accountNumber, date));
			}
			model.addSeries(chartSeries);
		}
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
