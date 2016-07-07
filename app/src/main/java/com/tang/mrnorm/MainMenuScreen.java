package com.tang.mrnorm;

import android.text.method.Touch;

import com.tang.mrnorm.framework.Game;
import com.tang.mrnorm.framework.Graphics;
import com.tang.mrnorm.framework.Input.TouchEvent;
import com.tang.mrnorm.framework.Screen;

import java.util.List;
import java.util.Set;

/**
 * Created by tang on 6/30/16.
 */

public class MainMenuScreen extends Screen{
	public MainMenuScreen(Game game){
		super(game);
	}

	public void update(float deltaTime){
		Graphics graphics=game.getGraphics();
		List<TouchEvent> touchEvents=game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len=touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event=touchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(inBounds(event,0,graphics.getHeight()-64,64,64)){
					Settings.soundEnabled=!Settings.soundEnabled;
					if(Settings.soundEnabled)
						Assets.click.play(1);
				}
				if(inBounds(event,64,220,192,42)){
					game.setScreen(new GameScreen(game));
					if(Settings.soundEnabled){
						Assets.click.play(1);
						return;
					}
				}
				if(inBounds(event,64,220+42,192,42)){
					game.setScreen(new HighscoreScreen(game));
					if(Settings.soundEnabled){
						Assets.click.play(1);
						return;
					}
				}
				if(inBounds(event,64,220+84,192,42)){
					game.setScreen(new HelpScreen(game));
					if(Settings.soundEnabled){
						Assets.click.play(1);
						return;
					}
				}
			}
		}
	}

	private boolean inBounds(TouchEvent event,int x,int y,int width,int height){
		if(event.x>x&&event.x<x+width-1&&event.y>y&&event.y<y+height-1)
			return true;
		else
			return false;
	}

	public void present(float deltaTime){
		Graphics graphics=game.getGraphics();

		graphics.drawPixmap(Assets.background,0,0);
		graphics.drawPixmap(Assets.logo,32,20);
		graphics.drawPixmap(Assets.mainMenu,64,220);
		if(Settings.soundEnabled)
			graphics.drawPixmap(Assets.buttons,0,416,0,0,64,64);
		else
			graphics.drawPixmap(Assets.buttons,0,416,64,0,64,64);
	}

	public void pause(){
		Settings.save(game.getFileIO());
	}

	public void resume(){}

	public void dispose(){}
}
