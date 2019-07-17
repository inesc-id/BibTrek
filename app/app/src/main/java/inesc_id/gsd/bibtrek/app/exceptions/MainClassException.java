package inesc_id.gsd.bibtrek.app.exceptions;

public class MainClassException extends Exception {
	
	public MainClassException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public MainClassException(String errorMessage) {
        super(errorMessage);
    }
}