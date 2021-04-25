package org.ifcparaclete.utilities;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Builder;

import org.ifcparaclete.IFCStatics;

public class IFCPrintStream extends PrintStream {
   
    public IFCPrintStream(OutputStream out) {
        super(out, false);
    }

    public static void main(String[] args) {
        PatternLayout ifcLayout;

        IFCOutputStream ifcOutputStream;
        IFCPrintStream ifcPrintStream;
        Builder ifcBuilder;

        ifcBuilder = PatternLayout.newBuilder()
                                  .withPattern(IFCStatics.IFC_DEFAULT_LOG_LAYOUT)
                                  .withHeader("HEADER: Testing IFCOutputStream")
                                  .withFooter("FOOTER: Testing IFCOutputStream");
        ifcLayout = (PatternLayout) ifcBuilder.build();
        try {
            ifcOutputStream = new IFCOutputStream(IFCStatics.IFC_DEFAULT_LOG_FILE, true, ifcLayout);
            ifcPrintStream = new IFCPrintStream(ifcOutputStream);
        } catch (IOException e) {
        }

        ;
    }
}
