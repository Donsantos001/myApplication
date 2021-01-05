package com.gamecodeschool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Test myTest;
    RadioGroup rg;
    TextView ques;
    RadioButton opA;
    RadioButton opB;
    RadioButton opC;
    RadioButton opD;
    Button confirm;

    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTest = new Test();
        myTest.startTest();
        ques = (TextView)findViewById(R.id.questionView);
        rg = (RadioGroup)findViewById(R.id.myRadios);
        confirm = (Button)findViewById(R.id.check);
        confirm.setText("START");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > -1){
                    int selected = rg.getCheckedRadioButtonId();
                    int option = -1;
                    switch (selected){
                        case R.id.optionA:
                            option = 0;
                            myTest.correct(myTest.checkAnswer(position, option));
                            break;
                        case R.id.optionB:
                            option = 1;
                            myTest.correct(myTest.checkAnswer(position, option));
                            break;
                        case R.id.optionC:
                            option = 2;
                            myTest.correct(myTest.checkAnswer(position, option));
                            break;
                        case R.id.optionD:
                            option = 3;
                            myTest.correct(myTest.checkAnswer(position, option));
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                            position--;
                            break;
                    }
                    position++;
                    if(position > 3){
                        Intent result = new Intent(getApplicationContext(), Result.class);
                        result.putExtra("com.gamecodeschool.myapplication.RESULT", myTest.getScore());
                        startActivity(result);
                    }
                    else{
                        nextQuestion();
                    }
                }
                else {
                    position++;
                    nextQuestion();
                }
            }
        });

//        Toast.makeText(this, "This is Santos' first app", Toast.LENGTH_SHORT).show();
//        Log.i("info", "App created sucessfully");
    }

    public void nextQuestion(){
        Question presentQ = myTest.getQuestion(position);
        ques.setText(presentQ.getQues());
        for(int i = 0; i < rg.getChildCount(); i++){
            ((RadioButton)rg.getChildAt(i)).setText(presentQ.getOpt()[i]);
        }

        if(position < 3){
            confirm.setText("NEXT");
        }
        else {
            confirm.setText("SUBMIT");
        }
    }
}