package inesc_id.gsd.bibtrek.app.dblp.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import inesc_id.gsd.bibtrek.app.exceptions.DBLPNoSQLWriterException;
import inesc_id.gsd.bibtrek.app.utils.TimeUtils;

public class PublicationsDBLPNoSQLWriter extends DBLPNoSQLWriter{
	
	public PublicationsDBLPNoSQLWriter(ArrayList<Object[]> listToWrite) {
		super(listToWrite);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void writeToFile() throws DBLPNoSQLWriterException {
		BufferedWriter bufferedWriter;
		Iterator<Object[]> iter;
		Object[] tuple;
		ArrayList<String> authorsArrayList;
		String author = "no_author", title, url, year, venue, type, key;
		
		iter = listToWrite.iterator();
		authorsArrayList = new ArrayList<String>();
		
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(getCurrentFile(), true));
			
			while(iter.hasNext()) {
				tuple = iter.next();
				
				bufferedWriter.append(TimeUtils.getCurrentTimeString() + ": ");				
				
				title = (String) tuple[1];
				url = (String) tuple[2];
				year = (String) tuple[3];
				venue = (String) tuple[4];
				type = (String) tuple[5];
				key = (String) tuple[6];				
				
				bufferedWriter.append("CREATE (" + title.replaceAll("[.:\\s]", "")
						+ ":Publication {title:\"" + title 
						+ "\", url:\"" + url + "\", year:\"" + year 
						+ "\", venue:\"" + venue + "\", type:\"" + type 
						+ "\", key:\"" + key + "})");				
				authorsArrayList = (ArrayList<String>) tuple[0];
				for(String authorIter : authorsArrayList) {
					author = authorIter;					
					
					bufferedWriter.append(" ");
					
					bufferedWriter.append("CREATE (" 
							+ author.replaceAll("\\W", "") + ":Author {name:\"" + author + "\"})");
					
					bufferedWriter.append(" ");
					
					bufferedWriter.append("CREATE (" + author.replaceAll("\\W", "")
					+ ")-[:WROTE]->(" + title.replaceAll("[.:\\s]", "") + ")");
				}
				
				bufferedWriter.append("\n");
						
			}			
			bufferedWriter.close();
		} catch(IOException ioe) {
			throw new DBLPNoSQLWriterException("writeToFile(): could not write to the current state.");
		}
	}
}
