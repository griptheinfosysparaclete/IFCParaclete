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

    private static HashMap ifcRunnableObjects = new HashMap();
    private static HashMap ifcRunnableObjectsKeys = new HashMap();
    private static HashMap[] ifcRunnableObjectsArray = new HashMap[2];
    private static IFCRunnableObject ifcRunnableObject = null;


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

            nList = doc.getElementsByTagName(IFC_RUNNABLE_OBJECT);
            for (int temp = 0; temp < nList.getLength(); temp++) {

                nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    nElement = (Element) nNode;

                    ifcRunnableObject = new IFCRunnableObject(nElement);
                    ifcRunnableObjects.put(ifcRunnableObject.getID(), ifcRunnableObject);
                    ifcRunnableObjectsKeys.put(ifcRunnableObject.getName(),ifcRunnableObject.getID());
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            System.exit(1);

        }
        ifcRunnableObjectsArray[0] = ifcRunnableObjectsKeys;
  
        ifcRunnableObjectsArray[1] = ifcRunnableObjects;
        
        return ifcRunnableObjectsArray;
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
