package com.example.workoutkeeper;

import android.content.Intent;
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
    private final LinkedList<String> mRecipeYoutube = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private TrainingListAdapter mAdapter;

    public static final int TEXT_REQUEST = 1;

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

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription, null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        String[] recipeYoutube = getResources().getStringArray(R.array.chest_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mRecipeYoutube.addLast(recipeYoutube[i]);
        }


        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription, mRecipeYoutube);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();

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

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription, mRecipeYoutube);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();

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

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription, mRecipeYoutube);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();
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


        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription, mRecipeYoutube);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();

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

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription, mRecipeYoutube);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();
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
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TrainingListAdapter(this, 0, mRecipe, mRecipeDescription, mRecipeYoutube);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();
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
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
