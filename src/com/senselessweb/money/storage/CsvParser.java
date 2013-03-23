package com.senselessweb.money.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import au.com.bytecode.opencsv.CSVReader;

@ManagedBean
public class CsvParser
{

	private static final Logger log = Logger.getLogger(CsvParser.class.getCanonicalName());

	private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");

	@ManagedProperty("#{accountsStorage}")
	private AccountStorage accountStorage;

	/**
	 * Reads the account data from the given csv input
	 * 
	 * @param csv The csv file.
	 * @param accountNumber
	 */
	public void parseAndStore(final InputStream csv, final long accountNumber)
	{
		try
		{
			final CSVReader reader = new CSVReader(new InputStreamReader(csv), ';');
			for (String[] line = reader.readNext(); line != null; line = reader.readNext())
				this.parseAndStore(accountNumber, line);
			try
			{
				reader.close();
			} catch (final IOException e)
			{
			}
		} catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Reads (safely) a single entry
	 * 
	 * @param accountNumber
	 * @param line
	 */
	private void parseAndStore(final long accountNumber, final String[] line)
	{
		if (line.length == 9)
		{
			try
			{
				final Number amount = NumberFormat.getInstance().parse(line[5]);
				final Number balance = NumberFormat.getInstance().parse(line[7]);

				this.accountStorage.storeAccountActivity(accountNumber, DateTime.parse(line[0], fmt), line[2], line[3], line[4], line[6],
						amount.doubleValue(), balance.doubleValue());
			} catch (final Exception e)
			{
				log.info("Could not read line " + Arrays.toString(line));
			}
		}
	}

	public void setAccountStorage(AccountStorage accountStorage)
	{
		this.accountStorage = accountStorage;
	}
}
