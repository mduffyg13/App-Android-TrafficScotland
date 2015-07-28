package com.md.trafficscotlandcw;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Debug;
import android.util.Log;


public class XMLparser {
	
LinkedList<Event> aList;
public LinkedList<Event> getEventList(){
	return aList;
}
Event anEvent;

public void parseXML(String dataToParse ){
	
	aList = new LinkedList<Event>();
	
	try
	{
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		xpp.setInput( new StringReader ( dataToParse ) );
		int eventType = xpp.getEventType();
		int titleCount = 0;
		while (eventType != XmlPullParser.END_DOCUMENT) 
		{
				// Found a start tag 
				if(eventType == XmlPullParser.START_TAG) 
				{
					//Log.e("PARSE TEST", xpp.getName());
					
					
					
						// Check which Tag we have
						if (xpp.getName().equalsIgnoreCase("title") && titleCount != 2)
	            		{
							//Log.e("PARSE TEST", Integer.toString(titleCount));
							anEvent = new Event();
							// Now just get the associated text 
	            			String temp = xpp.nextText();
	            			
	            			anEvent.setTitle(temp);
	            			
	            			
	            		}					
						else						
							// Check which Tag we have
							if (xpp.getName().equalsIgnoreCase("description") && titleCount != 3)
							{
								// Now just get the associated text 
								String temp = xpp.nextText();
								anEvent.setDescription(temp);
								
						
							}
							else							
								// Check which Tag we have
								if (xpp.getName().equalsIgnoreCase("link") && titleCount != 4)
								{
									// Now just get the associated text 
									String temp = xpp.nextText();
									anEvent.setLink(temp);
									aList.add(anEvent);
								
								}
					titleCount++;
				}
					
			// Get the next event	
			eventType = xpp.next();
			
		}
		
	}
		catch (XmlPullParserException ae1)
		{
			Log.e("MyTag","Parsing error" + ae1.toString());
		}
		catch (IOException ae1)
		{
			Log.e("MyTag","IO error during parsing");
		}
	
	Log.e("MyTag","End document");

	
}	
	

	
}