package com.senselessweb.money.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import au.com.bytecode.opencsv.CSVReader;

public class CsvParser
{

	private static final Logger log = Logger.getLogger(CsvParser.class.getCanonicalName());

	private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");

	/**
	 * Reads the account data from the given csv input
	 * 
	 * @param csv The csv file.
	 * @param accountNumber
	 */
	public void parseAndStore(final AccountStorage accountStorage, 
			final InputStream csv, final long accountNumber)
	{
		try
		{
			final CSVReader reader = new CSVReader(new InputStreamReader(csv, Charset.forName("ISO-8859-1")), ';');
			for (String[] line = reader.readNext(); line != null; line = reader.readNext())
				this.parseAndStore(accountStorage, accountNumber, line);
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
	private void parseAndStore(final AccountStorage accountStorage, 
			final long accountNumber, final String[] line)
	{
		if (line.length == 9)
		{
			try
			{
				accountStorage.storeAccountActivity(accountNumber, DateTime.parse(line[0], fmt), 
						line[2], line[3], line[4], line[6],
						parseMoneyAmount(line[5]), parseMoneyAmount(line[7]));
			} catch (final Exception e)
			{
				log.info("Could not read line " + Arrays.toString(line));
			}
		}
	}
	
	private static double parseMoneyAmount(final String amount)
	{
		final String parsed = amount.replaceAll("\\.", "").replaceAll(",", ".");
		return Double.parseDouble(parsed);
	}
}
