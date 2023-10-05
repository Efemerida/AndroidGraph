package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.FunctionsAdapterHolder> {

    private final LayoutInflater inflater;
    List<ImageView> imageViews;


    public  ActionsAdapter(Context context, List<ImageView> imageViews){
        this.inflater = LayoutInflater.from(context);
        this.imageViews = imageViews;
    }

    @NonNull
    @Override
    public FunctionsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.available_actions, parent, false);
        return new ActionsAdapter.FunctionsAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionsAdapterHolder holder, int position) {
        holder.kartinka = imageViews.get(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class FunctionsAdapterHolder extends RecyclerView.ViewHolder {

        ImageView kartinka;

        public FunctionsAdapterHolder(@NonNull View itemView) {
            super(itemView);
            kartinka = itemView.findViewById(R.id.img);
        }




    }
}
