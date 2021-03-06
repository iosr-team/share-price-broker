package pl.edu.agh.iosr.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.iosr.stockquote.StockQuote;
import pl.edu.agh.iosr.stockquote.StockQuoteListenerImpl;

@Controller
public class HomeController {
    @Autowired AmqpTemplate amqpTemplate;
    
    StockQuoteListenerImpl stockQuoteListener = new StockQuoteListenerImpl();

    @RequestMapping(value = "/")
    public String home(Model model) {
        model.addAttribute(new Message());
        model.addAttribute(new Key());
        return "home";
    }

    @RequestMapping(value = "/publish", method=RequestMethod.POST)
    public String publish(Model model, Message message) {
        // Send a message to the "messages" queue
    	System.out.println("["+message.getKey()+"]["+message.getValue()+"]");
    	
    	stockQuoteListener.setAmqpTemplate(amqpTemplate);
    	StockQuote stockQuote = new StockQuote(message.getKey(), new Date(), Double.parseDouble(message.getValue()));
    	stockQuoteListener.newStockQuote(stockQuote);
        //amqpTemplate.convertAndSend(message.getKey(), message.getValue());
        model.addAttribute("published", true);
        return home(model);
    }

    @RequestMapping(value = "/get", method=RequestMethod.POST)
    public String get(Model model, Key key) {
        // Receive a message from the "messages" queue
    	System.out.println("["+key.getValue()+"]");
        String message = (String)amqpTemplate.receiveAndConvert(key.getValue());
        if (message != null)
            model.addAttribute("got", message);
        else
            model.addAttribute("got_queue_empty", true);

        return home(model);
    }
    
    @RequestMapping(value = "/test/{nums}", method=RequestMethod.GET)
    public String performanceTest(Model model, @PathVariable String nums) {
    	List<TestResult> results = new ArrayList<TestResult>();
    	AmqpPerformanceTest tester = new AmqpPerformanceTest(amqpTemplate);
    	for(String num : nums.split(","))
    		results.add(tester.testNum(Integer.parseInt(num)));
    	model.addAttribute("results", results);
    	return "testResults";
    }
    

}