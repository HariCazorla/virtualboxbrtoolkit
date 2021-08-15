package com.shreeharibi.virtualboxbrtoolkit.exceptions;

public class VirtualMachineException extends Exception{
    public VirtualMachineException(String message) {
        super(message);
    }

    public VirtualMachineException(String message, Throwable cause) {
        super(message, cause);
    }
}
