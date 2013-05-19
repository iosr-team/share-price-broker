package pl.edu.agh.iosr.fetchquotes.yahoofinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import pl.edu.agh.iosr.fetchquotes.StockQuoteFetcherAdapter;
import pl.edu.agh.iosr.stockquote.StockQuote;

public class YahooFinanceStockQuoteFetcher extends StockQuoteFetcherAdapter {
	public static final int MINUTES_INTERVAL = 5;
	public static final String DATE_FORMAT = "\"M/d/yyyy\"\"h:mma\"";
	
	@Autowired
	private QuoteSource quoteSource;
	
	public void setQuoteSource(QuoteSource quoteSource) {
		this.quoteSource = quoteSource;
	}
	
	private void sleep() {
		try {
			Thread.sleep(60*1000*MINUTES_INTERVAL);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void fetchQuotes() {
		try {
			BufferedReader in = quoteSource.getBufferedReader(getObservedCompaniesSymbols());
			String line;
			while ((line = in.readLine()) != null) {
				String[] parts = line.split(",");
				DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
				
				StockQuote stockQuote = new StockQuote();
				stockQuote.setCompanySymbol(parts[0].substring(1, parts[0].length() - 1));
				stockQuote.setValue(Double.parseDouble(parts[1]));
				try {
					stockQuote.setDate(dateFormat.parse(parts[2]+parts[3]));
				}catch(ParseException e) {
					stockQuote.setDate(new Date());
					e.printStackTrace();
				}
				
				newStockQuote(stockQuote);
			}
	        in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			fetchQuotes();
			sleep();
		}
	}
}
