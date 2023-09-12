import kaufvertrag.dataLayer.businessObjects.Adresse;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.DataLayerManager;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;
import kaufvertrag.dataLayer.dataAccessObjects.IVertragspartnerDao;
import kaufvertrag.exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Programm {

    public static void main(String[] args) throws DaoException {

        DataLayerManager dataLayerManager = DataLayerManager.getInstance();
        IDataLayer dataLayer = dataLayerManager.getDataLayer();
        boolean lastRun = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Möchten Sie Vertragspartner/Ware: \n\terstellen? -> \"C\"\n\tlesen? -> \"R\"\n\tupdaten? -> \"U\"\n\tlöschen? -> \"D\"\n\tBeenden \"Q\":");
            String chosenOption = scanner.next();
            switch (chosenOption) {
                case "c" -> create(scanner, dataLayer);
                case "r" -> read(scanner, dataLayer);
                case "u" -> update(scanner, dataLayer);
                case "d" -> delete(scanner, dataLayer);
                case "q" -> lastRun = true;
                default -> printInvalidInput();
            }

            if (lastRun) {
                scanner.close();
                return;
            }
        } while (true);

    }

    private static void create(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        //Abfrage was hinzugefügt werden soll -> Vertragspartner oder Ware
        System.out.println("Was möchten Sie erstellen?\n\tVertragspartner -> \"V\"\n\tWare -> \"W\"\n\tAbbrechen -> \"Q\"");

        //Mit scanner auslesen was Antwort ist und entsprechend reagieren
        while (scanner.hasNext()) {
            switch (scanner.next().toLowerCase()) {
                case "w" -> createWare(scanner, dataLayer);
                case "v" -> createVertragspartner(scanner, dataLayer);
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
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
        System.out.println("Bezeichnung:");
        String bezeichnung = scanner.next();
        System.out.println("Beschreibung:");
        String beschreibung = scanner.next();
        double preis;
        System.out.println("Preis:");

        do {
            String input = scanner.next();
            if (isDouble(input)) {
                preis = Double.parseDouble(input);
                break;
            } else {
                printInvalidInput();
            }
        } while (true);

        System.out.println("Gibt es Besonderheiten?\nJa \"y\"\tNein \"n\"");
        List<String> besonderheiten = new ArrayList<>();
        addListItems(scanner, "Besonderheiten: ", besonderheiten);

        System.out.println("Gibt es Mängel?\nJa \"y\"\tNein \"n\"");
        List<String> maengel = new ArrayList<>();
        addListItems(scanner, "Mängel: ", maengel);

        Ware ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
        dataLayer.getWareDao().create(ware);
    }

    private static void addListItems(Scanner scanner, String x, List<String> items) {
        while (scanner.next().equalsIgnoreCase("y")) {
            System.out.println(x);
            String besonderheit = scanner.nextLine();
            items.add(besonderheit);
            System.out.println("Füge weitere hinzu? \nJa \"y\"\tNein \"n\"");
            String bool = scanner.nextLine().toLowerCase();
            if (bool.equals("n")) {
                break;
            }
        }
    }

    private static void delete(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        System.out.println("Was soll gelöscht werden?\n\tWare \"W\"\n\tVertragspartner \"V\"\n\tAbbrechen\"Q\"");
        int id;
        while (true) {
            switch (scanner.next().toLowerCase()) {
                case "w" -> {
                    id = askId(scanner);
                    dataLayer.getWareDao().delete(id);
                }
                case "v" -> {
                    id = askId(scanner);
                    dataLayer.getVertragspartnerDao().delete(id);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        }
    }

    private static void read(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        System.out.println("Was soll ausgegeben werden?\n\tWare \"W\"\n\tVertragspartner \"V\"\n\tAbbrechen\"Q\"");
        String objectType = scanner.next().toLowerCase();
        while (true) {
            switch (objectType) {
                case "w" -> chooseWareReadOption(scanner, dataLayer);
                case "v" -> chooseVertragspartnerReadOption(scanner, dataLayer);
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        }
    }

    private static void chooseVertragspartnerReadOption(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        int id;
        String infoType = askReadType(scanner);
        do {
            switch (infoType) {
                case "a" -> dataLayer.getVertragspartnerDao().read();
                case "id" -> {
                    id = askId(scanner);
                    dataLayer.getVertragspartnerDao().delete(id);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        } while (true);
    }

    private static void chooseWareReadOption(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        int id;
        String infoType = askReadType(scanner);
        do {
            switch (infoType) {
                case "a" -> dataLayer.getWareDao().read();
                case "id" -> {
                    id = askId(scanner);
                    dataLayer.getWareDao().delete(id);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        } while (true);
    }

    private static String askReadType(Scanner scanner) {
        System.out.println("Sollen alle Objekte ausgegeben werden oder nur ein einzelnes?\n\tAlle \"A\"\n\tEins mit einer ID \"ID\"\n\tAbbrechen\"Q\"");
        return scanner.next().toLowerCase();
    }

    private static void update(Scanner scanner, IDataLayer data) {
    }

    private static void printInvalidInput() {
        System.out.println("Ungültige Eingabe\nBitte nochmal veruschen:");
    }

    private static int askId(Scanner scanner) {
        System.out.println("Wie ist die ID des zu löschenden Objekts?");
        while (true) {
            String input = scanner.next();
            if (isDouble(input)) {
                return Integer.parseInt(input);
            } else {
                printInvalidInput();
            }
        }
    }
}
