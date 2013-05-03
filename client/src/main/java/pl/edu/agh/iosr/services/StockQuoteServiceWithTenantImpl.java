package pl.edu.agh.iosr.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.iosr.model.entity.StockQuote;
import pl.edu.agh.iosr.model.entity.Tenant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Service("stockQuoteServiceWithTenant")
public class StockQuoteServiceWithTenantImpl extends StockQuoteServiceImpl {

    private final Logger log = LoggerFactory.getLogger(StockQuoteServiceWithTenantImpl.class);

    @Autowired
    private TenantResolverService tenantResolverService;

    @Override
    @Transactional
    public StockQuote getStockQuote(Long id) {
        StockQuote stockQuote = super.getStockQuote(id);
        if(false == tenantResolverService.canModify(stockQuote)){
            return null;
        }
        return stockQuote;
    }

    @Override
    @Transactional
    public StockQuote createStockQuote(StockQuote stockQuote) {
        if(false == tenantResolverService.canModify(stockQuote)){
            return null;
        }
        getEntityManager().persist(stockQuote);
        return stockQuote;
    }

    @Override
    @Transactional
    public List<StockQuote> getAllStockQuotes() {
        Tenant myTenant = tenantResolverService.resolveTenant();
        if(myTenant.isSuperuser()){
            return super.getAllStockQuotes();
        }

        Query query = getEntityManager().createQuery("from StockQuote s where s.tenant.name = :tenantName");
        query.setParameter("tenantName", myTenant.getName());

        List<StockQuote> resultList = (List<StockQuote>) query.getResultList();
        return  (resultList != null && resultList.size() > 0) ?  resultList : null;
    }
}
