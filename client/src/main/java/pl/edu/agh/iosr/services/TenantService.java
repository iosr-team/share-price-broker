package pl.edu.agh.iosr.services;

import java.util.List;

import pl.edu.agh.iosr.model.entity.Tenant;

public interface TenantService {
    List<Tenant> getAllTenants();
    List<Tenant> getAllNotSuperuserTenants();
    Tenant getTenantByName(String name);
    Tenant getTenantById(Long id);
    Tenant createTenant(Tenant tenant);
    void removeTenantById(Long id);
    Tenant merge(Tenant tenant);
}

