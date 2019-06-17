package com.example.workoutkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RMShowActivity extends AppCompatActivity {

    private double[] rm = new double[]{
            1,0.95,0.925,0.9,0.875,0.85,0.825,0.8,0.775,0.75,0.725,0.7
    };

    TextView a,b,c,d,e,f;
    private int W,T;
    private int ONE ,TWO,THREE,FORE,FIVE,SIX ;
    private double percentage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmshow);

        Bundle info =this.getIntent().getExtras();

        W = info.getInt("Weight");
        T = info.getInt("Rtimes");

        a=(TextView) findViewById(R.id.textView6);
        b=(TextView) findViewById(R.id.textView8);
        c=(TextView) findViewById(R.id.textView10);
        d=(TextView) findViewById(R.id.textView12);
        e=(TextView) findViewById(R.id.textView14);
        f=(TextView) findViewById(R.id.textView16);


        percentage = rm[T-1];
        Log.d("w",String.valueOf(percentage));
        a.setText(String.valueOf(Math.round(W/percentage)));
        b.setText(String.valueOf(Math.round(W/percentage*0.95)));
        c.setText(String.valueOf(Math.round(W/percentage*0.9)));
        d.setText(String.valueOf(Math.round(W/percentage*0.8)));
        e.setText(String.valueOf(Math.round(W/percentage*0.7)));
        f.setText(String.valueOf(Math.round(W/percentage*0.5)));

    }

}
