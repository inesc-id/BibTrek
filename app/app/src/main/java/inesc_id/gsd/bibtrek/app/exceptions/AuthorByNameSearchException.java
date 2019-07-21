package inesc_id.gsd.bibtrek.app.exceptions;

public class AuthorByNameSearchException extends Exception {
	
	public AuthorByNameSearchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public AuthorByNameSearchException(String errorMessage) {
        super(errorMessage);
    }
}
