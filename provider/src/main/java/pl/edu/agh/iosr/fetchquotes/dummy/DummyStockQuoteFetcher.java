package pl.edu.agh.iosr.fetchquotes.dummy;

import java.util.Date;

import pl.edu.agh.iosr.fetchquotes.StockQuoteFetcherAdapter;
import pl.edu.agh.iosr.stockquote.StockQuote;

public class DummyStockQuoteFetcher extends StockQuoteFetcherAdapter {
	
	@Override
	public void run() {
		for(String companySymbol : getObservedCompaniesSymbols())
			newStockQuote(new StockQuote(companySymbol, new Date(), 8788));
	}

}
