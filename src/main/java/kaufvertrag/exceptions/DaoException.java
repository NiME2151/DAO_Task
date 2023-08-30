package kaufvertrag.exceptions;

public class DaoException extends Exception {

    private String message;

    public DaoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
