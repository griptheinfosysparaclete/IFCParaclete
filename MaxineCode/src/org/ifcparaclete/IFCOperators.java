package org.ifcparaclete;

import java.util.BitSet;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

abstract class IFCOperators implements IFCStatics {

    /**
     * @param ifcOpsList
     * @return
     */
    protected static BitSet getIFCOperators(NodeList ifcOpsList) {
        BitSet ifcBitSet;
        Node nNode;
        NodeList nList;
        NodeList opsList;
        Element nElement;
        String ifcOp;
        int ifcOpInt;
        int nListLength;
        int opsListLength;
        nList = ifcOpsList;
        nListLength = nList.getLength();

        ifcBitSet = new BitSet(IFC_OPS.size());
        ifcBitSet.clear();
        for (int temp = 0; temp < nListLength; temp++) {

            nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                nElement = (Element) nNode;
                opsList = nElement.getElementsByTagName(IFC_OP);
                
                if (opsList != null) {
                    opsListLength = opsList.getLength();
                    for (int j = 0; j < opsListLength; j++) {
                        if (opsList.item(j) != null) {
                            ifcOp = opsList.item(j).getTextContent();
                            ifcOpInt = IFC_OPS.get(ifcOp);
                            ifcBitSet.set(ifcOpInt);
                        }
                    }
                }
            }
        }

        return ifcBitSet;
    }

}
