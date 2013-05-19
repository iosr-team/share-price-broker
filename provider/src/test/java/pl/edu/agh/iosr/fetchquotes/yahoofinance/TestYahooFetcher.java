package pl.edu.agh.iosr.fetchquotes.yahoofinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import pl.edu.agh.iosr.stockquote.StockQuote;
import pl.edu.agh.iosr.stockquote.StockQuoteListener;

public class TestYahooFetcher {

	class ListenerMock implements StockQuoteListener {
		public List<StockQuote> stockQuotes = Collections
				.synchronizedList(new ArrayList<StockQuote>());

		@Override
		public void newStockQuote(StockQuote stockQuote) {
			stockQuotes.add(stockQuote);
		}

	}

	class StringQuoteSource implements QuoteSource {

		@Override
		public BufferedReader getBufferedReader(
				Collection<String> companiesSymbols) throws IOException {
			Assert.assertTrue(companiesSymbols.contains("GOOG"));
			Assert.assertTrue(companiesSymbols.contains("NVDA"));
			String reply = 
					"\"GOOG\",909.18,\"5/17/2013\",\"4:00pm\",+5.31,910.02,913.489,900.52,2792663\n" +
					"\"NVDA\",14.87,\"5/17/2013\",\"4:00pm\",+0.24,14.72,14.97,14.59,7803782\n";
			return new BufferedReader(new StringReader(reply));
		}

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testRun() {
		YahooFinanceStockQuoteFetcher fetcher = new YahooFinanceStockQuoteFetcher();
		fetcher.setQuoteSource(new StringQuoteSource());
		ListenerMock listener = new ListenerMock();
		fetcher.addQuoteListener(listener);
		fetcher.addObservedCompanySymbol("GOOG");
		fetcher.addObservedCompanySymbol("NVDA");
		fetcher.fetchQuotes();
		StockQuote stockQuote = listener.stockQuotes.get(0);
		Assert.assertEquals("GOOG", stockQuote.getCompanySymbol());
		Assert.assertEquals(909.18, stockQuote.getValue(), 0.01);
		Assert.assertEquals(16, stockQuote.getDate().getHours());
		Assert.assertEquals(17, stockQuote.getDate().getDate());
		stockQuote = listener.stockQuotes.get(1);
		Assert.assertEquals("NVDA", stockQuote.getCompanySymbol());
		Assert.assertEquals(14.87, stockQuote.getValue(), 0.01);
		Assert.assertEquals(16, stockQuote.getDate().getHours());
		Assert.assertEquals(17, stockQuote.getDate().getDate());
	}

}
