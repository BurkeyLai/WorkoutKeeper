package com.example.workoutkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class TimerActivity extends AppCompatActivity {

    public static ArrayList<TimerItems> TimerData = new ArrayList<>();

    private RecyclerView TimerView;
    private TimerAdapter mTimerAdapter;

    private int M,S,P;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Bundle info = this.getIntent().getExtras();

        TimerView = findViewById(R.id.Timer_recyclerview);
        mTimerAdapter = new TimerAdapter(this, TimerData);
        TimerView.setAdapter(mTimerAdapter);
        TimerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT ) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(TimerData, from, to);
                mTimerAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.

                TimerItems currentItem = TimerData.get(viewHolder.getAdapterPosition());

                CountDownTimer c = currentItem.getCdt();
                if (c != null) {
                    c.cancel();
                }

                TimerData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                mTimerAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(TimerView);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void Timeradd(View view) {
        Intent intent = new Intent(this, TimerAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Position", 0);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras(); // Get intent from SetsAndRepsActivity

                if(bundle != null) {
                    M = bundle.getInt("Minute");
                    S = bundle.getInt("Second");
                    Log.d("MIN",Integer.toString(M)+"SD"+Integer.toString(S));
                }

                TimerData.add(new TimerItems(Integer.toString(M),Integer.toString(S)));
                mTimerAdapter.notifyDataSetChanged();

            }
        }
        else if (requestCode == 2 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras(); // Get intent from SetsAndRepsActivity

            if(bundle != null) {
                M = bundle.getInt("Minute");
                S = bundle.getInt("Second");
                P = bundle.getInt("Position");
                Log.d("MIN",Integer.toString(P));

            }
            TimerItems currentItem = TimerData.get(P);
            currentItem.setMinute(Integer.toString(M));
            currentItem.setSecond(Integer.toString(S));
            mTimerAdapter.notifyDataSetChanged();
        }
    }


}
