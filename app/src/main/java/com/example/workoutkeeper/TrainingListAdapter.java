package com.example.workoutkeeper;

import android.app.Activity;
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
    private final LinkedList<String> mYoutubeList;
    private LayoutInflater mInflater;
    private int schedule;
    //public static final String EXTRA_MESSAGE = "com.example.workoutkeeper.extra.MESSAGE";
    //public static final String Schedule_ID = "com.example.workoutkeeper.schedule.ID";

    public TrainingListAdapter(Context context,
                           int n,
                           LinkedList<String> wordList,
                           LinkedList<String> descriptionList,
                               LinkedList<String> youtubeList ) {

        mInflater = LayoutInflater.from(context);
        schedule = n;
        this.mWordList = wordList;
        this.mDescriptionList = descriptionList;
        this.mYoutubeList = youtubeList;
    }

    class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            int ID = schedule;
            String ITEM = mWordList.get(mPosition);

            Intent intent = new Intent(view.getContext(), SetsAndRepsActivity.class);
            intent.putExtra("Title_KEY", ITEM);
            intent.putExtra("Schedule_KEY", Integer.toString(ID));
            intent.putExtra("youtube_KEY", mYoutubeList.get(mPosition));

            if (ID == 0){
                view.getContext().startActivity(intent);
            } else if (ID == 1) {
                ((Activity) view.getContext()).startActivityForResult(intent, 1);
            }


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
