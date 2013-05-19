package pl.edu.agh.iosr.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.entity.StockQuote;


@Service("stockQuoteService")
public class StockQuoteServiceImpl implements StockQuoteService {

    protected EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public StockQuote getStockQuote(Long id) {
        return getEntityManager().find(StockQuote.class, id);
    }

    @Override
    @Transactional
    public StockQuote createStockQuote(StockQuote stockQuote) {
        getEntityManager().persist(stockQuote);
        return stockQuote;
    }

    @Override
    @Transactional
    public List<StockQuote> getAllStockQuotes() {
        Query query = getEntityManager().createQuery("from StockQuote");
        List<StockQuote> resultList = (List<StockQuote>) query.getResultList();
        return  (resultList != null && resultList.size() > 0) ?  resultList : null;
    }

    @Override
    @Transactional
    public List<StockQuote> getStockQuotesForCompany(String companySymbol) {
        Query query = getEntityManager().createQuery("from StockQuote s where s.stockCompany.symbol = :companySymbol");
        query.setParameter("companySymbol",companySymbol);
        List<StockQuote> resultList = (List<StockQuote>) query.getResultList();
        return  (resultList != null && resultList.size() > 0) ?  resultList : null;
    }
}
