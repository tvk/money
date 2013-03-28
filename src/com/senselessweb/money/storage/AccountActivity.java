package com.senselessweb.money.storage;

import java.util.Date;

import org.joda.time.DateTime;

public class AccountActivity implements Comparable<AccountActivity>
{

	private long accountNumber;
	
	private DateTime dateTime;
	
	private String receiver;
	
	private String description;
	
	private String reason;
	
	private String currency;
	
	/** The amount of this transaction, may be negative */
	private double amount;
	
	/** The amount of money after this transaction */
	private double balance;
	
	public AccountActivity(final long accountNumber,
			final DateTime date, final String receiver, final String description, 
			final String reason, final String currency, final double amount, final double balance)
	{
		this.accountNumber = accountNumber;
		this.dateTime = date;
		this.receiver = receiver;
		this.description = description;
		this.reason = reason;
		this.currency = currency;
		this.amount = amount;
		this.balance = balance;
	}
	
	public AccountActivity()
	{
	}
	
	public long getAccountNumber()
	{
		return this.accountNumber;
	}
	
	public void setAccountNumber(long accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	
	public DateTime getDateTime()
	{
		return this.dateTime;
	}
	
	public void setDateTime(DateTime date)
	{
		this.dateTime = date;
	}
	
	public Date getDate()
	{
		return this.dateTime.toDate();
	}
	
	public String getReceiver()
	{
		return this.receiver;
	}
	
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getReason()
	{
		return this.reason;
	}
	
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	
	public String getCurrency()
	{
		return this.currency;
	}
	
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
	
	public double getAmount()
	{
		return this.amount;
	}
	
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	
	public double getBalance()
	{
		return this.balance;
	}
	
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	
	@Override
	public int compareTo(final AccountActivity o)
	{
		return this.dateTime.compareTo(o.dateTime);
	}
}
