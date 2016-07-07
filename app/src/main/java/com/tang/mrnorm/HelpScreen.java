package com.tang.mrnorm;

import com.tang.mrnorm.framework.Game;
import com.tang.mrnorm.framework.Graphics;
import com.tang.mrnorm.framework.Input.TouchEvent;
import com.tang.mrnorm.framework.Screen;

import java.util.List;

/**
 * Created by tang on 6/30/16.
 */

public class HelpScreen extends Screen{
	public HelpScreen(Game game){
		super(game);
	}

	@Override
	public void update(float deltaTime){
		List<TouchEvent> touchEvents=game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len=touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event=touchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(event.x>256&&event.y>416){
					game.setScreen(new HelpScreen2(game));
					if(Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime){
		Graphics graphics=game.getGraphics();
		graphics.drawPixmap(Assets.background,0,0);
		graphics.drawPixmap(Assets.help1,64,100);
		graphics.drawPixmap(Assets.buttons,256,416,0,64,64,64);
	}

	@Override
	public void pause(){}

	@Override
	public void resume(){}

	@Override
	public void dispose(){}
}
