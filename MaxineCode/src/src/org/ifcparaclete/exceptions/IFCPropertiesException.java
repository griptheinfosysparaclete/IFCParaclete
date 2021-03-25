package org.ifcparaclete.exceptions;

import java.lang.Exception;
import java.lang.System;

public class IFCPropertiesException extends Exception {
    @SuppressWarnings("compatibility:8252828390533577979")
    private static final long serialVersionUID = 1L;

    public IFCPropertiesException() {
        super();
    }

    /**
     * @param errorMessage
     */
    public IFCPropertiesException(String errorMessage) {
        super();
        System.err.printf(errorMessage);
        printStackTrace();

    }


    public static void main(String[] args) {
        IFCPropertiesException IFCPropertiesException = new IFCPropertiesException();
    }
}
