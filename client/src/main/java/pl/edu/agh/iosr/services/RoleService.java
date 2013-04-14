package pl.edu.agh.iosr.services;

import java.util.List;

import pl.edu.agh.iosr.model.entity.Role;
public interface RoleService {
    Role getRole(Long id);

    Role getRoleByName(String name);
    
    List<Role> getAllRoles();
}
