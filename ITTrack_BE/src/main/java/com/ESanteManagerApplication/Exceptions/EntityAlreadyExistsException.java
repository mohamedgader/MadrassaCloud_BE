package com.ESanteManagerApplication.Exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException() {
        super("L'entité existe déjà.");
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}