package com.example.demofile.exception;

public class FileNotFound extends RuntimeException{
    public FileNotFound(String message) {
        super(message);
    }
}