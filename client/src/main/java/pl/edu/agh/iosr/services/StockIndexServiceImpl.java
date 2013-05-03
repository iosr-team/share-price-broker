package pl.edu.agh.iosr.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.iosr.model.entity.Role;
import pl.edu.agh.iosr.model.entity.StockIndex;
import pl.edu.agh.iosr.services.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Service
public class StockIndexServiceImpl implements StockIndexService {

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
    public StockIndex getStockIndex(Long id) {
        return getEntityManager().find(StockIndex.class, id);
    }

    @Override
    @Transactional
    public StockIndex getStockIndexByName(String name) {
        Query query = getEntityManager().createQuery("" +
                "select s from StockIndex s where "
                + "s.name =:name", StockIndex.class);
        query.setParameter("name", name);
        return (query.getResultList() != null && query.getResultList().size() > 0)
                ? (StockIndex) query.getResultList().get(0) : null;
    }

    @Override
    @Transactional
    public StockIndex createStockIndex(StockIndex stockIndex) {
        getEntityManager().persist(stockIndex);
        return stockIndex;
    }

    @Override
    @Transactional
    public List<StockIndex> getAllStockIndices() {
        Query query = getEntityManager().createQuery("from StockIndex");
        List<StockIndex> resultList = (List<StockIndex>) query.getResultList();
        return  (resultList != null && resultList.size() > 0) ?  resultList : null;
    }
}
