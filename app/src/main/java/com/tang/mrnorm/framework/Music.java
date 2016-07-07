package com.tang.mrnorm.framework;

/**
 * Created by tang on 4/14/16.
 */
public interface Music {
    public void play();
    public void stop();
    public void pause();
    public void setLooping(boolean looping);
    public void setVolume(float volume);
    public boolean isPlaying();
    public boolean isStopped();
    public boolean isLooping();
    public void dispose();
}
