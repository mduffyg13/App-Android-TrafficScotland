package com.md.trafficscotlandcw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class RSSReader {

	public String readRSS(String urlString)throws IOException{
		
			String result = "";
			InputStream anInStream = null;
			int response = -1;
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			
			if(!(conn instanceof HttpURLConnection))
				throw new IOException("Not an HTTP connection");
			
			try{
				Log.e("XMLTAG", "TRY2HIT");
	    		// Set up the connection
	    		HttpURLConnection httpConn = (HttpURLConnection) conn;
	    		httpConn.setAllowUserInteraction(false);
	    		httpConn.setInstanceFollowRedirects(true);
	    		httpConn.setRequestMethod("GET");
	    		httpConn.setConnectTimeout(5000);
	    		Log.e("XMLTAG", httpConn.toString());
	    		//Error Thrown
	    		httpConn.connect();
	    		Log.e("XMLTAG", "CatchError");
	    		response = httpConn.getResponseCode();
	    		
	    		
	    		if (response == HttpURLConnection.HTTP_OK)
	    		{
	    			Log.e("XMLTAG","Connection Found");
	    			// Connection is Ok so open a reader 
	    			anInStream = httpConn.getInputStream();
	    			InputStreamReader in= new InputStreamReader(anInStream);
	    			BufferedReader bin= new BufferedReader(in);
	    			
	    			// Read data into a string
	    			String line = new String();
	    			while (( (line = bin.readLine())) != null)
	    			{
	    				result = result + line;
	    			}
	    		}
	    		else{
	    			Log.e("XMLTAG","Connection Not Found");
	    		}
				
			}catch(IOException ioe){
				throw new IOException("Error connecting");
			}
			
			
		return result;
		}
}
