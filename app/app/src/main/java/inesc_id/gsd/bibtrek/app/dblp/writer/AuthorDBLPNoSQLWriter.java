package inesc_id.gsd.bibtrek.app.dblp.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.neo4j.DBLPConnectNeo4J;
import inesc_id.gsd.bibtrek.app.utils.TimeUtils;

public class AuthorDBLPNoSQLWriter extends DBLPNoSQLWriter{
	
	public AuthorDBLPNoSQLWriter(ArrayList<Object[]> listToWrite) {
		super(listToWrite);
	}
	
	public synchronized void writeToFile() throws DBLPNoSQLWriterException {		
		DBLPConnectNeo4J neo4J;
		BufferedWriter bufferedWriter;
		Iterator<Object[]> iter;
		Object[] tuple;
		String author, url;
		
		iter = listToWrite.iterator();
		
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(getCurrentFile(), true));
			
			while(iter.hasNext()) {
				tuple = iter.next();
				author = (String) tuple[0];
				url = (String) tuple[1];
				bufferedWriter.append(TimeUtils.getCurrentTimeString() + ": ");
				bufferedWriter.append("CREATE (" 
						+ author.replaceAll("\\W", "") + ":Author {name:\"" 
						+ author + "\", url:\"" + url + "\"})");
				
				bufferedWriter.append("\n");												
			}			
			bufferedWriter.close();
		} catch (IOException ioe) {
			throw new DBLPNoSQLWriterException("writeToFile(): could not write to the current state.");
		}
		
	}
}
