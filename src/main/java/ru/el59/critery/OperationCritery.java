package ru.el59.critery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperationCritery {
    /**
     * Список идентификаторов
     */
    public List<Long> ids = new ArrayList<Long>();
    /**
     * Период с
     */
    public Date toDate;
    /**
     * Период по
     */
    public Date fromDate;
    /**
     * Номер счета, с которого было перечисление
     */
    public Long srcAccountId;
    /**
     * Номер счета, на который было перечисление
     */
    public Long dstAccountId;
}
