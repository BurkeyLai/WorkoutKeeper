package com.example.workoutkeeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton workout_bottom;
    private ViewPager mViewPager;
    private FitnessPageAdapter viewPagerFragmentAdapter;
    private List<Fragment> pageList;
    private List<String> pageTitles;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        viewPagerFragmentAdapter = new FitnessPageAdapter(getSupportFragmentManager(), pageList, pageTitles);
        mViewPager.setAdapter(viewPagerFragmentAdapter);

        tabLayout.setupWithViewPager(mViewPager);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.fitness_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    private void initData() {
        pageList = new ArrayList<Fragment>();
        pageTitles = new ArrayList<String>();
        pageList.add(new ChooseSchedulePage());
        pageList.add(new PreDefineMenuPage());
        pageTitles.add(getString(R.string.tab_schedule));
        pageTitles.add(getString(R.string.tab_recommended));
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
            Intent intent = new Intent(this, RMCalculatorActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.anatomical_settings) {
            AlertDialog.Builder myAlertBuilder =
                    new AlertDialog.Builder(MainActivity.this);

            // Set the dialog title and message.
            myAlertBuilder.setTitle(R.string.dialog_muscle_title);
            LayoutInflater factory = LayoutInflater.from(MainActivity.this);

            final View view = factory.inflate(R.layout.muscle, null);

            ImageView image= (ImageView) view.findViewById(R.id.muscle);
            image.setImageResource(R.drawable.muscle);

            // Add the dialog buttons.
            myAlertBuilder.setView(view);

            myAlertBuilder.setPositiveButton(R.string.dialog_muscle_ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked OK button.
                            Toast.makeText(getApplicationContext(),
                                    R.string.dialog_muscle_ok,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            // Create and show the AlertDialog.
            myAlertBuilder.show();

            displayToast(getString(R.string.dialog_muscle_toast));
            return true;
        } else if (id == R.id.Timer_icon) {

            Intent intent = new Intent(this, TimerActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void chooseSchedule(View view) {

        if (view.getId() == R.id.choose_schedule_button1) {
            Intent intent1 = new Intent(this, ChooseBodyPartActivity.class);
            startActivity(intent1);
        } else if (view.getId() == R.id.choose_schedule_button2) {
            Intent intent2 = new Intent(this, DailyScheduleActivity.class);
            startActivity(intent2);
        } else if (view.getId() == R.id.choose_schedule_button3) {
            Intent intent3 = new Intent(this, KoreanFish.class);
            startActivity(intent3);
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}
