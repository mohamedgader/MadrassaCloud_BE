package com.ESanteManagerApplication.Exceptions;

public class NotFoundEntityException extends RuntimeException{
    public NotFoundEntityException() {
        super("L'entité fournie est introuvable.");
    }

    public NotFoundEntityException(String message) {
        super(message);
    }

}
