package com.senselessweb.money.beans.charts;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

public class RepresentiveDates
{

	/**
	 * Returns representive dates between the given start and end and tries to match the 
	 * desired number of steps.
	 */
	public static List<DateMidnight> getRepresentiveDates(
			final DateTime startDate, final DateTime endDate, final int desiredSteps)
	{

		final DateMidnight start = startDate.toDateMidnight();
		final long numberOfDays = (endDate.getMillis() - startDate.getMillis()) / (1000*60*60*24);
		final long numberOfWeeks = (endDate.getMillis() - startDate.getMillis()) / (1000*60*60*24*7);
		final long numberOfMonths = (endDate.getMillis() - startDate.getMillis()) / (1000*60*60*24*7*12);
		final List<DateMidnight> result = new ArrayList<DateMidnight>();
		result.add(start);
		
		// Only a few days...
		if (numberOfDays <= desiredSteps)
		{
			for (DateMidnight step = start.plusDays(1); step.isBefore(endDate); step = step.plusDays(1))
				result.add(step);
		}
		// A few weeks
		else if (numberOfWeeks <= desiredSteps)
		{
			for (DateMidnight step = start.plusWeeks(1); step.isBefore(endDate); step = step.plusWeeks(1))
				result.add(step);
		}
		// A few months
		else if (numberOfMonths <= desiredSteps)
		{
			for (DateMidnight step = start.plusMonths(1); step.isBefore(endDate); step = step.plusMonths(1))
				result.add(step);
		}
		// Add all years otherwise
		else 
		{
			for (DateMidnight step = start.plusYears(1); step.isBefore(endDate); step = start.plusYears(1))
				result.add(step);
		}
		
		result.add(endDate.toDateMidnight());
		return result;

	}
}
