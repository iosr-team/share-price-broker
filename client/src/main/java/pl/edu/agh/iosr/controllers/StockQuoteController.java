package pl.edu.agh.iosr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.iosr.model.entity.StockQuote;
import pl.edu.agh.iosr.services.StockQuoteService;

@Controller
public class StockQuoteController {
	
	//private final Logger log = LoggerFactory.getLogger(StockQuoteController.class);

    @Autowired
    @Qualifier("stockQuoteServiceWithTenant")
    private StockQuoteService stockQuoteService;

	@RequestMapping(value = "/stockQuote/list", method = RequestMethod.GET)
	public String list(ModelMap model){
		
		List<StockQuote> stockQuoteList = stockQuoteService.getAllStockQuotes();
		
		model.put("stockQuoteList", stockQuoteList);
		return "stockQuote/list";
    }
}
