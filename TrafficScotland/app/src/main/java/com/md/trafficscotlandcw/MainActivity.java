package com.md.trafficscotlandcw;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import com.md.trafficscotlandcw.R.layout;

//import org.me.myandroidstuff.R;

//import org.me.myandroidstuff.R;

import android.R.integer;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener {

	//Current Incidents http://www.trafficscotland.org/rss/feeds/currentincidents.aspx  
	//Road works http://www.trafficscotland.org/rss/feeds/roadworks.aspx  
	//Planned Road works http://www.trafficscotland.org/rss/feeds/plannedroadworks.aspx
	private String listingURL = "http://www.trafficscotland.org/rss/feeds/roadworks.aspx";
	//private String listingURL = "http://www.trafficscotland.org/rss/feeds/plannedroadworks.aspx";
	private Boolean bCurrentRoadworks;
	private Boolean bPlannedRoadworks;
	
	private LoadAnimation aView;
	private LinearLayout mainview;
	RSSReader rssReader = new RSSReader();
	XMLparser xmlParser = new XMLparser();
	LinkedList<Event> eventList;
	LinkedList<Event> currentRoadWorks;
	LinkedList<Event> titles;
	ListView eventViewList;
	Date selectedDate;
	private Button btnDate;
	private Button btnViewAll;
	private Button btnRefresh;

	TextView txtCurrentDate;
	Spinner spRoadworks;
	
	private Boolean bViewAll = false;
	final int Date_Dialog_ID = 0;
	int cDay, cMonth, cYear;
	Calendar cDate;
	int sDay,sMonth,sYear;
	
	private LoadingThread myLoadingThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		LoadInitWorks();
		SetUpViews();

		//Thread thread = new Thread(ldAn);
	    //thread.start();
		//Thread
		myLoadingThread = new LoadingThread();
		myLoadingThread.start();
		Log.e("CRASHTAG", "HERE");
	}
	public void LoadInitWorks(){
		String XML = null;

		try {
			Log.e("TESTAG", "try hit");
			XML = rssReader.readRSS(listingURL);
			if(XML ==""){Log.e("XML NOT FOUND", "CANNOT CONNECT");}
			 Log.e("XMLTESTTAG","RETURNED");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xmlParser.parseXML(XML);
		currentRoadWorks = xmlParser.getEventList();
		eventList = currentRoadWorks;
		
	}
	public void SetUpViews(){
		
		setContentView(R.layout.main);
		
		btnDate = (Button) findViewById(R.id.btnDate);
		btnDate.setOnClickListener(this);
		btnViewAll = (Button)findViewById(R.id.btnViewAll);
		btnViewAll.setOnClickListener(this);
		btnRefresh = (Button)findViewById(R.id.btnRefresh);
		btnRefresh.setOnClickListener(this);
		//Spinner for stream selection
		spRoadworks = (Spinner) findViewById(R.id.road_work_spinner);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.road_work_choice_array,
				android.R.layout.simple_list_item_1);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spRoadworks.setAdapter(adapter);
		spRoadworks.setOnItemSelectedListener(this);

		
		//Calendar/Date data
		cDate=Calendar.getInstance();
		cDay=cDate.get(Calendar.DAY_OF_MONTH);
		cMonth=cDate.get(Calendar.MONTH);
		cYear=cDate.get(Calendar.YEAR);
		sDay = cDay;
		sMonth = cMonth;
		sYear = cYear;
		//Log.e("DATE TEST", Integer.toString(cDate.get(Calendar.YEAR)));
		
		txtCurrentDate = (TextView)findViewById(R.id.txtSelectedDate);
		updateDateDisplay(sYear, sMonth, sDay);
		
		eventViewList = (ListView) findViewById(R.id.road_event_list);
		
		eventViewList.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View View, int position,
					long id) {
				Log.e("CLICK CHECK", Integer.toString(position));
				// TODO Auto-generated method stub
				  Toast.makeText(getApplicationContext(),
					      "Click ListItem Number " + position, Toast.LENGTH_LONG)
					      .show();
				  String item = (String) parent.getItemAtPosition(position);
				  Event choice = titles.get(position);
				  item = titles.get(position).getTitle();
				  showcustomDialog(choice);
				  //showcustomDialog(item);
			}
		});

		populateList();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		 Log.e("CONFIGTAG", "Orientation changed");
		super.onConfigurationChanged(newConfig);
		SetUpViews();
		Configuration c = getResources().getConfiguration();
		
		
		if(c.orientation == Configuration.ORIENTATION_PORTRAIT){
		//	setContentView(R.layout.main);
			Toast toast = Toast.makeText(this, "Portrait Mode", Toast.LENGTH_SHORT);
			toast.show();
		}
		else if(c.orientation == Configuration.ORIENTATION_LANDSCAPE){
			//setContentView(R.layout.main);
			Toast toast = Toast.makeText(this, "Landscape Mode", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void populateList() {
		//findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
		//Get Current Date
		Date currentDate = new Date(sYear - 1900, sMonth, sDay);
		titles = new  LinkedList<Event>();
		int i = 0;
		for (Event anEvent : eventList) {
	
			
			String check = "NO";

			
			long diff = anEvent.getEndDate().getTime() - currentDate.getTime();
			int iDiff = (int) (diff/1000/60/60/24);
			//Log.e("DAYS TILL TAG", Integer.toString(iDiff));
			
			anEvent.setDaysToComplete(iDiff);
			//Log.e("GET DAYS BETWEEN", Integer.toString(anEvent.getDaysToComplete()));
			
			//Log.e
			
			if(bViewAll){
				titles.add(anEvent);
			}
			else {
				if( currentDate.before(anEvent.getEndDate()) && currentDate.after(anEvent.getStartDate()) || 
						currentDate.equals(anEvent.getStartDate())|| currentDate.equals(anEvent.getEndDate())){
					//Log.e("DATETAG", "on current date " + anEvent.getTitle());
					check = "YES";
					titles.add(anEvent);
					i++;
				}else{
					check ="NO";
					//Log.e("DATETAG", "--Not on current date " + anEvent.getTitle());
				}
			}
	
			//findViewById(R.id.loadingPanel).setVisibility(View.GONE);
		}
		
		eventViewList.setAdapter(new MyListAdapter(this, titles));
		//Log.e("CRASHTAG", "AdapterSet");
	}

	@Override
	public void onClick(View v) {
		//Log.e("CHERCK", Integer.toString(v.getId()));
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		
		case R.id.btnDate:
		
			showDialog(Date_Dialog_ID);
			// showDateDialog(v);
			break;
		case R.id.btnViewAll:
			//View All RoadWorks
			//Message messageToThread = new Message();				
			//messageToThread.what = 1;
			//aView.getHandler().sendMessage(messageToThread);
			
			if(!bViewAll){
				bViewAll = true;
				btnViewAll.setText("View Date");
			}
			else{
				bViewAll = false;
				btnViewAll.setText("View All");
			}
			populateList();
			break;
		case R.id.btnRefresh:
			
			Log.e("MyTag","In start button handler");
			Message messageToThread = new Message();				
			messageToThread.what = 1;
			
			myLoadingThread.getHandler().sendMessage(messageToThread);
			Log.e("MyTag","CRASH TEST");
			//aview.getHandler().sendMessage(messageToThread);
			
			//myLoadingThread.RefreshRoadworks();
			//myLoadingThread.start();
			// showDateDialog(v);
			break;
		
		}

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Date_Dialog_ID:
			return new DatePickerDialog(this, onDateSet, cYear, cMonth, cDay);
		}
		return null;
	}

	private OnDateSetListener onDateSet = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			Log.e("DIALOG TEST", "CHECK");
			//System.out.println("2");
			sYear = year;
			sMonth = monthOfYear;
			sDay = dayOfMonth;
			updateDateDisplay(sYear, sMonth, sDay);
			populateList();
		}
	};
	
	private void updateDateDisplay(int year,int month,int date) {
		// TODO Auto-generated method stub
		txtCurrentDate.setText(date+"-"+(month+1)+"-"+year);
		}
	
	//private void showcustomDialog(String info)
	private void showcustomDialog(Event event)
	{
		// Custom dialog setup
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_info);
		dialog.setTitle("    Roadwork Details");
		
		SimpleDateFormat df = new SimpleDateFormat("E dd.MM.yyyy");
		 
		// Set the custom dialog components as a TextView and Button component
		TextView text = (TextView) dialog.findViewById(R.id.infoView);
		text.setText(event.getTitle());
		
		String delay_info = "None Available";
		
		if(event.getDelayInfo() != "")
		{
			delay_info = event.getDelayInfo();
		}
		
		TextView txtDelayInfo = (TextView) dialog.findViewById(R.id.txtDelayInfo);
		txtDelayInfo.setText(delay_info);
		
		//Log.e("DIALOG CHECK", "Date: " + Integer.toString(event.getStartDate().getDay()) );
		TextView txtStartDate = (TextView) dialog.findViewById(R.id.txtStartDate);
		Date start_date = event.getStartDate();
		txtStartDate.setText(df.format(start_date));
		
		TextView txtEndDate = (TextView) dialog.findViewById(R.id.txtEndDate);	
		Date end_date = event.getEndDate();
		txtEndDate.setText(df.format(end_date));
		
		TextView txtMoreInfo = (TextView) dialog.findViewById(R.id.txtMoreInfo);		
		txtMoreInfo.setText(event.getLink());

		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);			
		dialogButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
					dialog.dismiss();
					
			}
		});
		 
		dialog.show();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		String selected = "";
		// TODO Auto-generated method stub
		Log.e("CLICK TAG", Integer.toString(arg2));
		if(arg2 == 0){
			bCurrentRoadworks = true;
			bPlannedRoadworks = false;
			
			selected = "CurrentRoadWorks";
			eventList = currentRoadWorks;
			populateList();
		}
		else if(arg2 == 1){
			bCurrentRoadworks = false;
			bPlannedRoadworks = true;

			selected = "PlannedRoadWorks";
			eventList = myLoadingThread.GetEventList();
			populateList();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
