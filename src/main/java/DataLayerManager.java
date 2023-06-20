public class DataLayerManager {

    private DataLayerManager instance;
    private String persistenceType;

    private DataLayerManager() {}

    public DataLayerManager getInstance() {
        if (this.instance == null) {
            this.instance = new DataLayerManager();
        }
        return this.instance;
    }

    public IDataLayer getDataLayer() {
        return this.instance.getDataLayer();
    }

    private String readPersistenceType() {
        return this.instance.persistenceType;
    }
}
