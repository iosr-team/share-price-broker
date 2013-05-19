package pl.edu.agh.iosr.fetchquotes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.edu.agh.iosr.stockquote.StockQuoteListener;
import pl.edu.agh.iosr.stockquote.StockQuote;

public abstract class StockQuoteFetcherAdapter implements StockQuoteFetcher {
	private List<StockQuoteListener> listeners = new ArrayList<StockQuoteListener>();
	
	@Override
	public void addQuoteListener(StockQuoteListener quoteListener) {
		listeners.add(quoteListener);
	}
	
	protected Collection<StockQuoteListener> getListeners() {
		return listeners;
	}

	protected void newStockQuote(StockQuote stockQuote) {
		for(StockQuoteListener listener : listeners)
			listener.newStockQuote(stockQuote);
	}
	
	private List<String> observedCompaniesSymbols = new ArrayList<String>();

	@Override
	public void setObservedCompaniesSymbols(Collection<String> companiesSymbols) {
		observedCompaniesSymbols.clear();
		for(String companySymbol : companiesSymbols)
			addObservedCompanySymbol(companySymbol);
	}

	@Override
	public final void addObservedCompanySymbol(String companySymbol) {
		observedCompaniesSymbols.add(companySymbol);		
	}
	
	protected Collection<String> getObservedCompaniesSymbols() {
		return observedCompaniesSymbols;
	}
}
