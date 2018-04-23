package ru.el59.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T, PK extends Serializable> {
    public PK create(T newInstance) throws Exception;

    public T read(PK id);

    public T update(T transientT) throws Exception;

    void delete(PK id) throws Exception;

    public List<T> getByCritery(Object critery) throws Exception;
}
