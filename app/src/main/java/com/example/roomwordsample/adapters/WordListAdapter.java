package com.example.roomwordsample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomwordsample.databinding.LayoutWordItemBinding;
import com.example.roomwordsample.models.Word;

public class WordListAdapter extends ListAdapter<Word, WordListAdapter.WordHolder> {

    private static final DiffUtil.ItemCallback<Word> DIFFCALLBACK = new DiffUtil.ItemCallback<Word>() {
        @Override
        public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem.getWord().equals(newItem.getWord());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem.equals(newItem);
        }
    };

    public WordListAdapter() {
        super(DIFFCALLBACK);
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordHolder(
                LayoutWordItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        holder.bindWith(getItem(position).getWord());
    }

    static class WordHolder extends RecyclerView.ViewHolder{

        private final LayoutWordItemBinding binding;

        public WordHolder(@NonNull LayoutWordItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindWith(String word){
            this.binding.txtWord.setText(word);
        }
    }
}
