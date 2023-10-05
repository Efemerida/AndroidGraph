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
        recyclerView.setAdapter(new ActionsAdapter(this, imageViews));


    }

    public static class MyView extends View {
        float touchX;
        float touchY;
        Bitmap bitmap;
        Bitmap all;

        public List<Point> points = new ArrayList<>();


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
            for(Point point: points) {
                Paint paint1 = new Paint();
                paint1.setColor(Color.RED);
                paint1.setAntiAlias(true);
                canvas.drawCircle(point.x, point.y, 100, paint1);
            }
            TextView textView = new TextView(this.getContext());
            textView.setText("hehehhehe");
            textView.setTextColor(Color.BLACK);
            textView.draw(canvas);
        }


        @Override
        public boolean onTouchEvent(MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                touchX = event.getX();
                touchY = event.getY();
                points.add(new Point((int) touchX, (int) touchY));
                invalidate();
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