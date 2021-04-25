package org.ifcparaclete;


import java.util.BitSet;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

class IFCObject extends IFCOperators implements IFCStatics {

    private BitSet   ifcActiveOPS;
    private BitSet   ifcPassiveOPS;
    private String   ifcName;
    private String   ifcID;
    private String   ifcSecurityLevel;
    private int      ifcSecurityLevelInt;
    private String   ifcCategory;
    private String   ifcType;

    private NodeList ifcIFOPS;


    private IFCObject() {
        super();
    }

    public IFCObject(Element ifcObjectElement) {
        super();
 
        init(ifcObjectElement);
        System.out.println(this.toString());
    }

    private void init(Element ifcObjectElement) {

        ifcName = ifcObjectElement.getElementsByTagName(IFC_NAME)
                                          .item(0)
                                          .getTextContent();
        
        ifcID = ifcObjectElement.getElementsByTagName(IFC_ID)
                                        .item(0)
                                        .getTextContent();
        
        ifcSecurityLevel = ifcObjectElement.getElementsByTagName(IFC_SECURITY_LEVEL)
                                                   .item(0)
                                                   .getTextContent();
        System.out.println(ifcSecurityLevel);
        ifcSecurityLevelInt = IFC_SECURITY_LEVELS.get(ifcSecurityLevel);
        
        ifcCategory = ifcObjectElement.getElementsByTagName(IFC_CATEGORY)
                                              .item(0)
                                              .getTextContent();
        
        ifcType = ifcObjectElement.getElementsByTagName(IFC_TYPE)
                                          .item(0)
                                          .getTextContent();
        
        if (ifcType.equals(IFC_TYPE_TRANSITIVE)) {
            ifcIFOPS = ifcObjectElement.getElementsByTagName(IFC_ACTIVE_IFOPS);
            ifcActiveOPS = getIFCOperators(ifcIFOPS);
        }
        
        ifcIFOPS = ifcObjectElement.getElementsByTagName(IFC_PASSIVE_IFOPS);
        ifcPassiveOPS = getIFCOperators(ifcIFOPS);
    }

    protected String getName() {

        return ifcName;

    }

    protected String getID() {

        return ifcID;

    }

    protected String getSecurityLevel() {

        return ifcSecurityLevel;

    }

    protected int getSecurityLevelInt() {

        return ifcSecurityLevelInt;

    }
    
    protected String getCategory() {

        return ifcCategory;

    }


    protected String getType() {

        return ifcType;

    }

 

    protected BitSet getActiveOPS() {

        return ifcActiveOPS;

    }

    protected BitSet getPassiveOPS() {

        return ifcPassiveOPS;

    }

    public String toString() {
        
        String ifcObjectString;
        String ifcActive = "No Active Ops for this Object";
        String ifcPassive = "No Passive Ops for this Object";
        if (ifcActiveOPS != null) {
            ifcActive = ifcActiveOPS.toString();
        }
        if (ifcPassiveOPS != null) {
            ifcPassive = ifcPassiveOPS.toString();
        } 
        ifcObjectString =
            "IFCName == " + ifcName + "\n" + "ifcID == " + ifcID + "\n" + "ifcSecurityLevel == " + ifcSecurityLevel +
            "\n" + "ifcType == " + ifcType +"\n" + ifcActive + "\n" + ifcPassive;
        
        return ifcObjectString;
    }

    public static void main(String[] args) {
        IFCObject ifcActorObject = new IFCObject();
    }
}
