package inesc_id.gsd.bibtrek.app.exceptions;

public class DBLPNoSQLWriterException extends Exception {
	
	public DBLPNoSQLWriterException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public DBLPNoSQLWriterException(String errorMessage) {
        super(errorMessage);
    }
}