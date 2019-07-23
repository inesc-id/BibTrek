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

public class ConnectNeo4J implements AutoCloseable {
	
    private final Driver driver;
    
    public ConnectNeo4J(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    private void store(final String query) {
    	Session session = driver.session();
	
        Boolean r = session.writeTransaction(new TransactionWork<Boolean>() {
            @Override
            public Boolean execute(Transaction tx) {     
            	System.out.println(query);
                StatementResult result = tx.run(query, parameters());
                return true;
            }
        });
    }
    
    private synchronized boolean read(String fileName) throws DBLPConnectNeo4JException {
    	BufferedReader bufferedReader;
    	String line, query, queryTuple[];
    	try {
			bufferedReader = new BufferedReader(new FileReader(fileName));
			
			while((line = bufferedReader.readLine()) != null) {
				if(!line.equals("") ) {
					queryTuple = line.split(": ", 2);
					query = queryTuple[1];	
					this.store(query);
				}
			}
			
			return true;
			
		} catch (FileNotFoundException fnfe) {
			throw new DBLPConnectNeo4JException("read(): could not open file: \"" + fileName + "\".");
		} catch (IOException ioe) {
			throw new DBLPConnectNeo4JException("read(): could not read the file: \"" + fileName + "\".");
		}    	
    }
    
    public boolean execute(String fileName) throws DBLPConnectNeo4JException {
    	return this.read(fileName);
    }    
}