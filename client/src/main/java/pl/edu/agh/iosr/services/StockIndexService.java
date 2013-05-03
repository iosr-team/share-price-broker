package pl.edu.agh.iosr.services;

import pl.edu.agh.iosr.model.entity.Role;
import pl.edu.agh.iosr.model.entity.StockIndex;

import java.util.List;

public interface StockIndexService {
    StockIndex getStockIndex(Long id);

    StockIndex getStockIndexByName(String name);

    StockIndex createStockIndex(StockIndex stockIndex);
    
    List<StockIndex> getAllStockIndices();
}
