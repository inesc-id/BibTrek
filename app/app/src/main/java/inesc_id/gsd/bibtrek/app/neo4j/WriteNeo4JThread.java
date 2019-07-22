package inesc_id.gsd.bibtrek.app.neo4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;

import inesc_id.gsd.bibtrek.app.exceptions.WriteNeo4JThreadException;

public class WriteNeo4JThread implements Runnable {
	
	private final static String NOSQL_PATH = "src/main/java/inesc_id/gsd/bibtrek/app/nosql/";
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(5*1000);
				this.write();
			}
		} catch (InterruptedException ie) {
			System.out.println("run(): the writing thread was interrupted!");
		}
	}
	
	private void write() {
		File noSQLPath;
		File[] listOfFiles;
		
		noSQLPath = new File(NOSQL_PATH);
		listOfFiles = noSQLPath.listFiles();
		
		this.recursiveDirectory(noSQLPath, listOfFiles);
	}
	
	private void recursiveDirectory(File path, File[] listOfFiles) {			
		File newPath;
		File[] newListOfFiles;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				//System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				//System.out.println("Directory " + listOfFiles[i].getName());
				newPath = new File(path + File.separator + listOfFiles[i].getName());
				newListOfFiles = newPath.listFiles();
				this.recursiveDirectory(newPath, newListOfFiles);
			}
		}
	}

}



