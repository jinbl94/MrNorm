package com.tang.mrnorm;

import com.tang.mrnorm.framework.Screen;
import com.tang.mrnorm.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame{
	public Screen getStartScreen(){
		return new LoadingScreen(this);
	}
}
