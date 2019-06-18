package com.example.workoutkeeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;

public class TrainingActivity extends AppCompatActivity {

    private final LinkedList<String> mRecipe = new LinkedList<>();
    private final LinkedList<String> mRecipeDescription = new LinkedList<>();
    private final LinkedList<String> mRecipeYoutube = new LinkedList<>();

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
    //private String sharedPrefFile = "LinkedList";

    private String body_part;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(TrainingActivity.this, AddTrainingActivity.class);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        retrieveBodyPart();// Cause error when get back from SetsAndRepsActivity, so I set SetsAndRepsActivity's parent as
                            // ChooseBodyPartActivity in AndroidManifest.xml
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Sets RecyclerView and Adapter
        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription, mRecipeYoutube);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Log.d("fuckkkk", Integer.toString(state));

        initializeRecipe();
    }

/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        state = 0;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(STATE_KEY, state);
        preferencesEditor.apply();
    }

*/
    private void initializeRecipe(){
        if (body_part.equals("chest")) {
            chest_state = mPreferences.getInt(CHEST_STATE_KEY, 0);
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
                        mRecipeYoutube.addLast(mPreferences.getString("ChestY_" + i, null));
                    }
                    mChestTitle = mRecipe;
                    mChestDes = mRecipeDescription;
                    mChestYT = mRecipeYoutube;
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
                        mRecipeYoutube.addLast(mPreferences.getString("ShoulderY_" + j, null));
                    }
                    mShoulderTitle = mRecipe;
                    mShoulderDes = mRecipeDescription;
                    mShoulderYT = mRecipeYoutube;
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
                        mRecipeYoutube.addLast(mPreferences.getString("BackY_" + k, null));
                    }
                    mBackTitle = mRecipe;
                    mBackDes = mRecipeDescription;
                    mBackYT = mRecipeYoutube;
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
                        mRecipeYoutube.addLast(mPreferences.getString("ABSY_" + l, null));
                    }
                    mABSTitle = mRecipe;
                    mABSDes = mRecipeDescription;
                    mABSYT = mRecipeYoutube;
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
                        mRecipeYoutube.addLast(mPreferences.getString("LegY_" + m, null));
                    }
                    mLegTitle = mRecipe;
                    mLegDes = mRecipeDescription;
                    mLegYT = mRecipeYoutube;
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
                        mRecipeYoutube.addLast(mPreferences.getString("ArmY_" + n, null));
                    }
                    mArmTitle = mRecipe;
                    mArmDes = mRecipeDescription;
                    mArmYT = mRecipeYoutube;
                }
            }
        }
    }

    public void resetRecipe(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    mRecipeYoutube.clear();
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
                    mRecipeYoutube.clear();
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
                    mRecipeYoutube.clear();
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
                    mRecipeYoutube.clear();
                    initializeABSData();
                } else if (body_part.equals("leg")) {
                    leg_state = 0;
                    preferencesEditor.putInt(LEG_STATE_KEY, leg_state);
                    int listSize = mPreferences.getInt("leg_size", 0);
                    if (listSize != 0) {
                        for (int m = 0; m < listSize; m++) {
                            preferencesEditor.remove("LegT_" + m);
                            preferencesEditor.remove("LegD_" + m);
                            preferencesEditor.remove("LegY_" + m);
                        }
                    }
                    mRecipe.clear();
                    mRecipeDescription.clear();
                    mRecipeYoutube.clear();
                    initializeLegData();
                } else if (body_part.equals("arm")) {
                    arm_state = 0;
                    preferencesEditor.putInt(ARM_STATE_KEY, arm_state);
                    int listSize = mPreferences.getInt("arm_size", 0);
                    if (listSize != 0) {
                        for (int n = 0; n < listSize; n++) {
                            preferencesEditor.remove("ArmT_" + n);
                            preferencesEditor.remove("ArmD_" + n);
                            preferencesEditor.remove("ArmY_" + n);
                        }
                    }
                    mRecipe.clear();
                    mRecipeDescription.clear();
                    mRecipeYoutube.clear();
                    initializeArmData();
                }
                preferencesEditor.apply();

                finish();
                startActivity(getIntent());
            }
        });
        builder.show();
    }

    private void retrieveBodyPart() {
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            body_part = bundle.getString("BodyPart_KEY", "");
        }
    }

    private void initializeChestData(){
        String[] recipeList = getResources().getStringArray(R.array.chest_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.chest_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.chest_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mRecipeYoutube.addLast(recipeYoutube[i]);
        }


        mChestTitle = mRecipe;
        mChestDes = mRecipeDescription;
        mChestYT = mRecipeYoutube;

    }

    private void initializeShoulderData(){
        String[] recipeList = getResources().getStringArray(R.array.shoulder_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.shoulder_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.shoulder_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mRecipeYoutube.addLast(recipeYoutube[i]);
        }

        mShoulderTitle = mRecipe;
        mShoulderDes = mRecipeDescription;
        mShoulderYT = mRecipeYoutube;
    }

    private void initializeBackData(){
        String[] recipeList = getResources().getStringArray(R.array.back_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.back_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.back_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mRecipeYoutube.addLast(recipeYoutube[i]);
        }

        mBackTitle = mRecipe;
        mBackDes = mRecipeDescription;
        mBackYT = mRecipeYoutube;
    }

    private void initializeABSData(){
        String[] recipeList = getResources().getStringArray(R.array.abs_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.abs_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.abs_recipe_youtube);
        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mRecipeYoutube.addLast(recipeYoutube[i]);
        }
        mABSTitle = mRecipe;
        mABSDes = mRecipeDescription;
        mABSYT = mRecipeYoutube;
    }

    private void initializeLegData(){
        String[] recipeList = getResources().getStringArray(R.array.leg_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.leg_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.leg_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mRecipeYoutube.addLast(recipeYoutube[i]);
        }

        mLegTitle = mRecipe;
        mLegDes = mRecipeDescription;
        mLegYT = mRecipeYoutube;
    }

    private void initializeArmData(){
        String[] recipeList = getResources().getStringArray(R.array.arm_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.arm_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.arm_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mRecipeYoutube.addLast(recipeYoutube[i]);
        }

        mArmTitle = mRecipe;
        mArmDes = mRecipeDescription;
        mArmYT = mRecipeYoutube;
    }


    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String RecipeTitle = data.getStringExtra(AddTrainingActivity.EXTRA_TITLE);
                String RecipeInfo = data.getStringExtra(AddTrainingActivity.EXTRA_INFO);

                mRecipe.addLast(RecipeTitle);
                mRecipeDescription.addLast(RecipeInfo);
                mRecipeYoutube.addLast("No Video");
                mAdapter.notifyDataSetChanged();
                //Log.d("fuckkkkkkk", Integer.toString(chest_state));
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
    protected void onPause(){
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
