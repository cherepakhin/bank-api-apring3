package ru.el59.controller.util;

import ru.el59.model.Operation;

import java.io.Serializable;
import java.util.List;

public class Operations implements Serializable {
    List<Operation> operations;

    public Operations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
