package com.example.workoutkeeper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

public class BodyPartFragment extends Fragment {

    private String body_part;
    private final LinkedList<String> mRecipe = new LinkedList<>();
    private final LinkedList<String> mRecipeDescription = new LinkedList<>();
    private final LinkedList<String> mYoutubeList = new LinkedList<>();

    private LinkedList<String> mChestTitle = new LinkedList<>();
    private LinkedList<String> mChestDes = new LinkedList<>();
    private LinkedList<String> mChestYT = new LinkedList<>();
    private LinkedList<String> mShoulderTitle = new LinkedList<>();
    private LinkedList<String> mShoulderDes = new LinkedList<>();
    private LinkedList<String> mShoulderYT = new LinkedList<>();
    private LinkedList<String> mBackTitle = new LinkedList<>();
    private LinkedList<String> mBackDes = new LinkedList<>();
    private LinkedList<String> mBackYT = new LinkedList<>();
    private LinkedList<String> mABSTitle = new LinkedList<>();
    private LinkedList<String> mABSDes = new LinkedList<>();
    private LinkedList<String> mABSYT = new LinkedList<>();
    private LinkedList<String> mLegTitle = new LinkedList<>();
    private LinkedList<String> mLegDes = new LinkedList<>();
    private LinkedList<String> mLegYT = new LinkedList<>();
    private LinkedList<String> mArmTitle = new LinkedList<>();
    private LinkedList<String> mArmDes = new LinkedList<>();
    private LinkedList<String> mArmYT = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private TrainingListAdapter mAdapter;

    public static final int TEXT_REQUEST = 1;

    private final String CHEST_STATE_KEY = "chest_state";
    private final String SHOULDER_STATE_KEY = "shoulder_state";
    private final String BACK_STATE_KEY = "back_state";
    private final String ABS_STATE_KEY = "abs_state";
    private final String LEG_STATE_KEY = "leg_state";
    private final String ARM_STATE_KEY = "arm_state";
    private int chest_state = 0;
    private int shoulder_state = 0;
    private int back_state = 0;
    private int abs_state = 0;
    private int leg_state = 0;
    private int arm_state = 0;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.workoutkeeper";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //導入Tab分頁的Fragment Layout
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        // Make sure that when you switch between each fragment, the fragment layout should be initialized.
        mRecipe.clear();
        mRecipeDescription.clear();
        mYoutubeList.clear();
        mRecyclerView = null;
        mAdapter = null;

        if (getActivity() != null) {
            FloatingActionButton fab = getActivity().findViewById(R.id.fab1);
            Button resetButton = getActivity().findViewById(R.id.frag_reset);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), AddTrainingActivity.class);
                    startActivityForResult(intent, TEXT_REQUEST);
                }
            });
            resetButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    resetRecipeFrag(getView());
                }
            });
        }
        // Get the arguement from DailyScheduleActivity's .addtab
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            body_part = bundle.getString("BodyPart_KEY", "");
        }
        //Log.d("BBBBBBBBBBBBBBBB", body_part);
        //取得TextView元件並帶入text字串
        /*
        if (getView() != null) {
            TextView mText = (TextView) getView().findViewById(R.id.body_part_title);
            mText.setText(body_part);
        }
        */
        //Log.d("fuckkkkkkk1", body_part);
        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Log.d("fuckkkkkkk2", body_part);

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recyclerview);
        }
        mAdapter = new TrainingListAdapter(getContext(), 1, mRecipe, mRecipeDescription, mYoutubeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initializeRecipeFrag();
        /*
        if (body_part.equals("Chest")) {
            initializeChestData();
        } else if (body_part.equals("Shoulder")) {
            initializeShoulderData();
        } else if (body_part.equals("Back")) {
            initializeBackData();
        } else if (body_part.equals("ABS")) {
            initializeABSData();
        } else if (body_part.equals("Leg")) {
            initializeLegData();
        } else if (body_part.equals("Arm")) {
            initializeArmData();
        }
        */
    }
    /*
    public void onDestroy () {
        super.onDestroy();
        mRecipe.clear();
        mRecipeDescription.clear();
        mRecyclerView = null;
        mAdapter = null;
    }
    */
    private void initializeRecipeFrag() {
        if (body_part.equals("chest")) {
            chest_state = mPreferences.getInt(CHEST_STATE_KEY, 0);
            //Log.d("fuckkkkkkk", Integer.toString(chest_state));
            if (chest_state == 0) {
                initializeChestData();
                chest_state = 1;
            } else if (chest_state == 1) {
                int listSize = mPreferences.getInt("chest_size", 0);
                //Log.d("fuckkkk", Integer.toString(listSize));
                if (listSize != 0) {
                    for (int i = 0; i < listSize; i++) {
                        mRecipe.addLast(mPreferences.getString("ChestT_" + i, null));
                        mRecipeDescription.addLast(mPreferences.getString("ChestD_" + i, null));
                        mYoutubeList.addLast(mPreferences.getString("ChestY_" + i, null));
                    }
                    mChestTitle = mRecipe;
                    mChestDes = mRecipeDescription;
                    mChestYT = mYoutubeList;
                }
            }
        } else if (body_part.equals("shoulder")) {
            shoulder_state = mPreferences.getInt(SHOULDER_STATE_KEY, 0);
            if (shoulder_state == 0) {
                initializeShoulderData();
                shoulder_state = 1;
            } else if (shoulder_state == 1) {
                int listSize = mPreferences.getInt("shoulder_size", 0);
                if (listSize != 0) {
                    for (int j = 0; j < listSize; j++) {
                        mRecipe.addLast(mPreferences.getString("ShoulderT_" + j, null));
                        mRecipeDescription.addLast(mPreferences.getString("ShoulderD_" + j, null));
                        mYoutubeList.addLast(mPreferences.getString("ShoulderY_" + j, null));
                    }
                    mShoulderTitle = mRecipe;
                    mShoulderDes = mRecipeDescription;
                    mShoulderYT = mYoutubeList;
                }
            }
        } else if (body_part.equals("back")) {
            back_state = mPreferences.getInt(BACK_STATE_KEY, 0);
            if (back_state == 0) {
                initializeBackData();
                back_state = 1;
            } else if (back_state == 1) {
                int listSize = mPreferences.getInt("back_size", 0);
                if (listSize != 0) {
                    for (int k = 0; k < listSize; k++) {
                        mRecipe.addLast(mPreferences.getString("BackT_" + k, null));
                        mRecipeDescription.addLast(mPreferences.getString("BackD_" + k, null));
                        mYoutubeList.addLast(mPreferences.getString("BackY_" + k, null));
                    }
                    mBackTitle = mRecipe;
                    mBackDes = mRecipeDescription;
                    mBackYT = mYoutubeList;
                }
            }
        } else if (body_part.equals("abs")) {
            abs_state = mPreferences.getInt(ABS_STATE_KEY, 0);
            if (abs_state == 0) {
                initializeABSData();
                abs_state = 1;
            } else if (abs_state == 1) {
                int listSize = mPreferences.getInt("abs_size", 0);
                if (listSize != 0) {
                    for (int l = 0; l < listSize; l++) {
                        mRecipe.addLast(mPreferences.getString("ABST_" + l, null));
                        mRecipeDescription.addLast(mPreferences.getString("ABSD_" + l, null));
                        mYoutubeList.addLast(mPreferences.getString("ABSY_" + l, null));
                    }
                    mABSTitle = mRecipe;
                    mABSDes = mRecipeDescription;
                    mABSYT = mYoutubeList;
                }
            }
        } else if (body_part.equals("leg")) {
            leg_state = mPreferences.getInt(LEG_STATE_KEY, 0);
            if (leg_state == 0) {
                initializeLegData();
                leg_state = 1;
            } else if (leg_state == 1) {
                int listSize = mPreferences.getInt("leg_size", 0);
                if (listSize != 0) {
                    for (int m = 0; m < listSize; m++) {
                        mRecipe.addLast(mPreferences.getString("LegT_" + m, null));
                        mRecipeDescription.addLast(mPreferences.getString("LegD_" + m, null));
                        mYoutubeList.addLast(mPreferences.getString("LegY_" + m, null));
                    }
                    mLegTitle = mRecipe;
                    mLegDes = mRecipeDescription;
                    mLegYT = mYoutubeList;
                }
            }
        } else if (body_part.equals("arm")) {
            arm_state = mPreferences.getInt(ARM_STATE_KEY, 0);
            if (arm_state == 0) {
                initializeArmData();
                arm_state = 1;
            } else if (arm_state == 1) {
                int listSize = mPreferences.getInt("arm_size", 0);
                if (listSize != 0) {
                    for (int n = 0; n < listSize; n++) {
                        mRecipe.addLast(mPreferences.getString("ArmT_" + n, null));
                        mRecipeDescription.addLast(mPreferences.getString("ArmD_" + n, null));
                        mYoutubeList.addLast(mPreferences.getString("ArmY_" + n, null));
                    }
                    mArmTitle = mRecipe;
                    mArmDes = mRecipeDescription;
                    mArmYT = mYoutubeList;
                }
            }
        }
    }

    public void resetRecipeFrag(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.reset_title);
        builder.setMessage(R.string.reset_message);
        builder.setPositiveButton(R.string.reset_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                builder.create();
            }
        });
        builder.setNegativeButton(R.string.reset_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();

                if (body_part.equals("chest")) {
                    chest_state = 0;
                    preferencesEditor.putInt(CHEST_STATE_KEY, chest_state);
                    int listSize = mPreferences.getInt("chest_size", 0);
                    if (listSize != 0) {
                        for (int i = 0; i < listSize; i++) {
                            preferencesEditor.remove("ChestT_" + i);
                            preferencesEditor.remove("ChestD_" + i);
                            preferencesEditor.remove("ChestY_" + i);
                        }
                    }
                    mRecipe.clear();
                    mRecipeDescription.clear();
                    mYoutubeList.clear();
                    initializeChestData();
                } else if (body_part.equals("shoulder")) {
                    shoulder_state = 0;
                    preferencesEditor.putInt(SHOULDER_STATE_KEY, shoulder_state);
                    int listSize = mPreferences.getInt("shoulder_size", 0);
                    if (listSize != 0) {
                        for (int j = 0; j < listSize; j++) {
                            preferencesEditor.remove("ShoulderT_" + j);
                            preferencesEditor.remove("ShoulderD_" + j);
                            preferencesEditor.remove("ShoulderY_" + j);
                        }
                    }
                    mRecipe.clear();
                    mRecipeDescription.clear();
                    mYoutubeList.clear();
                    initializeShoulderData();
                } else if (body_part.equals("back")) {
                    back_state = 0;
                    preferencesEditor.putInt(BACK_STATE_KEY, back_state);
                    int listSize = mPreferences.getInt("back_size", 0);
                    if (listSize != 0) {
                        for (int k = 0; k < listSize; k++) {
                            preferencesEditor.remove("BackT_" + k);
                            preferencesEditor.remove("BackD_" + k);
                            preferencesEditor.remove("BackY_" + k);
                        }
                    }
                    mRecipe.clear();
                    mRecipeDescription.clear();
                    mYoutubeList.clear();
                    initializeBackData();
                } else if (body_part.equals("abs")) {
                    abs_state = 0;
                    preferencesEditor.putInt(ABS_STATE_KEY, abs_state);
                    int listSize = mPreferences.getInt("abs_size", 0);
                    if (listSize != 0) {
                        for (int l = 0; l < listSize; l++) {
                            preferencesEditor.remove("ABST_" + l);
                            preferencesEditor.remove("ABSD_" + l);
                            preferencesEditor.remove("ABSY_" + l);
                        }
                    }
                    mRecipe.clear();
                    mRecipeDescription.clear();
                    mYoutubeList.clear();
                    initializeABSData();
                } else if (body_part.equals("leg")) {
                    leg_state = 0;
                    preferencesEditor.putInt(LEG_STATE_KEY, leg_state);
                    int listSize = mPreferences.getInt("leg_size", 0);
                    if (listSize != 0) {
                        for (int i = 0; i < listSize; i++) {
                            preferencesEditor.remove("LegT_" + i);
                            preferencesEditor.remove("LegD_" + i);
                            preferencesEditor.remove("LegY_" + i);
                        }
                    }
                    mRecipe.clear();
                    mRecipeDescription.clear();
                    mYoutubeList.clear();
                    initializeLegData();
                } else if (body_part.equals("arm")) {
                    arm_state = 0;
                    preferencesEditor.putInt(ARM_STATE_KEY, arm_state);
                    int listSize = mPreferences.getInt("arm_size", 0);
                    if (listSize != 0) {
                        for (int i = 0; i < listSize; i++) {
                            preferencesEditor.remove("ArmT_" + i);
                            preferencesEditor.remove("ArmD_" + i);
                            preferencesEditor.remove("ArmY_" + i);
                        }
                    }
                    mRecipe.clear();
                    mRecipeDescription.clear();
                    mYoutubeList.clear();
                    initializeArmData();
                }
                preferencesEditor.apply();

                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });
        builder.show();
    }


    private void initializeChestData(){
        String[] recipeList = getResources().getStringArray(R.array.chest_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.chest_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.chest_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mYoutubeList.addLast(recipeYoutube[i]);
        }
        mChestTitle = mRecipe;
        mChestDes = mRecipeDescription;
        mChestYT = mYoutubeList;
    }

    private void initializeShoulderData(){
        String[] recipeList = getResources().getStringArray(R.array.shoulder_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.shoulder_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.shoulder_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mYoutubeList.addLast(recipeYoutube[i]);
        }

        mShoulderTitle = mRecipe;
        mShoulderDes = mRecipeDescription;
        mShoulderYT = mYoutubeList;
    }

    private void initializeBackData(){
        String[] recipeList = getResources().getStringArray(R.array.back_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.back_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.back_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mYoutubeList.addLast(recipeYoutube[i]);
        }

        mBackTitle = mRecipe;
        mBackDes = mRecipeDescription;
        mBackYT = mYoutubeList;
    }

    private void initializeABSData(){
        String[] recipeList = getResources().getStringArray(R.array.abs_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.abs_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.abs_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mYoutubeList.addLast(recipeYoutube[i]);
        }

        mABSTitle = mRecipe;
        mABSDes = mRecipeDescription;
        mABSYT = mYoutubeList;
    }

    private void initializeLegData(){
        String[] recipeList = getResources().getStringArray(R.array.leg_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.leg_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.leg_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mYoutubeList.addLast(recipeYoutube[i]);
        }

        mLegTitle = mRecipe;
        mLegDes = mRecipeDescription;
        mLegYT = mYoutubeList;
    }

    private void initializeArmData(){
        String[] recipeList = getResources().getStringArray(R.array.arm_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.arm_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.arm_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mYoutubeList.addLast(recipeYoutube[i]);
        }
        mArmTitle = mRecipe;
        mArmDes = mRecipeDescription;
        mArmYT = mYoutubeList;
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String RecipeTitle = data.getStringExtra(AddTrainingActivity.EXTRA_TITLE);
                String RecipeInfo = data.getStringExtra(AddTrainingActivity.EXTRA_INFO);
                mRecipe.addLast(RecipeTitle);
                mRecipeDescription.addLast(RecipeInfo);
                mYoutubeList.addLast("No Video");
                mAdapter.notifyDataSetChanged();
            }
        }
    }
    /*
    @Override
    public void onStop(){
        super.onStop();
        Log.d("fuckkkkk", "onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("fuckkkkk", "onDestroy");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("fuckkkkk", "onResume");
    }
    */
    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        if (body_part.equals("chest")) {
            preferencesEditor.putInt(CHEST_STATE_KEY, chest_state);
            preferencesEditor.putInt("chest_size", mChestTitle.size());
            for (int i = 0; i < mChestTitle.size(); i++) {
                preferencesEditor.putString("ChestT_" + i, mChestTitle.get(i));
                preferencesEditor.putString("ChestD_" + i, mChestDes.get(i));
                preferencesEditor.putString("ChestY_" + i, mChestYT.get(i));
            }
        } else if (body_part.equals("shoulder")) {
            preferencesEditor.putInt(SHOULDER_STATE_KEY, shoulder_state);
            preferencesEditor.putInt("shoulder_size", mShoulderTitle.size());
            for (int j = 0; j < mShoulderTitle.size(); j++) {
                preferencesEditor.putString("ShoulderT_" + j, mShoulderTitle.get(j));
                preferencesEditor.putString("ShoulderD_" + j, mShoulderDes.get(j));
                preferencesEditor.putString("ShoulderY_" + j, mShoulderYT.get(j));
            }
        } else if (body_part.equals("back")) {
            preferencesEditor.putInt(BACK_STATE_KEY, back_state);
            preferencesEditor.putInt("back_size", mBackTitle.size());
            for (int k = 0; k < mBackTitle.size(); k++) {
                preferencesEditor.putString("BackT_" + k, mBackTitle.get(k));
                preferencesEditor.putString("BackD_" + k, mBackDes.get(k));
                preferencesEditor.putString("BackY_" + k, mBackYT.get(k));
            }
        } else if (body_part.equals("abs")) {
            preferencesEditor.putInt(ABS_STATE_KEY, abs_state);
            preferencesEditor.putInt("abs_size", mABSTitle.size());
            for (int l = 0; l < mABSTitle.size(); l++) {
                preferencesEditor.putString("ABST_" + l, mABSTitle.get(l));
                preferencesEditor.putString("ABSD_" + l, mABSDes.get(l));
                preferencesEditor.putString("ABSY_" + l, mABSYT.get(l));
            }
        } else if (body_part.equals("leg")) {
            preferencesEditor.putInt(LEG_STATE_KEY, leg_state);
            preferencesEditor.putInt("leg_size", mLegTitle.size());
            for (int m = 0; m < mLegTitle.size(); m++) {
                preferencesEditor.putString("LegT_" + m, mLegTitle.get(m));
                preferencesEditor.putString("LegD_" + m, mLegDes.get(m));
                preferencesEditor.putString("LegY_" + m, mLegYT.get(m));
            }
        } else if (body_part.equals("arm")) {
            preferencesEditor.putInt(ARM_STATE_KEY, arm_state);
            preferencesEditor.putInt("arm_size", mArmTitle.size());
            for (int n = 0; n < mArmTitle.size(); n++) {
                preferencesEditor.putString("ArmT_" + n, mArmTitle.get(n));
                preferencesEditor.putString("ArmD_" + n, mArmDes.get(n));
                preferencesEditor.putString("ArmY_" + n, mArmYT.get(n));
            }
        }
        preferencesEditor.apply();
    }
}
