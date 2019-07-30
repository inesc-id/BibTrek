package inesc_id.gsd.bibtrek.app.dblp.exceptions;

public class DBLPClassException extends SearchException {
	
	public DBLPClassException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public DBLPClassException(String errorMessage) {
        super(errorMessage);
    }
}
