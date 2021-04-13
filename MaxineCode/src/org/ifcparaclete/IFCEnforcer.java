package org.ifcparaclete;

import java.util.HashMap;


public class IFCEnforcer implements IFCStatics {

    @SuppressWarnings("oracle.jdeveloper.java.unrestricted-field-access")

    private HashMap[] actorObjectsArray;
    private HashMap[] targetObjectsArray;
    private HashMap[] ifcObjectsArray;
    private IFCActorObject ifcActor;
    private IFCTargetObject ifcTarget;

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
     * @param actorClassName
     * @param targetClassName
     * @param ifcOp
     * @return
     */
    public boolean ifcCheck(String actorClassName, String targetClassName, String ifcOp) {

        boolean allowed = false;
        boolean inActorArray = false;
        boolean inTargetArray = false;

        if (buildingImage) {
            allowed = true;
        } else {
            inActorArray = !actorObjectsArray[0].containsKey(actorClassName);
            inTargetArray = !targetObjectsArray[0].containsKey(targetClassName);
            if ((inActorArray | inTargetArray)) {
                if ((inActorArray && inTargetArray)) {
                    ifcActor = (IFCActorObject) actorObjectsArray[1].get(actorClassName);
                    ifcTarget = (IFCTargetObject) targetObjectsArray[1].get(targetClassName);
                    if (!ifcOpAllowed(ifcOp)) {
                        exitJVM(actorClassName);
                    } else {
                        allowed = true;
                    }
                } else {
                    exitJVM(actorClassName);
                }
            } else {
                exitJVM(actorClassName);
            }
        }

        return allowed;

    }

    private void exitJVM(String actorClassName) {

        System.out.println("Illegal Operation Attemted by: " + actorClassName);
        System.err.println("Illegal Operation Attemted by: " + actorClassName);
        System.console().printf("%s", "Illegal Operation Attemted by: " + actorClassName);
        System.exit(99);

    }

    private boolean ifcOpAllowed(String ifcOp) {
        boolean allowed = false;
        if (ifcActor.getType() != IFC_TYPE_INTRANSITIVE) {
            if (ifcActor.getSecurityLevelInt() >= ifcTarget.getSecurityLevelInt()) {
                allowed =
                    ifcActor.getActiveOPS().get(IFC_OPS.get(ifcOp)) &&
                    ifcTarget.getPassiveOPS().get(IFC_OPS.get(ifcOp));
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
