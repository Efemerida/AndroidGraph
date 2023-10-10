package com.example.myapplication.services;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.myapplication.MainActivity;
import com.example.myapplication.entities.Edge;
import com.example.myapplication.entities.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

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

    public void saveGraph(Context context, Graph graph, Uri path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for(Edge edge: graph.edgeList){
            stringBuilder.append(edge.getVertex1().getNumber() + " ");
            stringBuilder.append(edge.getVertex2().getNumber() + " ");
            stringBuilder.append(edge.getWeight() + "\n");
        }

        String string = stringBuilder.toString();

        File file = new File(path.getEncodedPath());
        File file1 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
        ), "isMy.txt");
        Log.d("taggg", "paths is " + file.getAbsolutePath());
       // file1.createNewFile();
        Log.d("tagggg", String.valueOf(file.mkdirs()));
        FileOutputStream fileOutputStream = new FileOutputStream(file1, true);
        fileOutputStream.write(string.getBytes());
        fileOutputStream.close();
//        FileOutputStream outputStream = context.openFileOutput(file.getPath(), Context.MODE_APPEND);
//        outputStream.write(string.getBytes());
//        outputStream.close();

    }
    private static final int PICK_PDF_FILE = 2;

    private void openFile(Uri pickerInitialUri, Context context) {



    }

}
