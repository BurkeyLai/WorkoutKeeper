package com.example.workoutkeeper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressActivity extends AppCompatActivity {

    private String mAction, mWeights, mUnit, mSets, mReps, mTime;
    private int mSec, mNowSec;
    private boolean isCounterRunning = false, isPause = false;
    private TextView actionTextView, weightsTextView, setsTextView, repsTextView, timeTextView, percentTextView;
    private Button pauseButton;
    private ProgressBar mProgressBar;
    private CountDownTimer cdt;

    private static final String ACTION_UPDATE_NOTIFICATION = "com.android.example.workoutkeeper.ACTION_UPDATE_NOTIFICATION";
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    private NotificationManager mNotifyManager;
    private NotificationReceiver mReceiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        retriveData();
        mProgressBar = findViewById(R.id.determinateBar);
        percentTextView = findViewById(R.id.percent_text);

        actionTextView = findViewById(R.id.action_title);
        weightsTextView = findViewById(R.id.weights_title);
        setsTextView = findViewById(R.id.sets_title);
        repsTextView = findViewById(R.id.reps_title);
        timeTextView = findViewById(R.id.time_title);
        actionTextView.setText(mAction);
        weightsTextView.setText(mWeights + " " + mUnit);
        setsTextView.setText(mSets);
        repsTextView.setText(mReps);
        timeTextView.setText(mTime);

        String[] split_time = mTime.split(" ");
        if (split_time[1] == Character.toString('m')) {
            mSec = 60 * Integer.parseInt(split_time[0]);
        }
        else {
            mSec = Integer.parseInt(split_time[0]);
        }

        createNotificationChannel();
        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher. So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID, "Counter Notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Counter");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification() {

        // Sets up the pending intent to update the notification. Corresponds to a press of the Update Me! button.
        /*
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        */
        // Build the notification with all of the parameters using helper method.
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Add the action button using the pending intent.
        //notifyBuilder.addAction(R.drawable.ic_update, getString(R.string.update), updatePendingIntent);

        // Deliver the notification.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // When sets become 0, backward to previous activity
        if (Integer.parseInt(setsTextView.getText().toString()) == 0) {
            displayToast("Great Job! Move Forward!");
            finish();
        }

        // Enable the update and cancel buttons but disables the "Notify Me!" button.
        //setNotificationButtonState(false, true, true);
    }

    private NotificationCompat.Builder getNotificationBuilder() {

        // Set up the pending intent that is delivered when the notification is clicked.
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification with all of the parameters.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Time's out!")
                .setContentText("Move your big bottom!")
                .setSmallIcon(R.drawable.ic_counter)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    private void retriveData() {
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            mAction = bundle.getString("Action_Key", "");
            mWeights = bundle.getString("Weights_Key", "");
            mUnit = bundle.getString("Unit_Key", "");
            mSets = bundle.getString("Sets_Key", "");
            mReps = bundle.getString("Reps_Key", "");
            mTime = bundle.getString("Time_Key", "");
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateProgressBar(int totalSets, int nowSet) {
        int progress = totalSets - nowSet;
        int percent = (progress * 100) / totalSets;
        mProgressBar.setProgress(percent);
        String percentView = Integer.toString(percent) + " %";
        percentTextView.setText(percentView);
    }

    private void countDown(final int sec, int pause, int reset, int resume) {

        if(!isCounterRunning && pause == 0 && reset == 0 && isPause == false) { //Begin countdown
            // Decrease the sets by 1
            if (Integer.parseInt(setsTextView.getText().toString()) > 0 && resume == 0) {
                int set = Integer.parseInt(setsTextView.getText().toString()) - 1;
                setsTextView.setText(Integer.toString(set));
                updateProgressBar(Integer.parseInt(mSets), set);
            }
            isCounterRunning = true;
            cdt = new CountDownTimer(1000L * sec, 1000L) {
                public void onTick(long millisUntilFinished) {
                    String timeInSec = Long.toString(millisUntilFinished / 1000) + " s";
                    timeTextView.setText(timeInSec);
                }

                public void onFinish() {
                    sendNotification();
                    String resetToBegin = Integer.toString(mSec) + " s";
                    timeTextView.setText(resetToBegin);
                    isCounterRunning = false;
                }
            }.start(); //start the countdowntimer

        } else if (pause == 0 && reset == 1) { //Reset counter
            // Increase the sets by 1
            if (Integer.parseInt(setsTextView.getText().toString()) < Integer.parseInt(mSets)) {
                int set = Integer.parseInt(setsTextView.getText().toString()) + 1;
                setsTextView.setText(Integer.toString(set));
                updateProgressBar(Integer.parseInt(mSets), set);
            }
            isCounterRunning = false;
            cdt.cancel();
            String resetToBegin = Integer.toString(sec) + " s";
            timeTextView.setText(resetToBegin);
            pauseButton = findViewById(R.id.pause_counter_button);
            if (pauseButton.getText().toString().equals("Resume")) {
                pauseButton.setText("Pause");
                isPause = false;
            }

        } else if (pause == 1 && reset == 0) { //Pause counter
            isCounterRunning = false;
            cdt.cancel();
        }

    }

    public void beginToCountdown(View view) {
        countDown(mSec, 0, 0, 0);

    }

    public void resetCountdown(View view) {
        if (isCounterRunning || isPause) {
            countDown(mSec, 0, 1, 0);
        }
    }

    public void pauseCountdown(View view) {
        timeTextView = findViewById(R.id.time_title);
        String getNowSec = timeTextView.getText().toString();
        String[] split_time = getNowSec.split(" ");
        mNowSec = Integer.parseInt(split_time[0]);

        if (!isPause && isCounterRunning) { // Pause

            isPause = true;
            countDown(mSec, 1, 0, 0);
            pauseButton = findViewById(R.id.pause_counter_button);
            pauseButton.setText("Resume");
        } else if (isPause) { // Resume

            isPause = false;
            countDown(mNowSec, 0, 0, 1);
            pauseButton = findViewById(R.id.pause_counter_button);
            pauseButton.setText("Pause");
        }
        //displayToast("FUCKKKKKKKKKK");
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        /**
         * Receives the incoming broadcasts and responds accordingly.
         *
         * @param context Context of the app when the broadcast is received.
         * @param intent The broadcast intent containing the action.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification.
            //updateNotification();
        }
    }

    // If backward button is pressed.
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (isCounterRunning) {
            builder.setTitle("Do tou want to exit?");
            builder.setMessage("It is still counting.");
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    builder.create();
                }
            });
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    // Cancel the counter
                    cdt.cancel();
                    ProgressActivity.super.onBackPressed();
                }
            });
            builder.show();
        } else if (Integer.parseInt(setsTextView.getText().toString()) < Integer.parseInt(mSets)) {
            int remain = Integer.parseInt(mSets) - Integer.parseInt(setsTextView.getText().toString());
            builder.setTitle("Do tou want to exit?");
            builder.setMessage("You just finished " + remain + " sets......");
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    builder.create();
                }
            });
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    // Cancel the counter
                    cdt.cancel();
                    ProgressActivity.super.onBackPressed();
                }
            });
            builder.show();
        } else {
            finish();
        }
    }
    /*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, percentTextView.getText().toString());
    }
    */
}
