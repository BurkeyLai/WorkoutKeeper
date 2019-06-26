package com.example.workoutkeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            String ITEM = mTitleList.get(mPosition);
            String FROM_RECOMMENDED = "yes";

            Bundle bundle = new Bundle();
            bundle.putString("Pos_Key", Integer.toString(mPosition - mIconList.size()));
            bundle.putString("Title_Key", ITEM);
            bundle.putString("From_Key", FROM_RECOMMENDED);

            Intent intent = new Intent(view.getContext(), DailyScheduleActivity.class);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);

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
        if (position < mIconList.size()) {
            Integer mCurrentIcon = mIconList.get(position);
            holder.iconItemView.setImageResource(mCurrentIcon);
        }
        String mCurrentTitle = mTitleList.get(position);
        holder.titleItemView.setText(mCurrentTitle);
    }

    @Override
    public int getItemCount() {
        return mTitleList.size();
    }
}
