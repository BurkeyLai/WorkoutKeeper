package com.example.workoutkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RMCalculatorActivity extends AppCompatActivity {

    EditText Weight,Rtimes;
    private int W,T;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmcalculator);
    }


    public void RM(View view) {

        Weight = (EditText)findViewById(R.id.weight);
        Rtimes = (EditText)findViewById(R.id.rtimes);


        if (Weight.getText().toString().isEmpty()){
            displayToast(getString(R.string.rm_dialog_weight_is_empty));

        }

        else if (Rtimes.getText().toString().isEmpty()){
            displayToast(getString(R.string.rm_dialog_rep_is_empty));
        }

        else {

            W = Integer.valueOf(Weight.getText().toString());
            T = Integer.valueOf(Rtimes.getText().toString());
        }

        if (W <= 0){

            displayToast(getString(R.string.rm_dialog_weight_must_bigger_than_zero));
        }

        else if (T<=0 || T>12){
            displayToast(getString(R.string.rm_dialog_rep_times));
        }

        else {

            Intent intent = new Intent(this, RMShowActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Weight",W);
            bundle.putInt("Rtimes",T);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
