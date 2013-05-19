package pl.edu.agh.iosr.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service("userServiceWithTenant")
public class UserServiceWithTenantImpl extends UserServiceImpl {
	
	private final Logger log = LoggerFactory.getLogger(UserServiceWithTenantImpl.class);

    @Autowired
    private TenantResolverService tenantResolverService;

    public void setTenantResolverService(TenantResolverService tenantResolverService) {
        this.tenantResolverService = tenantResolverService;
    }

    @Override
    @Transactional
    public UserEntity getUserById(Long userId) {
        UserEntity userEntity = super.getUserById(userId);
        if(false == tenantResolverService.canModify(userEntity)){
            return null;
        }

        return userEntity;
    }

    @Override
    @Transactional
    public UserEntity getUserByLogin(String login) {
        UserEntity userEntity = super.getUserByLogin(login);
        if(false == tenantResolverService.canModify(userEntity)){
            return null;
        }

        return userEntity;
    }

    @Override
    @Transactional
    public UserEntity createUser(UserEntity user) {
        if(false == tenantResolverService.canModify(user)){
            return null;
        }
        getEntityManager().persist(user);
        return user;
    }

	@Override
	public List<UserEntity> getAllUsersOfRole(String roleName) {
        Tenant myTenant = tenantResolverService.resolveTenant();
        if(myTenant == null){
            log.info("null tenant");
        }

        if(myTenant.isSuperuser()){
            return super.getAllUsersOfRole(roleName);
        }

		Query query = getEntityManager().createQuery(
        		"from UserEntity u where u.role.name = :roleName and u.tenant.name = :tenantName");
        query.setParameter("roleName", roleName);
        query.setParameter("tenantName", myTenant.getName());
		
        @SuppressWarnings("unchecked")
		List<UserEntity> resultList = query.getResultList();
        
        return  resultList;
	}

    @Override
    @Transactional
    public void removeUserById(Long id){
        UserEntity user = getUserById(id);
        if(user != null){
            getEntityManager().remove(user);
        }
    }

    @Override
    @Transactional
    public UserEntity merge(UserEntity user) {
        if(false == tenantResolverService.canModify(user)){
            return null;
        }

        return super.merge(user);
    }

    @Override
    @Transactional
    public List<UserEntity> getAllUsers() {
        Tenant myTenant = tenantResolverService.resolveTenant();
        if(myTenant.isSuperuser()){
            return super.getAllUsers();
        }

        Query query = getEntityManager().createQuery("from UserEntity u where u.tenant.name = :tenantName");
        query.setParameter("tenantName", myTenant.getName());

        List<UserEntity> userList = (List<UserEntity>) query.getResultList();
        return  (userList != null && userList.size() > 0) ?  userList : null;
    }
}
