package org.ifcparaclete;

import java.util.BitSet;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class IFCActorObject implements IFCStatics {

    private static BitSet ifcActiveOPS;
    private static BitSet ifcPassiveOPS;
    private String ifcName;
    private String ifcID;
    private String ifcSecurityLevel;
    private String ifcCategory;
    private String ifcType;
    private int    ifcSecurityLevelInt;
    private int    ifcCategoryInt;
    private int    ifcTypeInt;


    private IFCActorObject() {
        super();
    }

    public IFCActorObject(Element ifcActorObjectElement) {
        super();
        System.out.println(this.toString());
        init(ifcActorObjectElement);
    }

    private void init(Element ifcActorObjectElement) {

        ifcName = ifcActorObjectElement.getElementsByTagName(IFC_NAME)
                                          .item(0)
                                          .getTextContent();
        ifcID = ifcActorObjectElement.getElementsByTagName(IFC_ID)
                                        .item(0)
                                        .getTextContent();
        ifcSecurityLevel = ifcActorObjectElement.getElementsByTagName(IFC_SECURITY_LEVEL)
                                                   .item(0)
                                                   .getTextContent();
        ifcSecurityLevelInt = IFC_SECURITY_LEVELS.get(ifcSecurityLevel);
        ifcCategory = ifcActorObjectElement.getElementsByTagName(IFC_CATEGORY)
                                              .item(0)
                                              .getTextContent();
        ifcCategoryInt = IFC_CATEGORIES.get(ifcCategory);
        
        ifcType = ifcActorObjectElement.getElementsByTagName(IFC_TYPE)
                                          .item(0)
                                          .getTextContent();
        ifcTypeInt = IFC_TYPES.get(ifcType);

        ifcActiveOPS = initIFCOps(ifcActorObjectElement.getElementsByTagName(IFC_ACTIVE_IFOPS));
        ifcPassiveOPS = initIFCOps(ifcActorObjectElement.getElementsByTagName(IFC_PASSIVE_IFOPS));
        
        System.out.println(this.toString());
    }

    private BitSet initIFCOps(NodeList ifcOpsList) {

        BitSet ifcBitSet;
        Node nNode;
        NodeList nList;
        NodeList opsList;
        Element nElement;
        String ifcOp;
        nList = ifcOpsList;
        ifcBitSet = new BitSet(IFC_OPS.size());
        ifcBitSet.clear();
        for (int temp = 0; temp < nList.getLength(); temp++) {

            nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                nElement = (Element) nNode;
                opsList = nElement.getElementsByTagName(IFC_OP);
                if (opsList != null) {
                    if (opsList.item(0) != null) {
                        ifcOp = opsList.item(0).getTextContent();
                        ifcBitSet.set(IFC_OPS.get(ifcOp));
                    }
                }
            }
        }
        return ifcBitSet;
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

    protected int getCategoryInt() {

        return ifcCategoryInt;

    }

    protected String getType() {

        return ifcType;

    }

    protected int getTypeInt() {

        return ifcTypeInt;

    }

    protected BitSet getActiveOPS() {

        return ifcActiveOPS;

    }

    protected BitSet getPassiveOPS() {

        return ifcPassiveOPS;

    }

    public String toString() {
        String ifcActorObjectString;
        ifcActorObjectString =
            "IFCName == " + ifcName + "\n" + "ifcID == " + ifcID + "\n" + "ifcSecurityLevel == " + ifcSecurityLevel +
            "\n" + "ifcCategory == " + ifcCategory + "\n" + "ifcType == " + ifcType + "\n" + ifcActiveOPS.toString() +
            ifcPassiveOPS.toString();

        return ifcActorObjectString;

    }

    public static void main(String[] args) {
        IFCActorObject ifcActorObject = new IFCActorObject();
    }
}
