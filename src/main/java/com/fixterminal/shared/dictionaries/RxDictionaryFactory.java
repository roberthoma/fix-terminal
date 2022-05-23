package com.fixterminal.shared.dictionaries;

import com.fixterminal.shared.dictionaries.brokers.RxBroker;
import com.fixterminal.shared.dictionaries.brokers.RxDicBrokers;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class RxDictionaryFactory {
    static int  n_pop = 0; // TODO do usuniecia


public static RxDicBrokers initDicBrokers() throws ParserConfigurationException, IOException, SAXException {

    RxDicBrokers brokers = new RxDicBrokers();

    DocumentBuilderFactory dBfactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = dBfactory.newDocumentBuilder();
    // Fetch XML File
    Document document = builder.parse(new File("config/brokers.xml"));
    document.getDocumentElement().normalize();
    //Get root node
    Element root = document.getDocumentElement();
    System.out.println(root.getNodeName());
    //Get all broker
    NodeList nList = document.getElementsByTagName("broker");

    for (int i = 0; i < nList.getLength(); i++) {
        Node node = nList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;

            brokers.add(new RxBroker(Double.parseDouble( element.getAttribute("id")),
                    element.getElementsByTagName("symbol").item(0).getTextContent(),
                    element.getElementsByTagName("name").item(0).getTextContent(),
                    element.getElementsByTagName("description").item(0).getTextContent()));


        }
    }
   return brokers;
  }

  public static RxDicInstruments initDicInstruments() throws ParserConfigurationException, IOException, SAXException {
        n_pop++;
      System.out.println("POBRANIE n="+n_pop);
        RxDicInstruments instruments = new RxDicInstruments();

        DocumentBuilderFactory dBfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dBfactory.newDocumentBuilder();
        // Fetch XML File
        Document document = builder.parse(new File("config/instruments.xml"));
        document.getDocumentElement().normalize();
        //Get root node
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());
        //Get all broker
        NodeList nList = document.getElementsByTagName("instrument");

      for (int i = 0; i < nList.getLength(); i++) {
          Node node = nList.item(i);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
              Element element = (Element) node;

              RxInstrument instrument = new RxInstrument();
              instrument.setSymbol(element.getElementsByTagName("symbol").item(0).getTextContent());
              instrument.setFixSymbol(element.getElementsByTagName("fixsymbol").item(0).getTextContent());
              instrument.setPipPosition(Integer.parseInt(element.getElementsByTagName("pip_position").item(0).getTextContent()));
              instrument.setConfigFileName(element.getElementsByTagName("config_file").item(0).getTextContent());
              instruments.add(instrument);
          }
      }


        return  instruments;
    }



}



