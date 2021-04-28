package org.ifcparaclete;

import com.sun.max.vm.Log;

import java.util.HashMap;

import org.ifcparaclete.exceptions.IFCOperativeException;

public class IFCEnforcer implements IFCStatics {

    @SuppressWarnings("oracle.jdeveloper.java.unrestricted-field-access")

    private HashMap[] actorObjectsArray = new HashMap[2];
    private HashMap[] targetObjectsArray = new HashMap[2];
    private HashMap[] ifcObjectsArray;
    private IFCObject ifcActor;
    private IFCObject ifcTarget;
    private String mainApplication;
    int numOfPackages = IFCStatics.IFC_VM_PACKAGES.size();

    private boolean buildingImage;

    private IFCEnforcer() {
        super();
        ifcObjectsArray = IFCPolicy.loadIFCPolicy(IFCStatics.IFC_DEFAULT_POLICY_FILE);
        actorObjectsArray[0] = ifcObjectsArray[0];
        actorObjectsArray[1] = ifcObjectsArray[1];
        targetObjectsArray[0] = ifcObjectsArray[2];
        targetObjectsArray[1] = ifcObjectsArray[3];
        buildingImage = false;
    }

    public IFCEnforcer(String mainApplicationArg) {
        super();
        ifcObjectsArray = IFCPolicy.loadIFCPolicy(IFCStatics.IFC_DEFAULT_POLICY_FILE);
        actorObjectsArray[0] = ifcObjectsArray[0];
        actorObjectsArray[1] = ifcObjectsArray[1];
        targetObjectsArray[0] = ifcObjectsArray[2];
        targetObjectsArray[1] = ifcObjectsArray[3];
        mainApplication = mainApplicationArg;
        buildingImage = false;
    }

    /**
     * @param ifcPolicyFileArg
     * @param isBuildingImageArg
     *
     */
    public IFCEnforcer(String ifcPolicyFileArg, boolean isBuildingImageArg) {
        super();

        ifcObjectsArray = IFCPolicy.loadIFCPolicy(ifcPolicyFileArg);

        actorObjectsArray[0] = ifcObjectsArray[0];
        actorObjectsArray[1] = ifcObjectsArray[1];
        targetObjectsArray[0] = ifcObjectsArray[2];
        targetObjectsArray[1] = ifcObjectsArray[3];
        buildingImage = isBuildingImageArg;
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

        boolean allowed = false;
        int i;
        int j;
        
        int stackTraceLength;
        String actorClassName = null;
        String actorClassPackageName = null;

        if (buildingImage) {
            allowed = true;
        } else {
            stackTraceLength = actorClassNames.length - 1;
            for (i = stackTraceLength;i >= 0; i--) {
                actorClassPackageName = actorClassNames[i].getClass().getPackage().getName();
               
                for (j = 0; j <=  numOfPackages;j++) {
                   Log.println(actorClassPackageName + ": " + IFCStatics.IFC_VM_PACKAGES.get(j));
                    if (actorClassPackageName.startsWith(IFCStatics.IFC_VM_PACKAGES.get(j))) {
                        return true;
                    }
                }
                    
                actorClassName = actorClassNames[i].getClassName();
                allowed = ifcCheck(actorClassName, targetClassName, ifcOp);
                if (!allowed) {
                    throw new IFCOperativeException("Either one or both of these classes:\n" + targetClassName + "\n" +
                                                    actorClassName +
                                                    "\n is not an authorized class for this application, " +
                                                    mainApplication + "\n");
                }
            }
        }
        
        return allowed;

    }

    /**
     * @param actorClassName
     * @param targetClassName
     * @param ifcOp
     * @return
     */
    public boolean ifcCheck(String actorClassName, String targetClassName, String ifcOp)  {

        boolean allowed = false;
        boolean inActorArray = false;
        boolean inTargetArray = false;
        String ifcActorKey;
        String ifcTargetKey;

        if (buildingImage) {
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

    private boolean ifcOpAllowed(String ifcOp)  {
        
        boolean allowed = false;
        String ifcActorName = ifcActor.getName();
        String ifcTargetName = ifcTarget.getName();
        String ifcActorType = ifcActor.getType();
        int        ifcActorSecurityLevelInt = ifcActor.getSecurityLevelInt();
        int        ifcTargetSecurityLevelInt = ifcTarget.getSecurityLevelInt();
        System.out.println(ifcActorName + ": " + ifcActorType);
        System.out.println(ifcActorName + ": " + ifcActorSecurityLevelInt);
        System.out.println(ifcTargetName + ": " + ifcTargetSecurityLevelInt);
        System.out.println("Hi!");
        boolean ifcActorActiveOp = ifcActor.getActiveOPS().get(IFC_OPS.get(ifcOp));
        boolean ifcTargetPassiveOp = ifcTarget.getPassiveOPS().get(IFC_OPS.get(ifcOp));

        System.out.println(ifcActorName + ": " + ifcActorActiveOp);
        System.out.println(ifcTargetName + ": " + ifcTargetPassiveOp);
        if (ifcActorType.equals(IFCStatics.IFC_TYPE_TRANSITIVE)) {
            if (ifcActorSecurityLevelInt >= ifcTargetSecurityLevelInt) {
                allowed =  ifcActorActiveOp &&  ifcTargetPassiveOp;
            }

        }

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
