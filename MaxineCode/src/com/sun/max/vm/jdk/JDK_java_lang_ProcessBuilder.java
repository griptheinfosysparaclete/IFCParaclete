/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.max.vm.jdk;

import com.sun.max.annotate.ALIAS;
import com.sun.max.annotate.INTRINSIC;
import com.sun.max.vm.actor.holder.ClassActor;
import com.sun.max.vm.heap.Heap;
import static com.sun.max.vm.intrinsics.MaxineIntrinsicIDs.UNSAFE_CAST;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


public final class JDK_java_lang_ProcessBuilder
{
    @ALIAS(declaringClass = ProcessBuilder.class)
    File directory;
    
    @ALIAS(declaringClass = ProcessBuilder.class)
    private List<String> command;

 
    @ALIAS(declaringClass = ProcessBuilder.class, name = "<environment>", optional = true)
    protected native Process environment(String[] envp);

    @ALIAS(declaringClass = ProcessBuilder.class, name = "<start>", optional = true)
    public native Process start();

    @INTRINSIC(UNSAFE_CAST)
    static native JDK_java_lang_ProcessBuilder asThis(ProcessBuilder pb);

    public static Process createProcessBuilder(String[] cmdarray, String[] envp, File dir) throws IOException {
        final ProcessBuilder pb =
            (ProcessBuilder) Heap.createTuple(ClassActor.fromJava(ProcessBuilder.class).dynamicHub());
        final Process process;
        JDK_java_lang_ProcessBuilder thisProcessBuilder = asThis(pb);
        thisProcessBuilder.command = new ArrayList<>(cmdarray.length);
        for (String arg : cmdarray)
            thisProcessBuilder.command.add(arg);
        thisProcessBuilder.directory = dir;
        thisProcessBuilder.environment(envp);
        
        process = thisProcessBuilder.start();
        return process;
    }
 
}
