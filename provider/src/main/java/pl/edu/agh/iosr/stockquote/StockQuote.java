package pl.edu.agh.iosr.stockquote;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StockQuote {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private String companySymbol; 
	
	private double value;
	
	private Date date;	
	
	public Date getDate() {
		return date;
	}

	public double getValue() {
		return value;
	}	

	public String getCompanySymbol() {
		return companySymbol;
	}

	public void setCompanySymbol(String companySymbol) {
		this.companySymbol = companySymbol;
	}

	public StockQuote(String companySymbol, Date date, double value) {
		super();
		this.companySymbol = companySymbol;
		this.value = value;
		this.date = date;
	}
	
	@Override
	public String toString() {
		return companySymbol + "#" + value + "#" + new SimpleDateFormat(DATE_FORMAT).format(date);
	}
	

}
