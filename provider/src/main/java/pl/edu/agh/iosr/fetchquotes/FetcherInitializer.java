package pl.edu.agh.iosr.fetchquotes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import pl.edu.agh.iosr.stockquote.StockQuoteListener;
import pl.edu.agh.iosr.stockquote.StockQuoteListenerImpl;

public class FetcherInitializer implements InitializingBean  {
	
	private final Logger log = LoggerFactory.getLogger(FetcherInitializer.class);
	
	private List<String> companiesSymbols;

	private StockQuoteFetcher stockQuoteFetcher;
	
	@Autowired
	AmqpTemplate amqpTemplate;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("FETCHER INITIALIZATION STARTED");
		
		StockQuoteListener stockQuoteListener = new StockQuoteListenerImpl(amqpTemplate);
		
		stockQuoteFetcher.setObservedCompaniesSymbols(companiesSymbols);
		stockQuoteFetcher.addPriceListener(stockQuoteListener);
		
		new Thread(stockQuoteFetcher).run();
	}
	
	public void setCompaniesSymbols(List<String> companiesSymbols) {
		this.companiesSymbols = companiesSymbols;
	}

	public void setStockQuoteFetcher(StockQuoteFetcher stockQuoteFetcher) {
		this.stockQuoteFetcher = stockQuoteFetcher;
	}

}
