package com.example.workoutkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SetsAndRepsActivity  extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private EditText weights_edittext;
    private String mAction, mWeights, mUnit, mSets, mReps, mTime;
    private Button mNextButton;
    private int mScheduleID;
    private Button mVideoBt;
    public static ArrayList<ScheduleListItem> mDailyData = new ArrayList<ScheduleListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_and_reps);

        Intent intent = getIntent();
        mAction = intent.getStringExtra("Title_KEY");
        String string_id = intent.getStringExtra("Schedule_KEY");
        mScheduleID = Integer.parseInt(string_id);

        String youtubeID = intent.getStringExtra("youtube_KEY");
        final Intent intent2 = new Intent(this, VideoPLayer.class);
        intent2.putExtra("youtube_KEY", youtubeID);
        //Log.d("youtube_KEY", youtubeID);

        TextView textView = findViewById(R.id.sets_and_reps_title);
        textView.setText(mAction);

        weights_edittext = (EditText) findViewById(R.id.weights_edit);

        mNextButton = (Button) findViewById(R.id.next_button);

        mVideoBt = findViewById(R.id.video_button);
        mVideoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });



        if (mScheduleID == 1) {
            mNextButton.setText(R.string.setting_confirm);
        }

        //Spinner for weights
        Spinner weightsSpinner = findViewById(R.id.weights_spinner);
        if (weightsSpinner != null) {
            weightsSpinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> weightsAdapter = ArrayAdapter.createFromResource(this,
                R.array.weights_array, android.R.layout.simple_spinner_item);
        weightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (weightsSpinner != null) {
            weightsSpinner.setAdapter(weightsAdapter);
        }
        //Spinner for sets
        Spinner setsSpinner = findViewById(R.id.sets_spinner);
        if (setsSpinner != null) {
            setsSpinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> setsAdapter = ArrayAdapter.createFromResource(this,
                R.array.sets_array, android.R.layout.simple_spinner_item);
        if (setsSpinner != null) {
            setsSpinner.setAdapter(setsAdapter);
        }
        //Spinner for reps
        Spinner repsSpinner = findViewById(R.id.reps_spinner);
        if (repsSpinner != null) {
            repsSpinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> repsAdapter = ArrayAdapter.createFromResource(this,
                R.array.reps_array, android.R.layout.simple_spinner_item);
        if (repsSpinner != null) {
            repsSpinner.setAdapter(repsAdapter);
        }
        //Spinner for rest time
        Spinner restSpinner = findViewById(R.id.rest_spinner);
        if (restSpinner != null) {
            restSpinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> restAdapter = ArrayAdapter.createFromResource(this,
                R.array.rest_array, android.R.layout.simple_spinner_item);
        if (restSpinner != null) {
            restSpinner.setAdapter(restAdapter);
        }


    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerLabel = parent.getItemAtPosition(position).toString();

        if(parent.getId() == R.id.sets_spinner) {
            mSets = spinnerLabel;
            //displayToast(spinnerLabel + " sets");
        }
        else if(parent.getId() == R.id.reps_spinner) {
            mReps = spinnerLabel;
            //displayToast(spinnerLabel + " reps");
        }
        else if(parent.getId() == R.id.weights_spinner) {
            mUnit = spinnerLabel;

            //displayToast(mWeights + " " + spinnerLabel);
        }
        else if(parent.getId() == R.id.rest_spinner) {
            mTime = spinnerLabel;
            //displayToast(mWeights + " " + spinnerLabel);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goToNext(View view) {

        mWeights = weights_edittext.getText().toString();
        //Log.d("GOOOOOOOO", mAction + ", " + mWeights + " " + mUnit + ", " + mSets + "sets, " + mReps + "reps, " + mTime);

        if (mScheduleID == 0) {
            if (mWeights.isEmpty() && !mAction.equals("Push Up") && !mAction.equals("Handstand Push Up")
                    && !mAction.equals("Narrow Pull Up") && !mAction.equals("Wide Pull Up")
                    && !mAction.equals("Hardstyle Plank") && !mAction.equals("Dead Bug")
                    && !mAction.equals("Hollow Extension-To-Cannonball") && !mAction.equals("Crossover Curl")
                    && !mAction.equals("Decline Curl") && !mAction.equals("Bodyweight Squats")
                    && !mAction.equals("Reverse Lunges With Knee Lifts") && !mAction.equals("Squat Jumps")
                    && !mAction.equals("Side Lunges") && !mAction.equals("Single-Leg Calf Raises")) {
                displayToast("You must fill the weights blank!");
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Action_Key", mAction);
                bundle.putString("Weights_Key", mWeights);
                bundle.putString("Unit_Key", mUnit);
                bundle.putString("Sets_Key", mSets);
                bundle.putString("Reps_Key", mReps);
                bundle.putString("Time_Key", mTime);

                Intent intent = new Intent(this, ProgressActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else if (mScheduleID == 1) {
            if (mWeights.isEmpty() && !mAction.equals("Push Up") && !mAction.equals("Handstand Push Up")
                    && !mAction.equals("Narrow Pull Up") && !mAction.equals("Wide Pull Up")
                    && !mAction.equals("Hardstyle Plank") && !mAction.equals("Dead Bug")
                    && !mAction.equals("Hollow Extension-To-Cannonball") && !mAction.equals("Crossover Curl")
                    && !mAction.equals("Decline Curl") && !mAction.equals("Bodyweight Squats")
                    && !mAction.equals("Reverse Lunges With Knee Lifts") && !mAction.equals("Squat Jumps")
                    && !mAction.equals("Side Lunges") && !mAction.equals("Single-Leg Calf Raises")) {
                displayToast("You must fill the weights blank!");
            } else {
                //dailyListView = (ListView) findViewById(R.id.daily_listView);
                //dailyListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"一", "二", "三"});
                //dailyListView.setAdapter(dailyListAdapter);


                //Log.d("FUCKKKKKKKKKKKK", Integer.toString(mDailyRecyclerView));
                //mDailyData.add(new ScheduleListItem(mAction, mWeights, mUnit, mSets, mReps, mTime));

                //displayToast(mDailyData.get(0).getAction());
                Bundle bundle = new Bundle();
                bundle.putString("Re_Action_Key", mAction);
                bundle.putString("Re_Weights_Key", mWeights);
                bundle.putString("Re_Unit_Key", mUnit);
                bundle.putString("Re_Sets_Key", mSets);
                bundle.putString("Re_Reps_Key", mReps);
                bundle.putString("Re_Time_Key", mTime);

                Intent replyIntent = new Intent();
                replyIntent.putExtras(bundle);

                setResult(RESULT_OK, replyIntent);
                finish();
            }
        }
    }


}
