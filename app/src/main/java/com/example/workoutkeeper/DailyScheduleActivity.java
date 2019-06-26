package com.example.workoutkeeper;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private String mPreDefine, mFromRecommended = "no", mCustomizedTitle;
    private Integer mPreDefinePos, mNumberOfCustomizedRecipe = 20;
    private Button mDoneButton, mSaveButton;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "CustomizedRecipeDataSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);


        if (savedInstanceState != null) {
            mDailyData = savedInstanceState.getParcelableArrayList("daily_data_list");
        }

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            mPreDefinePos = Integer.parseInt(bundle.getString("Pos_Key"));
            mPreDefine = bundle.getString("Title_Key");
            mFromRecommended = bundle.getString("From_Key");
        }

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.container);

        addNewTab("chest", R.drawable.ic_chest);
        addNewTab("shoulder", R.drawable.ic_shoulder);
        addNewTab("back", R.drawable.ic_back);
        addNewTab("abs", R.drawable.ic_abs);
        addNewTab("leg", R.drawable.ic_leg);
        addNewTab("arm", R.drawable.ic_arm);

        int gridColumnCount = 1;

        mDailyRecyclerView = findViewById(R.id.daily_recyclerView);
        mHintText = findViewById(R.id.hint_text);
        mDoneButton = findViewById(R.id.done_button);
        mSaveButton = findViewById(R.id.save_button);
        mDailyRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
        mDailyAdapter = new ScheduleListAdapter(this, mDailyData);
        mDailyRecyclerView.setAdapter(mDailyAdapter);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // If we need to set recommended recipe...
        if (mFromRecommended.equals("yes")) {
            mDailyData.clear();
            /*
            mDailyRecyclerView.setVisibility(View.VISIBLE);
            mHintText.setVisibility(View.GONE);
            mDoneButton.setVisibility(View.VISIBLE);
            mSaveButton.setVisibility(View.GONE);
            */
            setRecommendedRecipe(mPreDefinePos, mPreDefine);
        }

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
                    mDoneButton.setVisibility(View.GONE);
                    mSaveButton.setVisibility(View.GONE);
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
            mDoneButton.setVisibility(View.GONE);
            mSaveButton.setVisibility(View.GONE);
        } else if (mFromRecommended.equals("yes")) {
            mDailyRecyclerView.setVisibility(View.VISIBLE);
            mHintText.setVisibility(View.GONE);
            mDoneButton.setVisibility(View.VISIBLE);
            mSaveButton.setVisibility(View.GONE);
        } else {
            mDailyRecyclerView.setVisibility(View.VISIBLE);
            mHintText.setVisibility(View.GONE);
            mDoneButton.setVisibility(View.VISIBLE);
            mSaveButton.setVisibility(View.VISIBLE);
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
                mDoneButton.setVisibility(View.VISIBLE);
                mSaveButton.setVisibility(View.VISIBLE);

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
        if (mDailyData.size() != 0 && !mFromRecommended.equals("yes")) {
            builder.setTitle(R.string.back_pressed_title);
            builder.setMessage(R.string.back_pressed_message);
            builder.setPositiveButton(R.string.back_pressed_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    builder.create();
                }
            });
            builder.setNegativeButton(R.string.back_pressed_yes, new DialogInterface.OnClickListener() {
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

            builder.setTitle(R.string.workout_done_title);
            builder.setMessage(R.string.workout_done_message);
            builder.setPositiveButton(R.string.workout_done_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    builder.create();
                }
            });
            builder.setNegativeButton(R.string.workout_done_yes, new DialogInterface.OnClickListener() {
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

    public void setRecommendedRecipe(int pos, String title){

        String[] programTitleList = getResources().getStringArray(R.array.predefine_menu_titles);
        String[] recipeList1 = getResources().getStringArray(R.array.chest_recipe_titles);
        String[] recipeList2 = getResources().getStringArray(R.array.back_recipe_titles);
        String[] recipeList3 = getResources().getStringArray(R.array.leg_recipe_titles);
        String[] recipeList4 = getResources().getStringArray(R.array.shoulder_recipe_titles);
        String[] recipeList5 = getResources().getStringArray(R.array.arm_recipe_titles);
        String[] recipeList6 = getResources().getStringArray(R.array.abs_recipe_titles);
        if (title.equals(programTitleList[0])) {
            mDailyData.add(new ScheduleListItem(recipeList1[0], ITS(20), getString(R.string.KG), ITS(6), ITS(8), "60 s"));
            mDailyData.add(new ScheduleListItem(recipeList1[1], ITS(10), getString(R.string.KG), ITS(4), ITS(8), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList1[4], ITS(8), getString(R.string.KG), ITS(4), ITS(8), "60 s"));
            mDailyData.add(new ScheduleListItem(recipeList1[2], ITS(10), getString(R.string.KG), ITS(4), ITS(8), "60 s"));
            mDailyData.add(new ScheduleListItem(recipeList5[4], ITS(8), getString(R.string.KG), ITS(6), ITS(10), "60 s"));
        } else if (title.equals(programTitleList[1])) {
            mDailyData.add(new ScheduleListItem(recipeList2[0], "", "", ITS(6), ITS(6), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList2[2], ITS(20), getString(R.string.KG), ITS(4), ITS(8), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList2[3], ITS(20), getString(R.string.KG), ITS(4), ITS(8), "60 s"));
            mDailyData.add(new ScheduleListItem(recipeList2[7], ITS(20), getString(R.string.KG), ITS(4), ITS(10), "60 s"));
            mDailyData.add(new ScheduleListItem(recipeList5[0], ITS(4), getString(R.string.KG), ITS(6), ITS(10), "60 s"));
        } else if (title.equals(programTitleList[2])) {
            mDailyData.add(new ScheduleListItem(recipeList3[0], ITS(30), getString(R.string.KG), ITS(6), ITS(8), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList3[2], ITS(20), getString(R.string.KG), ITS(4), ITS(8), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList3[3], "", "", ITS(4), ITS(8), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList3[7], ITS(20), getString(R.string.KG), ITS(4), ITS(10), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList3[9], ITS(15), getString(R.string.KG), ITS(4), ITS(8), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList3[10], ITS(15), getString(R.string.KG), ITS(6), ITS(10), "40 s"));
            mDailyData.add(new ScheduleListItem(recipeList3[11], ITS(15), getString(R.string.KG), ITS(6), ITS(10), "40 s"));
        } else if (title.equals(programTitleList[3])) {
            mDailyData.add(new ScheduleListItem(recipeList4[0], ITS(8), getString(R.string.KG), ITS(6), ITS(6), "60 s"));
            mDailyData.add(new ScheduleListItem(recipeList4[2], ITS(4), getString(R.string.KG), ITS(4), ITS(8), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList4[3], ITS(5), getString(R.string.KG), ITS(6), ITS(6), "60 s"));
            mDailyData.add(new ScheduleListItem(recipeList4[5], ITS(10), getString(R.string.KG), ITS(4), ITS(10), "60 s"));
        } else if (title.equals(programTitleList[4])) {
            mDailyData.add(new ScheduleListItem(recipeList5[0], ITS(6), getString(R.string.KG), ITS(6), ITS(10), "60 s"));
            mDailyData.add(new ScheduleListItem(recipeList5[2], ITS(4), getString(R.string.KG), ITS(4), ITS(6), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList5[3], ITS(6), getString(R.string.KG), ITS(6), ITS(6), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList5[4], ITS(6), getString(R.string.KG), ITS(4), ITS(10), "60 s"));
        } else if (title.equals(programTitleList[5])) {
            mDailyData.add(new ScheduleListItem(recipeList6[1], "", "", ITS(4), ITS(20), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList6[3], ITS(8), getString(R.string.KG), ITS(4), ITS(20), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList6[4], ITS(5), getString(R.string.KG), ITS(4), ITS(20), "90 s"));
            mDailyData.add(new ScheduleListItem(recipeList6[5], "", "", ITS(4), ITS(10), "90 s"));
        } else {
            // Re-Load the saved recipes.
            //int index = pos;
            int num = mPreferences.getInt("Customized_Key_" + pos + "_Size", 0);
            for (int n = 0; n < num; n++) {
                String action = mPreferences.getString("Customized_Key_" + pos + "_" + n + "_Action", null);
                String weights = mPreferences.getString("Customized_Key_" + pos + "_" + n + "_Weights", null);
                String unit = mPreferences.getString("Customized_Key_" + pos + "_" + n + "_Unit", null);
                String sets = mPreferences.getString("Customized_Key_" + pos + "_" + n + "_Sets", null);
                String reps = mPreferences.getString("Customized_Key_" + pos + "_" + n + "_Reps", null);
                String time = mPreferences.getString("Customized_Key_" + pos + "_" + n + "_Time", null);
                mDailyData.add(new ScheduleListItem(action, weights, unit, sets, reps, time));
            }
        }

    }

    public String ITS(int n) {
        return Integer.toString(n);
    }

    public void workoutSave(View view) {

        final int fullOrNot = mPreferences.getInt("Customized_Key_19", 0);

        if (fullOrNot == 0) {
            if (mDailyData.size() != 0) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater linf = LayoutInflater.from(this);
                final View inflator = linf.inflate(R.layout.dialog_save, null);
                final EditText saveEdit = (EditText) inflator.findViewById(R.id.save_edit);

                builder.setTitle(R.string.save_these_title);
                builder.setMessage(R.string.save_these_msg);
                builder.setView(inflator);
                builder.setPositiveButton(R.string.reset_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        builder.create();
                    }
                });
                builder.setNegativeButton(R.string.schedule_list_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // **********
                        // Save data
                        // **********
                        String[] programTitleList = getResources().getStringArray(R.array.predefine_menu_titles);

                        mCustomizedTitle = saveEdit.getText().toString();
                        if (mCustomizedTitle.isEmpty()) {
                            // No empty blank.
                            displayToast(getString(R.string.you_need_to_enter_title));
                        } else if (mCustomizedTitle.equals(programTitleList[0]) ||
                                mCustomizedTitle.equals(programTitleList[1]) ||
                                mCustomizedTitle.equals(programTitleList[2]) ||
                                mCustomizedTitle.equals(programTitleList[3]) ||
                                mCustomizedTitle.equals(programTitleList[4]) ||
                                mCustomizedTitle.equals(programTitleList[5])) {
                            // No repeat name.
                            displayToast(mCustomizedTitle + getString(R.string.has_been_used));
                        } else {
                            // Save the customized recipes.
                            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                            int existOrNot = 0;

                            for (int n = 0; n < 20; n++) {
                                if (mPreferences.getInt("Customized_Key_" + n, 0) == 0) {
                                    existOrNot = n;
                                    break;
                                }
                            }

                            preferencesEditor.putInt("Customized_Key_" + existOrNot, 1);
                            preferencesEditor.putString("Customized_Key_" + existOrNot + "_Title", mCustomizedTitle);
                            preferencesEditor.putInt("Customized_Key_" + existOrNot + "_Size", mDailyData.size());

                            for (int i = 0; i < mDailyData.size(); i++) {
                                preferencesEditor.putString(
                                        "Customized_Key_" + existOrNot + "_" + i + "_Action", mDailyData.get(i).getAction());
                                preferencesEditor.putString(
                                        "Customized_Key_" + existOrNot + "_" + i + "_Weights", mDailyData.get(i).getWeights());
                                preferencesEditor.putString(
                                        "Customized_Key_" + existOrNot + "_" + i + "_Unit", mDailyData.get(i).getUnit());
                                preferencesEditor.putString(
                                        "Customized_Key_" + existOrNot + "_" + i + "_Sets", mDailyData.get(i).getSets());
                                preferencesEditor.putString(
                                        "Customized_Key_" + existOrNot + "_" + i + "_Reps", mDailyData.get(i).getReps());
                                preferencesEditor.putString(
                                        "Customized_Key_" + existOrNot + "_" + i + "_Time", mDailyData.get(i).getTime());
                            }

                            preferencesEditor.apply();
                            DailyScheduleActivity.super.onBackPressed();
                        }
                    }
                });
                builder.show();
            }
        } else {
            displayToast(getString(R.string.only_twenty_recipes));
        }
    }
}


