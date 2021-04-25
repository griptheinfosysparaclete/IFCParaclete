package org.ifcparaclete;


import java.io.File;
import java.io.IOException;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class IFCPolicy implements IFCStatics {

    private static HashMap         ifcActorObjects      = new HashMap();
    private static HashMap         ifcActorObjectsKeys  = new HashMap();
    private static IFCObject       ifcActorObject       = null;
    private static HashMap         ifcTargetObjects     = new HashMap();
    private static HashMap         ifcTargetObjectsKeys = new HashMap();
    private static HashMap[]       ifcObjectsArray      = new HashMap[4];
    private static IFCObject       ifcTargetObject      = null;

    @SuppressWarnings("oracle.jdeveloper.java.unused-field")
    private static String ifcID = null;

    public IFCPolicy() {
    }

    /**
     * @param args
     */
    public IFCPolicy(String[] args) {
    }

    /**
     * @param ifcPolicyXMLFile
     * @return
     */
    @SuppressWarnings("unchecked")
    public static HashMap[] loadIFCPolicy(String ifcPolicyXMLFile) {
        
        File inputFile = new File(ifcPolicyXMLFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Node nNode;
        NodeList nList;
        Element nElement;
        Document doc;
        doc = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();

            doc = dBuilder.parse(inputFile);      

            doc.getDocumentElement().normalize();

            nList = doc.getElementsByTagName(IFC_ACTOR_OBJECT);
            for (int temp = 0; temp < nList.getLength(); temp++) {

                nNode = nList.item(temp);
                System.out.println(nNode.getNodeName());
                System.out.println(nNode.getNodeType());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    nElement = (Element) nNode;

                    ifcActorObject = new IFCObject(nElement);
                    ifcActorObjects.put(ifcActorObject.getID(), ifcActorObject);
                    ifcActorObjectsKeys.put(ifcActorObject.getName(),ifcActorObject.getID());
                }
            }
            nList = doc.getElementsByTagName(IFC_TARGET_OBJECT);
            for (int temp = 0; temp < nList.getLength(); temp++) {

                nNode = nList.item(temp);
                System.out.println(nNode.getNodeName());
                System.out.println(nNode.getNodeType());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    nElement = (Element) nNode;

                    ifcTargetObject = new IFCObject(nElement);
                    ifcTargetObjects.put(ifcTargetObject.getID(), ifcTargetObject);
                    ifcTargetObjectsKeys.put(ifcTargetObject.getName(), ifcTargetObject.getID());
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            System.exit(1);

        }
        ifcObjectsArray[0] = ifcActorObjectsKeys;
  
        ifcObjectsArray[1] = ifcActorObjects;
        
        ifcObjectsArray[2] = ifcTargetObjectsKeys;

        ifcObjectsArray[3] = ifcTargetObjects;
        
        return ifcObjectsArray;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        @SuppressWarnings("oracle.jdeveloper.java.unused-variable")
        IFCPolicy ifcPolicy = new IFCPolicy(args);
        IFCPolicy.loadIFCPolicy(IFCStatics.IFC_DEFAULT_POLICY_FILE);

        System.exit(0);
    }
}
