package org.ifcparaclete;

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
     * @param actorClassName
     * @param targetClassName
     * @param ifcOp
     * @return
     */
    public boolean ifcCheck(String actorClassName, String targetClassName, String ifcOp) throws IFCOperativeException {

        boolean allowed = false;
        boolean inActorArray = false;
        boolean inTargetArray = false;
        String  ifcActorKey;
        String  ifcTargetKey;

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
                try {
                    allowed = ifcOpAllowed(ifcOp);
                } catch (IFCOperativeException IFCOE) {
                    throw IFCOE;
                }
            }

        }

        if (!allowed) {
            throw new IFCOperativeException("Either one or both of these classes:\n" + targetClassName + "\n" +
                                            actorClassName + "\n is not an authorized class for this application, " +
                                            mainApplication + "\n");
        }
        return allowed;

    }

    private boolean ifcOpAllowed(String ifcOp) throws IFCOperativeException {
        boolean allowed = false;
        System.out.println(ifcActor.getType());
        System.out.println(ifcActor.getSecurityLevelInt());
        System.out.println(ifcTarget.getSecurityLevelInt());
        System.out.println(ifcActor.getActiveOPS().get(IFC_OPS.get(ifcOp)));
        System.out.println(ifcTarget.getPassiveOPS().get(IFC_OPS.get(ifcOp)));
        if (ifcActor.getType().equals(IFC_TYPE_TRANSITIVE)) {
            if (ifcActor.getSecurityLevelInt() >= ifcTarget.getSecurityLevelInt()) {
                allowed =
                    ifcActor.getActiveOPS().get(IFC_OPS.get(ifcOp)) &&
                    ifcTarget.getPassiveOPS().get(IFC_OPS.get(ifcOp));
            }

        }
        if (!allowed) {
            throw new IFCOperativeException("Illegal operation, " + IFC_OPS_NAMES.get(ifcOp) + ", attemted by: " +
                                            ifcActor.getName() + "\nOn " + ifcTarget.getName());
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
