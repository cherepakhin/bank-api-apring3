package ru.el59.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.el59.critery.OperationCritery;
import ru.el59.dao.IAccountDao;
import ru.el59.dao.IOperationDao;
import ru.el59.model.Account;
import ru.el59.model.Operation;

import java.math.BigDecimal;
import java.util.List;

public class OperationDao extends GenericDaoImpl<Operation, Long> implements IOperationDao {

    IAccountDao accountDao;

    public OperationDao(Class<Operation> type) {
        super(type);
    }

    @Override
    public List<Operation> getByCritery(Object critery) throws Exception {
        if (!(critery instanceof OperationCritery)) {
            throw new Exception("Critery is not OperationCritery.class");
        }
        OperationCritery c = (OperationCritery) critery;
        Criteria q = getSession().createCriteria(Operation.class);

        if (c.ids != null && c.ids.size() > 0) {
            q.add(Restrictions.in("id", c.ids));
        }
        if (c.fromDate != null) {
            q.add(Restrictions.ge("ddate", c.fromDate));
        }
        if (c.toDate != null) {
            q.add(Restrictions.le("ddate", c.toDate));
        }
        if (c.srcAccountId != null) {
            Criteria qSrcAccount = q.createCriteria("srcAccount");
            qSrcAccount.add(Restrictions.eq("id", c.srcAccountId));
        }
        if (c.dstAccountId != null) {
            Criteria qDstAccount = q.createCriteria("dstAccount");
            qDstAccount.add(Restrictions.eq("id", c.dstAccountId));
        }
        q.addOrder(Order.asc("ddate"));
        List<Operation> list = q.list();
        return list;
    }

    @Override
    public Long create(Operation operation) throws Exception {
        correctBalance(operation.getSrcAccount(), operation.getDstAccount(), operation.getAmount());
        return super.create(operation);
    }

    /**
     * Корректировка балансов
     *
     * @param srcAccount -
     * @param dstAccount
     * @param amount
     */
    public void correctBalance(Account srcAccount, Account dstAccount, BigDecimal amount) throws Exception {
        srcAccount = getAccountDao().read(srcAccount.getId());
        dstAccount = getAccountDao().read(dstAccount.getId());
        if (srcAccount.getBalance().compareTo(amount) < 0) {
            throw new Exception(String.format("Balance account %d is less operation amount", srcAccount.getId()));
        }
        srcAccount.setBalance(srcAccount.getBalance().subtract(amount));
        dstAccount.setBalance(dstAccount.getBalance().add(amount));
    }

    @Override
    public Operation update(Operation operation) throws Exception {
        // Корректировки балансов нет
        super.update(operation);
        return operation;
    }

    @Override
    public void delete(Long id) throws Exception {
        Operation operation = read(id);
        correctBalance(operation.getDstAccount(), operation.getSrcAccount(), operation.getAmount());
        super.delete(id);
    }

    public IAccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
