package inesc_id.gsd.bibtrek.app.exceptions;

public class WriteNeo4JThreadException extends Exception {
	
	public WriteNeo4JThreadException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public WriteNeo4JThreadException(String errorMessage) {
        super(errorMessage);
    }
}