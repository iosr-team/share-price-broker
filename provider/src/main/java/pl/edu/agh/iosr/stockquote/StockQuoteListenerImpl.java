package pl.edu.agh.iosr.stockquote;

import org.springframework.amqp.core.AmqpTemplate;

public class StockQuoteListenerImpl implements StockQuoteListener {
	
	private AmqpTemplate amqpTemplate;

	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}
	
	public StockQuoteListenerImpl() {
		super();
	}
	
	public StockQuoteListenerImpl(AmqpTemplate amqpTemplate) {
		setAmqpTemplate(amqpTemplate);
	}
	
	@Override
	public void newStockQuote(StockQuote stockQuote) {
		 amqpTemplate.convertAndSend(stockQuote.getCompanySymbol(), stockQuote.toString());
	}

}
