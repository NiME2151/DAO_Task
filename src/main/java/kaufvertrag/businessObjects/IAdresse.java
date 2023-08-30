package kaufvertrag.businessObjects;

import javax.xml.bind.annotation.XmlElement;

public interface IAdresse {

    @XmlElement(name = "strasse")
    String getStrasse();

    void setStrasse(String strasse);

    @XmlElement(name = "hausNr")
    String getHausNr();

    void setHausNr(String hausNr);

    @XmlElement(name = "plz")
    String getPlz();

    void setPlz(String plz);

    @XmlElement(name = "ort")
    String getOrt();

    void setOrt(String ort);

    String toString();
}
