package com.example.workoutkeeper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    private ArrayList<ScheduleListItem> mDailyData;
    private Context mContext;

    // Constructor that passes in the sports data and the context.
    ScheduleListAdapter(Context context, ArrayList<ScheduleListItem> dailyData) {
        this.mContext = context;
        this.mDailyData = dailyData;
    }

    // Required method for creating the viewholder objects.
    @Override
    public ScheduleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.schedulelist_item, parent, false));
    }

    // Required method that binds the data to the viewholder.
    @Override
    public void onBindViewHolder(ScheduleListAdapter.ViewHolder holder, int position) {
        ScheduleListItem currentItem = mDailyData.get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return mDailyData.size();
    }



    // ViewHolder class that represents each row of data in the RecyclerView.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Member Variables for the TextViews
        private TextView mActionText;
        private TextView mWeightsText;
        private TextView mSetsText;
        private TextView mRepsText;
        private TextView mUnitText;
        private TextView mTimeText;

        // Constructor for the ViewHolder, used in onCreateViewHolder().
        ViewHolder(View itemView) {
            // Initialize the views.
            super(itemView);
            mActionText = itemView.findViewById(R.id.schedule_action_text);
            mWeightsText = itemView.findViewById(R.id.schedule_weights_text);
            mSetsText = itemView.findViewById(R.id.schedule_sets_text);
            mRepsText = itemView.findViewById(R.id.schedule_reps_text);
            mUnitText = itemView.findViewById(R.id.schedule_unit_text);
            mTimeText = itemView.findViewById(R.id.schedule_time_text);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(ScheduleListItem item) {
            // Populate the textviews with data.
            mActionText.setText(item.getAction());
            mWeightsText.setText(item.getWeights());
            mSetsText.setText(item.getSets());
            mRepsText.setText(item.getReps());
            mUnitText.setText(item.getUnit());
            mTimeText.setText(item.getTime());
        }

        // Handle click to show ProgressActivity.
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {

            //Log.d("YOOOOOOOOO", "Clicked!!!");

            ScheduleListItem currentItem = mDailyData.get(getAdapterPosition());
            Intent processIntent = new Intent(mContext, ProgressActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Action_Key", currentItem.getAction());
            bundle.putString("Weights_Key", currentItem.getWeights());
            bundle.putString("Unit_Key", currentItem.getUnit());
            bundle.putString("Sets_Key", currentItem.getSets());
            bundle.putString("Reps_Key", currentItem.getReps());
            bundle.putString("Time_Key", currentItem.getTime());

            processIntent.putExtras(bundle);
            mContext.startActivity(processIntent);
        }
    }
}
