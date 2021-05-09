package org.ifcparaclete;

import com.sun.max.vm.MaxineVM;
import static com.sun.max.vm.MaxineVM.vm;

import java.util.HashMap;

import org.ifcparaclete.exceptions.IFCOperativeException;

public class IFCEnforcer implements IFCStatics {

    private static int numberOfVMPackages = IFC_VM_PACKAGES.size() - 1;

    @SuppressWarnings("oracle.jdeveloper.java.unrestricted-field-access")

    private static HashMap[] actorObjectsArray = new HashMap[2];
    private HashMap[] targetObjectsArray = new HashMap[2];
    private HashMap[] ifcObjectsArray;
    private IFCObject ifcActor;
    private IFCObject ifcTarget;
    private String mainApplication;
    int numOfPackages = IFCStatics.IFC_VM_PACKAGES.size();


    private IFCEnforcer() {
        super();
        ifcObjectsArray = IFCPolicy.loadIFCPolicy(IFCStatics.IFC_DEFAULT_POLICY_FILE);
        actorObjectsArray[0] = ifcObjectsArray[0];
        actorObjectsArray[1] = ifcObjectsArray[1];
        targetObjectsArray[0] = ifcObjectsArray[2];
        targetObjectsArray[1] = ifcObjectsArray[3];

    }

    public IFCEnforcer(String mainApplicationArg) {
        super();
        ifcObjectsArray = IFCPolicy.loadIFCPolicy(IFCStatics.IFC_DEFAULT_POLICY_FILE);
        actorObjectsArray[0] = ifcObjectsArray[0];
        actorObjectsArray[1] = ifcObjectsArray[1];
        targetObjectsArray[0] = ifcObjectsArray[2];
        targetObjectsArray[1] = ifcObjectsArray[3];
        mainApplication = mainApplicationArg;

    }

    public static boolean isUnknownClass(String classNameArg) {

        boolean isUnknown;
        int getThis;
        getThis = 0;
        isUnknown = false;
        String className;
        className = classNameArg;

        System.out.println("className: '" + className + "'");
        System.out.println("numberOfVMPackages: " + numberOfVMPackages);

        if (isVMPackage(className, getThis)) {
            isUnknown = false;
        } else {
            if (actorObjectsArray[0].containsKey(className)) {
                isUnknown = false;
            } else {
                isUnknown = true;
            }
        }

        System.out.println("Return 'isUnknown': " + isUnknown);
        return isUnknown;
    }

    private static boolean isVMPackage(String classNameArg, int getThisArg) {
        boolean match;
        match = false;
        int getThis = getThisArg;
        String packageName;

        System.out.println(getThis + ": getThis");
        if (getThis > numberOfVMPackages) {
            return match;
        }
        packageName = IFC_VM_PACKAGES.get(getThis);
        System.out.println(getThis + ": packageName: " + packageName);
        match = classNameArg.startsWith(packageName);
        System.out.println(getThis + ": match: " + match);
        if (!match) {
            if (getThis < numberOfVMPackages) {
                getThis++;
                match = isVMPackage(classNameArg, getThis);
                System.out.println(getThis + ": match: " + match);
            }
        } else {
            return match;
        }
        return match;
    }

    /**
     * @param actorClassNames
     * @param targetClassName
     * @param ifcOp
     * @return
     * @throws IFCOperativeException
     */
    public boolean ifcCheck(StackTraceElement[] actorClassNames, String targetClassName,
                            String ifcOp) throws IFCOperativeException {


        boolean allowed;
        allowed = false;
        int i;
        i = -1;
        int j;
        j = -1;

        int stackTraceLength;
        String actorClassName = null;
        String actorClassPackageName = null;
        stackTraceLength = actorClassNames.length - 1;
        if (vm().phase != MaxineVM.Phase.RUNNING) {
            allowed = true;
        } else {
            if (isNotVMActor(actorClassNames)) {
                for (i = stackTraceLength; i >= 0; i--) {
                    actorClassPackageName = actorClassNames[i].getClass()
                                                              .getPackage()
                                                              .getName();
                    actorClassName = actorClassNames[i].getClassName();
                    allowed = ifcCheck(actorClassName, targetClassName, ifcOp);
                    if (!allowed) {
                        throw new IFCOperativeException("Either one or both of these classes:\n" + targetClassName +
                                                        "\n" + actorClassName +
                                                        "\n is not an authorized class for this application, " +
                                                        mainApplication + "\n");
                    }
                }
            }
        }

        return allowed;

    }

    private boolean isNotVMActor(StackTraceElement[] actorClassNames) {

        boolean allowed;
        allowed = false;


        return allowed;

    }

    /**
     * @param actorClassName
     * @param targetClassName
     * @param ifcOp
     * @return
     */
    public boolean ifcCheck(String actorClassName, String targetClassName, String ifcOp) {

        boolean allowed = false;
        boolean inActorArray = false;
        boolean inTargetArray = false;
        String ifcActorKey;
        String ifcTargetKey;

        if (vm().phase != MaxineVM.Phase.RUNNING) {
            allowed = true;
        } else {
            inActorArray = actorObjectsArray[0].containsKey(actorClassName);
            inTargetArray = targetObjectsArray[0].containsKey(targetClassName);

            if ((inActorArray && inTargetArray)) {
                ifcActorKey = (String) actorObjectsArray[0].get(actorClassName);
                ifcTargetKey = (String) targetObjectsArray[0].get(targetClassName);
                ifcActor = (IFCObject) actorObjectsArray[1].get(ifcActorKey);
                ifcTarget = (IFCObject) targetObjectsArray[1].get(ifcTargetKey);
                allowed = ifcOpAllowed(ifcOp);
            }

        }

        return allowed;

    }

    private boolean ifcOpAllowed(String ifcOp) {

        boolean allowed = false;
        String ifcActorName = ifcActor.getName();
        String ifcTargetName = ifcTarget.getName();
        String ifcActorType = ifcActor.getType();
        int ifcActorSecurityLevelInt = ifcActor.getSecurityLevelInt();
        int ifcTargetSecurityLevelInt = ifcTarget.getSecurityLevelInt();
        System.out.println("ifcOpAllowed: " + ifcActorName + ": " + ifcActorType);
        System.out.println("ifcOpAllowed: " + ifcActorName + ": " + ifcActorSecurityLevelInt);
        System.out.println("ifcOpAllowed: " + ifcTargetName + ": " + ifcTargetSecurityLevelInt);
        System.out.println("ifcOpAllowed: " + "Hi!");
        boolean ifcActorActiveOp = ifcActor.getActiveOPS().get(IFC_OPS.get(ifcOp));
        boolean ifcTargetPassiveOp = ifcTarget.getPassiveOPS().get(IFC_OPS.get(ifcOp));

        System.out.println("ifcOpAllowed: " + ifcActorName + ": " + ifcActorActiveOp);
        System.out.println("ifcOpAllowed: " + ifcTargetName + ": " + ifcTargetPassiveOp);
        if (ifcActorType.equals(IFCStatics.IFC_TYPE_TRANSITIVE)) {
            if (ifcActorSecurityLevelInt >= ifcTargetSecurityLevelInt) {
                allowed = ifcActorActiveOp && ifcTargetPassiveOp;
            }

        }
        System.out.println("ifcOpAllowed: " + allowed);
        return allowed;

    }


    /**
     * @param args
     */
    public static void main(String[] args) {

        @SuppressWarnings("unused")
        IFCEnforcer iFC = new IFCEnforcer();
    }
}

