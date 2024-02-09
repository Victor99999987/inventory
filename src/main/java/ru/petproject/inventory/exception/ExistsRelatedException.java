package ru.petproject.inventory.exception;

public class ExistsRelatedException extends RuntimeException{
    public ExistsRelatedException(String message) {
        super(message);
    }
}
