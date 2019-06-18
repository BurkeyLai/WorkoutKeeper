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
import android.widget.Toast;

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
                break;
            case R.id.basketball_button:
                Intent intent2 = new Intent(this, Korean_fish.class);
                startActivity(intent2);
                break;
            case R.id.jogging_button:
                displayToast("Coming soon");
                break;
            default:
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timer_menu, menu);
        return true;
    }
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
