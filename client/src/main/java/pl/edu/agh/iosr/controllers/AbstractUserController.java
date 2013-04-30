package pl.edu.agh.iosr.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.iosr.model.entity.Role;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;
import pl.edu.agh.iosr.services.RoleService;
import pl.edu.agh.iosr.services.TenantService;
import pl.edu.agh.iosr.model.command.UserCommand;
import pl.edu.agh.iosr.services.UserService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    protected TenantService tenantService;

    @Autowired
    protected RoleService roleService;

    @Autowired
    protected UserService userService;

    protected UserCommand prepareUserCommand(UserEntity user, String tenantName, String roleName, Map<String,String> tenantMap, Map<String,String> roleMap){
        UserCommand userCommand = new UserCommand();

        if(user == null){
            user = new UserEntity();
        }
        userCommand.setUser(user);

        if(tenantName == null){
            tenantName = "";
        }
        userCommand.setTenantName(tenantName);

        if(roleName == null){

            roleName = "";
        }
        userCommand.setRoleName(roleName);

        if(tenantMap == null){
            log.debug("empty tenant map");
            tenantMap = new LinkedHashMap<String,String>();
        }
        userCommand.setTenantMap(tenantMap);

        if(roleMap == null){
            log.debug("empty role map");
            roleMap = new LinkedHashMap<String,String>();
        }
        userCommand.setRoleMap(roleMap);

        return userCommand;
    }

    protected Map<String,String> prepareTenantMap(boolean superuserTenantIncluded){
        List<Tenant> tenantList = null;
        if(superuserTenantIncluded){
            tenantList = tenantService.getAllTenants();
        }else{
            tenantList = tenantService.getAllNotSuperuserTenants();
        }
        Map<String,String> tenantMap = new LinkedHashMap<String,String>();
        for(Tenant tenant : tenantList){
            log.debug("put into tenant map: "+tenant.getName());
            tenantMap.put(tenant.getName(), tenant.getDescription());
        }
        return tenantMap;
    }

    protected Map<String,String> prepareRoleMap(boolean superuserRoleIncluded, boolean adminRoleIncluded){
        List<Role> roleList = null;
        roleList = roleService.getAllRoles();

        Map<String,String> roleMap = new LinkedHashMap<String,String>();
        for(Role role : roleList){
            if(role.isAdmin() && !adminRoleIncluded){
                continue;
            }
            if(role.isSuperuser() && !superuserRoleIncluded){
                continue;
            }
            roleMap.put(role.getName(), role.getName());
        }
        return roleMap;
    }

}
