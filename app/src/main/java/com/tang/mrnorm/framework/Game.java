package com.tang.mrnorm.framework;

/**
 * Created by tang on 4/14/16.
 */
public interface Game {
    public Input getInput();
    public FileIO getFileIO();
    public Graphics getGraphics();
    public Audio getAudio();
    public void setScreen(Screen screen);
    public Screen getCurrentScreen();
    public Screen getStartScreen();
}
