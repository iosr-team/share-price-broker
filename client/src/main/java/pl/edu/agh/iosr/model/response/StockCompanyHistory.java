package pl.edu.agh.iosr.model.response;

import pl.edu.agh.iosr.model.entity.StockQuote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockCompanyHistory {
    private String label;
    private List<List<Object>> data = new ArrayList<List<Object>>();

    public StockCompanyHistory(){

    }

    public StockCompanyHistory(String label, List<StockQuote> stockQuotes){
        this.init(label, stockQuotes);
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public void init(String label, List<StockQuote> stockQuotes){
        this.label = label;
        if(stockQuotes != null){
            for(StockQuote stockQuote : stockQuotes){
                data.add(Arrays.asList(new Object[] {stockQuote.getDate().getTime(), stockQuote.getValue()}));
            }
        }
    }
}
