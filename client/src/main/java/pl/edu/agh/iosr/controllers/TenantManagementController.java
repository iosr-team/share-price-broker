package pl.edu.agh.iosr.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
	public String editForm(ModelMap model,
			@PathVariable Long id){
		
		Tenant tenant = tenantService.getTenantById(id);
		model.put("tenant", tenant);
		return "tenant/edit";
	}

    @RequestMapping(value = "/tenant/edit/{id}", method = RequestMethod.POST)
    public String edit(ModelMap model,
        @ModelAttribute ("tenant") Tenant tenantCommand,
        @PathVariable Long id){

        Tenant tenant = tenantService.getTenantById(tenantCommand.getId());

        if (tenant == null) {
            String errorMsg = "Cannot modify tenant!";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/tenant/edit"+id+"?errorMsg=" + errorMsg;
        }

        tenant = tenantService.merge(tenantCommand);
        String succMsg = "Tenant successfully edited ";
        return "redirect:/tenant/list?succMsg=" + succMsg;
    }
	
	@RequestMapping(value = "/tenant/add", method = RequestMethod.GET)
	public String add(ModelMap model){
		
		model.put("tenant", new Tenant());
		return "tenant/add";
	}
	
	@RequestMapping(value = "/tenant/add", method = RequestMethod.POST)
    public String signUp(ModelMap model, 
    		@ModelAttribute ("tenant") Tenant tenantCommand ) {

        Tenant tenant = tenantService.getTenantByName(tenantCommand.getName());
        if (tenant == null) {
            tenantCommand.setEnabled(true);
            tenant = tenantService.createTenant(tenantCommand);
            String succMsg = "Tenant registration succeeds ";
            return "redirect:/tenant/list?succMsg=" + succMsg;
        } else {
            String errorMsg = "Tenant registration failed - already same tenant name exists!";
            model.addAttribute("errorMsg", errorMsg);
            return "redirect:/tenant/add?errorMsg=" + errorMsg;
        }
    }

    @RequestMapping(value = "/tenant/remove", method = RequestMethod.POST)
    public String remove(ModelMap model,
        @RequestParam(value = "itemIds", required = false) long[] itemIds ) {
        if(itemIds == null){
            itemIds = new long[]{};
        }
        String errorMsg = "";
        for(Long id : itemIds){
            log.debug("item to be removed: "+id);
            try{
                tenantService.removeTenantById(id);
            }catch(Exception e){
                log.error("error while removing tenent",e);
                errorMsg = "There were some errors when removing tenents";
            }
        }
        if(errorMsg.equals("")){
            return "redirect:/tenant/list";
        }
        return "redirect:/tenant/list?errorMsg="+errorMsg;
    }
}
