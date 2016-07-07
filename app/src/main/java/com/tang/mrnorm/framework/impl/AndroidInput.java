package com.tang.mrnorm.framework.impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.tang.mrnorm.framework.Input;

import java.util.List;

/**
 * Created by tang on 6/29/16.
 */

public class AndroidInput implements Input{
	AccelerometerHandler accelerometerHandler;
	KeyboardHandler keyboardHandler;
	TouchHandler touchHandler;

	public AndroidInput(Context context,View view,float scaleX,float scaleY){
		accelerometerHandler=new AccelerometerHandler(context);
		keyboardHandler=new KeyboardHandler(view);
		if(Integer.parseInt(VERSION.SDK)<5)
			touchHandler=new SingleTouchHandler(view,scaleX,scaleY);
		else
			touchHandler=new MultiTouchHandler(view,scaleX,scaleY);
	}

	public boolean isKeyPressed(int keyCode){
		return keyboardHandler.isKeyPressed(keyCode);
	}

	public boolean isTouchDown(int pointer){
		return touchHandler.isTouchDown(pointer);
	}

	public int getTouchX(int pointer){
		return touchHandler.getTouchX(pointer);
	}

	public int getTouchY(int pointer){
		return touchHandler.getTouchY(pointer);
	}

	public float getAccelX(){
		return accelerometerHandler.getAccelX();
	}

	public float getAccelY(){
		return accelerometerHandler.getAccelY();
	}

	public float getAccelZ(){
		return accelerometerHandler.getAccelZ();
	}

	public List<TouchEvent> getTouchEvents(){
		return touchHandler.getTouchEvents();
	}

	public List<KeyEvent> getKeyEvents(){
		return keyboardHandler.getKeyEvents();
	}
}
