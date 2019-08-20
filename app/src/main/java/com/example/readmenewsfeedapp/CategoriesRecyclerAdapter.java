package com.example.readmenewsfeedapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readmenewsfeedapp.model.Article;

import java.util.ArrayList;

public class CategoriesRecyclerAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int mCategory;

    // constants for view holder type
    private static final int TYPE_NEWS = 0;
    private static final int TYPE_LOADING = 1;

    private ArrayList<Article> mData;

    // Item and button item listener
    private final OnCategoryItemClick listener;
    private final OnSaveButtonItemClick saveButtonListener;

    public CategoriesRecyclerAdapter(ArrayList<Article> data,
                                     OnCategoryItemClick click, OnSaveButtonItemClick buttonClick, int category) {
        mData = data;
        listener = click;
        saveButtonListener = buttonClick;
        mCategory = category;
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder.getItemViewType() == 0) {

                ((NormalViewHolder) holder).mHeadLine.setText(mData.get(position).getHeadline());
                ((NormalViewHolder) holder).mSection.setText(mData.get(position).getSectionName());

                if (mCategory == 5) {
                    ((NormalViewHolder) holder).saveButton.setText(R.string.delete_button);
                }

                // on Save button pressed
                ((NormalViewHolder) holder).saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveButtonListener.onButtonClick(mData.get(position), position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size() - 1 && mCategory != 5) {
            return TYPE_LOADING;
        } else {
            return TYPE_NEWS;
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        TextView mHeadLine;
        TextView mSection;
        Button saveButton;

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
            saveButton = itemView.findViewById(R.id.button_item_save);
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

    public interface OnSaveButtonItemClick {
        void onButtonClick(Article position, int itemPosition);
    }

    //todo function to delete item from list
    public void deleteItem(int position){
        mData.remove(position);
    }

    public void addItems(ArrayList<Article> items) {
        if (mData != null){
            mData.addAll(items);
            notifyDataSetChanged();
        }
    }
}
