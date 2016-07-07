package com.tang.mrnorm;

import android.graphics.Color;

import com.tang.mrnorm.framework.Game;
import com.tang.mrnorm.framework.Graphics;
import com.tang.mrnorm.framework.Input.TouchEvent;
import com.tang.mrnorm.framework.Pixmap;
import com.tang.mrnorm.framework.Screen;

import org.xml.sax.SAXNotRecognizedException;

import java.util.List;

/**
 * Created by tang on 7/1/16.
 */

public class GameScreen extends Screen{
	enum GameState{
		Ready,
		Running,
		Paused,
		GameOver
	}

	GameState state=GameState.Ready;
	World world;
	int oldScore=0;
	String score="0";

	public GameScreen(Game game){
		super(game);
		world=new World();
	}

	@Override
	public void	update(float deltaTime){
		List<TouchEvent> touchEvents=game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if(state==GameState.Ready)
			updateReady(touchEvents);
		if(state==GameState.Running)
			updateRunning(touchEvents,deltaTime);
		if(state==GameState.Paused)
			updatePaused(touchEvents);
		if(state==GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents){
		if(touchEvents.size()>0){
			state=GameState.Running;
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents,float deltaTime){
		for(int i=0;i<touchEvents.size();i++){
			TouchEvent event=touchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(event.x<64&&event.y<64){
					if(Settings.soundEnabled)
						Assets.click.play(1);
					state=GameState.Paused;
					return;
				}
			}

			if(event.type==TouchEvent.TOUCH_DOWN){
				if(event.x<64&&event.y>416){
					world.snake.turnLeft();
				}
				if(event.x>256&&event.y>416){
					world.snake.turnRight();
				}
			}
		}

		world.update(deltaTime);
		if(world.gameOver){
			if(Settings.soundEnabled)
				Assets.bitten.play(1);
			state=GameState.GameOver;
		}
		if(oldScore!=world.score){
			oldScore=world.score;
			score=""+oldScore;
			if(Settings.soundEnabled)
				Assets.eat.play(1);
		}
	}

	private void updatePaused(List<TouchEvent> touchEvents){
		for(int i=0;i<touchEvents.size();i++){
			TouchEvent event=touchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(event.x>80&&event.x<=240){
					if(event.y>100&&event.y<148){
						if(Settings.soundEnabled)
							Assets.click.play(1);
						state=GameState.Running;
						return;
					}
					if(event.y>148&&event.y<196){
						if(Settings.soundEnabled)
							Assets.click.play(1);
						game.setScreen(new MainMenuScreen(game));
						return;
					}
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents){
		for(int i=0;i<touchEvents.size();i++){
			TouchEvent event=touchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(event.x>=128&&event.x<=192&&event.y>=200&&event.y<=264){
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
		drawWorld(world);
		if(state==GameState.Ready)
			drawReadyUI();
		if(state==GameState.Running)
			drawRunningUI();
		if(state==GameState.Paused)
			drawPausedUI();
		if(state==GameState.GameOver)
			drawGameOverUI();
	}

	private void drawWorld(World world){
		Graphics graphics=game.getGraphics();
		Snake snake=world.snake;
		SnakePart head=snake.parts.get(0);
		Stain stain=world.stain;

		Pixmap stainPixmap=null;
		if(stain.type==Stain.TYPE_1)
			stainPixmap=Assets.stain1;
		if(stain.type==Stain.TYPE_2)
			stainPixmap=Assets.stain2;
		if(stain.type==Stain.TYPE_3)
			stainPixmap=Assets.stain3;

		int x=stain.x*32;
		int y=stain.y*32;
		graphics.drawPixmap(stainPixmap,x,y);

		for(int i=1;i<snake.parts.size();i++){
			SnakePart part=snake.parts.get(i);
			x=part.x*32;
			y=part.y*32;
			graphics.drawPixmap(Assets.tail,x,y);
		}

		Pixmap headPixmap=null;
		if(snake.direction==Snake.UP)
			headPixmap=Assets.headUp;
		if(snake.direction==Snake.LEFT)
			headPixmap=Assets.headLeft;
		if(snake.direction==Snake.DOWN)
			headPixmap=Assets.headDown;
		if(snake.direction==Snake.RIGHT)
			headPixmap=Assets.headRight;
		x=head.x*32;
		y=head.y*32;
		graphics.drawPixmap(headPixmap,x-headPixmap.getWidth()/2,y-headPixmap.getHeight()/2);
	}

	private void drawReadyUI(){
		Graphics graphics=game.getGraphics();

		graphics.drawPixmap(Assets.ready,47,100);
		graphics.drawLine(0,416,480,416,Color.BLACK);
	}

	private void drawRunningUI(){
		Graphics graphics=game.getGraphics();

		graphics.drawPixmap(Assets.buttons,0,0,64,128,64,64);
		graphics.drawLine(0,416,480,416,Color.BLACK);
		graphics.drawPixmap(Assets.buttons,0,416,64,64,64,64);
		graphics.drawPixmap(Assets.buttons,256,416,0,64,64,64);
	}

	private void drawPausedUI(){
		Graphics graphics=game.getGraphics();

		graphics.drawPixmap(Assets.pause,80,100);
		graphics.drawLine(0,416,480,416,Color.BLACK);
	}

	private void drawGameOverUI(){
		Graphics graphics=game.getGraphics();

		graphics.drawPixmap(Assets.gameOver,62,100);
		graphics.drawPixmap(Assets.buttons,128,200,0,128,64,64);
		graphics.drawLine(0,416,408,416,Color.BLACK);
	}

	private void drawText(Graphics graphics,String line,int x,int y){
		for(int i=0;i<line.length();i++){
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
	public void pause(){
		if(state==GameState.Running)
			state=GameState.Paused;
		if(world.gameOver){
			Settings.addScore(world.score);
			Settings.save(game.getFileIO());
		}
	}

	@Override
	public void resume(){}

	@Override
	public void dispose(){}
}