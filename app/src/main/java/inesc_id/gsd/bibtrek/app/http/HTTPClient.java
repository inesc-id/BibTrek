package inesc_id.gsd.bibtrek.app.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import inesc_id.gsd.bibtrek.app.exceptions.HTTPClientException;

public class HTTPClient {
	
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public HTTPClient() {		
	}
	
    // HTTP GET request
 	public String getRequest(String query) throws HTTPClientException { 		
 		
 		URL url;
 		
		try {
			url = new URL(query);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

	 		// optional default is GET
	 		con.setRequestMethod("GET");

	 		//add request header
	 		con.setRequestProperty("User-Agent", USER_AGENT);

	 		int responseCode = con.getResponseCode();

	 		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	 		
	 		String inputLine;
	 		StringBuffer response = new StringBuffer();

	 		while ((inputLine = in.readLine()) != null) {
	 			response.append(inputLine);
	 		}
	 		
	 		in.close();

	 		//print result
	 		return response.toString();
	 		
		} catch (MalformedURLException murle) {
			throw new HTTPClientException("getRequest(): the url: \"" 
					+ query + "\" is not properly structured.");
		} catch (IOException ioe) {
			throw new HTTPClientException("getRequest(): could not open a connection"
					+ " for the website with url: \"" + query + "\".");
		}

 	}
}
