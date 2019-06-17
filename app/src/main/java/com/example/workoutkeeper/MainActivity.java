package com.example.workoutkeeper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton workout_bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void chooseWorkout(View view) {

        switch (view.getId()) {
            case R.id.fitness_button:
                Intent intent = new Intent(this, FitnessActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timer_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
