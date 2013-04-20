package pl.edu.agh.iosr.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.entity.UserEntity;

@Service
public class UserServiceImpl implements UserService {
	
	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return getEntityManager().find(UserEntity.class, userId);
    }

    @Override
    @Transactional
    public UserEntity getUserByLogin(String login) {
    	log.debug("login: "+login);
    	
        Query query = getEntityManager().createQuery(
        		"from UserEntity u where "
                +"u.login =:login");
        query.setParameter("login", login);
        
        @SuppressWarnings("unchecked")
		List<UserEntity> resultList = query.getResultList();
        
        if(resultList == null || resultList.size() == 0){
        	log.info("no such user: "+login);
        	return null;
        }
        
        return  resultList.get(0);
    }

    @Override
    @Transactional
    public UserEntity createUser(UserEntity user) {
        getEntityManager().persist(user);
        return user;
    }

	@Override
	public List<UserEntity> getAllUsersOfRole(String roleName) {

		Query query = getEntityManager().createQuery(
        		"from UserEntity u where u.role.name = :roleName");
        query.setParameter("roleName", roleName);
		
        @SuppressWarnings("unchecked")
		List<UserEntity> resultList = query.getResultList();
        
        return  resultList;
	}
}
