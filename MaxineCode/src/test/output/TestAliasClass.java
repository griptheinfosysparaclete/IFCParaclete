package test.output;

import com.sun.max.annotate.ALIAS;
import com.sun.max.annotate.INTRINSIC;
import com.sun.max.vm.actor.holder.ClassActor;
import com.sun.max.vm.heap.Heap;
import static com.sun.max.vm.intrinsics.MaxineIntrinsicIDs.UNSAFE_CAST;

public class TestAliasClass {
    /*     @ALIAS(declaringClass = ProcessBuilder.class)
    File directory;
    @ALIAS(declaringClass = ProcessBuilder.class)
    private List<String> command; */
    final static ProcessBuilder pb = (ProcessBuilder) Heap.createTuple(ClassActor.fromJava(ProcessBuilder.class).dynamicHub());
    @INTRINSIC(UNSAFE_CAST)
    static native TestAliasClass asThis(ProcessBuilder pb);
    @ALIAS(declaringClass = ProcessBuilder.class, name = "<environment>")
    protected native Process environment(String[] envp);
    
    public TestAliasClass() {
        super();
    }
    
    public static Process testAlias(String[] envp) {
        TestAliasClass thisProcessBuilder = asThis(pb);
        Process process = thisProcessBuilder.environment(envp);
        
        return process;
    }

    
    public static void main(String[] args) {
        TestAliasClass testAliasClass = new TestAliasClass();
        TestAliasClass.testAlias(args);
    }
}
