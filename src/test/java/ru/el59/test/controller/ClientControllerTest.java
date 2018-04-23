package ru.el59.test.controller;


import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import ru.el59.controller.ClientController;
import ru.el59.controller.util.Clients;
import ru.el59.dao.impl.ClientDao;
import ru.el59.model.Client;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ClientControllerTest {
    private final List<Client> clients = new ArrayList<Client>();
    private final static Long ID = new Long(1);
    private final static String NAME_1 = "NAME_1";

    @Before
    public void initClients() {
        Client client = new Client();
        client.setId(ID);
        client.setName(NAME_1);
        clients.add(client);
    }

    @Test
    public void testList() throws Exception {
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.getByCritery(any())).thenReturn(clients);
        ClientController clientController = new ClientController(clientDao);
        ReflectionTestUtils.setField(clientController, "dao", clientDao);
        ExtendedModelMap uiModel = new ExtendedModelMap();
        uiModel.addAttribute("clients", clientController.list("", null));
        Clients modelClients = (Clients) uiModel.get("clients");
        assertEquals(1, modelClients.getClients().size());
    }
}
