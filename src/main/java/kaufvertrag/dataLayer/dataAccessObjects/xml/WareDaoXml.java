package kaufvertrag.dataLayer.dataAccessObjects.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import kaufvertrag.businessObjects.IWare;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public  class WareDaoXml implements IDao<IWare, Long> {

    private static final String WAREN_PATH = "src/main/resources/waren/";

    @Override
    public Ware create() {
        return new Ware("", -1.0);
    }

    @Override
    public void create(IWare objectToInsert) throws DaoException {
        File outputXmlFile = new File(WAREN_PATH + objectToInsert.getId() + ".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Ware.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(objectToInsert, outputXmlFile);
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public IWare read(Long id) throws DaoException {
        File inputXmlFile = new File(WAREN_PATH + id + ".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Ware.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Ware) unmarshaller.unmarshal(inputXmlFile);
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<IWare> readAll() throws DaoException {
        File[] xmlFiles = new File(WAREN_PATH).listFiles();
        List<IWare> waren = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Ware.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            assert xmlFiles != null;
            for (File file : xmlFiles) {
                waren.add((Ware) unmarshaller.unmarshal(file));
            }
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return waren;
    }

    @Override
    public void update(IWare objectToUpdate) throws DaoException {
        File xmltobeUpdated = new File(WAREN_PATH + objectToUpdate.getId() + ".xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Ware.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(objectToUpdate, xmltobeUpdated);
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        File xmlToBeDeleted = new File(WAREN_PATH + id + ".xml");
        xmlToBeDeleted.delete();
    }
}
