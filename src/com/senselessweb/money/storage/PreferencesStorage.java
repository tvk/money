package com.senselessweb.money.storage;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

@ManagedBean
@ApplicationScoped
public class PreferencesStorage implements Serializable
{
	private static final long serialVersionUID = 6692717629276029106L;

	public Preferences getPreferences()
	{
		final User user = UserServiceFactory.getUserService().getCurrentUser();
		if (user != null)
		{
			final Query q = new Query("Preferences");
			q.setFilter(new FilterPredicate("user", FilterOperator.EQUAL, user.getUserId()));
			final Entity entity = DatastoreServiceFactory.getDatastoreService().prepare(q).asSingleEntity();
			if (entity != null) 
				return new Preferences((String) entity.getProperty("theme")); 
		}
		return new Preferences();
	}
	
	public void update(final Preferences preferences)
	{
		final User user = UserServiceFactory.getUserService().getCurrentUser();
		if (user != null) 
		{
			final Key key = KeyFactory.createKey("Preferences", user.getUserId());
			final Entity e = new Entity(key);
			e.setProperty("theme", preferences.getTheme());
			e.setProperty("user", user.getUserId());
			DatastoreServiceFactory.getDatastoreService().put(e);
		}
	}
}
