package pl.edu.agh.iosr.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.iosr.services.HelloService;

@Controller
public class HelloController {
	private final Logger logger = LoggerFactory
			.getLogger(HelloController.class);

	@Autowired
	private HelloService helloService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getExpenses(ModelMap model) {
		model.addAttribute("userName", helloService.getHelloName());
		return "hello";
	}
}