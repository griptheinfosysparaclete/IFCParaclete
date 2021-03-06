/*
 * Copyright (c) 2019, APT Group, School of Computer Science,
 * The University of Manchester. All rights reserved.
 * Copyright (c) 2015, Andrey Rodchenko. All rights reserved.
 * Copyright (c) 2007, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
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
import com.sun.max.annotate.INLINE;
import com.sun.max.annotate.INTRINSIC;
import com.sun.max.annotate.METHOD_SUBSTITUTIONS;
import com.sun.max.annotate.NEVER_INLINE;
import com.sun.max.annotate.SUBSTITUTE;
import com.sun.max.vm.Log;
import com.sun.max.vm.MaxineVM;
import static com.sun.max.vm.MaxineVM.vm;
import static com.sun.max.vm.actor.member.InjectedReferenceFieldActor.Thread_vmThread;
import com.sun.max.vm.heap.Heap;
import static com.sun.max.vm.intrinsics.MaxineIntrinsicIDs.UNSAFE_CAST;
import com.sun.max.vm.management.ThreadManagement;
import com.sun.max.vm.monitor.Monitor;
import static com.sun.max.vm.run.java.JavaRunScheme.ifcEnforcer;
import com.sun.max.vm.runtime.FatalError;
import com.sun.max.vm.thread.VmThread;
import com.sun.max.vm.thread.VmThreadFactory;
import com.sun.max.vm.thread.VmThreadMap;
import com.sun.max.vm.type.ClassRegistry;

import java.security.AccessControlContext;

import org.ifcparaclete.IFCStatics;
import org.ifcparaclete.exceptions.IFCOperativeException;

/**
 * Method substitutions for {@link java.lang.Thread java.lang.Thread}.
 *
 */
@METHOD_SUBSTITUTIONS(Thread.class)
public final class JDK_java_lang_Thread {

    private JDK_java_lang_Thread() {
    }

    /**
     * Register native methods, which there are none in this implementation.
     */
    @SUBSTITUTE
    private static void registerNatives() {
    }

    /**
     * Cast this object reference to the {@code java.lang.Thread} object it represents.
     * @return this object casted to {@code java.lang.Thread}
     */
    @INTRINSIC(UNSAFE_CAST)
    private native Thread thisThread();

    /**
     * Get the VM thread for this thread.
     *
     * @return the corresponding VM thread for this {@code java.lang.Thread}. This value will be {@code null} for a
     *         thread that hasn't been started.
     */
    @INLINE
    private VmThread thisVMThread() {
        return VmThread.fromJava(thisThread());
    }

    @ALIAS(declaringClass = Thread.class)
    private static native int nextThreadNum();

    @ALIAS(declaringClass = Thread.class)
    private native void exit();

    @ALIAS(declaringClass = ThreadGroup.class)
    private native void add(Thread thread);

    @ALIAS(declaringClass = Thread.class, optional = true)
    private native void init(ThreadGroup g, Runnable target, String name, long stackSize);

    @ALIAS(declaringClass = Thread.class, optional = true)
    private native void init(ThreadGroup g, Runnable target, String name, long stackSize, boolean setPriority);

    @ALIAS(declaringClass = Thread.class)
    int priority;

    @ALIAS(declaringClass = Thread.class)
    boolean daemon;

    @ALIAS(declaringClass = Thread.class, name = "name", optional = true) // Not available in JDK 8
    char[] nameArray;

    @ALIAS(declaringClass = Thread.class, name = "name", optional = true) // Not available in JDK 7
    String nameString;

    @ALIAS(declaringClass = Thread.class)
    AccessControlContext inheritedAccessControlContext;

    @INTRINSIC(UNSAFE_CAST)
    static native JDK_java_lang_Thread asThis(ThreadGroup tg);

    @INTRINSIC(UNSAFE_CAST)
    static native JDK_java_lang_Thread asThis(Thread t);

    public static void exitThread(Thread javaThread) {
        JDK_java_lang_Thread thisThread = asThis(javaThread);
        thisThread.exit();
    }

    public static Thread createThreadForAttach(VmThread vmThread, String name, ThreadGroup group,
                                               boolean daemon) throws Throwable {
        FatalError.check(group != null, "ThreadGroup for attaching thread cannot be null");

        final Thread javaThread = (Thread) Heap.createTuple(ClassRegistry.THREAD.dynamicHub());
        Thread_vmThread.setObject(javaThread, vmThread);
        JDK_java_lang_Thread thisThread = asThis(javaThread);
        thisThread.priority = Thread.NORM_PRIORITY;
        vmThread.suspendMonitor.init();
        if (name == null) {
            name = "Thread-" + String.valueOf(nextThreadNum());
        }
        vmThread.setJavaThread(javaThread, name);
        thisThread.init(group, null, name, 0L);

        if (daemon) {
            thisThread.daemon = true;
        }

        asThis(group).add(javaThread);
        return javaThread;
    }

    /**
     * Retrieve the current thread.
     * @see java.lang.Thread#currentThread()
     * @return the current thread
     */
    @SUBSTITUTE
    public static Thread currentThread() {

        return VmThread.current().javaThread();
    }

    /**
     * Prints a stack trace of the current thread to the standard error stream.
     * This method is used only for debugging.
     *
     * @see     Throwable#printStackTrace()
     */
    @SUBSTITUTE
    public static void dumpStack() {
        if (vm().phase == MaxineVM.Phase.RUNNING) {
            Log.println("In: " + Thread.class.getName());
            try {
                Log.println("Still in: " + Thread.class.getName());
                ifcEnforcer.ifcCheck(Thread.currentThread().getStackTrace(), "java.lang.Thread",
                                     IFCStatics.IFC_OP_ACCESS);
            } catch (IFCOperativeException ifcOperativeException) {
                MaxineVM.exitJVM(ifcOperativeException);
            }
        }
        new Exception("Stack trace").printStackTrace();
    }

    /**
     * Yield execution to other threads, if possible.
     * @see java.lang.Thread#yield()
     */
    @SUBSTITUTE
    public static void yield() {
        VmThread.yield();
    }

    /**
     * Sleep for the specified amount of time.
     * @see java.lang.Thread#sleep(long)
     * @param millis the number of milliseconds to sleep
     */
    @SUBSTITUTE
    public static void sleep(long millis) throws InterruptedException {
        VmThread.sleep(millis);
    }

    /**
     * Starts the thread running.
     * @see java.lang.Thread#start()
     */
    @SUBSTITUTE
    private void start0() {
        final VmThread vmThread = VmThreadFactory.create(thisThread());
        vmThread.start0();
    }

    /**
     * Checks whether this thread has been interrupted.
     * @see java.lang.Thread#isInterrupted()
     * @param clearInterrupted {@code true} if the interrupted status of this thread should be cleared by
     * this operation; {@code false} otherwise
     * @return the interrupted status of another thread
     */
    @SUBSTITUTE
    private boolean isInterrupted(boolean clearInterrupted) throws InterruptedException {
        VmThread vmThread = thisVMThread();
        return vmThread != null && vmThread.isInterrupted(clearInterrupted);
    }

    /**
     * Checks whether this thread is still alive (i.e. has not been terminated).
     * @see java.lang.Thread#isAlive()
     * @return {@code true} if this thread is still alive; {@code false} otherwise
     */
    @SUBSTITUTE
    public boolean isAlive() {
        VmThread vmThread = thisVMThread();
        return vmThread != null && vmThread.state() != Thread.State.NEW && vmThread.state() != Thread.State.TERMINATED;
    }

    /**
     * Counts the number of stack frames on this thread's stack.
     * @deprecated The definition of this call depends on {@link #suspend0()},
     *         which is deprecated.  Further, the results of this call
     *         were never well-defined.
     * @return 0
     */
    @SUBSTITUTE
    @Deprecated
    public int countStackFrames() {
        return 0;
    }

    /**
     * Checks whether this thread holds the monitor of the specified object.
     * @see java.lang.Thread#holdsLock(Object)
     * @param object the object which to check
     * @return {@code true} if this thread currently owns the monitor of the specified object; {@code false}
     * otherwise
     */
    @SUBSTITUTE
    public static boolean holdsLock(Object object) {
        return Monitor.threadHoldsMonitor(object, VmThread.current());
    }

    /**
     * Dump the stacks of the specified threads.
     * @param threads the threads to dump
     * @return an array of {@link java.lang.StackTraceElement java.lang.StackTraceElement}'s for each
     * thread's stack
     */
    @SUBSTITUTE
    private static StackTraceElement[][] dumpThreads(Thread[] threads) {
        return ThreadManagement.dumpThreads(threads);
    }

    /**
     * Gets all threads in the system.
     * @return an array of all the threads in the system
     */
    @SUBSTITUTE
    private static Thread[] getThreads() {
        return VmThreadMap.getThreads(false);
    }

    /**
     * Set the priority of this thread at the OS level.
     * @see java.lang.Thread#setPriority(int)
     * @param newPriority the new priority
     */
    @SUBSTITUTE
    private void setPriority0(int newPriority) {
        VmThread vmThread = thisVMThread();
        if (vmThread != null) {
            vmThread.setPriority0(newPriority);
        }
    }

    /**
     * Stops this thread at the OS level.
     * @param throwable the throwable to throw in the target thread
     */
    @SUBSTITUTE
    private void stop0(Object throwable) {
        VmThread vmThread = thisVMThread();
        if (vmThread != null) {
            vmThread.stop0(throwable);
        }
    }

    /**
     * Suspends this thread at the OS-level.
     */
    @SUBSTITUTE
    private void suspend0() {
        VmThread vmThread = thisVMThread();
        if (vmThread != null) {
            vmThread.suspend0();
        }
    }

    /**
     * The sole reason this is substituted is to ensure it is never inlined.
     * Why? Because JDWP agents set a (hidden) breakpoint here and if it is inlined we have
     * to recompile the inliners, at least one of which cannot be compiled with T1X.
     */
    @NEVER_INLINE
    @SUBSTITUTE
    @Deprecated
    private void resume() {
        thisThread().checkAccess();
        resume0();
    }


    /**
     * Resumes this thread at the OS level.
     */
    @SUBSTITUTE
    private void resume0() {
        VmThread vmThread = thisVMThread();
        if (vmThread != null) {
            vmThread.resume0();
        }
    }

    /**
     * Interrupts this thread at the OS level.
     * @see java.lang.Thread#interrupt()
     */
    @SUBSTITUTE
    private void interrupt0() {
        VmThread vmThread = thisVMThread();
        if (vmThread != null) {
            vmThread.interrupt0();
        }
    }

    /**
     * Gets the state of this thread.
     * @see java.lang.Thread#getState()
     * @return the state of this thread
     */
    @SUBSTITUTE
    private Thread.State getState() {
        VmThread vmThread = thisVMThread();
        return vmThread == null ? Thread.State.NEW : vmThread.state();
    }

    /**
     * Gets the name directly out of the {@link Thread}.
     * @param thread
     */
    public static String getName(Thread thread) {
        if (MaxineVM.isHosted()) {
            return thread.getName();
        } else {
            JDK_java_lang_Thread thisThread = asThis(thread);
            if (JDK.JDK_VERSION == JDK.JDK_7) {
                return String.valueOf(thisThread.nameArray);
            } else {
                return thisThread.nameString;
            }
        }
    }

    /**
     * We substitute this because {@link Thread} holds the name as a {@code char} array,
     * and we have cached it as a {@link String} in the {@link VmThread} after the thread was started.
     */
    @SUBSTITUTE
    public String getName() {
        if (thisVMThread() != null) {
            return thisVMThread().getName();
        } else {
            if (JDK.JDK_VERSION == JDK.JDK_7) {
                return String.valueOf(nameArray);
            } else {
                return nameString;
            }
        }
    }

    /**
     * Sets the name of the the thread, also updating the name in the corresponding VmThread.
     * @param name new name for thread
     */
    @SUBSTITUTE
    private void setName(String name) {
        thisThread().checkAccess();
        if (thisVMThread() != null) {
            thisVMThread().setName(name);
        }
        if (JDK.JDK_VERSION == JDK.JDK_7) {
            this.nameArray = name.toCharArray();
        } else {
            this.nameString = name;
        }
    }
}
