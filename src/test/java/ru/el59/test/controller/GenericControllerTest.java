package ru.el59.test.controller;


import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.el59.controller.ClientController;
import ru.el59.dao.impl.ClientDao;
import ru.el59.model.Client;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


public class GenericControllerTest {
    private final List<Client> clients = new ArrayList<Client>();
    private final static Long ID = new Long(1);
    private final static String NAME_1 = "NAME_1";

    @Before
    public void initClients() {
        Client client = new Client();
        client.setId(ID);
        client.setName(NAME_1);
        clients.add(client);
    }

    @Test
    public void testGetById() throws Exception {
        Client client = new Client();
        client.setId(new Long(ID));
        client.setName(NAME_1);
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.read(ID)).thenReturn(client);
        ClientController clientController = new ClientController(clientDao);
        ReflectionTestUtils.setField(clientController, "dao", clientDao);
        Client modelClient = clientController.getById(ID);
        assertEquals(client, modelClient);
    }

    @Test
    public void testCreate() throws Exception {
        Client client = new Client();
        client.setId(new Long(ID));
        client.setName(NAME_1);
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.create(client)).thenReturn(ID);
        ClientController clientController = new ClientController(clientDao);
        ReflectionTestUtils.setField(clientController, "dao", clientDao);
        Client responseClient = clientController.create(client);
        assertEquals(responseClient.getId(), ID);
        assertEquals(responseClient.getName(), NAME_1);
    }

    @Test
    public void testUpdate() throws Exception {
        Client client = new Client();
        client.setId(new Long(ID));
        client.setName(NAME_1);
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.update(client)).thenReturn(client);
        ClientController clientController = new ClientController(clientDao);
        ReflectionTestUtils.setField(clientController, "dao", clientDao);
        Client updatedClient = clientController.update(client, ID);
        verify(clientDao, times(1)).update(client);
        assertEquals(updatedClient, client);
    }

    @Test
    public void testDelete() throws Exception {
        ClientDao clientDao = mock(ClientDao.class);
        ClientController clientController = new ClientController(clientDao);
        ReflectionTestUtils.setField(clientController, "dao", clientDao);
        clientController.delete(ID);
        verify(clientDao, times(1)).delete(ID);
    }
}
