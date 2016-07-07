package com.tang.mrnorm;

import java.util.Random;

/**
 * Created by tang on 7/1/16.
 */

public class World{
	public static final int WORLD_WIDTH=10;
	public static final int WORLD_HEIGHT=13;
	private static final int SCORE_INCREMENT=10;
	private static final float TICK_INITIAL=0.5f;
	private static final float TICK_DECREMENT=0.05f;

	public Snake snake;
	public Stain stain;
	public boolean gameOver=false;
	public int score=0;

	private boolean fields[][]=new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	private Random random=new Random();
	private float tickTime=0;
	private float tick=TICK_INITIAL;

	public World(){
		snake=new Snake();
		placeStain();
	}

	private void placeStain(){
		for(int i=0;i<WORLD_WIDTH;i++){
			for(int j=0;j<WORLD_HEIGHT;j++){
				fields[i][j]=false;
			}
		}

		for(int i=0;i<snake.parts.size();i++){
			SnakePart part=snake.parts.get(i);
			fields[part.x][part.y]=true;
		}

		int stainX=random.nextInt(WORLD_WIDTH);
		int stainY=random.nextInt(WORLD_HEIGHT);
		while(true){
			if(!fields[stainX][stainY])
				break;
			stainX=(stainX+1)%(WORLD_WIDTH+1);
			stainY=(stainY+1)%(WORLD_HEIGHT+1);
		}
		stain=new Stain(stainX,stainY,random.nextInt(3));
	}

	public void update(float deltaTime){
		if(gameOver)
			return;
		tickTime+=deltaTime;

		while(tickTime>tick){
			tickTime-=tick;
			snake.advance();
			if(snake.checkBitten()){
				gameOver=true;
				return;
			}

			SnakePart head=snake.parts.get(0);
			if(head.x==stain.x&&head.y==stain.y){
				score+=SCORE_INCREMENT;
				snake.eat();
				if(snake.parts.size()==WORLD_WIDTH*WORLD_HEIGHT){
					gameOver=true;
					return;
				}else{
					placeStain();
				}

				if(score%100==0&&tick-TICK_DECREMENT>0){
					tick-=TICK_DECREMENT;
				}
			}
		}
	}
}
