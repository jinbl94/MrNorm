package com.tang.mrnorm.framework.impl;

import android.view.View.OnTouchListener;

import com.tang.mrnorm.framework.Input.TouchEvent;

import java.util.List;

/**
 * Created by tang on 6/29/16.
 */

public interface TouchHandler extends OnTouchListener{
	public boolean isTouchDown(int pointer);
	public int getTouchX(int pointer);
	public int getTouchY(int pointer);
	public List<TouchEvent> getTouchEvents();
}
