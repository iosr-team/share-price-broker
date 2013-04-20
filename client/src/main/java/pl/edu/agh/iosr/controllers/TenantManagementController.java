package pl.edu.agh.iosr.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.services.TenantService;

@Controller
public class TenantManagementController {
	
	private final Logger log = LoggerFactory.getLogger(TenantManagementController.class);
	
	@Autowired
	private TenantService tenantService;
	
	@RequestMapping(value = "/tenant/list", method = RequestMethod.GET)
	public String list(ModelMap model){
		
		List<Tenant> tenantList = tenantService.getAllTenants();
		model.put("tenantList", tenantList);
		return "tenant/list";
	}
	
	@RequestMapping(value = "/tenant/edit/{id}", method = RequestMethod.GET)
	public String edit(ModelMap model,
			@PathVariable Long id){
		
		Tenant tenant = tenantService.getTenantById(id);
		model.put("tenant", tenant);
		return "tenant/edit";
	}
}
