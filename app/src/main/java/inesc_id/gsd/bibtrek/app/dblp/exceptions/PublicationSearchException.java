package inesc_id.gsd.bibtrek.app.dblp.exceptions;

public class PublicationSearchException extends SearchException {
	
	public PublicationSearchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public PublicationSearchException(String errorMessage) {
        super(errorMessage);
    }
}
