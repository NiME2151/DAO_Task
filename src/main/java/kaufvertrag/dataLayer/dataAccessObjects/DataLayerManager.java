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
        Scanner scanner = new Scanner(System.in);
        String persistanceType = readPersistenceType(scanner);
        return switch (persistanceType) {
            case "xml" -> new DataLayerXml();
            case "sqlite" -> new DataLayerSqlite();
            default -> throw new DaoException("persistence type not valid");
        };
    }

    private String readPersistenceType(Scanner scanner) {
        System.out.println("Enter \"xml\" or \"sqlite\" as Persistance Type");
        return scanner.nextLine();
    }
}
