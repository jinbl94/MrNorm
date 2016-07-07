package com.tang.mrnorm;

import com.tang.mrnorm.framework.Game;
import com.tang.mrnorm.framework.Graphics;
import com.tang.mrnorm.framework.Screen;

/**
 * Created by tang on 6/30/16.
 */

public class LoadingScreen extends Screen{
	public LoadingScreen(Game game){
		super(game);
	}

	public void update(float deltaTime){
		Graphics graphics=game.getGraphics();
		Assets.background=graphics.newPixmap("background.png",Graphics.PixmapFormat.RGB565);
		Assets.logo=graphics.newPixmap("logo.png",Graphics.PixmapFormat.ARGB4444);
		Assets.mainMenu=graphics.newPixmap("mainmenu.png",Graphics.PixmapFormat.ARGB4444);
		Assets.buttons=graphics.newPixmap("buttons.png",Graphics.PixmapFormat.ARGB4444);
		Assets.help1=graphics.newPixmap("help1.png",Graphics.PixmapFormat.ARGB4444);
		Assets.help2=graphics.newPixmap("help2.png",Graphics.PixmapFormat.ARGB4444);
		Assets.help3=graphics.newPixmap("help3.png",Graphics.PixmapFormat.ARGB4444);
		Assets.numbers=graphics.newPixmap("numbers.png",Graphics.PixmapFormat.ARGB4444);
		Assets.ready=graphics.newPixmap("ready.png",Graphics.PixmapFormat.ARGB4444);
		Assets.pause=graphics.newPixmap("pausemenu.png",Graphics.PixmapFormat.ARGB4444);
		Assets.gameOver=graphics.newPixmap("gameover.png",Graphics.PixmapFormat.ARGB4444);
		Assets.headUp=graphics.newPixmap("headup.png",Graphics.PixmapFormat.ARGB4444);
		Assets.headLeft=graphics.newPixmap("headleft.png",Graphics.PixmapFormat.ARGB4444);
		Assets.headDown=graphics.newPixmap("headdown.png",Graphics.PixmapFormat.ARGB4444);
		Assets.headRight=graphics.newPixmap("headright.png",Graphics.PixmapFormat.ARGB4444);
		Assets.tail=graphics.newPixmap("tail.png",Graphics.PixmapFormat.ARGB4444);
		Assets.stain1=graphics.newPixmap("stain1.png",Graphics.PixmapFormat.ARGB4444);
		Assets.stain2=graphics.newPixmap("stain2.png",Graphics.PixmapFormat.ARGB4444);
		Assets.stain3=graphics.newPixmap("stain3.png",Graphics.PixmapFormat.ARGB4444);
		Assets.click=game.getAudio().newSound("click.ogg");
		Assets.eat=game.getAudio().newSound("eat.ogg");
		Assets.bitten=game.getAudio().newSound("bitten.ogg");
		Settings.load(game.getFileIO());
		game.setScreen(new MainMenuScreen(game));

	}

	public void present(float deltaTime){}
	public void pause(){};
	public void resume(){};
	public void dispose(){};
}
