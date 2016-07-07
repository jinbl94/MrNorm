package com.tang.mrnorm.framework;

/**
 * Created by tang on 4/14/16.
 */
public abstract class Screen {
    protected final Game game;

    public Screen(Game game){
        this.game=game;
    }

    public abstract void update(float deltaTime);
    public abstract void present(float deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();
}
