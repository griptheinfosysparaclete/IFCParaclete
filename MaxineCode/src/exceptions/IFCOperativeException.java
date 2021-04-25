package org.ifcparaclete.exceptions;


public class IFCOperativeException extends Exception {
    @SuppressWarnings("compatibility:8252828390533577979")
    private static final long serialVersionUID = 1L;

    public IFCOperativeException() {
        super();
    }

    /**
     * @param errorMessage
     */
    public IFCOperativeException(String errorMessage) {
        super();
        System.err.printf(errorMessage);
        printStackTrace();

    }


    public static void main(String[] args) {
        IFCOperativeException IFCPropertiesException = new IFCOperativeException();
    }
}
