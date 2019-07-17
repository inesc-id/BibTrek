package inesc_id.gsd.bibtrek.app.exceptions;

public class DBLPInterfaceException extends Exception {
	
	public DBLPInterfaceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public DBLPInterfaceException(String errorMessage) {
        super(errorMessage);
    }
}
