package com.senselessweb.money.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.senselessweb.money.storage.AccountStorage;
import com.senselessweb.money.storage.CsvParser;

public class UploadCsvFileServlet extends HttpServlet
{

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = -1905484474977678967L;

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
	{
		if (ServletFileUpload.isMultipartContent(req))
		{
			final long accountNumber = Long.parseLong(req.getParameter("accountNumber"));
			try
			{
				final ServletFileUpload upload = new ServletFileUpload();
				final FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext())
				{
					final FileItemStream item = iter.next();
					if (!item.isFormField()) 
					{
						final CsvParser parser = new CsvParser();
						parser.setAccountStorage(new AccountStorage());
						parser.parseAndStore(item.openStream(), accountNumber);
						resp.getWriter().write("Ok");
					}
				}
			} 
			catch (final Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}
