package com.senselessweb.money.storage;

import org.joda.time.DateTime;

public class AccountActivity implements Comparable<AccountActivity>
{

	private final long accountNumber;
	
	private final DateTime date;
	
	private final String receiver;
	
	private final String description;
	
	private final String reason;
	
	private final String currency;
	
	/** The amount of this transaction, may be negative */
	private final double amount;
	
	/** The amount of money after this transaction */
	private final double balance;
	
	public AccountActivity(final long accountNumber,
			final DateTime date, final String receiver, final String description, 
			final String reason, final String currency, final double amount, final double balance)
	{
		this.accountNumber = accountNumber;
		this.date = date;
		this.receiver = receiver;
		this.description = description;
		this.reason = reason;
		this.currency = currency;
		this.amount = amount;
		this.balance = balance;
	}
	
	public long getAccountNumber()
	{
		return this.accountNumber;
	}
	
	public DateTime getDate()
	{
		return this.date;
	}
	
	public String getReceiver()
	{
		return this.receiver;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getReason()
	{
		return this.reason;
	}
	
	public String getCurrency()
	{
		return this.currency;
	}
	
	public double getAmount()
	{
		return this.amount;
	}
	
	public double getBalance()
	{
		return this.balance;
	}
	
	@Override
	public int compareTo(final AccountActivity o)
	{
		return this.date.compareTo(o.date);
	}
}
