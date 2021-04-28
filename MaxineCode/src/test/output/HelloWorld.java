/*
 * Copyright (c) 2007, 2012, Oracle and/or its affiliates. All rights reserved. DO NOT ALTER OR
 * REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License version 2 only, as published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version 2 along with this work;
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA or visit www.oracle.com
 * if you need additional information or have any questions.
 */
package test.output;

import java.io.FileOutputStream;
import java.io.PrintStream;
/*
import com.sun.max.vm.Log;
import org.ifcparaclete.utilities.IFCLogManager;
import org.ifcparaclete.utilities.IFCLogger;
import org.ifcparaclete.utilities.IFCPrintStream; */

public class HelloWorld {
    FileOutputStream fos;
    PrintStream ps;

   public HelloWorld() {
       
       try {
            fos = new FileOutputStream("/Users/GripGlebe/jdeveloper/mywork/IFCParaclete/IFCLogs/IFCLogFile.log");
            ps = new PrintStream(fos);
            System.out.println("2 Hello World!");
            System.out.println("3 " + ps.getClass().getName());
            System.setOut(ps);
            System.out.println("4 Hello World!");
            System.out.println("5 " + ps.getClass().getName());
       }
       catch (java.io.FileNotFoundException fnfe) {
           printit(fnfe.getMessage());
       }


    }

    public void printit(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        // DO NOT HACK ME!
        System.out.println("1 Hello World!");       
        HelloWorld hw = new HelloWorld();
        hw.printit("6 Test Switch of Out");
        System.out.println("7 Hello World!");
        System.exit(0);
    }


}
