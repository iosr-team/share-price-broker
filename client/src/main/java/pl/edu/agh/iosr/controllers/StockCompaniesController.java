package pl.edu.agh.iosr.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.iosr.model.entity.StockCompany;
import pl.edu.agh.iosr.services.StockCompanyService;
import pl.edu.agh.iosr.services.TenantResolverService;

@Controller
public class StockCompaniesController {

	// private final Logger log =
	// LoggerFactory.getLogger(StockQuoteController.class);

	@Autowired
	private StockCompanyService stockCompanyService;

	@Autowired
	private TenantResolverService tenantResolverService;

	@RequestMapping(value = "/stockCompany/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<StockCompany> all = stockCompanyService.getAllStockCompanies(), observed = tenantResolverService
				.resolveTenant().getObservedStockCompanies(), unobserved = new ArrayList<StockCompany>();

		for (StockCompany company : all)
			if (!observed.contains(company))
				unobserved.add(company);

		model.put("observedStockCompanies", observed);
		model.put("unobservedStockCompanies", unobserved);

		return "stockQuote/companies";
	}
}
