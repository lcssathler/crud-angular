package com.br.crudcourses.crudspring.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;
    
    public RecordNotFoundException (Long id) {
        super(String.format("Record '%d' not found", id));
    }
}
