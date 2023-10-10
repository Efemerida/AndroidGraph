package com.example.myapplication.services;

import static android.app.PendingIntent.getActivity;
import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.myapplication.MainActivity;
import com.example.myapplication.entities.Edge;
import com.example.myapplication.entities.Graph;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class FIleService {

    public String loadGraph(Uri uri,Context context) throws IOException {

        String data = this.readTextFromUri(uri, context);
        Log.d("taggg", data);

        return data;
    }

    public void saveGraph(Context context, Graph graph, Uri path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for(Edge edge: graph.edgeList){
            stringBuilder.append(edge.getVertex1().getNumber() + " ");
            stringBuilder.append(edge.getVertex2().getNumber() + " ");
            stringBuilder.append(edge.getWeight() + "\n");
        }
        String graphString = stringBuilder.toString();
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().
                    openFileDescriptor(path, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(graphString.getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private static final int PICK_PDF_FILE = 2;

    private String readTextFromUri(Uri uri, Context context) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     context.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }


}
