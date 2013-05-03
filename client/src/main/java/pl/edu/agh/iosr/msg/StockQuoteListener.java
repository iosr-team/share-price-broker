package pl.edu.agh.iosr.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import pl.edu.agh.iosr.model.entity.StockQuote;
import pl.edu.agh.iosr.services.StockIndexService;
import pl.edu.agh.iosr.services.StockQuoteService;
import pl.edu.agh.iosr.services.TenantService;

import java.util.Date;

public class StockQuoteListener implements MessageListener{

    private final Logger log = LoggerFactory.getLogger(StockQuoteListener.class);

    private String tenantName;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private StockIndexService stockIndexService;

    @Autowired
    @Qualifier("stockQuoteService")
    private StockQuoteService stockQuoteService;

    @Override
    public void onMessage(Message message) {
        //TODO: this is only a mock
        //TODO: message format and transformation into StockQuote

        // MOCK FOMRAT: index_name#company_name#value#change
        String msg = new String(message.getBody());
        log.debug("RECEIVED MESSAGE ("+this.tenantName+"): "+msg);

        try{
            String[] parts = msg.split("#");

            StockQuote stockQuote = new StockQuote();
            stockQuote.setDate(new Date());

            stockQuote.setStockIndex(stockIndexService.getStockIndexByName(parts[0]));
            stockQuote.setCompanyName(parts[1]);
            stockQuote.setValue(Double.parseDouble(parts[2]));
            stockQuote.setChange(Double.parseDouble(parts[3]));

            stockQuote.setTenant(tenantService.getTenantByName(this.tenantName));

            stockQuoteService.createStockQuote(stockQuote);
        }catch(Exception e){
            log.error("ERROR WHILE RECEIVING MESSAGE",e);
        }
    }

    public String getTenantName() {
        return tenantName;
    }

    @Required
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public TenantService getTenantService() {
        return tenantService;
    }

    public void setTenantService(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    public StockIndexService getStockIndexService() {
        return stockIndexService;
    }

    public void setStockIndexService(StockIndexService stockIndexService) {
        this.stockIndexService = stockIndexService;
    }
}
