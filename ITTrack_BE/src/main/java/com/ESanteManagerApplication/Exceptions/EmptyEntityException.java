package com.ESanteManagerApplication.Exceptions;

public class EmptyEntityException extends RuntimeException{
    public EmptyEntityException() {
        super("L'entité fournie est vide ou nulle.");
    }

    public EmptyEntityException(String message) {
        super(message);
    }
}
