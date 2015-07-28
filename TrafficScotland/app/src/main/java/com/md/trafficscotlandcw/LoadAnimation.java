package com.md.trafficscotlandcw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

@SuppressLint("DrawAllocation")
public class LoadAnimation extends View implements Runnable {

	private Canvas canvas;
    private Bitmap bitmap;
    private Paint myPaint;
    private int radius;
    private boolean up;
    private boolean run;
    private int MAX_RADIUS = 30;
    private int MIN_RADIUS = 15;
	
	public LoadAnimation(Context context) {
		 
		super(context);
	        radius = MIN_RADIUS;
	        up = true;
	        run = true;
	      //  Log.e("ANIMATION TAG", "CONSTRUCTOR");
	}

	protected void onSizeChanged(int curw, int curh, int oldw, int oldh) 
    {
		if(curh == 0)
			curh = 539;
		
		Log.e("ANIMATION TAG", "SIZE CHANGE " + Integer.toString(curw) + " " + Integer.toString(curh));
        if (bitmap != null) 
        {
            bitmap.recycle();
        }
        canvas= new Canvas();
        Log.e("ANIMATION TAG", "CRASH 1");
        bitmap = Bitmap.createBitmap(curw, curh, Bitmap.Config.ARGB_8888);
        Log.e("ANIMATION TAG", "CRASH 2");
        canvas.setBitmap(bitmap);
        Log.e("ANIMATION TAG", "CRASH 3");
    }
    public void destroy() 
    {
        if (bitmap != null) 
        {
            bitmap.recycle();
        }
    }
    @SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) 
    {
      //draw onto the canvas
        myPaint = new Paint();
        myPaint.setStrokeWidth(3);
        myPaint.setColor(0xFF097286);
       // canvas.drawCircle(120, 140, radius, myPaint);
        canvas.drawCircle(120, 140, radius, myPaint);
        Thread bthread = new Thread(this);
        bthread.start();
              
    }
	@SuppressLint("HandlerLeak")
	@Override
	public void run() {
		Log.e("ANIMATION TAG", "RUN");
		// TODO Auto-generated method stub
		if (radius < MAX_RADIUS && up == true)
		{
			radius = radius + 1;
		}
		else if (radius == MAX_RADIUS)
		{
			up = false;
			radius = radius - 1;
		}
		else if (radius > MIN_RADIUS && up == false)
		{
			radius = radius -1;			
		}
		else if (radius == MIN_RADIUS)
		{
			up = true;
		}
		
		if (run == true)
		{
			handler.sendEmptyMessage(0);
		}
		else
		{
			handler.sendEmptyMessage(1);
		}
	}
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			Log.e("MyTag","In  handler" + msg.what + run);
			if (msg.what == 0)
			{
				invalidate();
			}
			else
				if (msg.what == 1)
				{
					run = false;
				}
				else
					if (msg.what == 2)
					{
						run = true;
						invalidate();
					}
		}
		
		
	};
	
	public Handler getHandler()
		{
			Log.e("MyTag","In gethandler");
			return handler;
		}

}
