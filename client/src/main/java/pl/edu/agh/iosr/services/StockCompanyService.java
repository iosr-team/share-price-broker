package pl.edu.agh.iosr.services;

import java.util.List;

import pl.edu.agh.iosr.model.entity.StockCompany;

public interface StockCompanyService {
	StockCompany getStockCompany(String symbol);

	StockCompany getStockCompanyByName(String name);

	StockCompany createStockCompany(StockCompany stockCompany);

	List<StockCompany> getAllStockCompanies();
	
	List<StockCompany> getObservedStockCompanies();
	
	StockCompany merge(StockCompany stockCompany);
	
	void removeStockCompanyBySymbol(String symbol);
}
