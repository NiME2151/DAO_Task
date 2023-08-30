package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.dataAccessObjects.sqlite.DataLayerSqlite;
import kaufvertrag.dataLayer.dataAccessObjects.xml.DataLayerXml;
import kaufvertrag.exceptions.DaoException;

import java.util.Scanner;

public class DataLayerManager {

    private static DataLayerManager dataLayerManager;

    private DataLayerManager() {
    }

    public static DataLayerManager getInstance() {
        if (dataLayerManager == null) {
            dataLayerManager = new DataLayerManager();
        }
        return dataLayerManager;
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
