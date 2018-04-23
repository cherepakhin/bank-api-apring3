package ru.el59.test.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.el59.dao.IClientDao;
import ru.el59.model.Client;
import ru.el59.test.AContextTest;

import static junit.framework.TestCase.*;

public class GenericDaoImplTest extends AContextTest {
    @Autowired
    IClientDao clientDao;

    @Test
    public void testRead() {
        Client client = clientDao.read(new Long(0));
        assertEquals(client.getName(), "NAME_0");
    }

    @Test
    public void testCreate() {
        Client client = new Client();
        String NAME = "NAME";
        String PHONE = "PHONE";
        client.setName(NAME);
        client.setPhone(PHONE);
        try {
            Long id = clientDao.create(client);
            assertEquals(id, new Long(2));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        Client client = clientDao.read(new Long(1));
        String NAME = "NAME";
        client.setName(NAME);
        try {
            clientDao.update(client);
            assertEquals(client.getName(), NAME);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {
//        Client client = clientDao.read(new Long(0));
        try {
            clientDao.delete(new Long(0));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        try {
            clientDao.read(new Long(0));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }
}
