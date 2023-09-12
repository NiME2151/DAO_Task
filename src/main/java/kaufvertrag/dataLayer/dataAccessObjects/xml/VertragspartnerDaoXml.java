package kaufvertrag.dataLayer.dataAccessObjects.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class VertragspartnerDaoXml implements IDao<Vertragspartner, String> {

    private static final String VERTRAGSPARTNER_PATH = "src/main/resources/vertragspartner/";

    @Override
    public Vertragspartner create() {
        return new Vertragspartner("", "");
    }

    @Override
    public void create(Vertragspartner objectToInsert) throws DaoException {
        File outputXmlFile = new File(VERTRAGSPARTNER_PATH + objectToInsert.getAusweisNr() + ".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vertragspartner.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(objectToInsert, outputXmlFile);
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Vertragspartner read(String id) throws DaoException {
        File inputXmlFile = new File(VERTRAGSPARTNER_PATH + id + ".xml");
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
    public List<Vertragspartner> readAll() throws DaoException {
        File[] xmlFiles = new File(VERTRAGSPARTNER_PATH).listFiles();
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
    public void update(Vertragspartner objectToUpdate) throws DaoException {
        File xmltobeUpdated = new File(VERTRAGSPARTNER_PATH + objectToUpdate.getAusweisNr() + ".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vertragspartner.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(objectToUpdate, xmltobeUpdated);
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        File xmlToBeDeleted = new File(VERTRAGSPARTNER_PATH + id + ".xml");
        xmlToBeDeleted.delete();
    }
}
