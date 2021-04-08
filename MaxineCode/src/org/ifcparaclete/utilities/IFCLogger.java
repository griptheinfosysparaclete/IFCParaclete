package org.ifcparaclete.utilities;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLogger;

import org.ifcparaclete.IFCStatics;


@SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
public class IFCLogger extends AbstractLogger implements IFCStatics, Logger, ExtendedLogger {
    @SuppressWarnings({ "compatibility:-8892366396897350509", "oracle.jdeveloper.java.serialversionuid-stale" })
    private static final long serialVersionUID = 1L;
    private Marker ifcLoggerMarker;
    private IFCLogger logger = null;
    private Level ifcLevel = Level.ALL;
    private IFCOutputStream ifcOutputStream;
    private IFCPrintStream  ifcPrintStream;

    private IFCLogger() {

    }

    public IFCLogger(String loggerName) {
        super(loggerName);
        this.ifcLoggerMarker = MarkerManager.getMarker(loggerName);
        logger = this;
    }

    public Marker getMarker() {

        return this.ifcLoggerMarker;
    }

    @Override
    public Level getLevel() {

        return this.ifcLevel;
    }

    public void setLevel(Level levelArg) {

        this.ifcLevel = levelArg;
        return;
    }


    public IFCPrintStream getPrintStream() {

        return this.ifcPrintStream;
    }

    public void setIFCPrintStream(IFCPrintStream ifcPrintStreamArg) {

        this.ifcPrintStream = ifcPrintStreamArg;
        return;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, CharSequence message, Throwable t) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable t) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object message, Throwable t) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                             Object p4) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                             Object p4, Object p5) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                             Object p4, Object p5, Object p6) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                             Object p4, Object p5, Object p6, Object p7) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                             Object p4, Object p5, Object p6, Object p7, Object p8) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                             Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object... params) {
        // TODO Implement this method
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Throwable t) {
        // TODO Implement this method
        return false;
    }

    @Override
    public void logMessage(String fqcn, Level level, Marker marker, Message message, Throwable t) {
        log(level, message, t);
        return;

    }

    public void printMessage(Message message) {
        
        return;

    }
    
    public void log(final Level level, final String message, final Throwable throwable) {
        final Message m =
            this.getMessageFactory()
            .newMessage("{} {} {}: {}", getClass().getSimpleName(), getName(), message, throwable);
        super.log(level, m, throwable);
    }

    public void logDebug(final String message, final Throwable throwable) {
        log(Level.DEBUG, message, throwable);
    }

    public void logError(final String message, final Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }

    public void logWarn(final String message, final Throwable throwable) {
        log(Level.WARN, message, throwable);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String className;
        String logFileName;
        Marker ifcTestMarker;
        IFCLogManager ifcLogManager;
        IFCLogger ifcLogger;

        className = IFCLogger.class.getCanonicalName();
        logFileName = "c:\\" + className + ".html";
        ifcLogManager = new IFCLogManager(logFileName);
        ifcLogger = ifcLogManager.getIFCLogger(className);

        ifcTestMarker = MarkerManager.getMarker("TEST");


        ifcLogger.logMessage(className, Level.ALL, ifcTestMarker, "Entering IFCLogger Main",
                             new Throwable("Who Knows"));

        ifcLogger.logMessage(className, Level.ALL, ifcTestMarker, "Exiting IFCLogger Main",
                             new Throwable("Who Always Knows"));

        System.exit(0);

    }
}
