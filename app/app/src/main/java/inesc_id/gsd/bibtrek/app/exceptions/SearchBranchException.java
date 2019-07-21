package inesc_id.gsd.bibtrek.app.exceptions;

public class SearchBranchException extends Exception {
	
	public SearchBranchException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public SearchBranchException(String errorMessage) {
        super(errorMessage);
    }
}
