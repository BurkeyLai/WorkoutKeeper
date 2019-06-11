package com.example.workoutkeeper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class TrainingListAdapter extends
        RecyclerView.Adapter<TrainingListAdapter.TrainingViewHolder> {

    private final LinkedList<String> mWordList;
    private final LinkedList<String> mDescriptionList;
    private LayoutInflater mInflater;
    public static final String EXTRA_MESSAGE = "com.example.droidcafe.extra.MESSAGE";

    public TrainingListAdapter(Context context,
                           LinkedList<String> wordList,
                           LinkedList<String> descriptionList ) {

        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
        this.mDescriptionList = descriptionList;
    }

    class TrainingViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView wordItemView;
        public final TextView descriptionItemView;
        final TrainingListAdapter mAdapter;

        public TrainingViewHolder(View itemView, TrainingListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.training_word);
            descriptionItemView = itemView.findViewById(R.id.training_description);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            String ITEM = mWordList.get(mPosition);
            Intent intent = new Intent(view.getContext(), SetsAndRepsActivity.class);
            intent.putExtra(EXTRA_MESSAGE, ITEM);
            view.getContext().startActivity(intent);
        }

    }
    @NonNull
    @Override
    public TrainingListAdapter.TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.traininglist_item, parent, false);
        return new TrainingViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingListAdapter.TrainingViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
        mCurrent = mDescriptionList.get(position);
        holder.descriptionItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
