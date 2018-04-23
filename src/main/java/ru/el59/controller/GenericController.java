package ru.el59.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.el59.controller.util.EntityNotFoundException;
import ru.el59.dao.IGenericDao;

import java.io.Serializable;

/**
 * Реализация типовых операций Restful
 *
 * @param <T>  - объект
 * @param <PK> - идентификатор
 *             <pre>
 *                                     {@code
 *                                     Пример создания:
 *                                     Autowired
 *                                     public ClientController(@Qualifier("clientDao") IClientDao dao) {
 *                                          super(Client.class, dao);
 *                                      }
 *                                     }
 *                         </pre>
 */
public class GenericController<T, PK extends Serializable> {

    protected Class<T> type;
    protected IGenericDao<T, PK> dao;

    protected Logger logger;

    public GenericController(Class<T> type, IGenericDao<T, PK> dao) {
        this.type = type;
        this.dao = dao;
        this.logger = LoggerFactory.getLogger(type);
    }

    /**
     * Получить сущность по идентификатору
     * @param id - идентификатор
     * @return - объект типа Entity
     * @throws EntityNotFoundException - если не найдено
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public T getById(@PathVariable Long id) throws EntityNotFoundException {
//        logger.info("Param id:" + id + ";");
        T o = dao.read((PK) id);
        if (o == null) {
            throw new EntityNotFoundException(id, type);
        }
        return dao.read((PK) id);
    }

    /**
     * Создание сущности
     * @param o
     * @return - созданная сущность
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public T create(@RequestBody T o) throws Exception {
        logger.info("Create: " + o);
        dao.create(o);
        logger.info("Successfully created:" + o);
        return o;
    }

    /**
     * Обновление сущности
     * @param o
     * @param id - идентификатор сущности
     * @return - обновленная сущность
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public T update(@RequestBody T o, @PathVariable PK id) throws Exception {
        logger.info("Update: " + o);
        T ret = dao.update(o);
        logger.info("Successfully updated:" + o);
        return ret;
    }

    /**
     * Удаление сущности
     * @param id - идентификатор
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable PK id) throws Exception {
        logger.info("Delete: " + id);
        dao.delete(id);
        logger.info("Successfully deleted:" + id);
        return "";
    }
}
