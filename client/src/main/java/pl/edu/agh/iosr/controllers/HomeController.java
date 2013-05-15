package pl.edu.agh.iosr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.iosr.services.TenantResolverService;

@Controller
public class HomeController {
	//private final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private TenantResolverService tenantResolverService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getExpenses(ModelMap model) {
		model.addAttribute("user", tenantResolverService.resolveUser());
		return "home";
	}
}
