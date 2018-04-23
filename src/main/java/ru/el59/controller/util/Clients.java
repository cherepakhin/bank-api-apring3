package ru.el59.controller.util;

import ru.el59.model.Client;

import java.io.Serializable;
import java.util.List;

public class Clients implements Serializable {
    private List<Client> clients;

    public Clients() {
    }

    public Clients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
