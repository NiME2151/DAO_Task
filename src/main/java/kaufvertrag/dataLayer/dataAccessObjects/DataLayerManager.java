package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.dataAccessObjects.sqlite.DataLayerSqlite;
import kaufvertrag.dataLayer.dataAccessObjects.xml.DataLayerXml;
import kaufvertrag.exceptions.DaoException;

import java.util.Scanner;

public class DataLayerManager {

    private static DataLayerManager instance;

    private DataLayerManager() {
    }

    public static DataLayerManager getInstance() {
        if (instance == null) {
            instance = new DataLayerManager();
        }
        return instance;
    }

    public IDataLayer getDataLayer() throws DaoException {
        return switch (readPersistenceType()) {
            case "xml" -> new DataLayerXml();
            case "sqlite" -> new DataLayerSqlite();
            default -> throw new DaoException("persistence type not valid");
        };
    }

    private String readPersistenceType() {
        System.out.println("Enter \"xml\" or \"sqlite\" as Persistance Type");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
