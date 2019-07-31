package inesc_id.gsd.bibtrek.app.dblp.search.condition;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.DBLPQueryCreator;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.AuthorsPublicationSearchBranchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.DBLPClassException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.PublicationSearchException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.dblp.search.PublicationSearch;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;

public class AuthorsPublicationsSearchBranch {
	
	private final static String PUBLICATION = "publication";
	
	private String choice;
	private DBLPQueryCreator queryCreator;
	private Scanner userInput;
	
	public AuthorsPublicationsSearchBranch(DBLPQueryCreator queryCreator, Scanner userInput, String choice) {
		this.choice = choice;
		this.queryCreator = queryCreator;
		this.userInput = userInput;
	}
	
	public void choose(ArrayList<Object[]> tupleArrayList) throws AuthorsPublicationSearchBranchException {
		String authorName, continueChoice;
		PublicationSearch publicationSearch;
		ArrayList<Object[]> publicationsList = null;
		DBLPNoSQLWriter dblpNoSQLWriter;
		
		publicationSearch = new PublicationSearch(this.queryCreator, this.userInput, this.choice);
		
		try {
			for(int i = 0; i<tupleArrayList.size(); i++) {
				authorName = (String) tupleArrayList.get(i)[0];
				System.out.println(authorName);
				if(i>=1) {
					while(true) {
						boolean breakCycle = false;
						System.out.println("");
						System.out.print("(*) Do you want to fetch the publications of the other authors you have fetched? Type [Y|N]: ");											
						continueChoice = this.userInput.nextLine();
						switch(continueChoice) {
							case "Y":							
								publicationsList = publicationSearch.search(authorName);
								breakCycle = true;
								break;
							case "N":									
								return;
							default:
								System.out.println("You must insert a valid choice...");
						}
						if(breakCycle)
							break;
					}
				} else {
					publicationsList = publicationSearch.search(authorName);
				}
				
				if(publicationsList!=null) {
					dblpNoSQLWriter = WriteConditionFactory.getWriter(publicationsList, PUBLICATION);
					dblpNoSQLWriter.writeToFile();
				}
			}
		} catch(PublicationSearchException pse) {
			throw new AuthorsPublicationSearchBranchException("choose(): could not search for publication.");
		} catch (DBLPNoSQLWriterException dblpnosqlwe) {
			throw new AuthorsPublicationSearchBranchException("choose(): could not invoke the write operation.");
		} 
	}
	
}
