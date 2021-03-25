package org.ifcparaclete.utilities;


import java.io.PrintWriter;
import java.io.StringWriter;

import java.nio.charset.Charset;

import java.util.HashMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;


public class IFCLogger
{
  private static String   logFileName = "c:\\IFCLogger.html";
  private static HashMap  loggerMap = null;
  private static int      loggerCount = 0;
  private boolean         printIt = Boolean.TRUE;
  
  private Logger          rootLogger = null;
  private Logger          logger = null;
  private LoggerConfig    lConfig = null;
  private FileAppender    fileAppender = null;
  private PatternLayout   layout = null;
  private Filter          filter = null;
  
  public IFCLogger() 
  {
    super();

    rootLogger = LogManager.getRootLogger();


    }

    /**
     * @param classNameArg
     * @param logFileNameArg
     * @param levelArg
     * @return
     */
    @SuppressWarnings({ "unchecked", "deprecation", "oracle.jdeveloper.java.trivial-assignment" })
    public Logger createActiveLogger(String classNameArg, String logFileNameArg,
                           Level levelArg)
  {
   
    loggerMap = new HashMap();    

    logger = LogManager.getLogger(classNameArg);

    layout = PatternLayout.createLayout(
            "[%level{lowerCase=true} %date{yyyy/MM/dd HH:mm:ss.SSS z} <%thread> tid=%tid] %message%n%throwable%n",
            null, null, null, Charset.defaultCharset(), true, false, "", "");
    
    if (logFileNameArg.equals("") && loggerCount == 0)
    {
      logFileName = IFCLogger.logFileName;

    }

    
    fileAppender = FileAppender.createAppender(logFileName, "true", "false", classNameArg,
                    "true", "false", "false", "0", layout, null, null, null, (Configuration) lConfig);

    fileAppender.start();
    loggerCount++;
    
    loggerMap.put(classNameArg, logger);

    return (logger);
  }

    /**
     * @param className
     * @param msg
     * @return
     */
    public int putLogMsg(String className, String msg)
  {
    if (printIt)
    {
     Logger logger = (Logger) loggerMap.get(className);
     Exception ex = new Exception();
     String stackTrace = null;
     StringWriter sw = new StringWriter();

     ex.printStackTrace(new PrintWriter(sw));
     stackTrace = sw.toString();
     
 
     logger.info("\n" +
         msg + "\nStack Trace ==\n" +
         stackTrace + "\n");
 
    }
    
    return (EnvironmentConstants.GOOD_EXIT);
  }

    /**
     * @param printItArg
     */
    public void setPrintIt(boolean printItArg)
  {
    printIt = printItArg;
    return;
  }


    /**
     * @param args
     */
    public static void main(String[] args)
  {
    String className = IFCLogger.class.getName();
    String logFileName = "c:\\" + className + ".html";

    IFCLogger ifcLogger = new IFCLogger();
    ifcLogger.createActiveLogger(className, logFileName, Level.ALL);
    ifcLogger.putLogMsg(className, "Entering IFCLogger Main");

    ifcLogger.putLogMsg(className, "Exiting IFCLogger Main");

    System.exit(0);

  }
}
