package com.senselessweb.money.beans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.senselessweb.money.storage.CsvParser;

@ManagedBean
public class UploadCsv
{

	@ManagedProperty("#{csvParser}")
	private CsvParser csvParser;

	private Long accountNumber;

	private UploadedFile file;

	public void setFile(UploadedFile file)
	{
		this.file = file;
	}

	public void handleFileUploadEvent(FileUploadEvent file)
	{
		this.file = file.getFile();
	}
	
	public UploadedFile getFile()
	{
		return this.file;
	}

	public void setAccountNumber(Long accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public Long getAccountNumber()
	{
		return this.accountNumber;
	}
	
	public void store() throws IOException
	{
		this.csvParser.parseAndStore(this.file.getInputstream(), accountNumber);
	}

	public void setCsvParser(CsvParser csvParser)
	{
		this.csvParser = csvParser;
	}

}
