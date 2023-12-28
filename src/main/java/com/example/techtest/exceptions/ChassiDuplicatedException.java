package com.example.techtest.exceptions;
public class ChassiDuplicatedException extends RuntimeException {
    public ChassiDuplicatedException(String chassi) {
        super("Chassis " + chassi + " is duplicated.");
    }
}
