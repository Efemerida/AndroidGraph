package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entities.Graph;
import com.example.myapplication.services.FIleService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.FunctionsAdapterHolder> {

    public interface OnStateClickListener{
        void onStateClick(int position);
    }

    public static States currentStates = States.ADD_POINT;


    private final LayoutInflater inflater;
    List<Drawable> imageViews;
    FIleService fIleService;

    private final OnStateClickListener onStateClickListener;

    private Context context;

    public  ActionsAdapter(Context context, List<Drawable> imageViews, OnStateClickListener onStateClickListener){
        this.inflater = LayoutInflater.from(context);
        this.imageViews = imageViews;
        this.onStateClickListener = onStateClickListener;
        fIleService = new FIleService();
        this.context = context;
    }

    @NonNull
    @Override
    public FunctionsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.available_actions, parent, false);
        return new ActionsAdapter.FunctionsAdapterHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull FunctionsAdapterHolder holder, int position) {
        Log.d("taggg", String.valueOf(imageViews.size()));
        holder.kartinka.setImageDrawable(imageViews.get(position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==5){
                    MainActivity.MyView view1 = BlankFragment.view;
                    MainActivity.launcherSave.launch("graph");


                    /*try {
                        MainActivity.MyView view1 = BlankFragment.view;
                        fIleService.saveGraph(context, view1.getGraph());
                        String g = fIleService.loadGraph(file,context);
                        Log.d("taggg", "ggg iss " + g);
                        Graph graph = Graph.loadGraph(g);
                        Log.d("taggg", "graph is " + graph.toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }*/
                }
                if(position==4){
                    MainActivity.MyView view1 = BlankFragment.view;
                    view1.clear();
                }
                else {
                    ActionsAdapter.currentStates = States.getState(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    class FunctionsAdapterHolder extends RecyclerView.ViewHolder {

        ImageView kartinka;

        public FunctionsAdapterHolder(@NonNull View itemView) {
            super(itemView);
            kartinka = itemView.findViewById(R.id.img);
        }




    }
}
