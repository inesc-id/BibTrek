package inesc_id.gsd.bibtrek.app.dblp.exceptions;

public class DBLPConnectNeo4JException extends Exception {
	
	public DBLPConnectNeo4JException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public DBLPConnectNeo4JException(String errorMessage) {
        super(errorMessage);
    }
}
