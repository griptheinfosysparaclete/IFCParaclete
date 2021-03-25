package org.ifcparaclete;

import java.util.HashMap;


public class IFC implements IFCStatics {

    
    
    @SuppressWarnings("oracle.jdeveloper.java.unrestricted-field-access")
    public static IFC thisIFC = new IFC();
    private       HashMap[] runnableObjectsArray = null;
    
    public IFC() {
        super();
        runnableObjectsArray = IFCPolicy.loadIFCPolicy(org.ifcparaclete
                                   .IFCStatics
                                   .IFC_DEFAULT_POLICY_FILE);
    }

    public IFC(String[] argsIn) {
        super();

        runnableObjectsArray = IFCPolicy.loadIFCPolicy(argsIn[0]);
    }
    
    
    public boolean ifcCheckMayOp(String className, String ifcOp) {
  
      boolean mayOp = false;
     
      if (runnableObjectsArray[0].containsKey(className)) {
          
          if (((IFCRunnableObject) (runnableObjectsArray[1].get(className))).getActiveIFOPS().containsValue(ifcOp)) {
              mayOp = true;
          }
      }  
      
      return mayOp;
    }

    public boolean ifcCheckMayBeOped(String className, String ifcOp) {
        boolean mayBeOped = false;
        if (runnableObjectsArray[0].containsKey(className)) {

            if (((IFCRunnableObject) (runnableObjectsArray[1].get(className))).getActiveIFOPS().containsValue(ifcOp)) {
                mayBeOped = true;
            }
        }

        return mayBeOped;
    }

    public static void main(String[] args) {
        
        IFC iFC = new IFC(args);
    }
}
