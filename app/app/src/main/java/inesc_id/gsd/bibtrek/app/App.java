package inesc_id.gsd.bibtrek.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class App 
{	
	private final static String USER_AGENT = "Mozilla/5.0";
	
	private final static String BASE_URL = "https://dblp.org/search";
	
	private final static String SEARCH_AUTHORS = "/author/api?q=";
	private final static String SEARCH_PUBLICATIONS = "/publ/api?q=";
	private final static String AUTHOR = "author=";
	
	private final static String FORMAT_JSON = "&format=json";
	
	private final static String DBLP = "dblp";
	private final static String SEARCH_AUTHOR_BY_NAME = "sabn";	
	private final static String SEARCH_AUTHORS_PUBLICATIONS = "sap";
	private final static String SEARCH_PUBLICATION = "sp";
	
	private final static String EXIT = "exit";	
	
    public static void main( String[] args ) throws Exception
    {	
    	String chosenLibrary;
    	
    	System.out.println("[+]--- BIBTREK ---[+]");
    	
    	Scanner userInput = new Scanner(System.in); 
    	
        while(true) {
        	
            
            System.out.println("[*]--- : Pick one of the options in the menu.");           
            System.out.println("[dblp]--- : Digital Bibliography & Library Project");
            System.out.println("[exit]--- : Exits the program");            
            System.out.print("[*]--- : Type one of the above commands: ");            
            chosenLibrary = userInput.nextLine();                                                 
            System.out.println("[*]---");  
            
            switch(chosenLibrary) {
            	case DBLP:
            		String dblpQuery, dblpOption, authorName, publicationTitle;    
            		boolean exit_flag = false;
                    
                    while(true) {
                    	
                    	System.out.println("[dblp]---");            	            		 
                        System.out.println("[sabn]--- : Searches for an author by name ;");
                        System.out.println("[sap]--- : Searches for an author's publications by name ;");
                        System.out.println("[sp]--- : Searches for a publications by title ;");
                        System.out.print("[*]--- Type one of the above commands: ");
                        
                        dblpOption = userInput.nextLine();
                        
	            		switch(dblpOption) {
	            			case SEARCH_AUTHOR_BY_NAME:
	            				System.out.print("[*]--- Type the author's name: ");	            				
	            				authorName = userInput.nextLine(); 
	            				//compose query object
	            				dblpQuery = BASE_URL + SEARCH_AUTHORS + authorName + FORMAT_JSON;
	            				sendGet(dblpQuery);
	            				break;
	            			case SEARCH_AUTHORS_PUBLICATIONS:
	            				System.out.print("[*]--- Type the author's name: ");	
	            				authorName = userInput.nextLine(); 
	            				//compose query object
	            				dblpQuery = BASE_URL + SEARCH_PUBLICATIONS + AUTHOR + authorName + FORMAT_JSON;
	            				sendGet(dblpQuery);
	            				break;
	            			case SEARCH_PUBLICATION: 
	            				System.out.print("[*]--- Type the publication's title: ");	
	            				publicationTitle = userInput.nextLine(); 
	            				//compose query object
	            				dblpQuery = BASE_URL + SEARCH_PUBLICATIONS + publicationTitle + FORMAT_JSON;
	            				sendGet(dblpQuery);
	            				break;
	            			case EXIT:		                        
		                        System.out.println("[dblp]---");
		                        exit_flag = true;
		                        break;
		                        
	            			default:
	            				break;
	            		}
	            		
	            		if(exit_flag) {
	            			break;
	            		}
                    }            		
            		break;
            	case EXIT:
            		System.exit(0);
            	default:
            		break;
            }                       
        }        
    }
    
    // HTTP GET request
 	private static void sendGet(String url) throws Exception { 		
 		
 		URL obj = new URL(url);
 		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

 		// optional default is GET
 		con.setRequestMethod("GET");

 		//add request header
 		con.setRequestProperty("User-Agent", USER_AGENT);

 		int responseCode = con.getResponseCode();
 		System.out.println("\nSending 'GET' request to URL : " + url);
 		System.out.println("Response Code : " + responseCode);

 		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
 		
 		String inputLine;
 		StringBuffer response = new StringBuffer();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		
 		in.close();

 		//print result
 		System.out.println(response.toString());

 	}
}
