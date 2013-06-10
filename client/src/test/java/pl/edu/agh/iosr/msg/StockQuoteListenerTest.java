package pl.edu.agh.iosr.msg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.core.Message;
import pl.edu.agh.iosr.model.entity.*;
import pl.edu.agh.iosr.services.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StockQuoteListenerTest {
    private static final String SEP = "#";
    private static final String COMPANY_SYMBOL = "GOOG";
    private static final String VALUE = "123";
    private static final String CORRECT_DATE = "2013-05-19 12:00:00";
    private static final String INCORRECT_DATE = "2013:05-19_12:00-00";

    private static final String CORRECT_MESSAGE = COMPANY_SYMBOL+SEP+VALUE+SEP+CORRECT_DATE;
    private static final String INCORRECT_MESSAGE = COMPANY_SYMBOL+SEP+VALUE+SEP+INCORRECT_DATE;
    private static final String BADLY_FORMATTED_MESSAGE = COMPANY_SYMBOL+'%'+VALUE+'*'+CORRECT_DATE;

    private static final String TENANT_NAME = "TENANT_NAME";

    private StockQuoteListener stockQuoteListener;

    @Mock
    Message messageMock;

    @Mock
    private Tenant tenantMock;

    @Mock
    private StockCompany stockCompanyMock;

    @Mock
    private TenantService tenantServiceMock;

    @Mock
    private StockCompanyService stockCompanyServiceMock;

    @Mock
    private StockQuoteService stockQuoteServiceMock;

    @Before
    public void setUp() throws Exception {
        when(stockCompanyMock.getSymbol()).thenReturn(COMPANY_SYMBOL);

        when(tenantMock.getName()).thenReturn(TENANT_NAME);

        when(tenantServiceMock.getTenantByName(TENANT_NAME)).thenReturn(tenantMock);

        stockQuoteListener = new StockQuoteListener();
        stockQuoteListener.setStockCompanyService(stockCompanyServiceMock);
        stockQuoteListener.setTenantService(tenantServiceMock);
        stockQuoteListener.setStockQuoteService(stockQuoteServiceMock);
    }

    @After
    public void tearDown() throws Exception {
        reset(tenantServiceMock);
        reset(tenantMock);
    }

    @Test
    public void testOnMessage() throws Exception {
        // stub
        when(messageMock.getBody()).thenReturn(CORRECT_MESSAGE.getBytes());
        when(stockCompanyServiceMock.getStockCompany(COMPANY_SYMBOL)).thenReturn(stockCompanyMock);

        // invoke
        stockQuoteListener.onMessage(messageMock);

        // verify
        verify(stockQuoteServiceMock, atLeastOnce()).createStockQuote(any(StockQuote.class));

        // stub
        when(messageMock.getBody()).thenReturn(INCORRECT_MESSAGE.getBytes());

        /*
        // invoke
         stockQuoteListener.onMessage(messageMock);

        // verify
        verifyZeroInteractions(stockQuoteServiceMock);

        // stub
        when(messageMock.getBody()).thenReturn(BADLY_FORMATTED_MESSAGE.getBytes());

        // invoke
        stockQuoteListener.onMessage(messageMock);

        // verify
        verifyZeroInteractions(stockQuoteServiceMock);   */
    }
}
