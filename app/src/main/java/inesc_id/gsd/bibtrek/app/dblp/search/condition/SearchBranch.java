package inesc_id.gsd.bibtrek.app.dblp.search.condition;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.dblp.exceptions.DBLPConnectNeo4JException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.dblp.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.dblp.writer.DBLPNoSQLWriter;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;

public class SearchBranch {
	
	private final static String AUTHOR = "author";
	private final static String AUTHORS_PUBLICATIONS = "authors-publications";
	private final static String PUBLICATION = "publication";
	
	private String choice;
	private Scanner userInput;
	
	public SearchBranch(String choice, Scanner userInput) {
		this.choice = choice;
		this.userInput = userInput;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Object[]> choose(ArrayList<Object[]> tupleArrayList) throws SearchBranchException {
		Object[] tuple;
		String publicationChoice;
		boolean exit = false;
		
		ArrayList<Object[]> addedPublications = new ArrayList<Object[]>();
		while(true) {
						
			if(this.choice.equals(AUTHOR)) {
				System.out.println("");			
				System.out.println("(*) Author's Menu");
			} else if(this.choice.equals(AUTHORS_PUBLICATIONS)) {
				System.out.println("");
				System.out.println("(*) Author's Publication Menu");
			} else if(this.choice.equals(PUBLICATION)) {
				System.out.println("");
				System.out.println("(*) Publications Menu");
			}
			System.out.println("");
			System.out.println("(a)");
			System.out.println("- Add all of the available choices and exits the menu ; ");
			System.out.println("");
			System.out.println("(es)");
			System.out.println("- Exits the choice menu and saves the configuration you have chosen ;");
			System.out.println("");
			System.out.println("(e!)");
			System.out.println("- Exits the choice menu without saving ;");
			System.out.println(""); 
			System.out.print("Pick one of the publications to insert in the database: ");	            									
			publicationChoice = this.userInput.nextLine();

			if(StringToIntegerUtils.isInteger(publicationChoice)) {
				addedPublications = chooseIntegerCondition(publicationChoice, tupleArrayList);				
			} else {
				tuple = chooseStringCondition(publicationChoice, tupleArrayList, addedPublications, this.choice);
				exit = (boolean) tuple[0];
				addedPublications = (ArrayList<Object[]>) tuple[1];
				if(exit) {
					return addedPublications;
				}
			}			
		}
	}
	
	private ArrayList<Object[]> chooseIntegerCondition(String publicationChoice, ArrayList<Object[]> tupleArrayList) {
		Object[] tuple;
		ArrayList<Object[]> addedPublications;
		
		addedPublications = new ArrayList<Object[]>();
		
		try {
			tuple = tupleArrayList.get(Integer.valueOf(publicationChoice)-1);	
			if(addedPublications.contains(tuple)) {
				System.out.println("");
				System.out.println("You cannot pick the same publication twice!");
			} else {				
				addedPublications.add(tuple);
			}
			
		} catch(IndexOutOfBoundsException ioobe) {
			System.out.println("");
			System.out.println("You must insert a valid option!");
		} catch(NumberFormatException nfe) {
			System.out.println("");
			System.out.println("You must insert a valid option!");
		}
		
		return addedPublications;
	}
	
	private Object[] chooseStringCondition(String publicationChoice, ArrayList<Object[]> tupleArrayList, ArrayList<Object[]> addedPublications, String choice)  {						
		if(publicationChoice.equals("a")) {
			return new Object[]{true, tupleArrayList};
		} else if(publicationChoice.equals("es") && !addedPublications.isEmpty()) {
			return new Object[]{true, addedPublications};
		} else if(publicationChoice.equals("es") && addedPublications.isEmpty()) {			
			return new Object[]{true, null};
		} else if(publicationChoice.equals("e!")) {			
			return new Object[]{true, null};
		} else {
			System.out.println("");
			System.out.println("You must insert a valid option!");
			return new Object[]{false, addedPublications};
		}
	}	
}
