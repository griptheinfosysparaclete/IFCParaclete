package org.ifcparaclete;

import java.util.HashMap;


public class IFCEnforcer implements IFCStatics {

    
    
    @SuppressWarnings("oracle.jdeveloper.java.unrestricted-field-access")
    public static IFCEnforcer thisIFC = new IFCEnforcer();
    private       HashMap[] runnableObjectsArray = null;
    private       IFCRunnableObject ifcRO = null;
    private       IFCRunnableObject mainApplication = null;
    
    public IFCEnforcer() {
        super();
        runnableObjectsArray = IFCPolicy.loadIFCPolicy(IFCStatics.IFC_DEFAULT_POLICY_FILE);
        
    }

    /**
     * @param argsIn
     */
    public IFCEnforcer(String[] argsIn) {
        super();

        runnableObjectsArray = IFCPolicy.loadIFCPolicy(argsIn[0]);
    }


    /**
     * @param className
     * @param ifcOp
     * @return
     */
    public boolean ifcCheckMayOp(String className, String ifcOp) {
  
      boolean mayOp = false;
     
      if (runnableObjectsArray[0].containsKey(className)) {
          ifcRO = (IFCRunnableObject) runnableObjectsArray[1].get(className);
          if (((IFCRunnableObject) (runnableObjectsArray[1].get(className))).getActiveIFOPS().containsValue(ifcOp)) {
              mayOp = true;
          }
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
        if (runnableObjectsArray[0].containsKey(className)) {

            if (((IFCRunnableObject) (runnableObjectsArray[1].get(className))).getActiveIFOPS().containsValue(ifcOp)) {
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
        IFCEnforcer iFC = new IFCEnforcer(args);
    }
}
