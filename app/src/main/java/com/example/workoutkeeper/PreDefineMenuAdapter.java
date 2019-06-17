package com.example.workoutkeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class PreDefineMenuAdapter extends
        RecyclerView.Adapter<PreDefineMenuAdapter.PreDefineMenuViewHolder>{

    private LayoutInflater mInflater;
    private final LinkedList<String> mTitleList;
    private final LinkedList<Integer> mIconList;

    public PreDefineMenuAdapter (Context context,
                                 LinkedList<String> titleList,
                                 LinkedList<Integer> iconList) {
        mInflater = LayoutInflater.from(context);
        this.mTitleList = titleList;
        this.mIconList = iconList;
    }


    class PreDefineMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView titleItemView;
        public final ImageView iconItemView;
        final PreDefineMenuAdapter mAdapter;

        public PreDefineMenuViewHolder(@NonNull View itemView, PreDefineMenuAdapter adapter) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.program_title);
            iconItemView = itemView.findViewById(R.id.program_icon);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


    @NonNull
    @Override
    public PreDefineMenuAdapter.PreDefineMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.programlist_item, parent, false);
        return new PreDefineMenuViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PreDefineMenuAdapter.PreDefineMenuViewHolder holder, int position) {
        String mCurrentTitle = mTitleList.get(position);
        Integer mCurrentIcon = mIconList.get(position);
        holder.titleItemView.setText(mCurrentTitle);
        holder.iconItemView.setImageResource(mCurrentIcon);
    }

    @Override
    public int getItemCount() {
        return mTitleList.size();
    }
}
