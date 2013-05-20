package pl.edu.agh.iosr.msg;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import pl.edu.agh.iosr.model.entity.StockQuote;
import pl.edu.agh.iosr.services.StockCompanyService;
import pl.edu.agh.iosr.services.StockQuoteService;
import pl.edu.agh.iosr.services.TenantService;

public class StockQuoteListener implements MessageListener {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private final Logger log = LoggerFactory
			.getLogger(StockQuoteListener.class);

	private String tenantName;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private StockCompanyService stockCompanyService;

    @Autowired
	@Qualifier("stockQuoteService")
	private StockQuoteService stockQuoteService;

	@Override
	public void onMessage(Message message) {
		// FOMRAT: company_symbol#value#date
		String msg = new String(message.getBody());
		log.debug("RECEIVED MESSAGE (" + this.tenantName + "): " + msg);

		try {
			String[] parts = msg.split("#");

			StockQuote stockQuote = new StockQuote();

			stockQuote.setStockCompany(stockCompanyService
                    .getStockCompany(parts[0]));
			stockQuote.setValue(Double.parseDouble(parts[1]));
			stockQuote.setDate(new SimpleDateFormat(DATE_FORMAT).parse(parts[2]));

			stockQuote
					.setTenant(tenantService.getTenantByName(this.tenantName));

			stockQuoteService.createStockQuote(stockQuote);
		} catch (Exception e) {
			log.error("ERROR WHILE RECEIVING MESSAGE", e);
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

	public StockCompanyService getStockCompanyService() {
		return stockCompanyService;
	}

	public void setStockCompanyService(StockCompanyService stockCompanyService) {
		this.stockCompanyService = stockCompanyService;
	}

    public void setStockQuoteService(StockQuoteService stockQuoteService) {
        this.stockQuoteService = stockQuoteService;
    }
}
