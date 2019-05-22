package com.acote.mastermind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ScoreAddActivity extends AppCompatActivity {

    private TextView tvCongrats;
    private EditText etName;

    public static final int RESULT_NOINPUT = 0;

    public static final String BUNDLE_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_add);

        tvCongrats = findViewById(R.id.lbCongrats);
        etName = findViewById(R.id.etName);

        Bundle extras = getIntent().getExtras();

        final int score = extras.getInt(MainActivity.BUNDLE_SCORE);

        tvCongrats.setText("Congrats! Your score is " + score + "! Please enter your name");

        Button btDone = findViewById(R.id.btDone);

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (etName.getText().toString().isEmpty()){
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else {
                    Bundle extras = new Bundle();
                    extras.putInt(MainActivity.BUNDLE_SCORE, score);
                    extras.putString(BUNDLE_NAME, etName.getText().toString());

                    replyIntent.putExtras(extras);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
