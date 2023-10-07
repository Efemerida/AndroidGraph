package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.entities.Edge;
import com.example.myapplication.entities.Vertex;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        float touchY;
        Bitmap bitmap;
        Bitmap all;

        public List<Vertex> points = new ArrayList<>();
        public List<Edge> edges = new ArrayList<>();

        public Edge currEdge = null;

        Canvas myCanvas;

        public MyView(Context context) {
            super(context);
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            bitmap = Bitmap.createBitmap(100,100,config);
            bitmap.eraseColor(Color.BLACK);
            all = Bitmap.createBitmap(1000,1000,config);
            myCanvas = new Canvas(all);
        }

        @Override
        protected void onDraw(Canvas canvas){
            for(Vertex point: points) {
                Paint paint1 = new Paint();
                paint1.setColor(Color.RED);
                paint1.setAntiAlias(true);
                canvas.drawCircle(point.getX(), point.getY(), 50, paint1);
            }

            for(Edge edge:edges){
                Paint paint1 = new Paint();
                paint1.setColor(Color.RED);
                paint1.setStrokeWidth(23);
                canvas.drawLine(edge.getVertex1().getX(),
                        edge.getVertex1().getY(),
                        edge.getVertex2().getX(),
                        edge.getVertex2().getY(),
                        paint1);
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

        @Override
        public boolean onTouchEvent(MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                if(ActionsAdapter.currentStates.equals(States.ADD_POINT)) {
                    touchX = event.getX();
                    touchY = event.getY();
                    points.add(new Vertex((int) touchX, (int) touchY, 50, 0));
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
                            currEdge.setVertex2(tmp);
                            edges.add(currEdge);
                            currEdge = null;
                            invalidate();
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
            }
            return true;
        }
    }


    /*class MyView extends SurfaceView {

        private final SurfaceHolder surfaceHolder;

        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        public MyView(Context context) {
            super(context);
            surfaceHolder = getHolder();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                if(surfaceHolder.getSurface().isValid()){
                    Canvas canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.BLACK);
                    canvas.drawCircle(event.getX(), event.getY(), 50, paint);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            return false;
        }

    }*/
}