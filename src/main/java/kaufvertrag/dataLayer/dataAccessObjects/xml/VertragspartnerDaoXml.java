package kaufvertrag.dataLayer.dataAccessObjects.xml;

import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public  class VertragspartnerDaoXml implements IDao<IVertragspartner, String> {

    @Override
    public IVertragspartner create() {
        return new Vertragspartner("", "");
    }

    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {
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
    public IVertragspartner read(String id) throws DaoException {
        File inputXmlFile = new File("src/main/resources/" + id + ".xml");
        Vertragspartner vertragspartner;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vertragspartner.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            vertragspartner = (Vertragspartner) unmarshaller.unmarshal(inputXmlFile);

        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return vertragspartner;
    }

    @Override
    public List<IVertragspartner> readAll() throws DaoException {
        File[] xmlFiles = new File("src/main/resources/").listFiles();
        List<IVertragspartner> vertragspartners = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vertragspartner.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            assert xmlFiles != null;
            for (File file : xmlFiles) {
                vertragspartners.add((IVertragspartner) unmarshaller.unmarshal(file));
            }

        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return vertragspartners;
    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {
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
