package com.senselessweb.money.beans.charts;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.senselessweb.money.beans.AbstractBean;

@ManagedBean
public class AccountBalanceAsLineChart extends AbstractBean
{

	private Date startDate;

	private Date endDate;
	
	@PostConstruct
	private void init()
	{
		this.startDate = this.accountStorage.getEarliestEntryTime() == null ? null : 
			this.accountStorage.getEarliestEntryTime().toDate();
		this.endDate = this.accountStorage.getLatestEntryTime() == null ? null :
			this.accountStorage.getLatestEntryTime().toDate();
	}

	public CartesianChartModel getModel()
	{
		final CartesianChartModel model = new CartesianChartModel();
		final DateTime start = this.startDate != null ? 
				new DateTime(this.startDate.getTime()) : this.accountStorage.getEarliestEntryTime();
		final DateTime end = this.endDate != null ? 
				new DateTime(this.endDate.getTime()) : this.accountStorage.getLatestEntryTime();
		final List<DateMidnight> representiveDates = RepresentiveDates.getRepresentiveDates(start, end, 120);		
		
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
}
