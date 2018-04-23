package ru.el59.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.el59.critery.AccountCritery;
import ru.el59.dao.IAccountDao;
import ru.el59.model.Account;

import java.util.List;

public class AccountDao extends GenericDaoImpl<Account, Long> implements IAccountDao {

    public AccountDao(Class<Account> type) {
        super(type);
    }

    @Override
    public List<Account> getByCritery(Object critery) throws Exception {
        if (!(critery instanceof AccountCritery)) {
            throw new Exception("Critery is not AccountCritery.class");
        }
        AccountCritery c = (AccountCritery) critery;
        Criteria q = getSession().createCriteria(Account.class);

        if (c.ids != null && c.ids.size() > 0) {
            q.add(Restrictions.in("id", c.ids));
        }

        if (c.nameClient != null) {
            Criteria queryClient = q.createCriteria("client");
            queryClient.add(Restrictions.like("name", c.nameClient, MatchMode.ANYWHERE).ignoreCase());
        }
        q.addOrder(Order.asc("id"));
        List<Account> list = q.list();
        return list;
    }
}
