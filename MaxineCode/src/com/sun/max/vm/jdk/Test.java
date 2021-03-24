package com.sun.max.vm.jdk;

import com.sun.max.vm.type.JavaTypeDescriptor;
import com.sun.max.vm.type.TypeDescriptor;

import java.util.HashMap;
import java.util.Map;

public class Test {
    @SuppressWarnings({ "oracle.jdeveloper.java.unchecked-conversion", "unchecked" })
    protected HashMap<String,String> environment = new HashMap();
    public Test() {
 
    }

    @SuppressWarnings("oracle.jdeveloper.java.null-map-return")
    public Map<String,String> method() {
        return null;
    }

    @SuppressWarnings({ "oracle.jdeveloper.java.null-map-return", "oracle.jdeveloper.java.null-array-return" })
    public Test[][] method1() {
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Test test = new Test();
        TypeDescriptor td = JavaTypeDescriptor.forJavaClass(Map.class);

        System.out.println("x: " + td.toJavaString());
        
        /*         System.out.println("x: " + Test.class.getMethod("method1")
                                             .getReturnType().toString());
        System.out.println(test.toString());
        */
       
    }

 
}
