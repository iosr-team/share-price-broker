package pl.edu.agh.iosr.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.entity.Role;


@Service
public class RoleServiceImpl implements RoleService {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Role getRole(Long id) {
        return getEntityManager().find(Role.class, id);
    }

    @Transactional
    public Role getRoleByName(String name) {
        Query query = getEntityManager().createQuery("select r from Role r where "
             + "name =:roleName", Role.class);
                query.setParameter("roleName", name);
        return (query.getResultList() != null && query.getResultList().size() > 0)
                ? (Role) query.getResultList().get(0) : null;
    }

    @Override
    @Transactional
    public List<Role> getAllRoles() {
        Query query = getEntityManager().createQuery("from Role");
        List<Role> resultList = (List<Role>) query.getResultList();
        return  (resultList != null && resultList.size() > 0) ?  resultList : null;
    }
}
