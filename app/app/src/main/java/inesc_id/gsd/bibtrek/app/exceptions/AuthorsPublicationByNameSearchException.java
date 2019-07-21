package inesc_id.gsd.bibtrek.app.exceptions;

public class AuthorsPublicationByNameSearchException extends Exception {
	
	public AuthorsPublicationByNameSearchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public AuthorsPublicationByNameSearchException(String errorMessage) {
        super(errorMessage);
    }
}
