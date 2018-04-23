package ru.el59.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.el59.controller.util.Accounts;
import ru.el59.critery.AccountCritery;
import ru.el59.dao.IAccountDao;
import ru.el59.model.Account;

import java.util.List;

/**
 * Контроллер Rest для счетов клиентов
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController extends GenericController<Account, Long> {

    @Autowired
    public AccountController(@Qualifier("accountDao") IAccountDao dao) {
        super(Account.class, dao);
    }

    /**
     * Отбор счетов по параметрам
     * @param nameClient - имя клиента (отбор по части имени, без учета регистра)
     * @param ids - список номеров счетов
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Accounts list(
            @RequestParam(value = "name_client", required = false) String nameClient,
            @RequestParam(value = "ids", required = false) List<Long> ids
    ) {
        AccountCritery accountCritery = new AccountCritery();
        if (nameClient != null && !nameClient.isEmpty()) {
            accountCritery.nameClient = nameClient;
        }
        if (ids != null && ids.size() > 0) {
            accountCritery.ids = ids;
        }
        try {
            List<Account> accounts = dao.getByCritery(accountCritery);
            return new Accounts(accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Accounts();
    }
}
