package com.senselessweb.money.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserAuthentificatedFilter implements Filter
{
	
	private UserService userService;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		this.userService = UserServiceFactory.getUserService();
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse resp, 
			final FilterChain chain) throws IOException, ServletException
	{
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) resp;

		if (this.userService.isUserLoggedIn() || request.getServletPath().startsWith("/_ah/"))
		{
			chain.doFilter(request, response);
		}
		else
		{
			// User needs to athentificate first
			response.sendRedirect(this.userService.createLoginURL(request.getRequestURI()));
		}
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}


}
