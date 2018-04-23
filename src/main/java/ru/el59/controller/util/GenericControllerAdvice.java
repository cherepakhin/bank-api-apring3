package ru.el59.controller.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GenericControllerAdvice {
    /**
     * Сообщение, если сущность не найдена
     * @param ex - исключение
     * @return - ошибка
     */
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage userNotFoundExceptionHandler(EntityNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    /**
     * Все остальные исключения
     * @param ex - исключение
     * @return - ошибка
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage ExceptionHandler(Exception ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
