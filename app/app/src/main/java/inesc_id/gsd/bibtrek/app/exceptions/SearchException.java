package inesc_id.gsd.bibtrek.app.exceptions;

public class SearchException extends Exception {
	
	public SearchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public SearchException(String errorMessage) {
        super(errorMessage);
    }
}
