import kaufvertrag.dataLayer.businessObjects.Adresse;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.DataLayerManager;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;
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
            System.out.println("Möchten Sie Vertragspartner/Ware:\n\terstellen? \"C\"\n\tlesen? \"R\"\n\tupdaten? \"U\"\n\tlöschen? \"D\"\n\tBeenden \"Q\"");
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
        System.out.println("Was möchten Sie erstellen?\n\tVertragspartner \"V\"\n\tWare \"W\"\n\tAbbrechen \"Q\"");

        //Mit scanner auslesen was Antwort ist und entsprechend reagieren
        while (scanner.hasNext()) {
            switch (scanner.next().toLowerCase()) {
                case "w" -> createWare(scanner, dataLayer);
                case "v" -> {
                    createVertragspartner(scanner, dataLayer);
                    return;
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        }
    }

    private static void createVertragspartner(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Vertragspartner, String> vertragspartnerDao = getVertragspartnerDao(dataLayer);

        System.out.println("Vorname:");
        String vorname = scanner.next();
        System.out.println("Nachname:");
        String nachname = scanner.next();
        System.out.println("AusweisNr:");
        String ausweisNr = scanner.next();
        System.out.println("Strasse:");
        //next Line konsumiert die neue Zeile die ausgegeben wird mit println, damit ein neuer Zeileninput eingegeben werden kann
        scanner.nextLine();
        String strasse = scanner.nextLine();
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

    private static void createWare(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Ware, Long> wareDao = getWareDao(dataLayer);

        System.out.println("Bezeichnung:");
        //next Line konsumiert die neue Zeile die ausgegeben wird mit println, damit ein neuer Zeileninput eingegeben werden kann
        scanner.nextLine();
        String bezeichnung = scanner.nextLine();
        System.out.println("Beschreibung:");
        String beschreibung = scanner.nextLine();
        double preis;
        System.out.println("Preis:");

        preis = getPreis(scanner);

        System.out.println("Gibt es Besonderheiten?\nJa \"y\"\tNein \"n\"");
        List<String> besonderheiten = new ArrayList<>();
        scanner.nextLine();
        addListItems(scanner, "Besonderheit: ", besonderheiten);

        System.out.println("Gibt es Mängel?\nJa \"y\"\tNein \"n\"");
        List<String> maengel = new ArrayList<>();
        scanner.nextLine();
        addListItems(scanner, "Mangel: ", maengel);

        Ware ware = new Ware(bezeichnung, beschreibung, preis, besonderheiten, maengel);
        wareDao.create(ware);
    }

    private static void addListItems(Scanner scanner, String x, List<String> items) {
        while (scanner.next().equalsIgnoreCase("y")) {
            System.out.println(x);
            scanner.nextLine();
            String item = scanner.nextLine();
            items.add(item);
            System.out.println("Füge weitere hinzu? \nJa \"y\"\tNein \"n\"");
        }
    }

    private static void delete(Scanner scanner, IDataLayer dataLayer) {
        System.out.println("Was soll gelöscht werden?\n\tWare \"W\"\n\tVertragspartner \"V\"\n\tAbbrechen\"Q\"");
        while (true) {
            switch (scanner.next().toLowerCase()) {
                case "w" -> getWareDao(dataLayer).delete(askId(scanner));
                case "v" -> getVertragspartnerDao(dataLayer).delete(askAusweisNr(scanner));
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        }
    }

    private static void read(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        System.out.println("Was soll ausgegeben werden?\n\tWare \"W\"\n\tVertragspartner \"V\"\n\tAbbrechen \"Q\"");
        String objectType = scanner.next().toLowerCase();
        do {
            switch (objectType) {
                case "w" -> chooseWareReadOption(scanner, dataLayer);
                case "v" -> chooseVertragspartnerReadOption(scanner, dataLayer);
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        } while (true);
    }

    private static void chooseVertragspartnerReadOption(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Vertragspartner, String> vertragspartnerDao = getVertragspartnerDao(dataLayer);
        String readType = askReadType(scanner);
        do {
            switch (readType) {
                case "a" -> vertragspartnerDao.readAll();
                case "id" -> {
                    String id = askAusweisNr(scanner);
                    vertragspartnerDao.read(id);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        } while (true);
    }

    private static void chooseWareReadOption(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Ware, Long> wareDao = getWareDao(dataLayer);
        do {
            switch (askReadType(scanner)) {
                case "a" -> wareDao.readAll();
                case "id" -> {
                    Long id = askId(scanner);
                    wareDao.read(id);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        } while (true);
    }

    private static void update(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        System.out.println("Was soll geupdated werden?\n\tWare \"W\"\n\tVertragspartner \"V\"\n\tAbbrechen \"Q\"");
        String objectType = scanner.next().toLowerCase();
        do {
            switch (objectType) {
                case "w" -> updateWare(scanner, dataLayer);
                case "v" -> updateVertragsparnter(scanner, dataLayer);
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        } while (true);
    }

    private static void updateWare(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Ware, Long> wareDao = getWareDao(dataLayer);
        Ware wareToUpdate = wareDao.read(askId(scanner));
        System.out.println(wareToUpdate.toString() + "\nWelche Information soll geupdated werden?\n\t1. Bezeichnung\n\t2. Beschreibung\n\t3. Preis\n\tAbbrechen \"Q\"");
        String option = scanner.next().toLowerCase();
        boolean finishedInput;
        do {
            switch (option) {
                case "1" -> {
                    System.out.println("Neue Bezeichung:");
                    String bezeichnung = scanner.nextLine();
                    wareToUpdate.setBezeichnung(bezeichnung);
                }
                case "2" -> {
                    System.out.println("Neue Beschreibung");
                    String beschreibung = scanner.nextLine();
                    wareToUpdate.setBeschreibung(beschreibung);

                }
                case "3" -> {
                    System.out.println("Neuer Preis");
                    double preis = getPreis(scanner);
                    wareToUpdate.setPreis(preis);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
            System.out.println("Eine weitere Information bearbeiten?\nJa \"y\"\tNein \"n\"");
            finishedInput = !scanner.next().equalsIgnoreCase("y");
        } while (!finishedInput);
        wareDao.update(wareToUpdate);
    }

    private static void updateVertragsparnter(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Vertragspartner, String> vertragspartnerDao = getVertragspartnerDao(dataLayer);
        Vertragspartner vertragspartnerToUpdate = vertragspartnerDao.read(askAusweisNr(scanner));
        Adresse adresseToUpdate = vertragspartnerToUpdate.getAdresse();
        System.out.println(vertragspartnerToUpdate + "\nWelche Information soll geupdated werden?\n\t1. Ausweisnummer\n\t2. Vorname\n\t3. Nachname\n\t4. Straße\n\t5. Hausnummer\n\t6. Postleitzahl\n\t7. Ort\n\tAbbrechen \"Q\"");
        String option = scanner.next().toLowerCase();
        boolean finishedInput;
        do {
            switch (option) {
                case "1" -> {
                    System.out.println("Neue Ausweisnummer:");
                    String ausweisNr = scanner.next();
                    vertragspartnerToUpdate.setAusweisNr(ausweisNr);
                }
                case "2" -> {
                    System.out.println("Neuer Vorname:");
                    String vorname = scanner.next();
                    vertragspartnerToUpdate.setVorname(vorname);

                }
                case "3" -> {
                    System.out.println("Neuer Nachname:");
                    String nachname = scanner.next();
                    vertragspartnerToUpdate.setNachname(nachname);
                }
                case "4" -> {
                    System.out.println("Neue Straße:");
                    String strasse = scanner.next();
                    adresseToUpdate.setStrasse(strasse);
                }
                case "5" -> {
                    System.out.println("Neue Hausnummer:");
                    String hausNr = scanner.next();
                    adresseToUpdate.setHausNr(hausNr);
                }
                case "6" -> {
                    System.out.println("Neue Postleitzahl:");
                    String plz = scanner.next();
                    adresseToUpdate.setPlz(plz);
                }
                case "7" -> {
                    System.out.println("Neuer Ort:");
                    String nachname = scanner.next();
                    adresseToUpdate.setOrt(nachname);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
            System.out.println("Eine weitere Information bearbeiten?\nJa \"y\"\tNein \"n\"");
            finishedInput = !scanner.next().equalsIgnoreCase("y");
        } while (!finishedInput);

        vertragspartnerToUpdate.setAdresse(adresseToUpdate);
        vertragspartnerDao.update(vertragspartnerToUpdate);
    }

    private static double getPreis(Scanner scanner) {
        double preis;
        do {
            String input = scanner.next();
            String correctedInput = input.replace(",", ".");
            if (isDouble(correctedInput)) {
                preis = Double.parseDouble(correctedInput);
                break;
            } else {
                printInvalidInput();
            }
        } while (true);
        return preis;
    }

    private static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static long askId(Scanner scanner) {
        System.out.println("Wie ist die ID des Objekts?");
        while (true) {
            String input = scanner.next();
            try {
                return Integer.parseInt(input);
            } catch (Exception e) {
                printInvalidInput();
            }
        }
    }

    private static String askAusweisNr(Scanner scanner) {
        System.out.println("Wie ist die Ausweisnummer des Partners?");
        return scanner.next();
    }

    private static String askReadType(Scanner scanner) {
        System.out.println("Sollen alle Objekte ausgegeben werden oder nur ein einzelnes?\n\tAlle \"A\"\n\tEins mit einer ID \"ID\"\n\tAbbrechen \"Q\"");
        return scanner.next().toLowerCase();
    }

    private static void printInvalidInput() {
        System.out.println("Ungültige Eingabe\nBitte nochmal veruschen:");
    }

    private static IDao<Vertragspartner, String> getVertragspartnerDao(IDataLayer dataLayer) {
        return dataLayer.getVertragspartnerDao();
    }

    private static IDao<Ware, Long> getWareDao(IDataLayer dataLayer) {
        return dataLayer.getWareDao();
    }
}
