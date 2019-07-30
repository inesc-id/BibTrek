package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.AuthorSearchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchException;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.SearchBranch;
import inesc_id.gsd.bibtrek.app.dblp.writer.AuthorDBLPNoSQLWriter;

public class AuthorSearch extends Search {
	
	private String choice;
	private DBLPQueryCreator queryCreator; 
	private Scanner userInput;
	
	public AuthorSearch(DBLPQueryCreator queryCreator, Scanner userInput, String choice) {
		this.queryCreator = queryCreator;
		this.userInput = userInput;
		this.choice = choice;
	}

	@Override
	public ArrayList<Object[]> search() throws AuthorSearchException {
		String query, authorName, getRequest;		
		AuthorJSONParser authorJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		SearchBranch searchBranch;
		ArrayList<Object[]> authorsList = null;
				
		authorJSONParser = new AuthorJSONParser();
		searchBranch = new SearchBranch(this.choice, this.userInput);
		System.out.print("Type the author's name: ");	            									
		authorName = this.userInput.nextLine(); 
		query = this.queryCreator.searchAuthorByName(authorName);		
		try {
			getRequest = this.executeQuery(query);
		} catch (SearchException se) {
			throw new AuthorSearchException("search(): could not execute the query: \"" + query + "\".");
		}
		authorJSONParser.setString(getRequest);
		System.out.println("");
		System.out.println("(*) Authors Found: ");
		tupleArrayList = authorJSONParser.parseString();
		if(!tupleArrayList.isEmpty()) {		
			try {
				authorsList = searchBranch.choose(tupleArrayList);					
			} catch (SearchBranchException sbe) {
				throw new AuthorSearchException("search(): could not search for author.");
			} 
		} 
		return authorsList;
	}			
	
}
