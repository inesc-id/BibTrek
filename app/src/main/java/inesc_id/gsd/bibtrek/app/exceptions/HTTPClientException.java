package inesc_id.gsd.bibtrek.app.exceptions;

public class HTTPClientException extends Exception {
	
	public HTTPClientException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public HTTPClientException(String errorMessage) {
        super(errorMessage);
    }
}
