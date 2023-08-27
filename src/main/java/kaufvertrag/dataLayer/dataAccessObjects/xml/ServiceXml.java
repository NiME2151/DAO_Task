package kaufvertrag.dataLayer.dataAccessObjects.xml;

import kaufvertrag.businessObjects.IWare;
import kaufvertrag.exceptions.DaoException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ServiceXml{
    
    private static void createXMLDoc(IWare ware) throws DaoException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        
        // Struktur erstellen
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("Ware");
        document.appendChild(root);
        
        
        root.appendChild(document.createElement(ware.getBezeichnung()));
        root.appendChild(document.createElement(ware.getBeschreibung()));
        root.appendChild(document.createElement(String.valueOf(ware.getPreis())));
        root.appendChild(document.createElement(ware.getBesonderheiten().toString()));
        root.appendChild(document.createElement(ware.getMaengel().toString()));
        
        // XML erstellen
        String datei = "src/main/java/xmlSpeicherPlatz/ware.xml";
        try (FileOutputStream fileOutputStream = new FileOutputStream(datei)){
            writeXml(document, fileOutputStream);
        } catch (IOException | TransformerException e) {
        throw new DaoException("XML einlesen fehlgeschlagen");
        }
    }
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }
    public static void XMLEinlesenDOMParser() throws ParserConfigurationException, IOException, SAXException {

        String datei = "src/main/java/xmlSpeicherPlatz/ware.xml";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.parse(new File(datei));
        document.getDocumentElement().normalize();
    }
}
    