package com.senselessweb.money.storage;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.appengine.labs.repackaged.com.google.common.collect.Sets;

@ManagedBean
@SessionScoped
public class AccountStorage implements Serializable
{

	private static final long serialVersionUID = 9015005680419291230L;

	private final Cache cache = new Cache();
	
	/**
	 * Stores a new account activity. If it is already stored, it will be replaced.
	 */
	public void storeAccountActivity(
			final long accountNumber, final DateTime date, String receiver, String description, 
			String reason, String currency, final double amount, final double balance)
	{
		final User user = UserServiceFactory.getUserService().getCurrentUser();
		if (user == null) throw new RuntimeException("No user!!");
		
		receiver = StringUtils.normalizeSpace(receiver);
		description = StringUtils.normalizeSpace(description);
		reason = StringUtils.normalizeSpace(reason);
		currency = StringUtils.normalizeSpace(currency);
		
		final Key key = KeyFactory.createKey("AccountActivity", 
				user.getUserId() + date.getMillis() + receiver + description + reason + currency + amount + balance);
		
		final Entity entity = new Entity(key);
		entity.setProperty("user", user.getUserId());
		entity.setProperty("accountNumber", accountNumber);
		entity.setProperty("date", date.getMillis());
		entity.setProperty("receiver", receiver);
		entity.setProperty("description", description);
		entity.setProperty("reason", reason);
		entity.setProperty("currency", currency);
		entity.setProperty("amount", amount);
		entity.setProperty("balance", balance);
		
		DatastoreServiceFactory.getDatastoreService().put(entity);
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
		final TreeSet<AccountActivity> result = new TreeSet<AccountActivity>();
		for (final Entity entity : this.getAccountActivitiesInternal(accountNumber, from, until))
		{
			result.add(new AccountActivity(
					(Long) entity.getProperty("accountNumber"),
					new DateTime((Long) entity.getProperty("date")),
					(String) entity.getProperty("receiver"),
					(String) entity.getProperty("description"),
					(String) entity.getProperty("reason"),
					(String) entity.getProperty("currency"),
					(Double) entity.getProperty("amount"),
					(Double) entity.getProperty("balance")));
		}
		return Lists.newArrayList(result);
	}

	/**
	 * Internal method to get all (unsorted!) account activity entities. Takes care of user permissions.
	 */
	private Iterable<Entity> getAccountActivitiesInternal(final Long accountNumber, final DateTime from, final DateTime until)
	{
		if (this.cache.contains(accountNumber, from, until))
			return this.cache.get(accountNumber, from, until);
		
		final Query q = new Query("AccountActivity");
		if (accountNumber != null)
			q.setFilter(new FilterPredicate("accountNumber", FilterOperator.EQUAL, accountNumber));
		q.setFilter(new FilterPredicate("user", FilterOperator.EQUAL, UserServiceFactory.getUserService().getCurrentUser().getUserId()));
		if (from != null)
			q.setFilter(new FilterPredicate("date", FilterOperator.GREATER_THAN_OR_EQUAL, from.getMillis()));
		if (until != null)
			q.setFilter(new FilterPredicate("date", FilterOperator.LESS_THAN_OR_EQUAL, from.getMillis()));
		
		final Set<Entity> result = Sets.newHashSet(DatastoreServiceFactory.getDatastoreService().prepare(q).asIterable());
		this.cache.put(accountNumber, from, until, result);
		return result;
	}
	
	public DateTime getEarliestEntryTime()
	{
		long time = Long.MAX_VALUE;
		for (final Entity entity : this.getAccountActivitiesInternal(null, null, null))
			time = Math.min(time, (Long) entity.getProperty("date"));
		return new DateTime(time);
	}
	
	public DateTime getLatestEntryTime()
	{
		long time = 0;
		for (final Entity entity : this.getAccountActivitiesInternal(null, null, null))
			time = Math.max(time, (Long) entity.getProperty("date"));
		return new DateTime(time);
	}
	
	/**
	 * Returns the balance at a specific date.
	 * @return The balance, may be 0 if no useable entries are found. 
	 */
	public double getBalance(final long accountNumber, final ReadableDateTime date)
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
	
	/**
	 * Returns all account numbers for which we have at least one entry 
	 */
	public List<Long> getAccountNumbers()
	{
		final TreeSet<Long> result = new TreeSet<Long>();
		for (final Entity entity : this.getAccountActivitiesInternal(null, null, null))
			result.add((Long) entity.getProperty("accountNumber"));
		return Lists.newArrayList(result);
	}
	
}
