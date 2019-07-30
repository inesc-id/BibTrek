package inesc_id.gsd.bibtrek.app.dblp.exceptions;

public class AuthorsPublicationsSearchException extends SearchException {
	
	public AuthorsPublicationsSearchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public AuthorsPublicationsSearchException(String errorMessage) {
        super(errorMessage);
    }
}

