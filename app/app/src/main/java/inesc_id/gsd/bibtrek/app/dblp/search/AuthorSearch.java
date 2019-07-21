package inesc_id.gsd.bibtrek.app.dblp.search;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.parsing.AuthorJSONParser;
import inesc_id.gsd.bibtrek.app.dblp.search.condition.SearchBranch;
import inesc_id.gsd.bibtrek.app.dblp.writer.AuthorDBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.exceptions.AuthorSearchException;
import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.exceptions.SearchException;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;

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
	public void search() throws AuthorSearchException {
		String query, authorName, getRequest;		
		AuthorJSONParser authorJSONParser;	
		ArrayList<Object[]> tupleArrayList;
		SearchBranch searchBranch;
				
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
				searchBranch.choose(tupleArrayList);
			} catch (SearchBranchException sbe) {
				throw new AuthorSearchException("search(): could not search for author.");
			}
		}		
	}			
	
}
