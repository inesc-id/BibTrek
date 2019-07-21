package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.parsing.PublicationJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.SearchBranch;
import inesc_id.gsd.bibtrek.app.exceptions.PublicationSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchException;

public class PublicationSearch extends Search {
	
	private final static String AUTHORS_PUBLICATIONS = "authors-publications";
	private final static String PUBLICATION = "publication";
	
	private String choice;
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;	
	
	public PublicationSearch(DBLPQueryCreator queryCreator, Scanner userInput, String choice) {		
		this.queryCreator = queryCreator;
		this.userInput = userInput;
		this.choice = choice;
	}

	@Override
	public void search() throws PublicationSearchException {
		String query, userChoice, getRequest;		
		PublicationJSONParser publicationJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		SearchBranch searchBranch;
				
		publicationJSONParser = new PublicationJSONParser();
		searchBranch = new SearchBranch(this.choice, this.userInput);
		
		if(this.choice.equals(AUTHORS_PUBLICATIONS)) {
			System.out.print("Type the author's name: ");
		} else if(this.choice.equals(PUBLICATION)) {
			System.out.print("Type the publication's title: ");
		}
		
		userChoice = this.userInput.nextLine(); 
		query = queryCreator.searchAuthorsPublications(userChoice);
		
		if(this.choice.equals(AUTHORS_PUBLICATIONS)) {
			query = queryCreator.searchAuthorsPublications(userChoice);
		} else if(this.choice.equals(PUBLICATION)) {
			query = queryCreator.searchForPublication(userChoice);
		}
		
		try {
			getRequest = this.executeQuery(query);
		} catch (SearchException se) {
			throw new PublicationSearchException("search(): could not execute the query: \"" + query + "\".");
		}
		publicationJSONParser.setString(getRequest);
		System.out.println("");
		System.out.println("(*) Publications Found: ");
		tupleArrayList = publicationJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {					
			try {
				searchBranch.choose(tupleArrayList);
			} catch (SearchBranchException sbe) {
				throw new PublicationSearchException("search(): could not search for publication.");
			}			
		}
	}

}
