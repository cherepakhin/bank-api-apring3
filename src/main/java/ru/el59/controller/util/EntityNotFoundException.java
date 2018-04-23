package ru.el59.controller.util;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id,Class type) {
        super("Could not find Entity "+type.getCanonicalName() +" id="+ id +".");
    }

}
