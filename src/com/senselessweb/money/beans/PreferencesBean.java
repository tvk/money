package com.senselessweb.money.beans;

import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.google.common.collect.Lists;
import com.senselessweb.money.storage.Preferences;
import com.senselessweb.money.storage.PreferencesStorage;

@ManagedBean
public class PreferencesBean
{
	@ManagedProperty("#{preferencesStorage}")
	private PreferencesStorage preferencesStorage;
	
	private static final List<String> themes = Lists.newArrayList("afterdark", "afternoon", "afterwork", "black-tie", "blitzer", "bluesky",
			"bootstrap", "casablanca", "cruze", "cupertino", "dark-hive", "dot-luv", "eggplant", "excite-bike", "flick", "glass-x",
			"hot-sneaks", "humanity","le-frog","midnight","mint-choc","overcast","pepper-grinder","redmond","rocket","sam","smoothness",
			"south-street","start","sunny","swanky-purse","trontastic","ui-darkness","ui-lightness","vader","home");
	
	private static final String defaultTheme = "glass-x";
	
	private Preferences preferences;

	public String getTheme()
	{
		final String theme = this.preferences.getTheme();
		return theme != null ? theme : defaultTheme;
	}
	
	public void setTheme(final String theme)
	{
		this.preferences.setTheme(theme);
	}
	
	public Collection<String> getThemes()
	{
		return themes;
	}
	
	public void savePreferences()
	{
		this.preferencesStorage.update(this.preferences);
	}
	
	public void setPreferencesStorage(final PreferencesStorage preferencesStorage)
	{
		this.preferencesStorage = preferencesStorage;
		this.preferences = this.preferencesStorage.getPreferences();
	}
}
