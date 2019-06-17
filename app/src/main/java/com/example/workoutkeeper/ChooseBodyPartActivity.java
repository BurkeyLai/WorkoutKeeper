package com.example.workoutkeeper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChooseBodyPartActivity extends AppCompatActivity {

    private String string_body_part;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_body_part);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public void chooseBodyPart(View view) {
        Bundle bundle = new Bundle();

        if(view.getId() == R.id.chest_button) {
            string_body_part = "chest";
            bundle.putString("BodyPart_KEY", string_body_part);
        }
        else if(view.getId() == R.id.shoulder_button) {
            string_body_part = "shoulder";
            bundle.putString("BodyPart_KEY", string_body_part);
        }
        else if(view.getId() == R.id.back_button) {
            string_body_part = "back";
            bundle.putString("BodyPart_KEY", string_body_part);
        }
        else if(view.getId() == R.id.abs_button) {
            string_body_part = "abs";
            bundle.putString("BodyPart_KEY", string_body_part);
        }
        else if(view.getId() == R.id.leg_button) {
            string_body_part = "leg";
            bundle.putString("BodyPart_KEY", string_body_part);
        }
        else if(view.getId() == R.id.arm_button) {
            string_body_part = "arm";
            bundle.putString("BodyPart_KEY", string_body_part);
        }

        Intent intent = new Intent(this, TrainingActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
