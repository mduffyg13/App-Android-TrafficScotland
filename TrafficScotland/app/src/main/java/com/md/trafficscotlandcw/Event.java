package com.md.trafficscotlandcw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Debug;
import android.util.Log;

public class Event {
	
	private String title;
	private String description;
	private String link;
	private String georssPoint;
	private String author;
	private String comments;
	private String pubDate;
	private String more_info = "http://tscot.org/03c456292";
	
	private Date startDate;
	private Date endDate;
	private String delayInformation = "";
	private int daysToComplete = 0;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description)  {
		
		String[] info = description.split("<br />");
		
		//Log.e("FULLINFO", description.toString());
		
		String endDateStr = "";
		String startDateStr = "";
		
		startDateStr = info[0];
		
		if(info.length > 1)
			endDateStr = info[1];
		
		if(info.length>2){
			
			String[] di = info[2].split(": ");
			
			if(di.length>1)
			delayInformation = di[1];
		}
		
		startDateStr = startDateStr.substring(12);
		startDateStr.trim();
		//startDateStr = startDateStr.
		endDateStr = endDateStr.substring(10);
		Date startDate = null;
		Date endDate = null;
		
		try {
			startDate = new SimpleDateFormat("EE, dd MMMM yyyy - kk:mm").parse(startDateStr);
			endDate = new SimpleDateFormat("EE, dd MMMM yyyy - kk:mm").parse(endDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.e("DATESTRING", startDateStr.toString());
		
		//if(startDate != null){
		//Log.e("STARTDATECHECK", startDate.toString());
		//Log.e("ENDDATECHECK", endDate.toString());
		//}
		
		//Log.e("DELAYINFO", delayInformation.toString());

		
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getGeorssPoint() {
		return georssPoint;
	}
	public void setGeorssPoint(String georssPoint) {
		this.georssPoint = georssPoint;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public Date getStartDate(){
		return startDate;
	}
	public Date getEndDate(){
		return endDate;
	}
	public String getDelayInfo(){
		return delayInformation;
	}
	public String getDateString(){
		return startDate.toString() + " " + endDate.toString();
	}
	public void setDaysToComplete(int daysIn){
		//Log.e("EVENT CLASS TAG", "SETDAYSTOCOMPLETE: " + Integer.toString(daysIn));
		this.daysToComplete = daysIn;
	}
	public int getDaysToComplete(){
		return this.daysToComplete;
	}
	public void setMoreInfo(String link){
		more_info = link;
	}
	public String GetMoreInfo(){
		return more_info;
	}
}
