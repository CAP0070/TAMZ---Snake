package com.example.snake;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static android.content.ContentValues.TAG;

public class LevelLoader {
    private int currLevel = 1;
    private static final String LEVEL_FILENAME = "levels/levels.txt";

    public static int[] loadLevel(SnakeEngine view, int input, int HEIGHT, int WIDTH){
        BufferedReader reader;
        int[] tmpArr = new int[HEIGHT * WIDTH];
        try{
            final InputStream file = view.getContext().getAssets().open(LEVEL_FILENAME);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            List<String> aux = new ArrayList<>();

            int delimiters = 1;
            while(line != null){
                line = reader.readLine();
                if (delimiters == input) {
                    if (line != null) {
                        for (String s : line.split(",")) {
                            aux.add(s.trim());
                        }
                    }
                }
                if (line != null)
                if (line.contains(";")) delimiters++;

                if (delimiters > input) break;

            }

            for(int i = 0; i < aux.size(); i++){
                if (aux.get(i).equals("EMPTY")){
                    tmpArr[i] = 0;
                } else if (aux.get(i).equals("TOPLEFTBRICK")){
                    tmpArr[i] = 1;

                } else if (aux.get(i).equals("TOPRIGHTBRICK")){
                    tmpArr[i] = 2;
                } else if (aux.get(i).equals("TOPGENERICBRICK")){
                    tmpArr[i] = 3;
                } else if (aux.get(i).equals("BOTTOMLEFTBRICK")){
                    tmpArr[i] = 4;
                } else if (aux.get(i).equals("BOTTOMRIGHTBRICK")){
                    tmpArr[i] = 5;
                } else if (aux.get(i).equals("BOTTOMGENERICBRICK")){
                    tmpArr[i] = 6;
                } else if (aux.get(i).equals("LEFTBRICK")){
                    tmpArr[i] = 7;
                } else if (aux.get(i).equals("RIGHTBRICK")){
                    tmpArr[i] = 8;
                } else if (aux.get(i).equals("STONE")){
                    tmpArr[i] = 17;
                } else if (aux.get(i).equals("GENERICBRICK")){
                    tmpArr[i] = 18;
                }
            }

        } catch(IOException ioe){
            ioe.printStackTrace();
        }

        return tmpArr;
    }
}