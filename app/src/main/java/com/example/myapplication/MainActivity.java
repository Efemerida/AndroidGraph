package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entities.Edge;
import com.example.myapplication.entities.Graph;
import com.example.myapplication.entities.Vertex;
import com.example.myapplication.services.FIleService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    public static ActivityResultLauncher<String[]> launcher;

    public static ActivityResultLauncher<String> launcherSave;

    public static Uri pathSave;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MainActivity.launcher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(), url -> {
                    FIleService fIleService = new FIleService();
                    MainActivity.MyView view1 = BlankFragment.view;
                    try {
                        String graphCode = fIleService.loadGraph(url, this);
                        view1.loadGraph(Graph.loadGraph(graphCode));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        MainActivity.launcherSave = registerForActivityResult(
                new ActivityResultContracts.CreateDocument(), url -> {
                    FIleService fIleService = new FIleService();
                    MainActivity.MyView view1 = BlankFragment.view;
                    try {
                        Log.d("taggg", url.getPath());
                        Log.d("taggg", url.getEncodedPath() );
                        Log.d("taggg", url.getPath().substring(9));
                        fIleService.saveGraph(this, view1.getGraph(), url);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    pathSave = url;
                }
        );




        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Drawable drawable1 = getDrawable(R.drawable.addpoint);
        Drawable drawable2 = getDrawable(R.drawable.addline);
        Drawable drawable3 = getDrawable(R.drawable.deleteline);
        Drawable drawable4 = getDrawable(R.drawable.deletepoint);
        Drawable drawable5 = getDrawable(R.drawable.clear);
        Drawable drawable6 = getDrawable(R.drawable.upload);
        Drawable drawable7 = getDrawable(R.drawable.download);
        Drawable drawable8 = getDrawable(R.drawable.sumpoints);
        Drawable drawable9 = getDrawable(R.drawable.sumlines);
        Drawable drawable10 = getDrawable(R.drawable.hasconnection);
        Drawable drawable11 = getDrawable(R.drawable.wt);
        List<Drawable> imageViews = new ArrayList<>();
        imageViews.add(drawable1);
        imageViews.add(drawable2);
        imageViews.add(drawable3);
        imageViews.add(drawable4);
        imageViews.add(drawable5);
        imageViews.add(drawable6);
        imageViews.add(drawable7);
        imageViews.add(drawable8);
        imageViews.add(drawable9);
        imageViews.add(drawable10);
        imageViews.add(drawable11);
        ActionsAdapter.OnStateClickListener onStateClickListener = new ActionsAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(int position) {
                Log.d("taggg", "pos is " + String.valueOf(ActionsAdapter.currentStates));
                ActionsAdapter.currentStates = States.getState(position);
            }
        };
        recyclerView.setAdapter(new ActionsAdapter(this, imageViews, onStateClickListener));



    }

    public static class MyView extends View {
        float touchX;
        EditText txt;
        float touchY;
        Bitmap bitmap;
        Bitmap all;

        public List<Vertex> points = new ArrayList<>();
        public List<Edge> edges = new ArrayList<>();

        public static Edge currEdge = null;

        public static Vertex vertexTmp = null;

        Canvas myCanvas;


        public MyView(Context context) {

            super(context);

            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            bitmap = Bitmap.createBitmap(100,100,config);
            bitmap.eraseColor(Color.BLACK);
            all = Bitmap.createBitmap(1000,1000,config);
            myCanvas = new Canvas(all);
        }
        public String collectInput(EditText edt){
            String getInput=edt.getText().toString();
            return getInput;
        }
        public void collectAndVerifyIntInput(EditText edt,Edge edge){
                String ref=this.collectInput(edt);
                Log.d("errors"," ref is"+ref);
                if(ref == null || ref.trim().equals("")|| ref.trim().equals("0")){
                    Toast.makeText(getContext(),R.string.remind_weight,Toast.LENGTH_SHORT);
                }else{
                    edge.setWeight(Integer.parseInt(ref));
                }
        }
        @Override
        protected void onDraw(Canvas canvas){
            Paint paintVertex = new Paint();
            paintVertex.setColor(Color.RED);
            paintVertex.setAntiAlias(true);
            for(Vertex point: points) {
                canvas.drawCircle(point.getX(), point.getY(), 50, paintVertex);
            }
            Paint paintText= new Paint();
            paintText.setColor(Color.YELLOW);
            paintText.setTextSize(70);
            List<Integer> textCoords=new ArrayList<>();
            Paint paintLine = new Paint();
            paintLine.setColor(Color.RED);
            paintLine.setStrokeWidth(23);
            for(Edge edge:edges){



                canvas.drawLine(edge.getVertex1().getX(),
                        edge.getVertex1().getY(),
                        edge.getVertex2().getX(),
                        edge.getVertex2().getY(),
                        paintLine);
            }
            int offset=20;
            for(Vertex vertex:points){
                canvas.drawText(""+vertex.getNumber(),vertex.getX()-
                                (vertex.getNumber()>=10?(offset*(1+(Utils.digitInNumber(vertex.getNumber())-1)/10)):0)
                        ,vertex.getY()+offset,paintText);
            }
            paintText.setTextSize(100);

            for (Edge edge:edges){
                textCoords=edge.getPlaceForWeight();
                canvas.drawText(""+edge.getWeight(),textCoords.get(0),textCoords.get(1),paintText);
            }
            TextView textView = new TextView(this.getContext());
            textView.setText("hehehhehe");
            textView.setTextColor(Color.BLACK);
            textView.draw(canvas);
        }
        public void clear(){
            edges.clear();
            points.clear();
            invalidate();
        }

        public void loadGraph(Graph graph){
            this.points.clear();
            this.edges.clear();
            this.edges.addAll(graph.edgeList);
            this.points.addAll(graph.vertices);
            Log.d("taggg", "list is " + this.points);
            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                if(ActionsAdapter.currentStates==null){
                    return false;
                }
                if(ActionsAdapter.currentStates.equals(States.ADD_POINT)) {
                    touchX = event.getX();
                    touchY = event.getY();
                    int number=1;
                    if(!points.isEmpty()){
                        number=points.stream().reduce((x,y)->x.getNumber()>y.getNumber()?x:y).get().getNumber()+1;
                    }
                    points.add(new Vertex((int) touchX, (int) touchY, 50, number));
                    invalidate();
                }
                else if(ActionsAdapter.currentStates.equals(States.DELETE_POINT)){
                    Vertex tmp  = null;
                    for(Vertex point: points){

                        if(Math.abs(point.getX()-event.getX())<=point.getRadius()){
                            if(Math.abs(point.getY()-event.getY())<=point.getRadius()){
                                tmp = point;
                            }
                        }

                    }
                    if(tmp!=null){
                        Edge tmpEdge = null;
                        for(int i = 0; i < edges.size(); i++){
                            if(edges.get(i).getVertex1().equals(tmp) || edges.get(i).getVertex2().equals(tmp)){
                                edges.remove(i--);
                            }
                        }

                        for(Edge edge: edges){
                            if(edge.getVertex1().equals(tmp) || edge.getVertex2().equals(tmp)){
                                tmpEdge = edge;
                            }
                        }
                        if(tmpEdge!=null) edges.remove(tmpEdge);
                        points.remove(tmp);
                    }
                    invalidate();
                }

                else if(ActionsAdapter.currentStates.equals(States.ADD_LINE)){
//                    Log.d("taggg", "1 is " + event.getX());
                    Vertex tmp  = null;
                    for(Vertex point: points){

                        if(Math.abs(point.getX()-event.getX())<=point.getRadius()){
                            if(Math.abs(point.getY()-event.getY())<=point.getRadius()){
                                tmp = point;
                            }
                        }

                    }
                    if(tmp!=null) {
                        if (currEdge == null) {
                            currEdge = new Edge();
                            currEdge.setVertex1(tmp);
                        } else {
                            if(tmp.equals(currEdge.getVertex1())) return true;

//                            ArrayList<CharSequence> arrayList=new ArrayList<>();
//                            ArrayAdapter <CharSequence> adapter;


                            AlertDialog.Builder alertweight = new AlertDialog.Builder(this.getContext());
                            final EditText edt=new EditText(this.getContext());

                            edt.setInputType(InputType.TYPE_CLASS_NUMBER);
                            alertweight.setTitle(R.string.alert_to_add_weight_title);
                            alertweight.setView(edt);

                            LinearLayout layoutalert =new LinearLayout(this.getContext());
                            layoutalert.setOrientation(LinearLayout.VERTICAL);
                            layoutalert.addView(edt);
                            alertweight.setView(layoutalert);
                            currEdge.setVertex2(tmp);
                            alertweight.setPositiveButton(R.string.accept_rus, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    txt=edt;
                                    collectAndVerifyIntInput(txt,currEdge);

                                    edges.add(currEdge);
                                    currEdge = null;
                                    invalidate();
                                }
                            });
                            alertweight.setNegativeButton(R.string.deny_rus, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    currEdge = null;
                                    invalidate();
                                }
                            });
                            alertweight.show();




                        }
                    }
                }

                else if(ActionsAdapter.currentStates.equals(States.DELETE_LINE)){
                    Vertex tmp  = null;
                    for(Vertex point: points){

                        if(Math.abs(point.getX()-event.getX())<=point.getRadius()){
                            if(Math.abs(point.getY()-event.getY())<=point.getRadius()){
                                tmp = point;
                            }
                        }
                    }

                    if(tmp!=null){
                        Edge tmpEdge = null;
                        for(Edge edge:edges){
                            if(edge.getVertex1().equals(tmp) || edge.getVertex2().equals(tmp)){
                                if(currEdge==null) {
                                    currEdge = new Edge();
                                    currEdge.setVertex1(tmp);
                                }
                                else{
                                    Log.d("taggg", String.valueOf(edge.getVertex1().getX()));
                                    if(edge.getVertex1().equals(tmp)){
                                        if(edge.getVertex2().equals(currEdge.getVertex1())){
                                            tmpEdge = edge;
                                        }
                                    }
                                    if(edge.getVertex2().equals(tmp)){
                                        if(edge.getVertex1().equals(currEdge.getVertex1())){
                                            tmpEdge=edge;
                                        }
                                    }
                                }
                            }
                        }
                        if(tmpEdge!=null){
                            edges.remove(tmpEdge);
                            currEdge=null;
                            invalidate();
                        }
                    }

                }
                else if (ActionsAdapter.currentStates.equals(States.CHECK_POINT)) {
                    Vertex tmp = null;
                    for (Vertex point : points) {
                        if (Math.abs(point.getX() - event.getX()) <= point.getRadius()) {
                            if (Math.abs(point.getY() - event.getY()) <= point.getRadius()) {
                                tmp = point;
                            }
                        }
                    }

                    if (tmp != null) {

                        if(vertexTmp==null){
                            vertexTmp = tmp;
                        }
                        else if(vertexTmp==tmp){
                            return true;
                        }
                        else{

                            if(this.getGraph().isAdjacent(vertexTmp, tmp))
                                Toast.makeText(getContext(), "Вершины смежные", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getContext(), "Вершины не смежные", Toast.LENGTH_LONG).show();

                            vertexTmp = null;
                        }

                    }
                }

                else if (ActionsAdapter.currentStates.equals(States.CHECK_WEIGHT_2)) {
                    Vertex tmp = null;
                    for (Vertex point : points) {
                        if (Math.abs(point.getX() - event.getX()) <= point.getRadius()) {
                            if (Math.abs(point.getY() - event.getY()) <= point.getRadius()) {
                                tmp = point;
                            }
                        }
                    }

                    if (tmp != null) {

                        if(vertexTmp==null){
                            vertexTmp = tmp;
                        }
                        else if(vertexTmp==tmp){
                            return true;
                        }
                        else{

                            int weight = this.getGraph().getWeight(vertexTmp, tmp);
                            if(weight==0)
                                Toast.makeText(getContext(), "Ребра не существует", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getContext(), String.valueOf(weight), Toast.LENGTH_LONG).show();

                            vertexTmp = null;
                        }
                    }
                }
            }

            return true;
        }

        public Graph getGraph(){
            Graph graph = new Graph();
            for(Edge edge: edges){
                graph.edgeList.add(edge);
            }
            for(Vertex vertex: points){
                graph.vertices.add(vertex);
            }
            return graph;
        }

    }



}