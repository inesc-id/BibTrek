package inesc_id.gsd.bibtrek.app.neo4j;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPConnectNeo4JException;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import static org.neo4j.driver.v1.Values.parameters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DBLPConnectNeo4J implements AutoCloseable {
	
	private final static String DBLP_NOSQL = "dblp.nosql";
    private final Driver driver;

    public DBLPConnectNeo4J(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    private void store(final String query) {
        try (Session session = driver.session()) {        	
            String greeting = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {     
                	System.out.println("with a little help");
                	System.out.println(query);
                    StatementResult result = tx.run(query, parameters());
                    return null;
                }
            });
            System.out.println(greeting);
        }
    }
    
    private String read() throws DBLPConnectNeo4JException {
    	BufferedReader bufferedReader;
    	String line, query, queryTuple[];
    	try {
			bufferedReader = new BufferedReader(new FileReader(DBLP_NOSQL));
			
			while((line = bufferedReader.readLine()) != null) {
				if(!line.equals("") ) {
					queryTuple = line.split(": ", 2);
					query = queryTuple[1];	
					this.store(query);
				}
			}
			
		} catch (FileNotFoundException fnfe) {
			throw new DBLPConnectNeo4JException("read(): could not open file: \"" + DBLP_NOSQL + "\".");
		} catch (IOException ioe) {
			throw new DBLPConnectNeo4JException("read(): could not read the file: \"" + DBLP_NOSQL + "\".");
		}
    	return "";
    }
    
    public void execute() throws DBLPConnectNeo4JException {
    	this.read();
    }    
}