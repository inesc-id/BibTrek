package inesc_id.gsd.bibtrek.app.neo4j;

import java.io.File;
import java.util.ArrayList;

import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPConnectNeo4JException;
import inesc_id.gsd.bibtrek.app.exceptions.WriteNeo4JThreadException;
import inesc_id.gsd.bibtrek.app.utils.TimeUtils;

public class WriteNeo4JThread implements Runnable {
	
	private final static String GET_FILE = "\\/.\\d*_\\d*.nosql";
	
	private final static String GRAPH_PASSWORD = "graph";
	
	private final static String NOSQL_PATH = "src/main/java/inesc_id/gsd/bibtrek/app/nosql/";
	
	private final static String PROCESSED = "processed";
	private final static String PROCESSED_REGEX = ".*\\/processed";
	
	
	
	
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
			} else if (listOfFiles[i].isDirectory() && !listOfFiles[i].toString().matches(PROCESSED_REGEX)) {				
				newPath = new File(path + File.separator + listOfFiles[i].getName());
				newListOfFiles = newPath.listFiles();
				fileNameArrayList = this.recursiveDirectoryList(newPath, newListOfFiles, fileNameArrayList);
			}
		}		
		return fileNameArrayList;
	}

	private void write(ArrayList<String> fileNameArrayList) throws WriteNeo4JThreadException {
		File currentFile;
		String currentPath;
		ConnectNeo4J neo4J;
		boolean result; 
		
		result = false;
					
		try {
			neo4J = new ConnectNeo4J("bolt://localhost:7687", "neo4j", GRAPH_PASSWORD);
			for(String fileName : fileNameArrayList) {
				result = neo4J.execute(fileName);
				currentFile = new File(fileName);
				currentPath = fileName.split(GET_FILE, 2)[0];
				currentFile.renameTo(new File(currentPath 
						+ File.separator + PROCESSED 
						+ File.separator + TimeUtils.getCurrentTimeString() + ".nosql"));
			}
		} catch(DBLPConnectNeo4JException dblpcneo4je) {
			throw new WriteNeo4JThreadException("write(): could not connect to the Neo4J graph database...");
		} catch(ServiceUnavailableException sue) {
			System.out.println("write(): the database seems to be offline...");
		}
		
		if(result) {
			System.out.println("Success! the most recent data were added to the database...");
		} else {
			System.out.println("Failure! could not add the most recent data to the database...");
		}
	}
}



