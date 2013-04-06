package pl.edu.agh.iosr.services;

import pl.edu.agh.iosr.model.Role;
public interface RoleService {
    Role getRole(Long id);

    Role getRoleByName(String name);
}
