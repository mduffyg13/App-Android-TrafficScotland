package com.md.trafficscotlandcw;

import java.util.LinkedList;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter  {

	Context context;
	String[] data;
    LinkedList<Event> eventData;
    private static LayoutInflater inflater = null;
    GradientDrawable gdRed;
    GradientDrawable gdAmber;
    GradientDrawable gdGreen;
    
    public MyListAdapter(Context context, String[] data){
    	
    	this.context = context;
    	this.data = data;	
    	inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public MyListAdapter(Context context, LinkedList<Event> eventData){
    	//Log.e("CRASHTAG", "CONSTRUCTOR");
    	this.context = context;
    	//Log.e("CRASHTAG", "CONTEXT");
    	this.eventData = eventData;	
    	//Log.e("CRASHTAG", "EventData");
    	inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	//Log.e("CRASHTAG", "Inflater");
    	 gdRed = new GradientDrawable(
 	            GradientDrawable.Orientation.TOP_BOTTOM,
 	            new int[] {0xffec1613 , 0xffd81621, 0xffC51630});
 	    gdRed.setCornerRadius(0f);
 	    
 	   gdAmber = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {0xffEBA53D, 0xffE0A130, 0xffD69D24});
	    gdAmber.setCornerRadius(0f);
	    
	    gdGreen = new GradientDrawable(
 	            GradientDrawable.Orientation.TOP_BOTTOM,
 	            new int[] {0xff47D926, 0xff3ABC24, 0xff2E9F23});
 	    gdGreen.setCornerRadius(0f);
    }
    
	@Override
	/*public int getCount() {
		// TODO Auto-generated method stub
		
			return data.length;
	}*/
	public int getCount() {
		// TODO Auto-generated method stub
		return eventData.size();
	}

	/*@Override
	public Object getItem(int position) {
		Log.e("CLICK CHECK", Integer.toString(position));
		// TODO Auto-generated method stub		
		return data[position];
			
		
	
	}*/
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return eventData.get(position).toString();
	}
	
	public Event getEvent(int position){
		return eventData.get(position);
	};

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.e("CLICK CHECK", Integer.toString(position));
		// TODO Auto-generated method stub
		View vi = convertView;
	//	Log.e("ADAPTER TEST", Integer.toString(position));
		
       
		if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        
		
			/*TextView text;
			text = (TextView) vi.findViewById(R.id.text1);
	    	text.setText(getItem(position).toString());
	    	text.setBackgroundColor(Color.RED);
		*/
	
			//Event event = (Event) getItem(position);
			/*TextView text;
			text = (TextView) vi.findViewById(R.id.text1);
			text.setText(getItem(position).toString());
			//text.setText(event.getTitle());
	    	text.setBackgroundColor(Color.RED);*/
		
		Event event = getEvent(position);
		//Log.e("ADAPTER TAG", "Event Name " + event.getTitle());
	//	Log.e("ADAPTER TAG", "DAYS TO COMPLETE " + Integer.toString(event.getDaysToComplete()));
        TextView text;
        LinearLayout row;
        //if text = "Data 1"
        if(event.getDaysToComplete() >= 14){
        //	Log.e("TEXT TEST", "text2 green");
        	//row = (LinearLayout) vi.findViewById(R.id.list_row);
        	//row.setBackgroundColor(Color.RED);
        	
        	  /* GradientDrawable gdRed = new GradientDrawable(
        	            GradientDrawable.Orientation.TOP_BOTTOM,
        	            new int[] {0xffec1613 , 0xffd81621, 0xffC51630});
        	    gdRed.setCornerRadius(0f);*/
        	
        	text = (TextView) vi.findViewById(R.id.text1);
        	text.setText(event.getTitle() + " Days: " + Integer.toString(event.getDaysToComplete()));
        	
        	text.setBackgroundDrawable(gdRed);
        	
       
        }else if(event.getDaysToComplete() >= 7 && event.getDaysToComplete() < 14 ){
        	text = (TextView) vi.findViewById(R.id.text1);
        	text.setBackgroundDrawable(gdAmber);
        	text.setText(event.getTitle() + " Days: " + Integer.toString(event.getDaysToComplete()));
        
        }else {
        	//Log.e("TEXT TEST", "text1 blue");
        	text = (TextView) vi.findViewById(R.id.text1);
        	text.setBackgroundDrawable(gdGreen);
        	text.setText(event.getTitle() + " Days: " + Integer.toString(event.getDaysToComplete()));
       
        }
       // text.setText(data[position]);
        return vi;
	}

}
