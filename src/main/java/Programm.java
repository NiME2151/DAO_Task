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
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Möchten Sie Vertragspartner/Ware:\n\terstellen? \"C\"\n\tlesen? \"R\"\n\tupdaten? \"U\"\n\tlöschen? \"D\"\n\tBeenden \"Q\"");
            String chosenOption = scanner.next();
            switch (chosenOption) {
                case "c" -> create(scanner, dataLayer);
                case "r" -> read(scanner, dataLayer);
                case "u" -> update(scanner, dataLayer);
                case "d" -> delete(scanner, dataLayer);
                case "q" -> {
                    scanner.close();
                    return;
                }
                default -> printInvalidInput();
            }
        } while (true);

    }

    private static void create(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        //Abfrage was hinzugefügt werden soll -> Vertragspartner oder Ware
        System.out.println("Was möchten Sie erstellen?\n\tVertragspartner \"V\"\n\tWare \"W\"\n\tAbbrechen \"Q\"");

        //Mit scanner auslesen was Antwort ist und entsprechend reagieren
        while (true) {
            switch (scanner.next().toLowerCase()) {
                case "w" -> {
                    createWare(scanner, dataLayer);
                    return;
                }
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

        String ausweisNr = askAusweisNr(scanner);
        System.out.println("Vorname:");
        String vorname = scanner.next();
        System.out.println("Nachname:");
        String nachname = scanner.next();
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


        long id = askId(scanner);

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

        Ware ware = new Ware(id, bezeichnung, beschreibung, preis, besonderheiten, maengel);
        wareDao.create(ware);
    }


    private static void delete(Scanner scanner, IDataLayer dataLayer) {
        while (true) {
            System.out.println("Was soll gelöscht werden?\n\tWare \"W\"\n\tVertragspartner \"V\"\n\tAbbrechen\"Q\"");
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
        while (true) {
            System.out.println("Was soll ausgegeben werden?\n\tWare \"W\"\n\tVertragspartner \"V\"\n\tAbbrechen \"Q\"");
            switch (scanner.next()) {
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
        IDao<Vertragspartner, String> vertragspartnerDao = getVertragspartnerDao(dataLayer);
        while (true) {
            switch (askReadType(scanner)) {
                case "a" -> {
                    List<Vertragspartner> allVertragspartnerToRead = vertragspartnerDao.readAll();
                    printAllOfVertragsartnerList(allVertragspartnerToRead);
                }
                case "id" -> {
                    String ausweisNr = askAusweisNr(scanner);
                    Vertragspartner vertragspartnerToRead = vertragspartnerDao.read(ausweisNr);
                    printVertragspartner(vertragspartnerToRead);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        }
    }

    private static void chooseWareReadOption(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Ware, Long> wareDao = getWareDao(dataLayer);
        while (true) {
            switch (askReadType(scanner)) {
                case "a" -> {
                    List<Ware> allWareToRead = wareDao.readAll();
                    printAllOfWareList(allWareToRead);
                }
                case "id" -> {
                    Long id = askId(scanner);
                    Ware wareToRead = wareDao.read(id);
                    printWare(wareToRead);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        }
    }

    private static void update(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        do {
            System.out.println("Was soll geupdated werden?\n\tWare \"W\"\n\tVertragspartner \"V\"\n\tAbbrechen \"Q\"");
            switch (scanner.next().toLowerCase()) {
                case "w" -> updateWare(scanner, dataLayer);
                case "v" -> updateVertragsparnter(scanner, dataLayer);
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }
        } while (true);
    }

    //Maengel und Besonderheiten sind ausgeschlossen, da für diese keine setMethoden existieren im Klassendiagramm
    private static void updateWare(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Ware, Long> wareDao = getWareDao(dataLayer);
        Ware wareToUpdate = wareDao.read(askId(scanner));
        boolean isFinished;
        do {
            System.out.println(wareToUpdate.toString() + "\n\nWelche Information soll geupdated werden?\n\t1. Bezeichnung\n\t2. Beschreibung\n\t3. Preis\n\t4. Maengel\n\t5. Besonderheiten\n\tAbbrechen \"Q\"");
            switch (scanner.next().toLowerCase()) {
                case "1" -> {
                    System.out.println("Neue Bezeichung:");
                    scanner.nextLine();
                    String bezeichnung = scanner.nextLine();
                    wareToUpdate.setBezeichnung(bezeichnung);
                }
                case "2" -> {
                    System.out.println("Neue Beschreibung");
                    scanner.nextLine();
                    String beschreibung = scanner.nextLine();
                    wareToUpdate.setBeschreibung(beschreibung);

                }
                case "3" -> {
                    System.out.println("Neuer Preis");
                    double preis = getPreis(scanner);
                    wareToUpdate.setPreis(preis);
                }
                case "4" -> {
                    System.out.println("Mängel anpassen");
                    List<String> updated = editListForUpdate(scanner, wareToUpdate.getMaengel(), "Mangel");
                    wareToUpdate.setMaengel(updated);
                }
                case "5" -> {
                    System.out.println("Besonderheiten anpassen");
                    List<String> updated = editListForUpdate(scanner, wareToUpdate.getBesonderheiten(), "Besonderheit");
                    wareToUpdate.setBesonderheiten(updated);
                }
                case "q" -> {
                    return;
                }
                default -> printInvalidInput();
            }

            System.out.println("Eine weitere Information bearbeiten?\nJa \"y\"\tNein \"n\"");
            isFinished = !scanner.next().equalsIgnoreCase("y");
        } while (!isFinished);
        wareDao.update(wareToUpdate);
        System.out.println("Aktualisierte Ware:\n\n" + wareToUpdate + "\n");
    }

    private static List<String> editListForUpdate(Scanner scanner, List<String> listToEdit, String itemType) {
        while (true) {
            printStringList(listToEdit, itemType);
            System.out.println("Sollen Informationen hinzugefügt/entfernt werden?\n\tHinzufügen \"A\"\n\tEntfernen \"R\"\n\tAbbrechen \"Q\"");
            switch (scanner.next().toLowerCase()) {
                case "a" -> {
                    System.out.println("Sollen  Informationen hinzugefügt werden?\nJa \"y\"\tNein \"n\"");
                    addListItems(scanner, itemType, listToEdit);
                }
                case "r" -> {
                    System.out.println("Sollen  Informationen entfernt werden?\nJa \"y\"\tNein \"n\"");
                    removeListItems(scanner, itemType, listToEdit);
                }
                case "q" -> {
                    return listToEdit;
                }
                default -> printInvalidInput();
            }
        }
    }

    private static void updateVertragsparnter(Scanner scanner, IDataLayer dataLayer) throws DaoException {
        IDao<Vertragspartner, String> vertragspartnerDao = getVertragspartnerDao(dataLayer);
        Vertragspartner vertragspartnerToUpdate = vertragspartnerDao.read(askAusweisNr(scanner));
        Adresse adresseToUpdate = vertragspartnerToUpdate.getAdresse();
        System.out.println(vertragspartnerToUpdate + "\n\nWelche Information soll geupdated werden?\n\t1. Ausweisnummer\n\t2. Vorname\n\t3. Nachname\n\t4. Straße\n\t5. Hausnummer\n\t6. Postleitzahl\n\t7. Ort\n\tAbbrechen \"Q\"");
        boolean finishedInput;
        do {
            switch (scanner.next().toLowerCase()) {
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
            vertragspartnerToUpdate.setAdresse(adresseToUpdate);
            System.out.println("Eine weitere Information bearbeiten?\nJa \"y\"\tNein \"n\"");
            finishedInput = !scanner.next().equalsIgnoreCase("y");
        } while (!finishedInput);
        System.out.println("Aktualisierter Vertragspartner:\n\n" + vertragspartnerToUpdate + "\n");


        vertragspartnerDao.update(vertragspartnerToUpdate);
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

    private static void removeListItems(Scanner scanner, String listType, List<String> items) {
        while (scanner.next().equalsIgnoreCase("y")) {
            printStringList(items, listType);
            System.out.println(listType);
            scanner.nextLine();
            int indexToRemove = askInt(scanner)-1;
            items.remove(indexToRemove);
            printStringList(items, listType);
            System.out.println("Weitere entfernen? \nJa \"y\"\tNein \"n\"");
        }
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
        return Math.round(preis * 100.0) / 100.0;
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
        System.out.println("ID:");
        while (true) {
            String input = scanner.next();
            try {
                return Long.parseLong(input);
            } catch (Exception e) {
                printInvalidInput();
            }
        }
    }

    private static int askInt(Scanner scanner) {
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
        System.out.println("Ausweisnummer:");
        return scanner.next();
    }

    private static String askReadType(Scanner scanner) {
        System.out.println("Sollen alle Objekte ausgegeben werden oder nur ein einzelnes?\n\tAlle \"A\"\n\tEins mit einer ID \"ID\"\n\tAbbrechen \"Q\"");
        return scanner.next().toLowerCase();
    }

    private static void printInvalidInput() {
        System.out.println("Ungültige Eingabe\nBitte nochmal veruschen:");
    }

    private static void printStringList(List<String> toPrint, String listType) {
        StringBuilder list = new StringBuilder("\n" + listType + ":\n");
        for (int i = 0; i < toPrint.size(); i++) {
            list.append(i + 1).append(". ").append(toPrint.get(i)).append("\n");
        }
        System.out.println(list + "\n");
    }

    private static void printAllOfWareList(List<Ware> items) {
        for (Ware item :
                items) {
            System.out.println(item.toString() + "\n");
        }
    }

    private static void printWare(Ware item) {
        System.out.println(item.toString() + "\n");
    }


    private static void printVertragspartner(Vertragspartner vertragspartnerToRead) {
        System.out.println(vertragspartnerToRead + "\n");
    }

    private static void printAllOfVertragsartnerList(List<Vertragspartner> items) {
        for (Vertragspartner item :
                items) {
            System.out.println(item.toString() + "\n");
        }
    }

    private static IDao<Vertragspartner, String> getVertragspartnerDao(IDataLayer dataLayer) {
        return dataLayer.getVertragspartnerDao();
    }


    private static IDao<Ware, Long> getWareDao(IDataLayer dataLayer) {
        return dataLayer.getWareDao();
    }
}
