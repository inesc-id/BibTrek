package inesc_id.gsd.bibtrek.app.exceptions;

public class MainInterfaceException extends Exception {
	
	public MainInterfaceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public MainInterfaceException(String errorMessage) {
        super(errorMessage);
    }
}