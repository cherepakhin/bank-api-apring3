package ru.el59.test.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.el59.critery.AccountCritery;
import ru.el59.dao.IAccountDao;
import ru.el59.dao.IClientDao;
import ru.el59.model.Account;
import ru.el59.model.Client;
import ru.el59.test.AContextTest;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertEquals;

public class AccountDaoTest extends AContextTest {
    @Autowired
    IAccountDao accountDao;
    @Autowired
    IClientDao clientDao;

    @Test
    public void testRead() {
        Account account = accountDao.read(new Long(1));
        assertEquals(account.getId(), new Long(1));
        assertEquals(account.getClient().getId(), new Long(0));
    }

    @Test
    public void testCreate() {
        Client client = clientDao.read(new Long(1));
        Account account = new Account();
        account.setClient(client);
        BigDecimal balance = new BigDecimal("100.00");
        account.setBalance(balance);
        try {
            Long id = accountDao.create(account);
            assertEquals(id, new Long(3));
            assertEquals(account.getClient().getId(), new Long(1));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetByCriteryByClientName() throws Exception {
        AccountCritery accountCritery = new AccountCritery();
        List<Account> accounts = accountDao.getByCritery(accountCritery);
        assertEquals(accounts.size(), 2);

        accountCritery = new AccountCritery();
        accountCritery.nameClient = "NAME_1";
        accounts = accountDao.getByCritery(accountCritery);
        assertEquals(accounts.size(), 1);
        assertEquals(accounts.get(0).getClient().getName(), "NAME_1");
    }

    @Test
    public void testGetByCriteryByIds() throws Exception {
        AccountCritery accountCritery = new AccountCritery();
        accountCritery.ids.add(new Long(1));

        List<Account> accounts = accountDao.getByCritery(accountCritery);
        assertEquals(accounts.size(), 1);
        assertEquals(accounts.get(0).getId(), new Long(1));
    }
}
