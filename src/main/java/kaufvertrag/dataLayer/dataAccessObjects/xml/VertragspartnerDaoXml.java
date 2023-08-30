package kaufvertrag.dataLayer.dataAccessObjects.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VertragspartnerDaoXml implements IDao<Vertragspartner, String> {

    @Override
    public Vertragspartner create() {
        return new Vertragspartner("", "");
    }

    @Override
    public void create(Vertragspartner objectToInsert) {
        File outputXmlFile = new File("src/main/resources/" + objectToInsert.getAusweisNr() + ".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vertragspartner.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(objectToInsert, outputXmlFile);
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Vertragspartner read(String id) {
        File inputXmlFile = new File("src/main/resources/" + id + ".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vertragspartner.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Vertragspartner) unmarshaller.unmarshal(inputXmlFile);
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Vertragspartner> readAll() {
        File[] xmlFiles = new File("src/main/resources/").listFiles();
        List<Vertragspartner> vertragspartners = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vertragspartner.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            assert xmlFiles != null;
            for (File file : xmlFiles) {
                vertragspartners.add((Vertragspartner) unmarshaller.unmarshal(file));
            }

        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return vertragspartners;
    }

    @Override
    public void update(Vertragspartner objectToUpdate) {
        File xmltobeUpdated = new File("src/main/resources/" + objectToUpdate.getAusweisNr() + ".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vertragspartner.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(objectToUpdate, xmltobeUpdated);
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        File xmlToBeDeleted = new File("src/main/resources/" + id + ".xml");
        xmlToBeDeleted.delete();
    }
}
