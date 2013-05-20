package pl.edu.agh.iosr.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import pl.edu.agh.iosr.model.entity.Role;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceWithTenantImplTest {

    private static final String USER_LOGIN = "USER_LOGIN";
    private static final String NOT_EXISTING_USER_LOGIN = "ASDFASD2345ADSDasdfaadsasdfasdf";

    private static final String ROLE_NAME = "ROLE_NAME";
    private static final String NOT_EXISTING_ROLE_NAME = "adfasdfasdadsf";

    private static final String TENANT_NAME = "TENANT_NAME";

    private static final Long TEST_ID = 1L;
    private static final Long NOT_EXISTING_ID = 2342L;

    private UserServiceWithTenantImpl userServicewithTenantImpl;

    @Mock
    Query query;

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserEntity userMock;

    @Mock
    private Role roleMock;

    @Mock
    private Tenant tenantMock;

    @Mock
    private TenantResolverService tenantResolverServiceMock;

    private List<Object> dummyResultList;

    @Before
    public void setUp() throws Exception {
        dummyResultList = new ArrayList<Object>();
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(entityManager.find(UserEntity.class, TEST_ID)).thenReturn(userMock);
        when(entityManager.find(UserEntity.class, NOT_EXISTING_ID)).thenReturn(null);

        when(query.getResultList()).thenReturn(dummyResultList);

        when(roleMock.getName()).thenReturn(ROLE_NAME);

        when(tenantMock.getName()).thenReturn(TENANT_NAME);

        when(tenantResolverServiceMock.resolveTenant()).thenReturn(tenantMock);

        when(userMock.getLogin()).thenReturn(USER_LOGIN);
        when(userMock.getRole()).thenReturn(roleMock);
        when(userMock.getTenant()).thenReturn(tenantMock);

        userServicewithTenantImpl = new UserServiceWithTenantImpl();
        userServicewithTenantImpl.setEntityManager(entityManager);
        userServicewithTenantImpl.setTenantResolverService(tenantResolverServiceMock);
    }

    @After
    public void tearDown() throws Exception {
        reset(userMock);
        reset(roleMock);
        reset(query);
        reset(tenantResolverServiceMock);
    }

    @Test
    public void testGetUserById() throws Exception {
        // stub
        dummyResultList.add(userMock);
        when(tenantResolverServiceMock.canModify(any(UserEntity.class))).thenReturn(true);

        // invoke
        UserEntity resultUser = userServicewithTenantImpl.getUserById(TEST_ID);

        // verify results
        verify(entityManager, atLeast(1)).find(UserEntity.class, TEST_ID);
        verify(tenantResolverServiceMock, atLeast(1)).canModify(any(UserEntity.class));
        assertNotNull(resultUser);

        // stub
        when(tenantResolverServiceMock.canModify(any(UserEntity.class))).thenReturn(false);

        // invoke
        UserEntity resultUserWhoCannotBeModified = userServicewithTenantImpl.getUserById(TEST_ID);

        // verify results
        verify(entityManager, atLeast(1)).find(UserEntity.class, TEST_ID);
        verify(tenantResolverServiceMock, atLeast(1)).canModify(any(UserEntity.class));
        assertNull(resultUserWhoCannotBeModified);

        // stub
        when(tenantResolverServiceMock.canModify(any(UserEntity.class))).thenReturn(true);

        // invoke
        UserEntity resultUserNonExistingId = userServicewithTenantImpl.getUserById(NOT_EXISTING_ID);

        // verify results
        verify(entityManager, atLeast(1)).find(UserEntity.class, NOT_EXISTING_ID);
        verify(tenantResolverServiceMock, atLeast(1)).canModify(any(UserEntity.class));
        assertNull(resultUserNonExistingId);
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        // stub
        when(query.setParameter(eq("login"), anyString())).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                dummyResultList.clear();

                // add to the list user with specified login
                if (args[1] != null && USER_LOGIN.equals(args[1].toString())) {
                    dummyResultList.add(userMock);
                }
                return query;
            }
        });

        when(tenantResolverServiceMock.canModify(any(UserEntity.class))).thenReturn(true);

        // invoke
        UserEntity resultUser = userServicewithTenantImpl.getUserByLogin(USER_LOGIN);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(resultUser);

        // stub
        when(tenantResolverServiceMock.canModify(any(UserEntity.class))).thenReturn(false);

        // invoke
        UserEntity resultUserWhoCannotBeModified = userServicewithTenantImpl.getUserByLogin(USER_LOGIN);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNull(resultUserWhoCannotBeModified);

        // stub
        when(tenantResolverServiceMock.canModify(any(UserEntity.class))).thenReturn(true);

        // invoke
        UserEntity resultUserNonExistingId = userServicewithTenantImpl.getUserByLogin(NOT_EXISTING_USER_LOGIN);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNull(resultUserNonExistingId);
    }

    @Test
    public void testGetAllUsersOfRole() throws Exception {
        // prepare stubs
        when(query.setParameter(eq("roleName"), anyString())).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                dummyResultList.clear();

                // add to the list user with specified role
                if(args[1] != null && ROLE_NAME.equals(args[1].toString())) {
                    dummyResultList.add(userMock);
                }
                return query;
            }
        });

        when(tenantMock.isSuperuser()).thenReturn(true);

        // invoke
        List<UserEntity> userList = userServicewithTenantImpl.getAllUsersOfRole(ROLE_NAME);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(userList);
        assertTrue(userList.size() > 0);
        assertEquals(ROLE_NAME, userList.get(0).getRole().getName());

        // stub
        when(tenantMock.isSuperuser()).thenReturn(false);

        // invoke
        List<UserEntity> userListWhenNotSuperuser = userServicewithTenantImpl.getAllUsersOfRole(ROLE_NAME);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(userListWhenNotSuperuser);
        assertTrue(userListWhenNotSuperuser.size() > 0);
        assertEquals(ROLE_NAME, userListWhenNotSuperuser.get(0).getRole().getName());
        assertEquals(TENANT_NAME, userListWhenNotSuperuser.get(0).getTenant().getName());

        // stub
        when(tenantMock.isSuperuser()).thenReturn(true);

        // invoke
        List<UserEntity> userListWhenNotExistingRole = userServicewithTenantImpl.getAllUsersOfRole(NOT_EXISTING_ROLE_NAME);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(userListWhenNotExistingRole);
        assertTrue(userListWhenNotExistingRole.isEmpty());
    }
}
