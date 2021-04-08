package org.ifcparaclete.utilities;

import org.apache.logging.log4j.message.Message;

import org.ifcparaclete.IFCStatics;

public class IFCLogMessage implements IFCStatics, Message {
    @SuppressWarnings({ "compatibility:1449192195770976972", "oracle.jdeveloper.java.serialversionuid-stale" })
    private static final long serialVersionUID = 1L;
    private              IFCLogMessage ifcLogMessage;
    private              String        theMessage;
    
    private IFCLogMessage() {
        super();
        theMessage = IFCStatics.IFC_DEFAULT_MESSAGE;
        this.ifcLogMessage = this;
    }

    public IFCLogMessage(String messageArg) {
        super();
        theMessage = messageArg;
        this.ifcLogMessage = this;
    }

    public Message getIFCMessage() {
        return this.ifcLogMessage;
    }
    
    @Override
    public String getFormattedMessage() {
        // TODO Implement this method
        return this.theMessage;
    }

    @Override
    public String getFormat() {
        // TODO Implement this method
        return null;
    }

    @Override
    public Object[] getParameters() {
        // TODO Implement this method
        return new Object[0];
    }

    @Override
    public Throwable getThrowable() {
        // TODO Implement this method
        return null;
    }

    public static void main(String[] args) {
        IFCLogMessage iFCLogMessage = new IFCLogMessage();
    }
}
