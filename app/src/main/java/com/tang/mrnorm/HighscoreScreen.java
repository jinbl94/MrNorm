package com.tang.mrnorm;

import com.tang.mrnorm.framework.Game;
import com.tang.mrnorm.framework.Graphics;
import com.tang.mrnorm.framework.Input.TouchEvent;
import com.tang.mrnorm.framework.Screen;

import java.util.List;

/**
 * Created by tang on 6/30/16.
 */

public class HighscoreScreen extends Screen{
	String lines[]=new String [5];

	public HighscoreScreen(Game game){
		super(game);

		for(int i=0;i<5;i++){
			lines[i]=""+(i+1)+". "+Settings.highscores[i];
		}
	}

	@Override
	public void update(float deltaTime){
		List<TouchEvent> touchEvents=game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len=touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event=touchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(event.x<64&&event.y>416){
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime){
		Graphics graphics=game.getGraphics();

		graphics.drawPixmap(Assets.background,0,0);
		graphics.drawPixmap(Assets.mainMenu,64,20,0,42,196,42);

		int y=100;
		for(int i=0;i<5;i++){
			drawText(graphics,lines[i],20,y);
			y+=50;
		}

		graphics.drawPixmap(Assets.buttons,0,416,64,64,64,64);
	}

	public void drawText(Graphics graphics,String line,int x,int y){
		int len=line.length();
		for(int i=0;i<len;i++){
			char character=line.charAt(i);

			if(character==' '){
				x+=20;
				continue;
			}

			int srcX=0;
			int srcWidth=0;
			if(character=='.'){
				srcX=200;
				srcWidth=10;
			}else{
				srcX=(character-'0')*20;
				srcWidth=20;
			}

			graphics.drawPixmap(Assets.numbers,x,y,srcX,0,srcWidth,32);
			x+=srcWidth;
		}
	}

	@Override
	public void pause(){}

	@Override
	public void resume(){}

	@Override
	public void dispose(){}
}
