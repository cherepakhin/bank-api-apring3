package ru.el59.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.el59.controller.util.Operations;
import ru.el59.critery.OperationCritery;
import ru.el59.dao.IOperationDao;
import ru.el59.model.Operation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Операция по счету
 */
@Controller
@RequestMapping(value = "/operation")
public class OperationController extends GenericController<Operation, Long> {

    final static SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public OperationController(@Qualifier("operationDao") IOperationDao dao) {
        super(Operation.class, dao);
    }

    /**
     * Отбор операций по параметрам
     * @param ids - список идентификаторов
     * @param srcAccountId - идентификатор с какого счета перечисление
     * @param dstAccountId - идентификатор на какой счет перечисление
     * @param fromDate - период с даты
     * @param toDate - период по дату
     * @return - список найденных операций
     * @throws ParseException
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Operations list(
            @RequestParam(value = "ids", required = false) List<Long> ids,
            @RequestParam(value = "src_account_id", required = false) Long srcAccountId,
            @RequestParam(value = "dst_account_id", required = false) Long dstAccountId,
            @RequestParam(value = "from_date", required = false) String fromDate,
            @RequestParam(value = "to_date", required = false) String toDate
    ) throws ParseException {
        logger.info(String.format("Param ids=%s,src_account_id,dst_account_id", ids));
        OperationCritery operationCritery = new OperationCritery();
        if (ids != null && ids.size() > 0) {
            operationCritery.ids = ids;
        }
        operationCritery.srcAccountId = srcAccountId;
        operationCritery.dstAccountId = dstAccountId;
        if (fromDate != null && !fromDate.isEmpty()) {
            Date ddate = parser.parse(fromDate);
            operationCritery.fromDate = ddate;
        }
        if (toDate != null && !toDate.isEmpty()) {
            Date ddate = parser.parse(toDate);
            operationCritery.toDate = ddate;
        }
        try {
            List<Operation> listOperation = dao.getByCritery(operationCritery);
            return new Operations(listOperation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Operations(new ArrayList<Operation>());
    }
}
