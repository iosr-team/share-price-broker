package pl.edu.agh.iosr.fetchquotes;

import java.util.Collection;

import pl.edu.agh.iosr.stockquote.StockQuoteListener;

public interface StockQuoteFetcher {
	void addPriceListener(StockQuoteListener priceListener);
	
	void addObservedCompanySymbol(String companySymbol);
	void setObservedCompaniesSymbols(Collection<String> companiesSymbols);
}
