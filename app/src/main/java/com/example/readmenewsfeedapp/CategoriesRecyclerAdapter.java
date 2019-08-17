package com.example.readmenewsfeedapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readmenewsfeedapp.model.Article;

import java.util.ArrayList;

public class CategoriesRecyclerAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Article> mData;
    private final OnCategoryItemClick listener;

    public CategoriesRecyclerAdapter(ArrayList<Article> data,
                                     OnCategoryItemClick click) {
        mData = data;
        listener = click;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_list_category, parent, false);
            return new NormalViewHolder(v);
        } else {
            View loadingView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(loadingView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder instanceof NormalViewHolder) {
//                ((NormalViewHolder) holder).mHeadLine.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        listener.onItemClick(mData.get(position));
//                    }
//                });

                ((NormalViewHolder) holder).mHeadLine.setText(mData.get(position).getHeadline());
                ((NormalViewHolder) holder).mSection.setText(mData.get(position).getSectionName());
            }
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        TextView mHeadLine;
        TextView mSection;

        NormalViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(mData.get(getAdapterPosition()));
                }
            });

            mHeadLine = itemView.findViewById(R.id.category_headline);
            mSection = itemView.findViewById(R.id.category_section);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View view) {
            super(view);
        }
    }

    public interface OnCategoryItemClick {
        void onItemClick(Article position);
    }
}
