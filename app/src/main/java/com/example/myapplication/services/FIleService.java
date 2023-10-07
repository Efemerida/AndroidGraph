package com.example.myapplication.services;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FIleService {

    public String loadGraph(File file,Context context) throws IOException {
        FileInputStream stream = context.openFileInput("load.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
            stringBuilder.append('\n');
        }
        bufferedReader.close();
        stream.close();
        return stringBuilder.toString();
    }

    public void saveGraph(Context context) throws IOException {
        String string = "1 2 3\n3 4 5";
        FileOutputStream outputStream = context.openFileOutput("load.txt", Context.MODE_PRIVATE);
        outputStream.write(string.getBytes());
        outputStream.close();

    }


}
