package inesc_id.gsd.bibtrek;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import inesc_id.gsd.bibtrek.exceptions.ExampleAppException;

import static org.neo4j.driver.v1.Values.parameters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

public class ExampleApp implements AutoCloseable {
    private static final String GRAPH_DATABASE_FILE = "example.nosql";
    
    private static final String CREATE_GRAPH_DATABASE = "1";
    private static final String DELETE_GRAPH_DATABASE = "2";
    private static final String QUERY_GRAPH_DATABASE = "3";
    private static final String EXIT = "4";

    private final Driver driver;

    public ExampleApp(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() throws Exception {
        driver.close();
    } 
    
    public String getGraphDatabase() throws ExampleAppException {
        try {
        	String line;
            File file = new File(GRAPH_DATABASE_FILE);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));            
            
            System.out.println("database query");
            
            String createDatabase = "";
            
            while((line = bufferedReader.readLine()) != null) {    
            	if(line.contains("//")) {      
            		continue;
            	}
                createDatabase += line;
            }
            
            return createDatabase;
        } catch (FileNotFoundException fnfe) {
            throw new ExampleAppException("getGraphDatabase(): The file you were looking for with name:\"" + GRAPH_DATABASE_FILE
                    + "\" does not exist.");
        } catch (IOException ioe) {
            throw new ExampleAppException("getGraphDatabase(): Could not close the Input Stream");
        }
    }
    
    public void createExampleDatabase() throws ExampleAppException { 
    	String createDatabaseTransaction, message;
    	
    	try ( Session session = driver.session() )
        {	    		
			createDatabaseTransaction = getGraphDatabase();			
            message = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {	                	
                	StatementResult result = tx.run(createDatabaseTransaction,
                            parameters());
                    return "The example database was successfully imported.";
                }
            } );
            System.out.println(message);
        }
    	catch (ExampleAppException e) {
			throw new ExampleAppException("createExampleDatabase(): Could not get the database from the \"" + 
					GRAPH_DATABASE_FILE + "\" file...");
		}
    }
    
    public static void main(String argv[]) throws Exception
    {	
        Scanner clientInputScanner = new Scanner(System.in);

        System.out.print("Insert the port on which your local graph database is running: ");
        String localport = clientInputScanner.nextLine();

        System.out.print("Insert your NEO4J username: ");
        String username = clientInputScanner.nextLine();

        System.out.print("Insert your password: ");
        String password = clientInputScanner.nextLine();

        ExampleApp queryAgent = new ExampleApp("bolt://localhost:" + localport, username, password);    

        while(true) {
        	 System.out.println("##### BIBTREK EXAMPLE #####");
             System.out.println("");
             System.out.println("===================================");
             System.out.println("");
             System.out.println("(*): Pick one of the available options:");
             System.out.println("");
             System.out.println("(1): Create an example database.");
             System.out.println("");
             System.out.println("(2): Delete the example database.");
             System.out.println("");
             System.out.println("(3): Query the database.");
             System.out.println("");
             System.out.println("(4): Exits the program.");
             System.out.println("");
             System.out.println("===================================");
             System.out.println("");
             System.out.print("Insert your choice: ");
            String option = clientInputScanner.nextLine();

            switch(option) {
                case CREATE_GRAPH_DATABASE:                
                    queryAgent.createExampleDatabase();
                    break;
                case DELETE_GRAPH_DATABASE:
                    String deleteDatabaseQuery = "MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r";
                    //queryAgent.doTransaction(option, deleteDatabaseQuery);
                    break;
                case QUERY_GRAPH_DATABASE:
                    System.out.println("Query the database:");
                    String query = clientInputScanner.nextLine();
                    //queryAgent.doTransaction(option, query);
                    break;
                case EXIT:
                    return;                    
                default:
                    System.out.println("main(): That option does not exist. Please insert another command...");
                    break;
            }
        }
    }
}