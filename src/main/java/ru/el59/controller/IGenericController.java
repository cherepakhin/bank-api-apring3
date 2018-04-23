package ru.el59.controller;

import java.io.Serializable;

public interface IGenericController<T, PK extends Serializable> {
    public T getById(PK id);
}
