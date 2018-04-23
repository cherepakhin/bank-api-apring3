package ru.el59.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.el59.dao.IGenericDao;
import ru.el59.model.AEntity;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class GenericDaoImpl<T extends AEntity, PK extends Serializable> implements IGenericDao<T, PK> {
    private Class<T> type;
    private SessionFactory sessionFactory;

    public GenericDaoImpl(Class<T> type) {
        this.type = type;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    @Override
    public PK create(T newInstance) throws Exception {
        PK id = (PK) getSession().save(newInstance);
        return id;
    }

    @Override
    public T read(PK id) {
        T o = null;
        try {
            o = (T) getSession().get(type, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public T update(T o) throws Exception {
        getSession().saveOrUpdate(o);
        return read((PK) o.getId());
    }

    @Override
    public void delete(PK id) throws Exception {
        T o = read(id);
        getSession().delete(o);
    }

    @Override
    public abstract List<T> getByCritery(Object critery) throws Exception;

}
