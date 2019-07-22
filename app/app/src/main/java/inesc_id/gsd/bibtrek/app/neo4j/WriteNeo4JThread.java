package inesc_id.gsd.bibtrek.app.neo4j;

import java.io.File;
import java.util.ArrayList;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPConnectNeo4JException;
import inesc_id.gsd.bibtrek.app.exceptions.WriteNeo4JThreadException;

public class WriteNeo4JThread implements Runnable {
	
	private final static String NOSQL_PATH = "src/main/java/inesc_id/gsd/bibtrek/app/nosql/";
	private final static String GRAPH_PASSWORD = "graph";
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(5*1000);
				this.execute();
			}
		} catch (InterruptedException ie) {
			System.out.println("run(): the writing thread was interrupted!");
		} catch (WriteNeo4JThreadException wneo4jte) {
			System.out.println(wneo4jte.getMessage());
		}
	}
	
	private void execute() throws WriteNeo4JThreadException {
		File noSQLPath;
		File[] listOfFiles;
		ArrayList<String> fileNameArrayList = new ArrayList<String>();
		
		noSQLPath = new File(NOSQL_PATH);
		listOfFiles = noSQLPath.listFiles();
		
		fileNameArrayList = this.recursiveDirectoryList(noSQLPath, listOfFiles, fileNameArrayList);
		this.write(fileNameArrayList);				
	}
	
	private ArrayList<String> recursiveDirectoryList(File path, File[] listOfFiles, ArrayList<String> fileNameArrayList) {
		String fileName;
		File newPath;
		File[] newListOfFiles;
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileName = path + File.separator + listOfFiles[i].getName();
				fileNameArrayList.add(fileName);				
			} else if (listOfFiles[i].isDirectory()) {
				newPath = new File(path + File.separator + listOfFiles[i].getName());
				newListOfFiles = newPath.listFiles();
				fileNameArrayList = this.recursiveDirectoryList(newPath, newListOfFiles, fileNameArrayList);
			}
		}		
		return fileNameArrayList;
	}

	@SuppressWarnings({ "resource"})
	private void write(ArrayList<String> fileNameArrayList) throws WriteNeo4JThreadException {
		ConnectNeo4J neo4J;
		boolean result; 
		
		result = false;
		neo4J = new ConnectNeo4J("bolt://localhost:7687", "neo4j", GRAPH_PASSWORD);
		try {
			for(String fileName : fileNameArrayList) {
				result = neo4J.execute(fileName);
			}
		} catch(DBLPConnectNeo4JException e) {
			throw new WriteNeo4JThreadException("write(): could not connect to the Neo4J graph database...");
		}
		
		if(result) {
			System.out.println("Success! the most recent data were added to the database...");
		} else {
			System.out.println("Failure! could not add the most recent data to the database...");
		}
	}
}



