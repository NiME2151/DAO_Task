import kaufvertrag.businessObjects.IAdresse;
import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.businessObjects.IWare;
import kaufvertrag.dataLayer.businessObjects.Adresse;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.DataLayerManager;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;
import kaufvertrag.dataLayer.dataAccessObjects.xml.VertragspartnerDaoXml;
import kaufvertrag.exceptions.DaoException;

public class Programm {

    public static void main(String[] args) throws DaoException {

        DataLayerManager dataLayerManager = DataLayerManager.getInstance();
        IDataLayer dataLayer = dataLayerManager.getDataLayer();

        // vertragspartner xml test
        IDao<IVertragspartner, String> vertragspartnerDaoXml = dataLayer.getVertragspartnerDao();

        IVertragspartner vertragspartner = vertragspartnerDaoXml.create();
        vertragspartner.setVorname("Nico");
        vertragspartner.setNachname("Meyer");
        vertragspartner.setAusweisNr("2911");
        Adresse adresse = new Adresse(
                "Klaus-Groth-Weg",
                "9M",
                "27753",
                "Delmenhorst"
        );
        vertragspartner.setAdresse(adresse);
        vertragspartnerDaoXml.create(vertragspartner);
        System.out.println(vertragspartnerDaoXml.read("2911"));
        vertragspartner.setNachname("Maier");
        vertragspartnerDaoXml.update(vertragspartner);
        System.out.println(vertragspartnerDaoXml.readAll());

        // ware xml test
        IDao<IWare, Long> wareDaoXml = dataLayer.getWareDao();

        IWare ware = wareDaoXml.create();
        ware.setBeschreibung("Eine Beschreibung");
        ware.setBezeichnung("Eine Ware");
        ware.setPreis(2911.99);
        ware.setId(1129);
        ware.getMaengel().add("Ein Mangel");
        ware.getBesonderheiten().add("Eine Besonderheit");
        wareDaoXml.create(ware);
        System.out.println(wareDaoXml.read(ware.getId()));
        wareDaoXml.update(ware);
        System.out.println(wareDaoXml.readAll());
    }
}
