package pl.edu.agh.iosr.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.entity.StockCompany;
import pl.edu.agh.iosr.model.entity.Tenant;

@Service
public class StockCompanyServiceImpl implements StockCompanyService {

	@Autowired
	private TenantResolverService tenantResolverService;

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public StockCompany getStockCompany(String symbol) {
		return getEntityManager().find(StockCompany.class, symbol);
	}

	@Override
	@Transactional
	public StockCompany getStockCompanyByName(String name) {
		Query query = getEntityManager().createQuery(
				"select s from StockCompany s where s.name = :name",
				StockCompany.class);
		query.setParameter("name", name);
		return (query.getResultList() != null && query.getResultList().size() > 0) ? (StockCompany) query
				.getResultList().get(0) : null;
	}

	@Override
	@Transactional
	public StockCompany createStockCompany(StockCompany stockCompany) {
		getEntityManager().persist(stockCompany);
		return stockCompany;
	}

	@Override
	@Transactional
	public List<StockCompany> getAllStockCompanies() {
		Query query = getEntityManager().createQuery("from StockCompany");
		List<StockCompany> resultList = (List<StockCompany>) query
				.getResultList();
		return (resultList != null && resultList.size() > 0) ? resultList
				: null;
	}

	@Override
	@Transactional
	public List<StockCompany> getObservedStockCompanies() {
		return tenantResolverService.resolveTenant()
				.getObservedStockCompanies();
	}

	@Override
	@Transactional
	public StockCompany merge(StockCompany stockCompany) {
		return getEntityManager().merge(stockCompany);
	}

	@Override
	@Transactional
	public void removeStockCompanyBySymbol(String symbol) {
		getEntityManager().remove(getStockCompany(symbol));
	}
}
