package com.senselessweb.money.storage;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import com.google.appengine.api.datastore.Entity;


class Cache implements Serializable
{
	private static final long serialVersionUID = 7310812373126732753L;

	class Key implements Serializable
	{
		private static final long serialVersionUID = 8237130283992697615L;

		final Long accountNumber;
		
		final DateTime from;
		
		final DateTime until;
		
		public Key(final Long accountNumber, final DateTime from, final DateTime until)
		{
			this.accountNumber = accountNumber;
			this.from = from;
			this.until = until;
		}
		
		@Override
		public boolean equals(final Object obj)
		{
			if (!(obj instanceof Key)) return false;
			final Key other = (Key) obj;
			return new EqualsBuilder()
					.append(this.accountNumber, other.accountNumber)
					.append(this.from, other.from)
					.append(this.until, other.until).isEquals();
		}
		
		@Override
		public int hashCode()
		{
			return new HashCodeBuilder().append(this.accountNumber).append(this.from).append(this.until).hashCode();
		}
	}
	
	private final Map<Key, Collection<Entity>> cache = new HashMap<Key, Collection<Entity>>();
	
	public void put(final Long accountNumber, final DateTime from, final DateTime until, final Collection<Entity> entities)
	{
		this.cache.put(new Key(accountNumber, from, until), entities);
	}
	
	public Collection<Entity> get(final Long accountNumber, final DateTime from, final DateTime until)
	{
		return this.cache.get(new Key(accountNumber, from, until));
	}
	
	public boolean contains(final Long accountNumber, final DateTime from, final DateTime until)
	{
		return this.cache.containsKey(new Key(accountNumber, from, until));
	}
	
}
