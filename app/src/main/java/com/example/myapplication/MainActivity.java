package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.entities.Edge;
import com.example.myapplication.entities.Vertex;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Drawable drawable = getDrawable(R.drawable.addpoint);
        List<Drawable> imageViews = new ArrayList<>();
        imageViews.add(drawable);
        imageViews.add(drawable);
        imageViews.add(drawable);
        imageViews.add(drawable);
        imageViews.add(drawable);
        imageViews.add(drawable);
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