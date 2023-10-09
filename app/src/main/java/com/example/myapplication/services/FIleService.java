package com.example.myapplication.services;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;

import com.example.myapplication.entities.Edge;
import com.example.myapplication.entities.Graph;

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

    public void saveGraph(Context context, Graph graph) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for(Edge edge: graph.edgeList){
            stringBuilder.append(edge.getVertex1().getNumber() + " ");
            stringBuilder.append(edge.getVertex2().getNumber() + " ");
            stringBuilder.append(edge.getWeight() + "\n");
        }

        String string = stringBuilder.toString();
        FileOutputStream outputStream = context.openFileOutput("load.txt", Context.MODE_PRIVATE);
        outputStream.write(string.getBytes());
        outputStream.close();

    }

    private void openFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

    }

}
