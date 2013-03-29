package com.senselessweb.money.storage;

/**
 * User preferences
 * @author thomas
 */
public class Preferences
{

	private String theme;
	
	Preferences()
	{
	}
	
	public Preferences(final String theme)
	{
		this.theme = theme;
	}

	public void setTheme(final String theme)
	{
		this.theme = theme;
	}
	
	public String getTheme()
	{
		return this.theme;
	}
}
