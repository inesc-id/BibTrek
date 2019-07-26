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
	
	private String choice;
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;	
	
	public PublicationSearch(DBLPQueryCreator queryCreator, Scanner userInput, String choice) {		
		this.queryCreator = queryCreator;
		this.userInput = userInput;
		this.choice = choice;
	}

	@Override
	public ArrayList<Object[]> search() throws PublicationSearchException {
		String query, userChoice, getRequest;		
		PublicationJSONParser publicationJSONParser;	
		ArrayList<Object[]> tupleArrayList = null;
		SearchBranch searchBranch;
				
		publicationJSONParser = new PublicationJSONParser();
		searchBranch = new SearchBranch(this.choice, this.userInput);
		
		System.out.print("Type the publication's title: ");
				
		userChoice = this.userInput.nextLine(); 
				
		query = queryCreator.searchForPublication(userChoice);		
		
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
		
		return tupleArrayList;
	}
	
	protected void search(String title) throws PublicationSearchException {
		String query, userChoice, getRequest;		
		PublicationJSONParser publicationJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		SearchBranch searchBranch;
				
		publicationJSONParser = new PublicationJSONParser();
		searchBranch = new SearchBranch(this.choice, this.userInput);
			 			
		query = queryCreator.searchForPublication(title);		
		
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
