package inesc_id.gsd.bibtrek.app.dblp.exceptions;

public class AuthorSearchException extends SearchException {
	
	public AuthorSearchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public AuthorSearchException(String errorMessage) {
        super(errorMessage);
    }
}
