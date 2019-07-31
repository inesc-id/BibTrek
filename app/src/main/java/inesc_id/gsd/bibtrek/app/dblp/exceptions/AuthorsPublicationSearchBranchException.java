package inesc_id.gsd.bibtrek.app.dblp.exceptions;

public class AuthorsPublicationSearchBranchException extends SearchException {
	
	public AuthorsPublicationSearchBranchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public AuthorsPublicationSearchBranchException(String errorMessage) {
        super(errorMessage);
    }
}
