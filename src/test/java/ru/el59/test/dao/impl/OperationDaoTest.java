package ru.el59.test.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.el59.critery.OperationCritery;
import ru.el59.dao.IAccountDao;
import ru.el59.dao.IOperationDao;
import ru.el59.model.Account;
import ru.el59.model.Operation;
import ru.el59.test.AContextTest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.*;

public class OperationDaoTest extends AContextTest {
    @Autowired
    IAccountDao accountDao;
    @Autowired
    IOperationDao operationDao;

    @Test
    public void testRead() {
        Operation operation = operationDao.read(new Long(0));
        assertEquals(operation.getSrcAccount().getId(), new Long(1));
        assertEquals(operation.getDstAccount().getId(), new Long(2));
        assertEquals(operation.getAmount(), new BigDecimal("50.00"));
    }

    @Test
    public void testCreate() {
        Account account1 = accountDao.read(new Long(1));
        assertNotNull(account1);
        Account account2 = accountDao.read(new Long(2));
        assertNotNull(account2);
        Operation operation = new Operation();
        operation.setSrcAccount(account1);
        operation.setDstAccount(account2);
        BigDecimal amount = new BigDecimal("20.00");
        operation.setAmount(amount);
        try {
            Long id = operationDao.create(operation);
            assertEquals(id, new Long(2));
            // Проверка балансов
            account1 = accountDao.read(new Long(1));
            assertEquals(account1.getBalance(), new BigDecimal("80.00"));
            account2 = accountDao.read(new Long(2));
            assertEquals(account2.getBalance(), new BigDecimal("120.00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateException() {
        Account account1 = accountDao.read(new Long(1));
        assertNotNull(account1);
        Account account2 = accountDao.read(new Long(2));
        assertNotNull(account2);
        Operation operation = new Operation();
        operation.setSrcAccount(account1);
        operation.setDstAccount(account2);
        BigDecimal amount = new BigDecimal("150.00");
        operation.setAmount(amount);
        try {
            Long id = operationDao.create(operation);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Balance account 1 is less operation amount");
        }
    }

    @Test
    public void testDelete() {
        Long ID = new Long(0);
        Operation operation = operationDao.read(ID);
        try {
            Account srcAccount = operation.getSrcAccount();
            Account dstAccount = operation.getDstAccount();
            operationDao.delete(ID);
            srcAccount = accountDao.read(srcAccount.getId());
            dstAccount = accountDao.read(dstAccount.getId());
            assertEquals(srcAccount.getBalance(), new BigDecimal("150.00"));
            assertEquals(dstAccount.getBalance(), new BigDecimal("50.00"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetByCriteryFromDate() throws Exception {
        OperationCritery operationCritery = new OperationCritery();
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 2, 10);
        operationCritery.fromDate = cal.getTime();
        List<Operation> operations = operationDao.getByCritery(operationCritery);
        assertEquals(operations.size(), 1);
        assertEquals(operations.get(0).getId(), new Long(1));
    }

    @Test
    public void testGetByCriteryToDate() throws Exception {
        OperationCritery operationCritery = new OperationCritery();
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 2, 9);
        operationCritery.toDate = cal.getTime();
        List<Operation> operations = operationDao.getByCritery(operationCritery);
        assertEquals(operations.size(), 1);
        assertEquals(operations.get(0).getId(), new Long(0));
    }

    @Test
    public void testGetByCriterySrcAccount() throws Exception {
        OperationCritery operationCritery = new OperationCritery();
        operationCritery.srcAccountId = new Long(1);
        List<Operation> operations = operationDao.getByCritery(operationCritery);
        assertEquals(operations.size(), 1);
        assertEquals(operations.get(0).getSrcAccount().getId(), new Long(1));
    }

    @Test
    public void testGetByCriteryDstAccount() throws Exception {
        OperationCritery operationCritery = new OperationCritery();
        operationCritery.dstAccountId = new Long(2);
        List<Operation> operations = operationDao.getByCritery(operationCritery);
        assertEquals(operations.size(), 1);
        assertEquals(operations.get(0).getDstAccount().getId(), new Long(2));
    }
}
