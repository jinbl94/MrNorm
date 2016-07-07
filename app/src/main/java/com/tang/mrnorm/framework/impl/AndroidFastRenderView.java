package com.tang.mrnorm.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.provider.Settings;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by tang on 6/29/16.
 */

public class AndroidFastRenderView extends SurfaceView implements Runnable{
	AndroidGame game;
	Bitmap frameBuffer;
	Thread renderThread=null;
	SurfaceHolder surfaceHolder;
	volatile boolean running=false;

	public AndroidFastRenderView(AndroidGame game,Bitmap frameBuffer){
		super(game);
		this.game=game;
		this.frameBuffer=frameBuffer;
		this.surfaceHolder=getHolder();
	}

	public void resume(){
		running=true;
		renderThread=new Thread(this);
		renderThread.start();
	}

	public void run(){
		Rect dstRect=new Rect();
		long starTime=System.nanoTime();
		while(running){
			if(!surfaceHolder.getSurface().isValid()){
				continue;
			}

			float deltaTime=(System.nanoTime()-starTime)/1000000000.0f;
			starTime=System.nanoTime();

			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);

			Canvas canvas=surfaceHolder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(frameBuffer,null,dstRect,null);
			surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause(){
		running=false;
		while(true){
			try{
				renderThread.join();
				return;
			}catch(InterruptedException e){
				//retry
			}
		}
	}
}
