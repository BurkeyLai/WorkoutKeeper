package com.example.workoutkeeper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class FitnessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fitness_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // This comment suppresses the Android Studio warning about simplifying the return statements. noinspection SimplifiableIfStatement
        if (id == R.id.rmCounter_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void chooseSchedule(View view) {
        /*
        switch (view.getId()) {
            case R.id.choose_schedule_button1:
                Intent intent1 = new Intent(this, ChooseBodyPartActivity.class);
                startActivity(intent1);
            case R.id.choose_schedule_button2:
                Intent intent2 = new Intent(this, DailyScheduleActivity.class);
                startActivity(intent2);
            default:
                break;
        }
        */
        if (view.getId() == R.id.choose_schedule_button1) {
            Intent intent1 = new Intent(this, ChooseBodyPartActivity.class);
            startActivity(intent1);
        } else if (view.getId() == R.id.choose_schedule_button2) {
            Intent intent2 = new Intent(this, DailyScheduleActivity.class);
            startActivity(intent2);
        }
    }
}
