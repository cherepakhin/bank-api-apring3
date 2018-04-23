package ru.el59.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.el59.critery.ClientCritery;
import ru.el59.dao.IClientDao;
import ru.el59.model.Client;

import java.util.List;

public class ClientDao extends GenericDaoImpl<Client, Long> implements
        IClientDao {

    public ClientDao(Class<Client> type) {
        super(type);
    }

    /**
     * Отбор клиентов по параметрам
     *
     * @param critery - ClientCritery
     * @return
     * @throws Exception
     */
    @Override
    public List<Client> getByCritery(Object critery) throws Exception {
        if (!(critery instanceof ClientCritery)) {
            throw new Exception("Critery is not ClentCritery.class");
        }
        ClientCritery c = (ClientCritery) critery;
        Criteria q = getSession().createCriteria(Client.class);

        if (c.ids != null && c.ids.size() > 0) {
            q.add(Restrictions.in("id", c.ids));
        }

        if (c.name != null) {
            q.add(Restrictions.like("name", c.name, MatchMode.ANYWHERE).ignoreCase());
        }
        q.addOrder(Order.asc("name"));
        List<Client> list = q.list();
        return list;
    }
}
