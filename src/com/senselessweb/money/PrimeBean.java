package com.senselessweb.money;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
public class PrimeBean
{

	public CartesianChartModel getModel()
	{
		final CartesianChartModel model = new CartesianChartModel();
		
		final ChartSeries chartSeries1 = new ChartSeries("label1");
		chartSeries1.set("first", Math.random() * 10);
		chartSeries1.set("second", Math.random() * 10);
		chartSeries1.set("third", Math.random() * 10);
		model.addSeries(chartSeries1);
		
		final ChartSeries chartSeries2 = new ChartSeries("label2");
		chartSeries2.set("first", Math.random() * 10);
		chartSeries2.set("second", Math.random() * 10);
		chartSeries2.set("third", Math.random() * 10);
		model.addSeries(chartSeries2);
		
		return model;
	}
	
}
