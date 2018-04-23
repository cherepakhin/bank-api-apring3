package ru.el59.test.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.el59.critery.ClientCritery;
import ru.el59.dao.IClientDao;
import ru.el59.model.Client;
import ru.el59.test.AContextTest;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ClientDaoImplTest extends AContextTest {
    @Autowired
    IClientDao clientDao;

    @Test
    public void testGetByCritery() throws Exception {
        ClientCritery clientCritery = new ClientCritery();
        clientCritery.name = "name";
        List<Client> clients = clientDao.getByCritery(clientCritery);
        assertEquals(clients.size(), 2);
        assertEquals(clients.get(0).getId(), new Long(0));
        assertEquals(clients.get(1).getId(), new Long(1));
    }

    @Test
    public void testGetByCriteryIds() throws Exception {
        ClientCritery clientCritery = new ClientCritery();
        clientCritery.ids.add(new Long(1));
        List<Client> clients = clientDao.getByCritery(clientCritery);
        assertEquals(clients.size(), 1);
        assertEquals(clients.get(0).getId(), new Long(1));
    }

}
