package inesc_id.gsd.bibtrek.app.exceptions;

public class AuthorSearchException extends SearchException {
	
	public AuthorSearchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public AuthorSearchException(String errorMessage) {
        super(errorMessage);
    }
}
