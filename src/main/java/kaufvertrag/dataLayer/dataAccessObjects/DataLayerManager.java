package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.dataAccessObjects.xml.DataLayerXml;
import kaufvertrag.exceptions.DaoException;

import java.util.Scanner;

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
        switch (readPersistenceType()) {
            case "xml" -> {
                return new DataLayerXml();
            }
            case "sqlite" -> {
            }
//                return new DataLayerSqlite();
        }
        return null;
    }

    private String readPersistenceType() {
        System.out.println("Enter \"xml\" or \"sqlite\" as Persistance Type");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
