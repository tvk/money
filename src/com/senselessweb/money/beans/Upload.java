package com.senselessweb.money.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@ManagedBean
public class Upload extends SupportBean
{

	private Long accountNumber;

	public Long getAccountNumber()
	{
		return this.accountNumber;
	}

	public void setAccountNumber(Long accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	
	public List<Long> getAccountNumbers(final String string)
	{
		return Lists.newArrayList(Collections2.filter(this.getAccountNumbers(), new Predicate<Long>() {
			@Override
			public boolean apply(final Long accountNumber)
			{
				return accountNumber.toString().startsWith(string);
			}
		}));
	}
}
