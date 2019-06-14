package com.example.workoutkeeper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    private RecyclerView mRecyclerView;
    private TrainingListAdapter mAdapter;

    private String body_part;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        retrieveBodyPart();// Cause error when get back from SetsAndRepsActivity, so I set SetsAndRepsActivity's parent as
                            // ChooseBodyPartActivity in AndroidManifest.xml

        if (body_part.equals("chest")) {
            initializeChestData();
        } else if (body_part.equals("shoulder")) {
            initializeShoulderData();
        } else if (body_part.equals("back")) {
            initializeBackData();
        } else if (body_part.equals("abs")) {
            initializeABSData();
        } else if (body_part.equals("leg")) {
            initializeLegData();
        } else if (body_part.equals("arm")) {
            initializeArmData();
        }

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

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeShoulderData(){
        String[] recipeList = getResources().getStringArray(R.array.shoulder_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.shoulder_recipe_info);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeBackData(){
        String[] recipeList = getResources().getStringArray(R.array.back_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.back_recipe_info);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeABSData(){
        String[] recipeList = getResources().getStringArray(R.array.abs_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.abs_recipe_info);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeLegData(){
        String[] recipeList = getResources().getStringArray(R.array.leg_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.leg_recipe_info);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeArmData(){
        String[] recipeList = getResources().getStringArray(R.array.arm_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.arm_recipe_info);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
