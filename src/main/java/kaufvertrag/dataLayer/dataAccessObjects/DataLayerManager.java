package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.dataAccessObjects.xml.DataLayerXml;
import kaufvertrag.exceptions.DaoException;

public class DataLayerManager {

    private static DataLayerManager instance;
    public String persistenceType;

    private DataLayerManager() {}

    public static DataLayerManager getInstance() {
        if (instance == null) {
            instance = new DataLayerManager();

        }
        return instance;
    }

    public IDataLayer getDataLayer() {
        if (instance.readPersistenceType().equalsIgnoreCase("xml")) {
            return new DataLayerXml();
        }
        else if (instance.readPersistenceType().equalsIgnoreCase("sqlite")){
//            return new DataLayerSqlite();
        }
        throw new DaoException("persistence type not valid");
    }

    private String readPersistenceType() {
        return instance.persistenceType;
    }
}
