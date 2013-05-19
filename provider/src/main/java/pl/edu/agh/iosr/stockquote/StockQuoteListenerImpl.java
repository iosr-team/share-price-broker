package pl.edu.agh.iosr.stockquote;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class StockQuoteListenerImpl implements StockQuoteListener {
	@Autowired
	private AmqpTemplate amqpTemplate;

	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}
	
	@Override
	public void newStockQuote(StockQuote stockQuote) {
		 amqpTemplate.convertAndSend(stockQuote.getCompanySymbol(), stockQuote.toString());
	}

}
