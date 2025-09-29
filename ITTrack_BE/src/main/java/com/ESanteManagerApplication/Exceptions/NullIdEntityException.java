package com.ESanteManagerApplication.Exceptions;

public class NullIdEntityException  extends RuntimeException{
    public NullIdEntityException() {
        super("L'identifiant fourni est nul. Veuillez fournir un ID valide.");
    }

    public NullIdEntityException(String message) {
        super(message);
    }
}
