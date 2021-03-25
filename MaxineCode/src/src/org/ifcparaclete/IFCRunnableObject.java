package org.ifcparaclete;

import java.util.HashMap;

import org.w3c.dom.Element;

class IFCRunnableObject implements IFCStatics {

    private static HashMap ifcActiveIFOPS = new HashMap();
    private static HashMap ifcPassiveIFOPS = new HashMap();
    private String ifcName = null;
    private String ifcID = null;
    private String ifcSecurityLevel = null;
    private String ifcCategory = null;
    private String ifcType = null;

    private IFCRunnableObject() {
        super();
    }

    public IFCRunnableObject(Element ifcRunnableObjectElement) {
        super();
        System.out.println(this.toString());
        init(ifcRunnableObjectElement);
    }

    private void init(Element ifcRunnableObjectElement) {

        ifcName = ifcRunnableObjectElement.getElementsByTagName(IFC_NAME)
                                          .item(0)
                                          .getTextContent();
        ifcID = ifcRunnableObjectElement.getElementsByTagName(IFC_ID)
                                        .item(0)
                                        .getTextContent();
        ifcSecurityLevel = ifcRunnableObjectElement.getElementsByTagName(IFC_SECURITY_LEVEL)
                                                   .item(0)
                                                   .getTextContent();
        ifcCategory = ifcRunnableObjectElement.getElementsByTagName(IFC_CATEGORY)
                                              .item(0)
                                              .getTextContent();
        ifcType = ifcRunnableObjectElement.getElementsByTagName(IFC_TYPE)
                                          .item(0)
                                          .getTextContent();
        System.out.println(this.toString());
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

    protected String getCategory() {

        return ifcCategory;

    }

    protected String getType() {

        return ifcType;

    }

    protected HashMap getActiveIFOPS() {

        return ifcActiveIFOPS;

    }

    protected HashMap getPassiveIFOPS() {

        return ifcPassiveIFOPS;

    }

    public String toString() {
        String ifcRunnableObjectString;
        ifcRunnableObjectString =
            "IFCName == " + ifcName + "\n" + "ifcID == " + ifcID + "\n" + "ifcSecurityLevel == " + ifcSecurityLevel +
            "\n" + "ifcCategory == " + ifcCategory + "\n" + "ifcType == " + ifcType + "\n" + ifcActiveIFOPS.toString() +
            ifcPassiveIFOPS.toString();

        return ifcRunnableObjectString;

    }

    public static void main(String[] args) {
        IFCRunnableObject ifcRunnableObject = new IFCRunnableObject();
    }
}
