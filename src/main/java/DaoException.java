public class DaoException extends RuntimeException {

    private String message;

    public DaoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
