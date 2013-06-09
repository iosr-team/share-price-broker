package pl.edu.agh.iosr.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.entity.StockCompany;
import pl.edu.agh.iosr.model.entity.Tenant;

@Service
public class TenantServiceImpl implements TenantService {
	
	public static final String EXCHANGE_NAME = "stock-broker"; 

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Autowired
    AmqpAdmin admin;
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public List<Tenant> getAllTenants() {
        Query query = getEntityManager().createQuery("from Tenant t");
        List<Tenant> tenantList = (List<Tenant>) query.getResultList();
        return  (tenantList != null && tenantList.size() > 0) ?  tenantList : null;
    }

    @Override
    @Transactional
    public List<Tenant> getAllNotSuperuserTenants() {
        Query query = getEntityManager().createQuery("from Tenant t where t.name != :name");
        query.setParameter("name", Tenant.SUPERUSER_TENANT_NAME);
        List<Tenant> tenantList = (List<Tenant>) query.getResultList();
        return  (tenantList != null && tenantList.size() > 0) ?  tenantList : null;
    }

	@Override
    @Transactional
    public Tenant getTenantByName(String name) {
        Query query = getEntityManager().createQuery(
        		"from Tenant t where t.name =:name");
        query.setParameter("name", name);
        List<Tenant> tenantList = (List<Tenant>)query.getResultList() ;
        return  (tenantList!= null && tenantList.size() > 0) ? (Tenant) (tenantList.get(0)) : null;
    }

	@Override
	public Tenant getTenantById(Long id) {
		return getEntityManager().find(Tenant.class, id);
	}
	
	@Override
    @Transactional
    public Tenant createTenant(Tenant tenant) {
        getEntityManager().persist(tenant);
        return tenant;
    }

    @Override
    @Transactional
    public void removeTenantById(Long id){
        getEntityManager().remove(getTenantById(id));
    }

    @Override
    @Transactional
    public Tenant merge(Tenant tenant){
        return getEntityManager().merge(tenant);
    }
    
    @Override
    public void updateQueueBindings(Tenant tenant) {   	    	
    	String queueName = tenant.getName();
    	admin.deleteQueue(queueName); // TODO: co robi purgeQueue ?
    	Queue queue = new Queue(queueName);
    	admin.declareQueue(queue);
    	
    	DirectExchange exchange = new DirectExchange(EXCHANGE_NAME);
    	
    	for(StockCompany company : tenant.getObservedStockCompanies()) {
    		String routingKey = company.getSymbol();
    		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
    	}
    }
}
