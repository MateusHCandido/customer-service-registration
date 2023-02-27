package com.mtzz.application.service.services.exceptions;

public class InvalidCpfException extends RuntimeException
{
    public InvalidCpfException(String message)
    {
        super(message);
    }
}
