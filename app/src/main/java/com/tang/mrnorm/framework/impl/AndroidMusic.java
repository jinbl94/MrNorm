package com.tang.mrnorm.framework.impl;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.tang.mrnorm.framework.Music;

import java.io.IOException;

/**
 * Created by tang on 4/14/16.
 */
public class AndroidMusic implements Music,MediaPlayer.OnCompletionListener {
    MediaPlayer mediaPlayer;
    boolean isPrepared=false;

    public AndroidMusic(AssetFileDescriptor assetFileDescriptor){
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared=true;
            mediaPlayer.setOnCompletionListener(this);
        }catch (Exception e){
            throw new RuntimeException("Couldn't load music");
        }
    }

    @Override
    public void dispose(){
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public boolean isLooping(){
        return mediaPlayer.isLooping();
    }

    @Override
    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped(){
        return !isPrepared;
    }

    @Override
    public void pause(){
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void play(){
        if (mediaPlayer.isPlaying())
            return;
        try {
            synchronized (this){
                if (!isPrepared)
                    mediaPlayer.prepare();
                mediaPlayer.start();
            }
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setLooping(boolean isLooping){
        mediaPlayer.setLooping(isLooping);
    }

    @Override
    public void setVolume(float volume){
        mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public void stop(){
        mediaPlayer.stop();
        synchronized (this){
            isPrepared=false;
        }
    }

    @Override
    public void onCompletion(MediaPlayer player){
        synchronized (this){
            isPrepared=false;
        }
    }
}
