package com.duydv.vn.foodappmvc.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duydv.vn.foodappmvc.databinding.ItemFeedbackBinding;
import com.duydv.vn.foodappmvc.model.Feedback;

import java.util.List;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.FeedbackViewHolder> {
    private final List<Feedback> mListFeedback;

    public FeedBackAdapter(List<Feedback> mListFeedback) {
        this.mListFeedback = mListFeedback;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeedbackBinding itemFeedbackBinding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FeedbackViewHolder(itemFeedbackBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = mListFeedback.get(position);
        if(feedback == null){
            return;
        }

        holder.mItemFeedbackBinding.txtEmail.setText(feedback.getEmail());
        holder.mItemFeedbackBinding.txtFeedback.setText(feedback.getFeedback());
    }

    @Override
    public int getItemCount() {
        if(mListFeedback != null){
            return mListFeedback.size();
        }
        return 0;
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder{
        private final ItemFeedbackBinding mItemFeedbackBinding;
        public FeedbackViewHolder(@NonNull ItemFeedbackBinding mItemFeedbackBinding) {
            super(mItemFeedbackBinding.getRoot());
            this.mItemFeedbackBinding = mItemFeedbackBinding;
        }
    }
}
