package inesc_id.gsd.bibtrek.app.dblp.search.condition;

import java.util.ArrayList;
import java.util.Scanner;

import inesc_id.gsd.bibtrek.app.exceptions.SearchBranchException;
import inesc_id.gsd.bibtrek.app.utils.StringToIntegerUtils;

public class AuthorsPublicationsSearchBranch {
	
	private Scanner userInput;
	
	public AuthorsPublicationsSearchBranch(Scanner userInput) {
		this.userInput = userInput;
	}
	
	public ArrayList<Object[]> choose(ArrayList<Object[]> tupleArrayList) throws SearchBranchException {
		ArrayList<Object[]> chosenAuthors;
		String authorChoice;
		Object[] exitTuple;
		boolean exit = false;
		
		ArrayList<Object[]> addedAuthors = new ArrayList<Object[]>();
		while(true) {
							
			System.out.println("(*) Author's Menu");			
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
			authorChoice = this.userInput.nextLine();

			if(StringToIntegerUtils.isInteger(authorChoice)) {
				addedAuthors = chooseIntegerCondition(authorChoice, tupleArrayList);				
			} else {
				exitTuple = chooseStringCondition(authorChoice, tupleArrayList, addedAuthors);
				exit = (boolean) exitTuple[0];
				chosenAuthors = (ArrayList<Object[]>) exitTuple[1];
				if(exit) {
					return chosenAuthors;
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
	
	private Object[] chooseStringCondition(String authorChoice, ArrayList<Object[]> tupleArrayList, ArrayList<Object[]> addedPublications) throws SearchBranchException  {				
		
		if(authorChoice.equals("a")) {			
			return new Object[] {true, tupleArrayList};
		} else if(authorChoice.equals("es") && !addedPublications.isEmpty()) {		
			return new Object[] {true, addedPublications};
		} else if(authorChoice.equals("e!")) {			
			return new Object[] {true, null};
		} else {
			System.out.println("");
			System.out.println("You must insert a valid option!");
			return new Object[] {false, null};
		}
		
	}	
}
