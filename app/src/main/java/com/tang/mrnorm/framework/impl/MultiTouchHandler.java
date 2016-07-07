package com.tang.mrnorm.framework.impl;

import android.annotation.TargetApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tang.mrnorm.framework.Pool;
import com.tang.mrnorm.framework.Pool.PoolObjectFactory;
import com.tang.mrnorm.framework.Input.TouchEvent;

import java.util.ArrayList;
import java.util.List;

@TargetApi(5)
/**
 * Created by tang on 6/29/16.
 */

public class MultiTouchHandler implements TouchHandler{
	private static final int MAX_TOUCHPOINTS=10;
	private boolean[] isTouched=new boolean[MAX_TOUCHPOINTS];
	private int[] touchX=new int[MAX_TOUCHPOINTS];
	private int[] touchY=new int[MAX_TOUCHPOINTS];
	private int[] id=new int[MAX_TOUCHPOINTS];
	private Pool<TouchEvent> touchEventPool;
	private List<TouchEvent> touchEvents=new ArrayList<TouchEvent>();
	private List<TouchEvent> touchEventsBuffer=new ArrayList<TouchEvent>();
	private float scaleX;
	private float scaleY;

	public MultiTouchHandler(View view,float scaleX,float scaleY){
		PoolObjectFactory<TouchEvent> factory=new PoolObjectFactory<TouchEvent>(){
			@Override
			public TouchEvent createObject(){
				return new TouchEvent();
			}
		};
		touchEventPool=new Pool<TouchEvent>(factory,100);
		view.setOnTouchListener(this);

		this.scaleX=scaleX;
		this.scaleY=scaleY;
	}

	public boolean onTouch(View view,MotionEvent event){
		synchronized(this){
			int action=event.getAction()&MotionEvent.ACTION_MASK;
			int pointerIndex=event.getActionIndex();
			int pointerCount=event.getPointerCount();
			TouchEvent touchEvent;
			for(int i=0;i<MAX_TOUCHPOINTS;i++){
				if(i>=pointerCount){
					isTouched[i]=false;
					id[i]=-1;
					continue;
				}
				int pointerId=event.getPointerId(i);
				if(event.getAction()!=MotionEvent.ACTION_MOVE&&i!=pointerIndex){
					continue;
				}
				switch(action){
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_POINTER_DOWN:
						touchEvent=touchEventPool.newObject();
						touchEvent.type=TouchEvent.TOUCH_DOWN;
						touchEvent.pointer=pointerId;
						touchEvent.x=touchX[i]=(int)(event.getX(i)*scaleX);
						touchEvent.y=touchY[i]=(int)(event.getY(i)*scaleY);
						isTouched[i]=true;
						id[i]=pointerId;
						touchEventsBuffer.add(touchEvent);
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_POINTER_UP:
					case MotionEvent.ACTION_CANCEL:
						touchEvent=touchEventPool.newObject();
						touchEvent.type=TouchEvent.TOUCH_UP;
						touchEvent.pointer=pointerId;
						touchEvent.x=touchX[i]=(int)(event.getX(i)*scaleX);
						touchEvent.y=touchY[i]=(int)(event.getY(i)*scaleY);
						isTouched[i]=false;
						id[i]=-1;
						touchEventsBuffer.add(touchEvent);
						break;
					case MotionEvent.ACTION_MOVE:
						touchEvent=touchEventPool.newObject();
						touchEvent.type=TouchEvent.TOUCH_DOWN;
						touchEvent.pointer=pointerId;
						touchEvent.x=touchX[i]=(int)(event.getX(i)*scaleX);
						touchEvent.y=touchY[i]=(int)(event.getY(i)*scaleY);
						isTouched[i]=true;
						id[i]=pointerId;
						touchEventsBuffer.add(touchEvent);
						break;
					default:
						Log.d("MultiTouchHandler","onTouch default");
						break;
				}
			}
			return true;
		}
	}

	public boolean isTouchDown(int pointer){
		synchronized(this){
			int index=getIndex(pointer);
			return (!(index<0||index>=MAX_TOUCHPOINTS))&&isTouched[index];
		}
	}

	public int getTouchX(int pointer){
		synchronized(this){
			int index=getIndex(pointer);
			if(index<0||index>=MAX_TOUCHPOINTS)
				return 0;
			else
				return touchX[index];
		}
	}

	public int getTouchY(int pointer){
		synchronized(this){
			int index=getIndex(pointer);
			if(index<0||index>=MAX_TOUCHPOINTS)
				return 0;
			else
				return touchY[index];
		}
	}

	public List<TouchEvent> getTouchEvents(){
		synchronized(this){
			int len=touchEvents.size();
			for(int i=0;i<len;i++)
				touchEventPool.free(touchEvents.get(i));
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}

	private int getIndex(int pointerId){
		for(int i=0;i<MAX_TOUCHPOINTS;i++){
			if(id[i]==pointerId){
				return i;
			}
		}
		return -1;
	}
}
