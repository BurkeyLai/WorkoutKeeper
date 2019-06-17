package com.example.workoutkeeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class BodyPartFragment extends Fragment {

    private String body_part;
    private final LinkedList<String> mRecipe = new LinkedList<>();
    private final LinkedList<String> mRecipeDescription = new LinkedList<>();
    private final LinkedList<String> mYoutubeList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private TrainingListAdapter mAdapter;

    public static final int TEXT_REQUEST = 1;

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
            FloatingActionButton fab = getActivity().findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), AddTrainingActivity.class);
                    startActivityForResult(intent, TEXT_REQUEST);
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
        if (getView() != null) {
            TextView mText = (TextView) getView().findViewById(R.id.body_part_title);
            mText.setText(body_part);
        }

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

    private void initializeChestData(){
        String[] recipeList = getResources().getStringArray(R.array.chest_recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.chest_recipe_info);
        String[] recipeYoutube = getResources().getStringArray(R.array.chest_recipe_youtube);

        for (int i = 0; i < recipeList.length; i++) {
            mRecipe.addLast(recipeList[i]);
            mRecipeDescription.addLast(recipeInfo[i]);
            mYoutubeList.addLast(recipeYoutube[i]);
        }

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recyclerview);
        }
        mAdapter = new TrainingListAdapter(getContext(), 1, mRecipe, mRecipeDescription, mYoutubeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recyclerview);
        }
        mAdapter = new TrainingListAdapter(getContext(), 1, mRecipe, mRecipeDescription, mYoutubeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recyclerview);
        }
        mAdapter = new TrainingListAdapter(getContext(), 1, mRecipe, mRecipeDescription, mYoutubeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recyclerview);
        }
        mAdapter = new TrainingListAdapter(getContext(), 1, mRecipe, mRecipeDescription, mYoutubeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recyclerview);
        }
        mAdapter = new TrainingListAdapter(getContext(), 1, mRecipe, mRecipeDescription, mYoutubeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recyclerview);
        }
        mAdapter = new TrainingListAdapter(getContext(), 1, mRecipe, mRecipeDescription, mYoutubeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                if (getView() != null) {
                    mRecyclerView = getView().findViewById(R.id.recyclerview);
                }
                mAdapter = new TrainingListAdapter(getContext(), 1, mRecipe, mRecipeDescription, null);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }
    }
}
