package com.shreeharibi.virtualboxbrtoolkit.exceptions;

public class VBoxConnectionException extends Exception{
    public VBoxConnectionException(String message) {
        super(message);
    }

    public VBoxConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
