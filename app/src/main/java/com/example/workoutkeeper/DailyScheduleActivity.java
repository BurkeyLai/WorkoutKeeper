package com.example.workoutkeeper;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class DailyScheduleActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private RecyclerView mDailyRecyclerView;
    private TextView mHintText;
    private ScheduleListAdapter mDailyAdapter;
    private ArrayList<ScheduleListItem> mDailyData = new ArrayList<>();
    public static final int TEXT_REQUEST = 1;
    private String mAction, mWeights, mUnit, mSets, mReps, mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        if (savedInstanceState != null) {
            mDailyData = savedInstanceState.getParcelableArrayList("daily_data_list");
        }

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.container);
        //TextView tabText = (TextView) findViewById(android.R.id.tabcontent);

        addNewTab("Chest", R.drawable.ic_chest);
        addNewTab("Shoulder", R.drawable.ic_shoulder);
        addNewTab("Back", R.drawable.ic_back);
        addNewTab("ABS", R.drawable.ic_abs);
        addNewTab("Leg", R.drawable.ic_leg);
        addNewTab("Arm", R.drawable.ic_arm);

        int gridColumnCount = 1;
        mDailyRecyclerView = findViewById(R.id.daily_recyclerView);
        mHintText = findViewById(R.id.hint_text);
        mDailyRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
        mDailyAdapter = new ScheduleListAdapter(this, mDailyData);
        mDailyRecyclerView.setAdapter(mDailyAdapter);

        int swipeDirs;
        if(gridColumnCount > 1){
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                /*ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT*/
                swipeDirs) {
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
                Collections.swap(mDailyData, from, to);
                mDailyAdapter.notifyItemMoved(from, to);
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
                mDailyData.remove(viewHolder.getAdapterPosition());
                // Show up the hint text when list is empty
                if (mDailyData.size() == 0) {
                    mDailyRecyclerView.setVisibility(View.GONE);
                    mHintText.setVisibility(View.VISIBLE);
                }
                // Notify the adapter.
                mDailyAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mDailyRecyclerView);

        // Show up the hint text when list is empty
        if (mDailyData.size() == 0) {
            mDailyRecyclerView.setVisibility(View.GONE);
            mHintText.setVisibility(View.VISIBLE);
        }

    }

    public void addNewTab(String tabName, int ID){

        Bundle bundle = new Bundle();
        bundle.putString("BodyPart_KEY", tabName);
        mTabHost.addTab(mTabHost.newTabSpec(tabName).setIndicator("", getResources().getDrawable(ID)),
                BodyPartFragment.class, bundle);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {

                // When list is not empty, show the card view
                mDailyRecyclerView.setVisibility(View.VISIBLE);
                mHintText.setVisibility(View.GONE);

                Bundle bundle = data.getExtras(); // Get intent from SetsAndRepsActivity

                if(bundle != null) {
                    mAction = bundle.getString("Re_Action_Key", "");
                    mWeights = bundle.getString("Re_Weights_Key", "");
                    mUnit = bundle.getString("Re_Unit_Key", "");
                    mSets = bundle.getString("Re_Sets_Key", "");
                    mReps = bundle.getString("Re_Reps_Key", "");
                    mTime = bundle.getString("Re_Time_Key", "");
                }

                mDailyData.add(new ScheduleListItem(mAction, mWeights, mUnit, mSets, mReps, mTime));
                mDailyAdapter.notifyDataSetChanged();
            }
        }
    }

    /*
    public void nextClicked (View view) {

    }
    */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("daily_data_list", mDailyData);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (mDailyData.size() != 0) {
            builder.setTitle("Do tou want to exit?");
            builder.setMessage("You will lost your scheduled list.");
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    builder.create();
                }
            });
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    DailyScheduleActivity.super.onBackPressed();
                }
            });
            builder.show();
        } else {
            finish();
        }
    }

    public void workoutDone(View view) {
        if (mDailyData.size() != 0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Workout done?");
            builder.setMessage("You will lost your scheduled list.");
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    builder.create();
                }
            });
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // **********
                    // Save data
                    // **********
                    DailyScheduleActivity.super.onBackPressed();
                }
            });
            builder.show();
        }
    }
}


