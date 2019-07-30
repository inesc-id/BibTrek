package inesc_id.gsd.bibtrek.app.dblp.exceptions;

public class DBLPInterfaceException extends Exception {
	
	public DBLPInterfaceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public DBLPInterfaceException(String errorMessage) {
        super(errorMessage);
    }
}
