package com.senselessweb.money.beans.charts;

import java.util.Collection;

import javax.faces.bean.ManagedBean;

import org.joda.time.DateTime;
import org.primefaces.model.chart.MeterGaugeChartModel;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.senselessweb.money.beans.AbstractBean;

@ManagedBean
public class Gauge extends AbstractBean
{

	/**
	 * Represents the model that is send to the frontend.
	 * @author thomas
	 */
	public class Model
	{
		
		private final long accountNumber;
		
		private final double currentBalance;
		
		private final double maximum;
		
		public Model(final long accountNumber, final double currentBalance, final double maximum)
		{
			this.accountNumber = accountNumber;
			this.currentBalance = currentBalance;
			this.maximum = maximum;
		}
		
		public MeterGaugeChartModel getChartModel()
		{
			final MeterGaugeChartModel model = new MeterGaugeChartModel();
			model.setValue(this.currentBalance);
			
			// Find a good maximum: 
			// I.e. for a maximum of 2001, 2100 would be good, for 999 it would be 1000...
			final int multiplicator = (int) Math.pow(10, ((int) Math.log10(maximum)) - 1);
			final int gaugeMax = ((int) maximum / multiplicator + 1) * multiplicator;

			for (int i = 0; i <= gaugeMax; i += gaugeMax/5)
				model.addInterval(i);
			
			return model;
		}
		
		public long getAccountNumber()
		{
			return this.accountNumber;
		}
		
		public double getCurrentBalance()
		{
			return this.currentBalance;
		}
	}

	public Collection<Model> getModels()
	{
		return Collections2.transform(this.accountStorage.getAccountNumbers(), new Function<Long, Model>() 
		{
			public Model apply(final Long accountNumber)
			{
				return new Model(accountNumber, 
						Gauge.this.accountStorage.getBalance(accountNumber, DateTime.now()), 
						Gauge.this.accountStorage.getAbsoluteMaximum(accountNumber));
			}
		});
	}
}
