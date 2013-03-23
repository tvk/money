package com.senselessweb.money.storage;

import java.util.List;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;

@ManagedBean
public class AccountStorage
{
	final DatastoreService datastore = 
			DatastoreServiceFactory.getDatastoreService();

	/**
	 * Stores a new account activity. If it is already stored, it will be replaced.
	 */
	public void storeAccountActivity(
			final long accountNumber, final DateTime date, String receiver, String description, 
			String reason, String currency, final double amount, final double balance)
	{
		receiver = StringUtils.normalizeSpace(receiver);
		description = StringUtils.normalizeSpace(description);
		reason = StringUtils.normalizeSpace(reason);
		currency = StringUtils.normalizeSpace(currency);
		
		final Key key = KeyFactory.createKey("AccountActivity", 
				date.getMillis() + receiver + description + reason + currency + amount + balance);
		
		final Entity entity = new Entity(key);
		entity.setProperty("accountNumber", accountNumber);
		entity.setProperty("date", date.getMillis());
		entity.setProperty("receiver", receiver);
		entity.setProperty("description", description);
		entity.setProperty("reason", reason);
		entity.setProperty("currency", currency);
		entity.setProperty("amount", amount);
		entity.setProperty("balance", balance);
		
		this.datastore.put(entity);
	}
	
	/**
	 * Returns all account activities for a given account number
	 * @param accountNumber
	 * @param from The optional start date
	 * @param until The optional end date
	 * @return All activities sorted by date
	 */
	public List<AccountActivity> getAccountActivities(final long accountNumber, final DateTime from, final DateTime until)
	{
		final Query q = new Query("AccountActivity");
		q.setFilter(new FilterPredicate("accountNumber", FilterOperator.EQUAL, accountNumber));
		if (from != null)
			q.setFilter(new FilterPredicate("date", FilterOperator.GREATER_THAN_OR_EQUAL, from.getMillis()));
		if (until != null)
			q.setFilter(new FilterPredicate("date", FilterOperator.LESS_THAN_OR_EQUAL, from.getMillis()));

		final TreeSet<AccountActivity> result = new TreeSet<AccountActivity>();
		for (final Entity entity : this.datastore.prepare(q).asIterable())
		{
			result.add(new AccountActivity(
					(Long) entity.getProperty("accountNumber"),
					new DateTime((Long) entity.getProperty("date")),
					(String) entity.getProperty("receiver"),
					(String) entity.getProperty("description"),
					(String) entity.getProperty("reason"),
					(String) entity.getProperty("currency"),
					(Long) entity.getProperty("amount"),
					(Long) entity.getProperty("balance")));
		}
		return Lists.newArrayList(result);
	}
	
	/**
	 * Returns the balance at a specific date.
	 * @param accountNumber
	 * @param date
	 * @return The balance, may be 0 if no useable entries are found. 
	 */
	public double getBalance(final long accountNumber, final DateTime date)
	{
		final List<AccountActivity> activities = this.getAccountActivities(accountNumber, null, null);
		if (activities.isEmpty()) return 0;
		
		// Find the first activity that is after the given date and return the balance from the
		// activity before
		for (int i = 0; i < activities.size(); i++)
		{
			if (activities.get(i).getDate().isAfter(date))
				return i == 0 ? activities.get(0).getBalance() : activities.get(i - 1).getBalance(); 
		}
		
		// All activities are before the given date, return the latest known balance
		return activities.get(activities.size() - 1).getBalance();
	}
	
}
