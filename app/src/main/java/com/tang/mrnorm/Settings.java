package com.tang.mrnorm;

import com.tang.mrnorm.framework.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by tang on 6/30/16.
 */

public class Settings{
	public static boolean soundEnabled=true;
	public static int[] highscores=new int[]{100,80,50,30,10};

	public static void load(FileIO fileIO){
		BufferedReader in=null;
		try{
			in=new BufferedReader(new InputStreamReader(fileIO.readFile(".mrnorm")));
			soundEnabled=Boolean.parseBoolean(in.readLine());
			for(int i=0;i<5;i++){
				highscores[i]=Integer.parseInt(in.readLine());
			}
		}catch(IOException e){
			//use default values
		}catch(NumberFormatException e){
			//defaults save day
		}finally{
			try{
				if(in!=null)
					in.close();
			}catch(IOException e){}
		}
	}

	public static void save(FileIO fileIO){
		BufferedWriter out=null;
		try{
			out=new BufferedWriter(new OutputStreamWriter(fileIO.writeFile(".mrnorm")));
			out.write(Boolean.toString(soundEnabled));
			for(int i=0;i<5;i++){
				out.write(Integer.toString(highscores[i]));
			}
		}catch(IOException e){
			//todo:
		}finally{
			try{
				if(out!=null)
					out.close();
			}catch(IOException e){}
		}
	}

	public static void addScore(int score){
		for(int i=0;i<5;i++){
			if(highscores[i]<score){
				for(int j=4;j>1;j--)
					highscores[j]=highscores[j-1];
				highscores[i]=score;
				break;
			}
		}
	}
}
