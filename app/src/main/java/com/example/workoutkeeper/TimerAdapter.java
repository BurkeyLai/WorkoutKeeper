package com.example.workoutkeeper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ViewHolder> {
    private ArrayList<TimerItems> TimerData;
    private Context TimerContext;
    private CountDownTimer cdt;
    // Constructor that passes in the sports data and the context.
    TimerAdapter(Context context, ArrayList<TimerItems> timerData) {
        this.TimerContext = context;
        this.TimerData = timerData;
    }

    // Required method for creating the viewholder objects.
    @Override
    public TimerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(TimerContext).
                inflate(R.layout.timer_list_item, parent, false));
    }

    // Required method that binds the data to the viewholder.
    @Override
    public void onBindViewHolder(TimerAdapter.ViewHolder holder, int position) {
        TimerItems currentItem = TimerData.get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return TimerData.size();
    }

    // ViewHolder class that represents each row of data in the RecyclerView.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Member Variables for the TextViews
        private TextView Timer_minute;
        private TextView Timer_second;
        protected Button btn_reset,btn_start;

        // Constructor for the ViewHolder, used in onCreateViewHolder().
        ViewHolder(View itemView) {
            // Initialize the views.
            super(itemView);
            Timer_minute = itemView.findViewById(R.id.timer_minute);
            Timer_second = itemView.findViewById(R.id.timer_second);
            btn_reset = itemView.findViewById(R.id.timer_reset);
            btn_start = itemView.findViewById(R.id.timer_start);
            // Set the OnClickListener to the entire view.
            btn_reset.setTag(1);
            btn_reset.setOnClickListener(this);
            btn_start.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        void bindTo(TimerItems item) {
            // Populate the textviews with data.
            Timer_minute.setText(item.getMinute());
            Timer_second.setText(item.getSecond());

        }

        // Handle click to show ProgressActivity.
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            if (view.getId() == btn_start.getId()) {
                Log.d("name",btn_start.getText().toString());
                if (btn_start.getText().toString().equals("start")) {
                    btn_start.setText("pause");
                    final TimerItems currentItem = TimerData.get(getAdapterPosition());
                    final int min = Integer.valueOf(currentItem.getMinute());
                    final int sec = Integer.valueOf(currentItem.getSecond());
                    final int all = (min * 60 + sec) * 1000;
                    //if (cdt != null){cdt.cancel();}

                    if (currentItem.getCdt() == null){
                        currentItem.setReset(all);
                    }

                    cdt = new CountDownTimer(all, 1000) {

                        @Override
                        public void onFinish() {
                            currentItem.setMinute("0");
                            currentItem.setSecond("0");
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onTick(long millisUntilFinished) {
                            long time = Math.round(millisUntilFinished / 1000);
                            long minute = time / 60;
                            long second = time % 60;

                            currentItem.setMinute(String.valueOf(minute));
                            currentItem.setSecond(String.valueOf(second));

                            notifyDataSetChanged();
                        }

                    }.start();
                    currentItem.setCdt(cdt);
                }
                else{
                    final TimerItems currentItem = TimerData.get(getAdapterPosition());
                    Log.d("reset",currentItem.getMinute());
                    CountDownTimer c =currentItem.getCdt();
                    c.cancel();
                    btn_start.setText("start");
                }
            }

            else if (view.getId() == btn_reset.getId()){
                final TimerItems currentItem = TimerData.get(getAdapterPosition());
                int all  =  currentItem.getReset();
                currentItem.setMinute(String.valueOf(all/1000/60));
                currentItem.setSecond(String.valueOf((all/1000)%60));
                notifyDataSetChanged();
                CountDownTimer c = currentItem.getCdt();
                c.cancel();
                btn_start.setText("start");
            }
            else{
                Bundle bundle = new Bundle();
                bundle.putInt("Position", getAdapterPosition());
                Log.d("position",Integer.toString(getAdapterPosition()));
                Intent intent = new Intent(view.getContext(), TimerAddActivity.class);
                intent.putExtras(bundle);
                ((Activity) view.getContext()).startActivityForResult(intent,2);
            }

        }

    }





}
