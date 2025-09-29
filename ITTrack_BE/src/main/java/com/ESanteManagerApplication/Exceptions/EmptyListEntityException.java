package com.ESanteManagerApplication.Exceptions;

public class EmptyListEntityException extends RuntimeException{
    public EmptyListEntityException() {
        super("Aucun enregistrement trouvé.");
    }

    public EmptyListEntityException(String message) {
        super(message);
    }
}
