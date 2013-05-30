package pl.edu.agh.iosr.services;

import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.iosr.model.entity.StockQuote;
import pl.edu.agh.iosr.model.entity.Tenant;


@Service("stockQuoteServiceWithTenant")
public class StockQuoteServiceWithTenantImpl extends StockQuoteServiceImpl {

    private final Logger log = LoggerFactory.getLogger(StockQuoteServiceWithTenantImpl.class);

    @Autowired
    private TenantResolverService tenantResolverService;

    public TenantResolverService getTenantResolverService() {
        return tenantResolverService;
    }

    public void setTenantResolverService(TenantResolverService tenantResolverService) {
        this.tenantResolverService = tenantResolverService;
    }

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

    @Override
    @Transactional
    public List<StockQuote> getStockQuotesForCompany(String companySymbol) {

        Tenant myTenant = tenantResolverService.resolveTenant();
        if(myTenant.isSuperuser()){
            return super.getStockQuotesForCompany(companySymbol);
        }

        Query query = getEntityManager().createQuery("from StockQuote s where s.stockCompany.symbol = :companySymbol and s.tenant.name = :tenantName order by date asc");
        query.setParameter("companySymbol",companySymbol);
        query.setParameter("tenantName", myTenant.getName());
        List<StockQuote> resultList = (List<StockQuote>) query.getResultList();
        return  (resultList != null && resultList.size() > 0) ?  resultList : null;
    }
}
