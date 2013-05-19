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
import pl.edu.agh.iosr.model.entity.UserEntity;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String USER_LOGIN = "USER_LOGIN";
    private static final String NOT_EXISTING = "ASDFASD2345ADSDasdfaadsasdfasdf";

    private static final String ROLE_NAME = "ROLE_NAME";

    private UserServiceImpl userServiceImpl;

    @Mock
    Query query;

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserEntity userMock;

    @Mock
    private Role roleMock;

    private List<Object> dummyResultList;

    @Before
    public void setUp() throws Exception {
        dummyResultList = new ArrayList<Object>();
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(dummyResultList);

        when(roleMock.getName()).thenReturn(ROLE_NAME);

        when(userMock.getLogin()).thenReturn(USER_LOGIN);
        when(userMock.getRole()).thenReturn(roleMock);

        userServiceImpl = new UserServiceImpl();
        userServiceImpl.setEntityManager(entityManager);
    }

    @After
    public void tearDown() throws Exception {
        reset(userMock);
        reset(roleMock);
        reset(query);
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        // prepare stubs
        when(query.setParameter(eq("login"), anyString())).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                dummyResultList.clear();

                // add to the list user with specified login
                if(args[1] != null && USER_LOGIN.equals(args[1].toString())) {
                    dummyResultList.add(userMock);
                }
                return query;
            }
        });

        // invoke
        UserEntity resultUser = userServiceImpl.getUserByLogin(USER_LOGIN);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(resultUser);
        assertTrue(USER_LOGIN.equals(resultUser.getLogin()));

        // invoke
        UserEntity resultUserNotExisting = userServiceImpl.getUserByLogin(NOT_EXISTING);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNull(resultUserNotExisting);

        // invoke
        UserEntity resultUserNull = userServiceImpl.getUserByLogin(null);

        // verify results
        assertNull(resultUserNull);

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

        // invoke
        List<UserEntity> userList = userServiceImpl.getAllUsersOfRole(ROLE_NAME);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(userList);
        assertTrue(userList.size() > 0);
        assertEquals(ROLE_NAME, userList.get(0).getRole().getName());

        // invoke
        List<UserEntity> resultRoleNameNotExisting = userServiceImpl.getAllUsersOfRole(NOT_EXISTING);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        verify(query, atLeast(1)).getResultList();
        assertNotNull(resultRoleNameNotExisting);
        assertTrue(resultRoleNameNotExisting.isEmpty());

        // invoke
        List<UserEntity> resultRoleNameNull = userServiceImpl.getAllUsersOfRole(null);

        // verify results
        verify(entityManager, atLeast(1)).createQuery(anyString());
        assertNotNull(resultRoleNameNull);
        assertTrue(resultRoleNameNull.isEmpty());
    }
}
