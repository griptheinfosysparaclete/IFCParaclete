package org.ifcparaclete;

import com.google.common.collect.ImmutableMap;

import java.util.Map;


public interface IFCStatics {

    public static final String IFC_DEFAULT_POLICY_FILE =
        "/Users/GripGlebe/jdeveloper/mywork/IFCParaclete/IFCPolicies/IFCPolicy.xml";
    public static final String IFC_DEFAULT_LOG_FILE =
        "/Users/GripGlebe/jdeveloper/mywork/IFCParaclete/IFCLogs/IFCLogFile.log";
    public static final String IFC_DEFAULT_LOG_LAYOUT = "[%level{lowerCase=true} %date{yyyy/MM/dd HH:mm:ss.SSS z} <%thread> tid=%tid] %message%n%throwable%n";
    public static final String IFC_DEFAULT_APPLICATION = "test.output.HelloWorld";
    static final String IFC_DEFAULT_MESSAGE = "This is the IFC_DEFAULT_MESSAGE";     
    static final String IFC_ACTOR_OBJECT = "ifc:ActorObject";
    static final String IFC_TARGET_OBJECT = "ifc:TargetObject";
    static final String IFC_NAME = "ifc:Name";
    static final String IFC_ID = "ifc:ID";
    static final String IFC_SECURITY_LEVEL = "ifc:SecurityLevel";
    static final String IFC_SL_FULLY_TRUSTED = "F";
    static final String IFC_SL_HIGHLY_TRUSTED = "H";
    static final String IFC_SL_PARTIALLY_TRUSTED = "P";
    static final String IFC_SL_UNTRUSTED = "U";
    @SuppressWarnings("unchecked")
    static final Map<String, Integer> IFC_SECURITY_LEVELS = ImmutableMap.of("F", 4, "H", 3, "P", 2, "U", 1);
    static final String IFC_CATEGORY = "ifc:Category";
    static final String IFC_CATEGORY_SYSTEM = "S";
    static final String IFC_CATEGORY_APPLICATION = "A";
    static final Map<String, Integer> IFC_CATEGORIES = ImmutableMap.of("S", 1, "A", 0);
    static final String IFC_TYPE = "ifc:Type";
    static final String IFC_TYPE_INTRANSITIVE = "I";
    static final String IFC_TYPE_TRANSITIVE = "T";
    static final Map<String, Integer> IFC_TYPES = ImmutableMap.of("T", 20, "I", 9);
    static final int    IFC_AUTHORIZATION_TYPE_FAI = 208;
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
    static final String IFC_OP_MODIFY = "m";
    static final String IFC_ACTIVE_IFOPS = "ifc:ActiveIFOPS";
    static final String IFC_PASSIVE_IFOPS = "ifc:PassiveIFOPS";
    static final Map<String, Integer> IFC_OPS = ImmutableMap.<String, Integer>builder()
                                                           .put("e", 0)
                                                           .put("o", 1)
                                                           .put("c", 2)
                                                           .put("l", 3)
                                                           .put("w", 4)
                                                           .put("r", 5)
                                                           .put("d", 6)
                                                           .put("f", 7)
                                                           .put("a", 8)
                                                           .put("m", 9)
                                                           .build();

    static final Map<String, String> IFC_OPS_NAMES = ImmutableMap.<String, String>builder()
                                                           .put("e", "Execute")
                                                           .put("o", "Open")
                                                           .put("c", "Close")
                                                           .put("l", "Load")
                                                           .put("w", "Write")
                                                           .put("r", "Read")
                                                           .put("d", "Delete")
                                                           .put("f", "Reflection")
                                                           .put("a", "AccessSystemInformation")
                                                           .put("m", "Modify")
                                                           .build();
    static final Map<Integer, String> IFC_VM_PACKAGES = ImmutableMap.<Integer,String>builder()
                                                            .put(0,"com.sun")
                                                            .put(1,"com.oracle")
                                                            .build();
}
