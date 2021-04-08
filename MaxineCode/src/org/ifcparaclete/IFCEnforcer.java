package org.ifcparaclete;

import java.util.HashMap;


public class IFCEnforcer implements IFCStatics {


    @SuppressWarnings("oracle.jdeveloper.java.unrestricted-field-access")
    public static IFCEnforcer thisIFC              = new IFCEnforcer();
    private       HashMap[]   runnableObjectsArray = null;
    private IFCOperativeObject ifcOperativeRO       = null;
    private IFCOperativeObject ifcTargetRO          = null;
    private IFCOperativeObject mainApplication      = null;
    private boolean           buildingImage;

    public IFCEnforcer() {
        super();
        runnableObjectsArray = IFCPolicy.loadIFCPolicy(IFCStatics.IFC_DEFAULT_POLICY_FILE);
        buildingImage = false;
    }

    /**
     * @param ifcPolicyFileArg
     * @param isBuildingImageArg
     *
     */
    public IFCEnforcer(String ifcPolicyFileArg, boolean isBuildingImageArg) {
        super();

        runnableObjectsArray = IFCPolicy.loadIFCPolicy(ifcPolicyFileArg);
        buildingImage = isBuildingImageArg;
    }


    /**
     * @param operativeClassName
     * @param targetClassName
     * @param ifcOp
     * @return
     */
    public boolean ifcCheckMayOp(String operativeClassName, String targetClassName, String ifcOp) {

        boolean mayOp = false;

        if (!buildingImage) {
            if (runnableObjectsArray[0].containsKey(operativeClassName)) {
                if (runnableObjectsArray[0].containsKey(targetClassName)) {
                    ifcOperativeRO = (IFCOperativeObject) runnableObjectsArray[1].get(operativeClassName);
                    ifcTargetRO = (IFCOperativeObject) runnableObjectsArray[1].get(operativeClassName);
                    
                if (((IFCOperativeObject) (runnableObjectsArray[1].get(className))).getActiveIFOPS()
                    .containsValue(ifcOp)) {
                    mayOp = true;
                }
            }
        } else {
            mayOp = true;
        }

        return mayOp;
    }

    /**
     * @param className
     * @param ifcOp
     * @return
     */
    public boolean ifcCheckMayBeOped(String className, String ifcOp) {
        boolean mayBeOped = false;

        if (!buildingImage) {
            if (runnableObjectsArray[0].containsKey(className)) {

                if (((IFCOperativeObject) (runnableObjectsArray[1].get(className))).getActiveIFOPS()
                    .containsValue(ifcOp)) {
                    mayBeOped = true;
                }
            } else {
                mayBeOped = true;
            }
        }
        return mayBeOped;

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        @SuppressWarnings("unused")
        IFCEnforcer iFC = new IFCEnforcer();
    }
}
