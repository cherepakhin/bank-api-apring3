package ru.el59.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.el59.controller.util.Clients;
import ru.el59.critery.ClientCritery;
import ru.el59.dao.IClientDao;
import ru.el59.model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер Rest для клиента банка
 */
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
@RestController
@RequestMapping(value = "/client")
public class ClientController extends GenericController<Client, Long> {

    @Autowired
    public ClientController(@Qualifier("clientDao") IClientDao dao) {
        super(Client.class, dao);
    }

    /**
     * Выборка клиентов по параметрам
     * @param name - имя
     * @param ids - список идентификаторов
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Clients list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "ids", required = false) List<Long> ids
    ) {
//        logger.info("Param name:" + name + ";");
//        logger.info("Param ids:" + ids + ";");
        ClientCritery clientCritery = new ClientCritery();
        if (name != null && !name.isEmpty()) {
            clientCritery.name = name;
        }
        if (ids != null && ids.size() > 0) {
            clientCritery.ids = ids;
        }
        try {
            List<Client> listClient = dao.getByCritery(clientCritery);
            return new Clients(listClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Clients(new ArrayList<Client>());
    }
}
