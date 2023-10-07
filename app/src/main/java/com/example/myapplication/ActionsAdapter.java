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

import java.util.List;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.FunctionsAdapterHolder> {

    public interface OnStateClickListener{
        void onStateClick(int position);
    }

    public static States currentStates = States.ADD_POINT;


    private final LayoutInflater inflater;
    List<Drawable> imageViews;

    private final OnStateClickListener onStateClickListener;


    public  ActionsAdapter(Context context, List<Drawable> imageViews, OnStateClickListener onStateClickListener){
        this.inflater = LayoutInflater.from(context);
        this.imageViews = imageViews;
        this.onStateClickListener = onStateClickListener;


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
                ActionsAdapter.currentStates = States.getState(position);
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
