package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorsPublicationsJSONParser;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorByNameSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorsPublicationByNameSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchException;


public class AuthorsPublicationByNameSearch extends Search {
	
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;
	
	public AuthorsPublicationByNameSearch(DBLPQueryCreator queryCreator, Scanner userInput) {
		this.queryCreator = queryCreator;
		this.userInput = userInput;
	}

	@Override
	public void search() throws AuthorsPublicationByNameSearchException {
		String query, authorName, getRequest;		
		AuthorsPublicationsJSONParser authorsPublicationsJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		
				
		authorsPublicationsJSONParser = new AuthorsPublicationsJSONParser();					
		System.out.print("Type the author's name: ");	            									
		authorName = this.userInput.nextLine(); 
		query = queryCreator.searchAuthorsPublications(authorName);
		try {
			getRequest = this.executeQuery(query);
		} catch (SearchException se) {
			throw new AuthorsPublicationByNameSearchException("search(): could not execute the query: \"" + query + "\".");
		}
		System.out.println(getRequest);		
		authorsPublicationsJSONParser.setString(getRequest);		
		tupleArrayList = authorsPublicationsJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {
			//chooseAuthorsToAdd(tupleArrayList);
		}
	}
	
}
