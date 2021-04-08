package org.ifcparaclete.utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.HashMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Builder;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;

import org.ifcparaclete.IFCStatics;

public class IFCLogManager extends LogManager implements IFCStatics, MessageFactory {
    private static HashMap<String, IFCLogger> loggerMap = new HashMap<String, IFCLogger>();
    private static int loggerCount = 0;
    private static IFCLogger logger;
    private static IFCOutputStream ifcOutputStream;
    private static IFCPrintStream ifcPrintStream;
    private static PatternLayout ifcLayout;

    private IFCLogManager() {
        super();
    }

    public IFCLogManager(String loggerFileNameArg) {
        super();
        initIFCLogManager(loggerFileNameArg);
    }

    private void initIFCLogManager(String loggerFileNameArg) {
        Builder     ifcBuilder;

        ifcBuilder = PatternLayout.newBuilder()
                                  .withPattern(IFCStatics.IFC_DEFAULT_LOG_LAYOUT)
                                  .withHeader("HEADER")
                                  .withFooter("FOOTER");
        ifcLayout = (PatternLayout) ifcBuilder.build();
        try {
            ifcOutputStream = new IFCOutputStream(loggerFileNameArg, true, ifcLayout);
            ifcPrintStream = new IFCPrintStream(ifcOutputStream);
        } catch (IOException e) {
        }

    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public IFCLogger getIFCLogger(String loggerNameArg) {

         if (loggerMap.containsKey(loggerNameArg)) {
            logger = loggerMap.get(loggerNameArg);
        }
        else {
            logger = new IFCLogger(loggerNameArg);
            loggerMap.put(loggerNameArg, logger);
            loggerCount++;
        }
        logger.setIFCPrintStream(ifcPrintStream);
        
        return logger;
    }

    /**
     * @param className
     * @param msg
     * @return
     */
    public int putLogMsg(String className, String msg) {

        IFCLogger logger = loggerMap.get(className);
        Message ifcMessage;
        
        String stackTrace = null;
        StringWriter sw = new StringWriter();

        Exception ex = new Exception();
        ex.printStackTrace(new PrintWriter(sw));
        stackTrace = sw.toString();

        Marker ifcMarker = logger.getMarker();
        ifcMessage = newMessage("\n" + msg + "\nStack Trace ==\n" + stackTrace + "\n");


        logger.logMessage(className,Level.ALL,ifcMarker, ifcMessage, new Throwable("I don't know"));


        return (EnvironmentConstants.GOOD_EXIT);
    }

    /**
     * @param className
     * @param msg
     * @return
     */
    public int printLogMsg(String className, String msg) {

        IFCLogger logger = loggerMap.get(className);
        Message ifcMessage;

        String stackTrace = null;
        StringWriter sw = new StringWriter();

        Exception ex = new Exception();
        ex.printStackTrace(new PrintWriter(sw));
        stackTrace = sw.toString();

        Marker ifcMarker = logger.getMarker();
        ifcMessage = newMessage("\n" + msg + "\nStack Trace ==\n" + stackTrace + "\n");


        logger.logMessage(className, Level.ALL, ifcMarker, ifcMessage, new Throwable("I don't know"));


        return (EnvironmentConstants.GOOD_EXIT);
    }
    @Override
    public Message newMessage(Object message) {

        return null;
    }

    @Override
    public Message newMessage(String message) {
        Message msg;
        msg = (Message) new IFCLogMessage(message);
        return msg;
    }

    @Override
    public Message newMessage(String message, Object... params) {
        // TODO Implement this method
        return null;
    }
    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public static void main(String[] args) {
        IFCLogManager ifcLogManager = new IFCLogManager(IFCStatics.IFC_DEFAULT_LOG_FILE);
        String ifcClassName = ifcLogManager.getClass().getName();
        
        ifcLogManager.getIFCLogger(ifcClassName);
        ifcLogManager.putLogMsg(ifcClassName, "Testing IFCLogManager");

    }


}
