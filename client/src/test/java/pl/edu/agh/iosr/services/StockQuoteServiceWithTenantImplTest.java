package pl.edu.agh.iosr.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import pl.edu.agh.iosr.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StockQuoteServiceWithTenantImplTest {
    private static final String COMPANY_SYMBOL = "COMPANY_SYMBOL";
    private static final String NOT_EXISTING_COMPANY_SYMBOL = "ASDFASD2345ADSDasdfaadsasdfasdf";

    private static final String TENANT_NAME = "TENANT_NAME";

    private static final Long TEST_ID = 1L;
    private static final Long NOT_EXISTING_ID = 2342L;

    private StockQuoteServiceWithTenantImpl stockQuoteServiceWithTenantImpl;

    @Mock
    Query query;

    @Mock
    private EntityManager entityManager;

    @Mock
    private StockQuote stockQuoteMock;

    @Mock
    private StockCompany stockCompanyMock;

    @Mock
    private Tenant tenantMock;

    @Mock
    private TenantResolverService tenantResolverServiceMock;

    private List<Object> dummyResultList;

    @Before
    public void setUp() throws Exception {
        dummyResultList = new ArrayList<Object>();
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(entityManager.find(StockQuote.class, TEST_ID)).thenReturn(stockQuoteMock);
        when(entityManager.find(StockQuote.class, NOT_EXISTING_ID)).thenReturn(null);

        when(query.getResultList()).thenReturn(dummyResultList);

        when(tenantMock.getName()).thenReturn(TENANT_NAME);

        when(tenantResolverServiceMock.resolveTenant()).thenReturn(tenantMock);

        when(stockCompanyMock.getSymbol()).thenReturn(COMPANY_SYMBOL);

        when(stockQuoteMock.getStockCompany()).thenReturn(stockCompanyMock);
        when(stockQuoteMock.getTenant()).thenReturn(tenantMock);

        stockQuoteServiceWithTenantImpl = new StockQuoteServiceWithTenantImpl();
        stockQuoteServiceWithTenantImpl.setEntityManager(entityManager);
        stockQuoteServiceWithTenantImpl.setTenantResolverService(tenantResolverServiceMock);
    }

    @After
    public void tearDown() throws Exception {
        reset(stockQuoteMock);
        reset(stockCompanyMock);
        reset(query);
        reset(tenantResolverServiceMock);
    }

    @Test
    public void testGetStockQuote() throws Exception {
        // stub
        dummyResultList.add(stockQuoteMock);
        when(tenantResolverServiceMock.canModify(any(StockQuote.class))).thenReturn(true);

        // invoke
        StockQuote stockQuote = stockQuoteServiceWithTenantImpl.getStockQuote(TEST_ID);

        // verify results
        verify(entityManager, atLeast(1)).find(StockQuote.class, TEST_ID);
        verify(tenantResolverServiceMock, atLeast(1)).canModify(any(StockQuote.class));
        assertNotNull(stockQuote);

        // stub
        when(tenantResolverServiceMock.canModify(any(StockQuote.class))).thenReturn(false);

        // invoke
        StockQuote resultStockQuoteWhoCannotBeModified = stockQuoteServiceWithTenantImpl.getStockQuote(TEST_ID);

        // verify results
        verify(entityManager, atLeast(1)).find(StockQuote.class, TEST_ID);
        verify(tenantResolverServiceMock, atLeast(1)).canModify(any(StockQuote.class));
        assertNull(resultStockQuoteWhoCannotBeModified);

        // stub
        when(tenantResolverServiceMock.canModify(any(StockQuote.class))).thenReturn(true);

        // invoke
        StockQuote resultStockQuoteNonExistingId = stockQuoteServiceWithTenantImpl.getStockQuote(NOT_EXISTING_ID);

        // verify results
        verify(entityManager, atLeast(1)).find(StockQuote.class, NOT_EXISTING_ID);
        verify(tenantResolverServiceMock, atLeast(1)).canModify(any(StockQuote.class));
        assertNull(resultStockQuoteNonExistingId);
    }

    @Test
    public void testGetAllStockQuotes() throws Exception {
        // prepare stubs
        dummyResultList.add(stockQuoteMock);
        when(tenantMock.isSuperuser()).thenReturn(true);

        // invoke
        List<StockQuote> stockQuoteList = stockQuoteServiceWithTenantImpl.getAllStockQuotes();

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(stockQuoteList);
        assertTrue(stockQuoteList.size() > 0);

        // stub
        when(tenantMock.isSuperuser()).thenReturn(false);

        // invoke
        List<StockQuote> stockQuoteListWhenNotSuperuser = stockQuoteServiceWithTenantImpl.getAllStockQuotes();

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(stockQuoteListWhenNotSuperuser);
        assertTrue(stockQuoteListWhenNotSuperuser.size() > 0);
        assertEquals(COMPANY_SYMBOL, stockQuoteListWhenNotSuperuser.get(0).getStockCompany().getSymbol());
        assertEquals(TENANT_NAME, stockQuoteListWhenNotSuperuser.get(0).getTenant().getName());
    }

    @Test
    public void testGetStockQuotesForCompany() throws Exception {
        // prepare stubs
        when(query.setParameter(eq("companySymbol"), anyString())).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                dummyResultList.clear();

                // add to the list user with specified role
                if(args[1] != null && COMPANY_SYMBOL.equals(args[1].toString())) {
                    dummyResultList.add(stockQuoteMock);
                }
                return query;
            }
        });

        when(tenantMock.isSuperuser()).thenReturn(true);

        // invoke
        List<StockQuote> stockQuoteList = stockQuoteServiceWithTenantImpl.getStockQuotesForCompany(COMPANY_SYMBOL);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(stockQuoteList);
        assertTrue(stockQuoteList.size() > 0);
        assertEquals(COMPANY_SYMBOL, stockQuoteList.get(0).getStockCompany().getSymbol());

        // stub
        when(tenantMock.isSuperuser()).thenReturn(false);

        // invoke
        List<StockQuote> stockQuoteListWhenNotSuperuser = stockQuoteServiceWithTenantImpl.getStockQuotesForCompany(COMPANY_SYMBOL);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(stockQuoteListWhenNotSuperuser);
        assertTrue(stockQuoteListWhenNotSuperuser.size() > 0);
        assertEquals(COMPANY_SYMBOL, stockQuoteListWhenNotSuperuser.get(0).getStockCompany().getSymbol());
        assertEquals(TENANT_NAME, stockQuoteListWhenNotSuperuser.get(0).getTenant().getName());

        // stub
        when(tenantMock.isSuperuser()).thenReturn(true);

        // invoke
        List<StockQuote> stockQuoteListWhenNotExistingCompanySymbol = stockQuoteServiceWithTenantImpl.getStockQuotesForCompany(NOT_EXISTING_COMPANY_SYMBOL);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNull(stockQuoteListWhenNotExistingCompanySymbol);
        // !!!
    }
}
