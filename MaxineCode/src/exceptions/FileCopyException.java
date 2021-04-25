package org.ifcparaclete.exceptions;

import java.lang.Exception;
import java.lang.System;

public class FileCopyException extends Exception {
    @SuppressWarnings("compatibility:-6499837191637525316")
    private static final long serialVersionUID = 1L;

    public FileCopyException() {
        super();
    }

    public FileCopyException(String errorMessage) {

        super();
        System.err.printf(errorMessage);
        printStackTrace();

    }


    public static void main(String[] args) {
        FileCopyException fileCopyException = new FileCopyException();
    }
}
