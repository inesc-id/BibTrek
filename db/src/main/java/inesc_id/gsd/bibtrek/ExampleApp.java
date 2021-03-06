package inesc_id.gsd.bibtrek;

import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import inesc_id.gsd.bibtrek.exceptions.ExampleAppException;

import static org.neo4j.driver.v1.Values.parameters;
import static org.neo4j.io.fs.FileUtils.deleteRecursively;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ExampleApp {
    private static final String EXAMPLE_DATABASE = "example.nosql";
    
    private static final String GRAPH_DATABASE_PATH = "target" + File.separator + "example-db";
    
    private static final File NEO4J_DATABASE = new File(GRAPH_DATABASE_PATH);
    
    private static final String GET_ALL_AUTHORS = "1";
    private static final String GET_ALL_PAPERS = "2";
    private static final String GET_ALL_INSTITUTIONS = "3";
    private static final String GET_ALL_SUBJECTS = "4";
    private static final String PAPERS_BY_AUTHOR = "5";
    private static final String AUTHORS_BY_PAPER = "6";
    private static final String SUBJECTS_BY_PAPER = "7";
    private static final String PAPERS_REFERENCES = "8";
    private static final String EXIT = "0";
    
    private static GraphDatabaseService graphDb;
    
	@SuppressWarnings("deprecation")
	public ExampleApp(String port) {
		clearDbPath();		
		GraphDatabaseSettings.BoltConnector bolt = GraphDatabaseSettings.boltConnector("0");    	
    	graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(NEO4J_DATABASE).setConfig( bolt.type, "BOLT").setConfig( bolt.enabled, "true").setConfig( bolt.address, "localhost:" + port).newGraphDatabase();
	}
    
    public String getGraphDatabase() throws ExampleAppException {
        try {
        	String line;
            File file = new File(EXAMPLE_DATABASE);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));            
            
            String createDatabase = "";
            
            while((line = bufferedReader.readLine()) != null) {    
            	if(line.contains("//")) {      
            		continue;
            	}
                createDatabase += line;
            }
            
            return createDatabase;
        } catch (FileNotFoundException fnfe) {
            throw new ExampleAppException("getGraphDatabase(): The file you were looking for with name:\"" + EXAMPLE_DATABASE
                    + "\" does not exist.");
        } catch (IOException ioe) {
            throw new ExampleAppException("getGraphDatabase(): Could not close the Input Stream");
        }
    }
    
    
    public void createExampleDatabase() throws ExampleAppException { 
    	String createDatabaseTransaction, message, rows = "";    	
    	
    	Map<String, Object> params = new HashMap<>();
    	
    	createDatabaseTransaction = getGraphDatabase();
    	
    	// tag::execute[]
		try (Transaction tx = graphDb.beginTx();
						
				Result result = graphDb.execute(createDatabaseTransaction, params)							
			) {
				while ( result.hasNext() ) {
					Map<String,Object> row = result.next();
					for ( Entry<String,Object> column : row.entrySet()) {
						rows += column.getKey() + ": " + column.getValue() + "; ";
					}
					rows += "\n";
				}
				tx.success();
		}
		
		// end::execute[]
		
		System.out.println("Success: the database was successfully populated!");
		System.out.println(""); 
    }
    
    public void deleteDatabase() {
    	String deleteDatabaseQuery = "MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r", message, rows = "";
    	
    	Map<String, Object> params = new HashMap<>();
    	
    	// tag::execute[]
		try (Transaction tx = graphDb.beginTx();
						
				Result result = graphDb.execute(deleteDatabaseQuery, params)							
			) {
				while ( result.hasNext() ) {
					Map<String,Object> row = result.next();
					for ( Entry<String,Object> column : row.entrySet()) {
						rows += column.getKey() + ": " + column.getValue() + "; ";
					}
					rows += "\n";
				}
				tx.success();
		}
		
		// end::execute[]
		
		System.out.println("Success: the database was successfully deleted!");
		System.out.println(""); 
    }   
    
    // getAuthors(): Simply allows the user to pick all authors from the database
    public void getAuthors() {
    	String attribute, fname = "", surname = "", node = "", rows = ""; 
    	
    	try ( Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute( "MATCH (authors:Author) RETURN authors.surname, authors.fname, authors" ) )
    	{
    		while ( result.hasNext() )
    		{
    			Map<String,Object> row = result.next();
                for ( Entry<String,Object> column : row.entrySet() )
                {	
                	attribute = column.getKey();
                	switch(attribute) {
                		case "authors.fname":
                			fname = (String) column.getValue();
                			break;
                		case "authors.surname":
                			surname = (String) column.getValue();      
                			break;
                		default:                			
                			node = column.getValue() + "";
                	}
                }
                rows += node + " :  " + fname + " " + surname + "\n";
           }
    	}
    	
    	System.out.println(rows);
    }
    
    // getInstitutions(): Simply allows the user to pick all institutions from the database
    public void getInstitutions() {
    	String attribute, iname = "", node = "", rows = ""; 
    	
    	try ( Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute( "MATCH (institutions:Institution) RETURN institutions, institutions.iname" ) )
    	{
    		while ( result.hasNext() )
    		{
    			Map<String,Object> row = result.next();
                for ( Entry<String,Object> column : row.entrySet() )
                {	
                	attribute = column.getKey();
                	switch(attribute) {
                		case "institutions.iname":
                			iname = (String) column.getValue();
                			break;                		
                		default:                			
                			node = column.getValue() + "";
                	}
                }
                rows += node + " :  " + iname + "\n";
           }
    	}
    	
    	System.out.println(rows);
    }
    
    // getPapers(): Simply allows the user to pick all papers from the database
    public void getPapers() {
    	String attribute, year = "", title = "", library = "", node = "", rows = ""; 
    	
    	try ( Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute( "MATCH (papers:Paper) RETURN papers, papers.title, papers.library, papers.year" ) )
    	{
    		while ( result.hasNext() )
    		{
    			Map<String,Object> row = result.next();
                for ( Entry<String,Object> column : row.entrySet() )
                {	
                	attribute = column.getKey();
                	switch(attribute) {
	                	case "papers.library":
	            			library = (String) column.getValue();
	            			break;
	                	case "papers.title":
	            			title = (String) column.getValue();
	            			break;
                		case "papers.year":
                			year = Long.toString((long) column.getValue());
                			break;                		
                		default:  
                			if(attribute.contains("Node")) {
                				node = column.getValue() + "";
                			}
                	}
                }
                rows += node + " :  [T] " + title + " [L] " + library + " [Y] " + year + "\n";
           }
    	}
    	
    	System.out.println(rows);
    }
    
    // getSubjects(): Simply allows the user to pick all subjects from the database
    public void getSubjects() {
    	String attribute, subject = "", node = "", rows = ""; 
    	
    	try ( Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute( "MATCH (subjects:Subject) RETURN subjects, subjects.subject" ) )
    	{
    		while ( result.hasNext() )
    		{
    			Map<String,Object> row = result.next();
                for ( Entry<String,Object> column : row.entrySet() )
                {	
                	attribute = column.getKey();
                	switch(attribute) {
                		case "subjects.subject":
                			subject = (String) column.getValue();
                			break;                		
                		default:                			
                			node = column.getValue() + "";
                	}
                }
                rows += node + " :  " + subject + "\n";
           }
    	}
    	
    	System.out.println(rows);
    }
    
    // getAuthorsPapers(): get all the papers written by a certain Author
    public void getAuthorsPapers(String fname, String surname) {
    	String attribute, node = "", rows = "", library = "", year = "", title = ""; 
    	boolean displayAuthorsName = true;
    	
    	try ( Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute( "MATCH (authors:Author {fname:\"" + fname + "\",surname:\"" + surname + "\"})"
             		+ "-[:WROTE]-(papers:Paper) RETURN papers.title, papers.year, papers.library, papers") )
    	{
    		while ( result.hasNext() )
    		{
    			Map<String,Object> row = result.next();
                for ( Entry<String,Object> column : row.entrySet() )
                {	
                	attribute = column.getKey();
                	switch(attribute) {
	                	case "papers.library":
	            			library = (String) column.getValue();
	            			break;
	                	case "papers.title":
	            			title = (String) column.getValue();
	            			break;
                		case "papers.year":
                			year = Long.toString((long) column.getValue());
                			break;                		
                		default:  
                			if(attribute.contains("Node")) {
                				node = column.getValue() + "";
                			}
                	}
                }
                if(displayAuthorsName) {
                	System.out.println("");
                	System.out.println("Author: " + fname + " " + surname);
                	displayAuthorsName = false;
                }
                rows += node + " :  [T] " + title + " [L] " + library + " [Y] " + year + "\n";
           }
    	}
    	
    	System.out.println(rows);
    }
    
    // getPapersAuthors(): get all the authors of a paper
    public void getPaperAuthors(String title) {
    	String attribute, node = "", rows = "", fname = "", surname = "", displayTitle = ""; 
    	boolean displayPapersTitle = true; 
    	
    	try ( Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute( "MATCH (papers:Paper)"
             		+ "<-[:WROTE]-(authors:Author) WHERE papers.title =~ \".*" + title
             		+ ".*\" RETURN authors.fname, authors.surname, authors, papers.title" ) )
    	{
    		while ( result.hasNext() )
    		{
    			Map<String,Object> row = result.next();
                for ( Entry<String,Object> column : row.entrySet() )
                {	
                	attribute = column.getKey();
                	switch(attribute) {
                		case "authors.fname":
                			fname = (String) column.getValue();
                			break;
                		case "authors.surname":
                			surname = (String) column.getValue();      
                			break;
                		case "papers.title":
                			if(displayPapersTitle)
                				displayTitle = (String) column.getValue();      
                		default:                			
                			node = column.getValue() + "";
                	}
                }
                if(displayPapersTitle) {
                	System.out.println("");
                	System.out.println("Paper: " + displayTitle);
                	displayPapersTitle = false;
                }
                rows += node + " :  " + fname + " " + surname + "\n";
           }
    	}
    	
    	System.out.println(rows);
    }
    
    // getPapersAuthors(): get all the subjects of a paper
    public void getPapersSubjects(String title) {
    	String attribute, node = "", rows = "", subject = "", displayTitle = ""; 
    	boolean displayPapersTitle = true; 
    	
    	try ( Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute( "MATCH (papers:Paper)"
             		+ "<-[:FOCUS_OF]-(subjects:Subject) WHERE papers.title =~ \".*" + title
             		+ ".*\" RETURN subjects.subject, subjects, papers.title" ) )
    	{
    		while ( result.hasNext() )
    		{
    			Map<String,Object> row = result.next();
                for ( Entry<String,Object> column : row.entrySet() )
                {	
                	attribute = column.getKey();
                	switch(attribute) {
                		case "subjects.subject":
                			subject = (String) column.getValue();
                			break;
                		case "papers.title":
                			if(displayPapersTitle)
                				displayTitle = (String) column.getValue();   
                		default:                			
                			node = column.getValue() + "";
                	}
                }
                if(displayPapersTitle) {
                	System.out.println("");
                	System.out.println("Paper: " + displayTitle);
                	displayPapersTitle = false;
                }
                rows += node + " :  " + subject + "\n";
           }
    	}
    	
    	System.out.println(rows);
    }
    
    // getPapersReferences(): get all the references of a paper
    public void getPapersReferences(String title) {
    	String attribute, node = "", rows = "", library = "", refsTitle = "", year = "", displayTitle = ""; 
    	boolean displayPapersTitle = true; 
    	
    	try ( Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute( "MATCH (papers:Paper)"
             		+ "-[:REFERENCES]->(references:Paper) WHERE papers.title =~ \".*" + title
             		+ ".*\" RETURN references.title, references.year, references.library, references, papers.title" ) )
    	{
    		while ( result.hasNext() )
    		{
    			Map<String,Object> row = result.next();
                for ( Entry<String,Object> column : row.entrySet() )
                {	
                	attribute = column.getKey();
                	switch(attribute) {
	                	case "references.library":
	            			library = (String) column.getValue();
	            			break;
	                	case "references.title":
	            			refsTitle = (String) column.getValue();
	            			break;
                		case "references.year":
                			year = Long.toString((long) column.getValue());
                			break;  
                		case "papers.title":
                			if(displayPapersTitle)
                				displayTitle = (String) column.getValue();   
                		default:  
                			if(attribute.contains("Node")) {
                				node = column.getValue() + "";
                			}
                	}
                }
                if(displayPapersTitle) {
                	System.out.println("");
                	System.out.println("Paper: " + displayTitle);
                	displayPapersTitle = false;
                }
                rows += node + " :  [T] " + refsTitle + " [L] " + library + " [Y] " + year + "\n";
           }
    	}
    	
    	System.out.println(rows);
    }
    
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        });
    }
    
    private void clearDbPath()
    {
        try
        {
            deleteRecursively(NEO4J_DATABASE);
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }
    
    public static void main(String argv[]) throws Exception
    {	
		String fname, surname, title;
		
    	System.out.println("##### BIBTREK EXAMPLE #####");
        System.out.println("");
        
        @SuppressWarnings("resource")
		Scanner clientInputScanner = new Scanner(System.in);
        
        ExampleApp queryAgent = new ExampleApp("7687");
        
        queryAgent.createExampleDatabase();
        
        while(true) {
            System.out.println("===================================");
            System.out.println("");
            System.out.println("(*): Pick one of the available options:");
            System.out.println("");
            System.out.println("(1): Get all authors.");
            System.out.println("");
            System.out.println("(2): Get all papers.");
            System.out.println("");
            System.out.println("(3): Get all institutions.");
            System.out.println("");
            System.out.println("(4): Get all subjects.");
            System.out.println("");
            System.out.println("(5): Get author's papers.");
            System.out.println("");
            System.out.println("(6): Get paper's authors.");
            System.out.println("");
            System.out.println("(7): Get paper's subjects.");
            System.out.println("");
            System.out.println("(8): Get paper's references.");
            System.out.println("");
            System.out.println("(0): Exits the program.");
            System.out.println("");
            System.out.println("===================================");
            System.out.println("");
            System.out.print("Insert your choice: ");          
            
            String option = clientInputScanner.nextLine();
            
            System.out.println("");
            
            switch(option) {                
                case GET_ALL_AUTHORS:
	                queryAgent.getAuthors();                	
                    break;
                case GET_ALL_PAPERS:
                    queryAgent.getPapers();                
                    break;
                case GET_ALL_INSTITUTIONS:
                    queryAgent.getInstitutions();
                    break;
                case GET_ALL_SUBJECTS:
                    queryAgent.getSubjects();
            		System.out.println("Error: the database is not created!");
            		System.out.println("");
                    break;
                case PAPERS_BY_AUTHOR:
            		System.out.print("Insert the author's first name: ");                                  
                    fname = clientInputScanner.nextLine();
                    System.out.print("Insert the author's surname name: ");
                    System.out.print("");
                    surname = clientInputScanner.nextLine();
                    queryAgent.getAuthorsPapers(fname, surname);
                    break;
                case AUTHORS_BY_PAPER:
            		System.out.print("Insert the paper's title: ");                                  
                    title = clientInputScanner.nextLine();                        
                    queryAgent.getPaperAuthors(title);
                    break;
                case SUBJECTS_BY_PAPER:
            		System.out.print("Insert the paper's title: ");                                  
                    title = clientInputScanner.nextLine();                        
                    queryAgent.getPapersSubjects(title);
                    break;
                case PAPERS_REFERENCES:
            		System.out.print("Insert the paper's title: ");                                  
                    title = clientInputScanner.nextLine();                        
                    queryAgent.getPapersReferences(title);
                    break;
                case EXIT:
                	queryAgent.deleteDatabase();
                    System.exit(0);              
                default:
                    System.out.println("main(): That option does not exist. Please insert another command...");
                    break;
            }                        
        }
    }
}