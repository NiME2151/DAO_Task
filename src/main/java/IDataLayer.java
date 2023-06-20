import entities.Vertragspartner;
import entities.Ware;

public interface IDataLayer {

    IDao<Vertragspartner, String> getDaoVertragspartner();
    IDao<Ware, String> getDaoWare();
}
