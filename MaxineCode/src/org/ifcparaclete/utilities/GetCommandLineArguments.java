package org.ifcparaclete.utilities;

import java.util.HashMap;

import org.apache.logging.log4j.Level;

public class GetCommandLineArguments {
    private static IFCLogger ifcLogger = null;
    private String commandLineArgs[] = null;
    private HashMap argsValueMap = null;

    public GetCommandLineArguments() {
        super();
    }

    public GetCommandLineArguments(String[] args, IFCLogger ifclArg) {
        super();
        commandLineArgs = args;
        argsValueMap = getArgsValues();
        ifcLogger = ifclArg;
    }

    public Object getArgValue(String argName) {
        Object argValue = argsValueMap.get(argName);

        return (argValue);
    }

    @SuppressWarnings("unchecked")
    private HashMap getArgsValues() {
        String argValue[] = null;
        HashMap argValueMap = new HashMap();

        for (int i = 0; i < commandLineArgs.length; i++) {
            argValue = parseArg(commandLineArgs[i]);
            argValueMap.put(argValue[0], argValue[1]);
        }

        return (argValueMap);
    }

    private String[] parseArg(String argToParse) {
        String delims = "[=]+";
        String[] tokens = null;

        tokens = argToParse.split(delims);

        return (tokens);
    }

    private void runTest() {
        int j = commandLineArgs.length;
        String className = GetCommandLineArguments.class.getName();
        String msg = null;

        String[] parsedArg = null;

        for (int i = 0; i < j; i++) {
            parsedArg = parseArg(commandLineArgs[i]);
            msg = "commandLineArgs[" + i + "] == '" + commandLineArgs[i] + "'\n";
            System.out.print(msg);
            ifcLogger.putLogMsg(className, msg);
            msg = "parsedArg Name  == '" + parsedArg[0] + "'\n";
            System.out.print(msg);
            ifcLogger.putLogMsg(className, msg);
            msg = "parsedArg Value == '" + parsedArg[1] + "'\n";
            System.out.print(msg);
            ifcLogger.putLogMsg(className, msg);
        }
    }

    private static void usage() {
        System.out.print("When run from the command line, the program is being run in test mode,\n");
        System.out.print("where the command line arguments will be printed back to the screen\n");
        System.out.print("Here is an example for running such a test:\n");
        System.out.print("java GetCommandLineArguments -strParam=Help -numericParam=2\n");
        System.out.print("Screen output will be:\n");
        System.out.print("strArg = Help\n");
        System.out.print("numericArg = 2\n");
    }

    public static void main(String[] args) {
        String className = GetCommandLineArguments.class.getName();
        String logFileName = "c:\\" + className + ".html";
        GetCommandLineArguments getCommandLineArguments = null;
        IFCLogger ifcLogger = new IFCLogger();

        ifcLogger.createActiveLogger(className, logFileName, Level.ALL);

        if (args.length > 0) {
            getCommandLineArguments = new GetCommandLineArguments(args, ifcLogger);
            getCommandLineArguments.runTest();
        } else {
            usage();
        }
    }
}

