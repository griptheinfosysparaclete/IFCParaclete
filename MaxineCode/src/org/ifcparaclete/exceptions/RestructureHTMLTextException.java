package org.ifcparaclete.exceptions;

import java.lang.Exception;
import java.lang.System;

public class RestructureHTMLTextException extends Exception {
    @SuppressWarnings("compatibility:-5284248603459044139")
    private static final long serialVersionUID = 1L;

    public RestructureHTMLTextException() {
        super();
    }

    public RestructureHTMLTextException(String errorMessage) {
        super();
        System.err.printf(errorMessage);
        printStackTrace();
    }

    public static void main(String[] args) {
        RestructureHTMLTextException restructureHTMLTextException = new RestructureHTMLTextException();
    }
}
