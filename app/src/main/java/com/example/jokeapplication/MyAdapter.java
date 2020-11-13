package com.example.jokeapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }


    public void updateData(List<ListItem> newItem){

        DiffUtilCallback diffUtilCallback=new DiffUtilCallback(listItems,newItem);
        DiffUtil.DiffResult diffResult=DiffUtil.calculateDiff(diffUtilCallback);

        listItems.clear();
        listItems.addAll(newItem);
        diffResult.dispatchUpdatesTo(this);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem=listItems.get(position);
        holder.textType.setText(listItem.getType());
        holder.textSetup.setText(listItem.getSetup());
        holder.textPunchline.setText(listItem.getPunchline());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textType,textSetup,textPunchline;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textType=itemView.findViewById(R.id.textType);
            textSetup=itemView.findViewById(R.id.textSetup);
            textPunchline=itemView.findViewById(R.id.textPunchline);
        }



    }

}
