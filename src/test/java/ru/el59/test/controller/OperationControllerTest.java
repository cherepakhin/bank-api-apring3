package ru.el59.test.controller;


import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.el59.controller.OperationController;
import ru.el59.controller.util.Operations;
import ru.el59.dao.impl.OperationDao;
import ru.el59.model.Operation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class OperationControllerTest {
    private final List<Operation> operations = new ArrayList<Operation>();
    private final static Long ID = new Long(1);

    @Before
    public void initOperations() {
        Operation operation = new Operation();
        operation.setId(ID);
        operations.add(operation);
    }

    @Test
    public void testParseDate() throws ParseException {
        String strDate = "2018-02-01";
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date ddate = parser.parse(strDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(ddate);
        assert cal.get(Calendar.YEAR) == 2018;
        assert cal.get(Calendar.MONTH) == 1;
        assert cal.get(Calendar.DAY_OF_MONTH) == 1;
    }

    @Test
    public void testList() throws Exception {
        OperationDao operationDao = mock(OperationDao.class);
        when(operationDao.getByCritery(any())).thenReturn(operations);
        OperationController operationController = new OperationController(operationDao);
        ReflectionTestUtils.setField(operationController, "dao", operationDao);
        List<Long> ids = new ArrayList<Long>();
        ids.add(new Long(1));
        Long srcAccount = new Long(1);
        Long dstAccount = new Long(2);
        String fromDate = "2018-03-01";
        String toDate = "2018-03-20";
        Operations actualOperations = operationController.list(
                ids,
                srcAccount,
                dstAccount,
                fromDate,
                toDate);
        assertEquals(1, actualOperations.getOperations().size());
    }

}
