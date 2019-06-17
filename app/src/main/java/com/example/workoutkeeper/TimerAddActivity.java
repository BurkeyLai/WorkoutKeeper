package com.example.workoutkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TimerAddActivity extends AppCompatActivity {

    private int M,S,P;
    EditText Minute,Second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_add);

        Bundle bundle =this.getIntent().getExtras();
        P = bundle.getInt("Position");

        Minute = (EditText)findViewById(R.id.T_minute);
        Second = (EditText)findViewById(R.id.T_second);
    }

    public void Set(View view) {

        if (Minute.getText().toString().isEmpty() && Second.getText().toString().isEmpty()) {

            displayToast("Please enter at least one number");
        }

        else{
            if (Minute.getText().toString().isEmpty()){
                M=0;
                S =Integer.valueOf(Second.getText().toString());
            }
            else if (Second.getText().toString().isEmpty()){
                M=Integer.valueOf(Minute.getText().toString());
                S=0;
            }
            else{
                M=Integer.valueOf(Minute.getText().toString());
                S =Integer.valueOf(Second.getText().toString());
            }
            if (S > 60){
                Second.setText("60");
                displayToast("Seconds must between 0 and 60");
            }
            else{
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("Minute", M);
                bundle.putInt("Second", S);
                bundle.putInt("Position", P);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();}
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
