package com.tang.mrnorm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tang on 7/1/16.
 */

public class Snake{
	public static final int UP=0;
	public static final int LEFT=1;
	public static final int DOWN=2;
	public static final int RIGHT=3;

	public List<SnakePart> parts=new ArrayList<SnakePart>();
	public int direction;

	public Snake(){
		direction=UP;
		parts.add(new SnakePart(World.WORLD_WIDTH/2,World.WORLD_HEIGHT/2));
		parts.add(new SnakePart(World.WORLD_WIDTH/2,World.WORLD_HEIGHT/2+1));
		parts.add(new SnakePart(World.WORLD_WIDTH/2,World.WORLD_HEIGHT/2+2));
	}

	public void turnLeft(){
		direction=(direction+1)%4;
	}

	public void turnRight(){
		direction=(direction+3)%4;
	}

	public void eat(){
		SnakePart end=parts.get(parts.size()-1);
		parts.add(new SnakePart(end.x,end.y));
	}

	public void advance(){
		SnakePart head=parts.get(0);

		for(int i=parts.size()-1;i>0;i--){
			SnakePart before=parts.get(i-1);
			SnakePart part=parts.get(i);
			part.x=before.x;
			part.y=before.y;
		}
		if(direction==UP)
			head.y--;
		if(direction==LEFT)
			head.x--;
		if(direction==DOWN)
			head.y++;
		if(direction==RIGHT)
			head.x++;

		head.x=(head.x+World.WORLD_WIDTH)%World.WORLD_WIDTH;
		head.y=(head.y+World.WORLD_HEIGHT)%World.WORLD_HEIGHT;
	}

	//check whether Mr. Norm have bitten it's tail
	public boolean checkBitten(){
		int len=parts.size();
		SnakePart head=parts.get(0);
		for(int i=1;i<len;i++){
			SnakePart part=parts.get(i);
			if(part.x==head.x&&part.y==head.y)
				return true;
		}
		return false;
	}
}
