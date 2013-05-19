package pl.edu.agh.iosr.services;

import pl.edu.agh.iosr.model.entity.StockQuote;

import java.util.List;

public interface StockQuoteService {
    StockQuote getStockQuote(Long id);

    StockQuote createStockQuote(StockQuote stockQuote);
    
    List<StockQuote> getAllStockQuotes();

    List<StockQuote> getStockQuotesForCompany(String companySymbol);
}
