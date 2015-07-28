package com.md.trafficscotlandcw;

import java.io.IOException;
import java.util.LinkedList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LoadingThread extends Thread {

	
	RSSReader rssReader = new RSSReader();
	XMLparser xmlParser = new XMLparser();
	private String plannedRoadworks = "http://www.trafficscotland.org/rss/feeds/plannedroadworks.aspx";
	private String currentRoadworks =  "http://www.trafficscotland.org/rss/feeds/roadworks.aspx";;
	private String listingURL;
	LinkedList<Event> plannedEventList;
	LinkedList<Event> currentEventList;
	private String XML = null;
	
	
	public void run(){
		
		super.run();
		//findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
		//Log.e("THREAD TAG", "TEST INIT");
		//RefreshRoadworks();
		LoadPlannedRoadworks();
		/*try {
			//Log.e("TESTAG", "try hit");
			XML = rssReader.readRSS(listingURL);
			if(XML ==""){Log.e("XML NOT FOUND", "CANNOT CONNECT");}
			// Log.e("XMLTESTTAG", XML.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xmlParser.parseXML(XML);
		plannedEventList = xmlParser.getEventList();
	
		
		
		Log.e("THREAD TAG", Integer.toString(plannedEventList.size()) + " Planned Road Works Loaded");*/
	}
	public LinkedList<Event> GetEventList(){
		return plannedEventList;
	}
	private void LoadPlannedRoadworks(){
		
		listingURL = plannedRoadworks;
		try {
			//Log.e("TESTAG", "try hit");
			XML = rssReader.readRSS(listingURL);
			if(XML ==""){Log.e("XML NOT FOUND", "CANNOT CONNECT");}
			// Log.e("XMLTESTTAG", XML.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xmlParser.parseXML(XML);
		plannedEventList = xmlParser.getEventList();
	
		
		
		Log.e("THREAD TAG", Integer.toString(plannedEventList.size()) + " Planned Road Works Loaded");
	}
	private void LoadCurrentRoadworks(){
		listingURL = currentRoadworks;
		try {
			//Log.e("TESTAG", "try hit");
			XML = rssReader.readRSS(listingURL);
			if(XML ==""){Log.e("XML NOT FOUND", "CANNOT CONNECT");}
			// Log.e("XMLTESTTAG", XML.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xmlParser.parseXML(XML);
		currentEventList = xmlParser.getEventList();
	
		
		
		Log.e("THREAD TAG", Integer.toString(plannedEventList.size()) + " Planned Road Works Loaded");
	}
	public void RefreshRoadworks(){
		LoadCurrentRoadworks();
		LoadPlannedRoadworks();
	}
	
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			Log.e("MyTag","In  handler" + msg.what);
			
			if (msg.what == 0)
			{
				//invalidate();
			}
			else
				if (msg.what == 1)
				{
				run();
				}
				else
					if (msg.what == 2)
					{
						//run = true;
						//invalidate();
					}
		}
		
		
	};
	
	public Handler getHandler()
		{
			Log.e("MyTag","In gethandler");
			return handler;
		}
	//private Handler MyThreadHandler = new Handler();
	
}
