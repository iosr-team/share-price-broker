package pl.edu.agh.iosr.controllers;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import pl.edu.agh.iosr.model.entity.StockCompany;
import pl.edu.agh.iosr.model.entity.StockQuote;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;
import pl.edu.agh.iosr.model.response.StockCompanyHistory;
import pl.edu.agh.iosr.services.StockCompanyService;
import pl.edu.agh.iosr.services.StockQuoteService;
import pl.edu.agh.iosr.services.TenantResolverService;
import pl.edu.agh.iosr.services.TenantService;

@Controller
public class StockCompaniesController {

	private final Logger log = LoggerFactory
			.getLogger(StockQuoteController.class);

    private static final String OBSERVE_STOCK_COMPANY_VIEW = "stockCompany/observe";

    private static final String STOCK_COMPANIES_REDIRECT_VIEW = "redirect:/stockCompanies";

    private static final String STOCK_COMPANY_HISTORY_VIEW = "stockCompany/history";

    private static final String EDIT_STOCK_COMPANY_VIEW = "stockCompany/edit";

	@Autowired
	private StockCompanyService stockCompanyService;

	@Autowired
	private TenantResolverService tenantResolverService;
	
	@Autowired
	private TenantService tenantService;

    @Autowired
    @Qualifier("stockQuoteServiceWithTenant")
    private StockQuoteService stockQuoteService;

	String listCompaniesByUser(ModelMap model) {
		model.put("stockCompanies",
				stockCompanyService.getObservedStockCompanies());
		return "stockCompany/list";
	}

	String observeCompaniesByAdministrator(ModelMap model) {
		List<StockCompany> all = stockCompanyService.getAllStockCompanies(), observed = stockCompanyService
				.getObservedStockCompanies(), unobserved = new ArrayList<StockCompany>();

		for (StockCompany company : all)
			if (!observed.contains(company))
				unobserved.add(company);

		model.put("observedStockCompanies", observed);
		model.put("unobservedStockCompanies", unobserved);

		return OBSERVE_STOCK_COMPANY_VIEW;
	}

	String manageCompaniesBySuperuser(ModelMap model) {
		model.put("stockCompanies", stockCompanyService.getAllStockCompanies());
		model.put("newStockCompany", new StockCompany());
		return EDIT_STOCK_COMPANY_VIEW;
	}

	@RequestMapping(value = "/stockCompanies", method = RequestMethod.GET)
	public String list(ModelMap model) {
		UserEntity user = tenantResolverService.resolveUser();

		// TODO: not authenticated?
		if (user.getRole().isSuperuser())
			return manageCompaniesBySuperuser(model);
		else if (user.getRole().isAdmin())
			return observeCompaniesByAdministrator(model);
		else
			return listCompaniesByUser(model);
	}

	@RequestMapping(value = "/stockCompanies/remove", method = RequestMethod.POST)
	public String remove(ModelMap model,
			@RequestParam(value = "symbols", required = false) String[] symbols) {
		if (symbols == null) {
			symbols = new String[] {};
		}
		for (String symbol : symbols) {
			log.debug("StockCompany to be removed: " + symbol);
			try {
				stockCompanyService.removeStockCompanyBySymbol(symbol);
			} catch (Exception e) {
				log.error("error while removing stock company", e);
				String errorMsg = "There were some errors when removing stock companies";
				model.addAttribute("errorMsg", errorMsg);
			}
		}
		return STOCK_COMPANIES_REDIRECT_VIEW;
	}
	
	@RequestMapping(value = "/stockCompanies/observe", method = RequestMethod.POST)
	public String observe(ModelMap model,
			@RequestParam(value = "symbols", required = false) String[] symbols) {
		if (symbols == null) {
			symbols = new String[] {};
		}
		Tenant tenant = tenantResolverService.resolveTenant();
		List<StockCompany> observedStockCompanies = new ArrayList<StockCompany>();
		for(String symbol : symbols) {
			log.debug("StockCompany to be observed: " + symbol);
			try {
				observedStockCompanies.add(stockCompanyService.getStockCompany(symbol));
			} catch (Exception e) {
				log.error("error while observing stock company", e);
				String errorMsg = "There were some errors when observing stock companies";
				model.addAttribute("errorMsg", errorMsg);
			}
		}
		tenant.setObservedStockCompanies(observedStockCompanies);
		tenantService.merge(tenant);
		tenantService.updateQueueBindings(tenant);
		return STOCK_COMPANIES_REDIRECT_VIEW;
	}

	@RequestMapping(value = "/stockCompanies/update", method = RequestMethod.POST)
	public String update(ModelMap model,
			@ModelAttribute("newStockCompany") StockCompany stockCompany) {
		StockCompany existing = stockCompanyService
				.getStockCompany(stockCompany.getSymbol());
		if (existing == null)
			stockCompanyService.createStockCompany(stockCompany);
		else
			stockCompanyService.merge(stockCompany);
		return STOCK_COMPANIES_REDIRECT_VIEW;
	}

    @RequestMapping(value = "/stockCompanies/history/{symbol}", method = RequestMethod.GET)
    public String editForm(ModelMap model,
        @PathVariable String symbol){

        model.put("companySymbol",symbol);
        return STOCK_COMPANY_HISTORY_VIEW;
    }

    @RequestMapping(value="/stockCompanies/data/{symbol}", method=RequestMethod.GET)
    public @ResponseBody StockCompanyHistory getAvailability(
            @PathVariable String symbol) {
        // converts by default to JSON format

        List<StockQuote> stockQuotes = stockQuoteService.getStockQuotesForCompany(symbol);

        StockCompanyHistory result = new StockCompanyHistory();
        result.init("Stock Quotes [ "+symbol+" ]",stockQuotes);

        return result;
    }

}
