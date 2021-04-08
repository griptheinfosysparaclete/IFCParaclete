package org.ifcparaclete;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IFCTargetObject implements IFCStatics {

    private static HashMap ifcActiveIFOPS = new HashMap();
    private static HashMap ifcPassiveIFOPS = new HashMap();
    private String ifcName = null;
    private String ifcID = null;
    private String ifcSecurityLevel = null;
    private String ifcCategory = null;
    private String ifcType = null;

    private IFCTargetObject() {
        super();
    }

    public IFCTargetObject(Element ifcOperativeObjectElement) {
        super();
        System.out.println(this.toString());
        init(ifcOperativeObjectElement);
    }

    private void init(Element ifcOperativeObjectElement) {

        ifcName = ifcOperativeObjectElement.getElementsByTagName(IFC_NAME)
                                          .item(0)
                                          .getTextContent();
        ifcID = ifcOperativeObjectElement.getElementsByTagName(IFC_ID)
                                        .item(0)
                                        .getTextContent();
        ifcSecurityLevel = ifcOperativeObjectElement.getElementsByTagName(IFC_SECURITY_LEVEL)
                                                   .item(0)
                                                   .getTextContent();
        ifcCategory = ifcOperativeObjectElement.getElementsByTagName(IFC_CATEGORY)
                                              .item(0)
                                              .getTextContent();
        ifcType = ifcOperativeObjectElement.getElementsByTagName(IFC_TYPE)
                                          .item(0)
                                          .getTextContent();
        ifcActiveIFOPS = initIFCOps(ifcOperativeObjectElement.getElementsByTagName(IFC_ACTIVE_IFOPS));
        ifcPassiveIFOPS = initIFCOps(ifcOperativeObjectElement.getElementsByTagName(IFC_PASSIVE_IFOPS));
        System.out.println(this.toString());
    }

    private HashMap initIFCOps(NodeList ifcOpsList) {

        HashMap<String, String> ifcOpsMap;
        Node nNode;
        NodeList nList;
        NodeList opsList;
        Element nElement;
        String ifcOp;
        nList = ifcOpsList;
        ifcOpsMap = new HashMap<String, String>();
        for (int temp = 0; temp < nList.getLength(); temp++) {

            nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                nElement = (Element) nNode;
                opsList = nElement.getElementsByTagName(IFC_OP);
                if (opsList != null) {
                    if (opsList.item(0) != null) {
                        ifcOp = opsList.item(0).getTextContent();
                        ifcOpsMap.put(ifcOp, ifcOp);
                    }
                }
            }
        }
        return ifcOpsMap;
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
        String ifcOperativeObjectString;
        ifcOperativeObjectString =
            "IFCName == " + ifcName + "\n" + "ifcID == " + ifcID + "\n" + "ifcSecurityLevel == " + ifcSecurityLevel +
            "\n" + "ifcCategory == " + ifcCategory + "\n" + "ifcType == " + ifcType + "\n" + ifcActiveIFOPS.toString() +
            ifcPassiveIFOPS.toString();

        return ifcOperativeObjectString;

    }

    public static void main(String[] args) {
        IFCTargetObject iFCTargetObject = new IFCTargetObject();
    }
}
