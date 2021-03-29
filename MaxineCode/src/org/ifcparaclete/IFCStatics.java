package org.ifcparaclete;

import com.google.common.collect.ImmutableMap;

import java.util.Map;


public interface IFCStatics {

    public static final String IFC_DEFAULT_POLICY_FILE =
        "/Users/GripGlebe/jdeveloper/mywork/IFCParaclete/MaxineCode/src/org/ifcparaclete/IFCPolicy.xml";
    public static final String IFC_DEFAULT_APPLICATION = "test.output.HelloWorld";
    static final String IFC_RUNNABLE_OBJECT = "ifc:RunnableObject";
    static final String IFC_NAME = "ifc:Name";
    static final String IFC_ID = "ifc:ID";
    static final String IFC_SECURITY_LEVEL = "ifc:SecurityLevel";
    static final String IFC_SL_FULLY_TRUSTED = "F";
    static final String IFC_SL_HIGHLY_TRUSTED = "H";
    static final String IFC_SL_PARTIALLY_TRUSTED = "P";
    static final String IFC_SL_UNTRUSTED = "U";
    @SuppressWarnings("unchecked")
    static final Map<String, String> IFC_SECURITY_LEVELS = ImmutableMap.of("F", "F", "H", "H", "P", "P", "U", "U");
    static final String IFC_CATEGORY = "ifc:Category";
    static final String IFC_CATEGORY_SYSTEM = "S";
    static final String IFC_CATEGORY_APPLICATION = "A";
    static final Map<String, String> IFC_CATEGORIES = ImmutableMap.of("S", "S", "A", "A");
    static final String IFC_TYPE = "ifc:Type";
    static final String IFC_TYPE_INTRANSITIVE = "I";
    static final String IFC_TYPE_TRANSITIVE = "T";
    static final Map<String, String> IFC_TYPES = ImmutableMap.of("T", "T", "I", "I");
    static final String IFC_OP = "ifc:Op";
    static final String IFC_OP_EXECUTE = "e";
    static final String IFC_OP_OPEN = "o";
    static final String IFC_OP_CLOSE = "c";
    static final String IFC_OP_LOAD = "l";
    static final String IFC_OP_WRITE = "w";
    static final String IFC_OP_READ = "r";
    static final String IFC_OP_DELETE = "d";
    static final String IFC_OP_REFLECT = "f";
    static final String IFC_OP_ACCESS = "a";
    static final String IFC_ACTIVE_IFOPS = "ifc:ActiveIFOPS";
    static final String IFC_PASSIVE_IFOPS = "ifc:PassiveIFOPS";
    static final Map<String, String> IFC_OPS = ImmutableMap.<String, String>builder()
                                                           .put("e", "e")
                                                           .put("o", "o")
                                                           .put("c", "c")
                                                           .put("l", "l")
                                                           .put("w", "w")
                                                           .put("r", "r")
                                                           .put("d", "d")
                                                           .put("f", "f")
                                                           .put("a", "a")
                                                           .build();

}
