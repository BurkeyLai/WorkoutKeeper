package com.example.workoutkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddTrainingActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.workoutkeeper.extra.TITLE";
    public static final String EXTRA_INFO = "com.example.workoutkeeper.extra.INFO";
    private EditText mRecipeTitlesEditText;
    private EditText mRecipeInfoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        mRecipeTitlesEditText = findViewById(R.id.input_recipe_titles);
        mRecipeInfoEditText = findViewById(R.id.input_recipe_info);

    }

    public void SendData(View view) {
        String RecipeTitle = mRecipeTitlesEditText.getText().toString();
        String RecipeInfo = mRecipeInfoEditText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE,RecipeTitle);
        intent.putExtra(EXTRA_INFO,RecipeInfo);
        setResult(RESULT_OK,intent);
        finish();
    }
}
