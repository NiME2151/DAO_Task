import kaufvertrag.dataLayer.businessObjects.Adresse;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.DataLayerManager;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;
import kaufvertrag.dataLayer.dataAccessObjects.IVertragspartnerDao;
import kaufvertrag.dataLayer.dataAccessObjects.IWareDao;
import kaufvertrag.exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Programm {

    public static void main(String[] args) throws DaoException {

        DataLayerManager dataLayerManager = DataLayerManager.getInstance();
        IDataLayer dataLayer = dataLayerManager.getDataLayer();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Möchten Sie Vertrag: \n\terstellen? -> \"C\"\n\tlesen? -> \"R\"\n\tupdaten? -> \"U\"\n\tlöschen? -> \"D\"\n\tQuit \"Q\":");
            String chosenOption = scanner.nextLine();
            switch (chosenOption) {
                case "c" -> create(scanner, dataLayer);
                case "r" -> read(scanner, dataLayer);
                case "u" -> update(scanner, dataLayer);
                case "d" -> delete(scanner, dataLayer);
                case "q" -> {
                    scanner.close();
                    return;
                }
                default -> System.out.println("Eingabe ungültig\nnochmal versuchen:");
            }
        }
    }

    private static void create(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        //Abfrage was hinzugefügt werden soll -> Vertragspartner oder Ware
        System.out.println("Was möchten Sie erstellen?\n\tVertragspartner -> \"V\"\n\tWare -> \"W\"\n\tQuit -> \"Q\"");

        //Mit scanner auslesen was Antwort ist und entsprechend reagieren
        while (scanner.hasNext()) {
            switch (scanner.next().toLowerCase()) {
                case "w" -> createWare(scanner, dataLayer);
                case "v" -> createVertragspartner(scanner, dataLayer);
                case "q" -> scanner.close();
                default -> System.out.println("invalid input\ntry again");
            }
        }
    }

    private static void createVertragspartner(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IVertragspartnerDao vertragspartnerDao = dataLayer.getVertragspartnerDao();

        System.out.println("Vorname:");
        String vorname = scanner.next();
        System.out.println("Nachname:");
        String nachname = scanner.next();
        System.out.println("AusweisNr:");
        String ausweisNr = scanner.next();
        System.out.println("Strasse:");
        String strasse = scanner.next();
        System.out.println("HausNr:");
        String hausNr = scanner.next();
        System.out.println("Plz:");
        String plz = scanner.next();
        System.out.println("Ort:");
        String ort = scanner.next();

        Adresse adresse = new Adresse(strasse, hausNr, plz, ort);

        Vertragspartner vertragspartner = new Vertragspartner(vorname, nachname, ausweisNr, adresse);

        System.out.println(vertragspartner);
        
        vertragspartnerDao.create(vertragspartner);
    }

    private static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void createWare(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IWareDao wareDao = dataLayer.getWareDao();
        System.out.println("Bezeichnung:");
        String bezeichnung = scanner.next();
        System.out.println("Beschreibung:");
        String beschreibung = scanner.next();
        double preis;
        System.out.println("Preis:");
       while (true) {
           String input = scanner.next();
           if (isDouble(input)) {
               preis = Double.parseDouble(input);
               break;
           } else {
               System.out.println("Ungültige Eingabe\nBitte nochmal veruschen:");
           }
        } 
        System.out.println("Gibt es Besonderheiten?\nJa \"y\"\tNein \"n\"");
        List<String> besonderheiten = new ArrayList<>();
        while (scanner.next().equalsIgnoreCase("y")) {
            System.out.println("Besonderheiten: ");
            String besonderheit = scanner.nextLine();
            besonderheiten.add(besonderheit);
            System.out.println("Füge weitere hinzu? \nJa \"y\"\tNein \"n\"");
            String bool = scanner.nextLine().toLowerCase();
            if (bool.equals("n")) {
                break;
            }
        }

        System.out.println("Gibt es Mängel?\nJa \"y\"\tNein \"n\"");
        List<String> maengel = new ArrayList<>();
        while (scanner.next().equalsIgnoreCase("y")) {
            System.out.println("Mängel: ");
            String mangel = scanner.nextLine();
            maengel.add(mangel);
            System.out.println("Füge weitere hinzu? \nJa \"y\"\tNein \"n\"");
            String bool = scanner.nextLine().toLowerCase();
            if (bool.equals("n")) {
                break;
            }
        }
        
        Ware ware = new Ware(bezeichnung,beschreibung,preis,besonderheiten,maengel);

        dataLayer.getWareDao().create(ware);
    }

    private static void delete(Scanner scanner, IDataLayer dataLayer) {
        System.out.println("Was soll gelöscht werden?\n\tWare \"W\"\n\tVertragspartner \"V\"");
    }

    private static void read(Scanner scanner, IDataLayer dataLayer) {
    }

    private static void update(Scanner scanner, IDataLayer data) {
    }
}
