package com.example.workoutkeeper;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FitnessActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private FitnessPageAdapter viewPagerFragmentAdapter;
    private List<Fragment> pageList;
    private List<String> pageTitles;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_fitness);

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
        pageTitles.add("Schedule");
        pageTitles.add("Recommended");
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

        if (view.getId() == R.id.choose_schedule_button1) {
            Intent intent1 = new Intent(this, ChooseBodyPartActivity.class);
            startActivity(intent1);
        } else if (view.getId() == R.id.choose_schedule_button2) {
            Intent intent2 = new Intent(this, DailyScheduleActivity.class);
            startActivity(intent2);
        }
    }

}
