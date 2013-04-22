package pl.edu.agh.iosr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.core.AmqpTemplate;

@Controller
public class HomeController {
    @Autowired AmqpTemplate amqpTemplate;

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
        amqpTemplate.convertAndSend(message.getKey(), message.getValue());
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
}