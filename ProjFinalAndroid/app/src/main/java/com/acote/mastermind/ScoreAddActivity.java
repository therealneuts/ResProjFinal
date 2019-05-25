package com.acote.mastermind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Activité simple qui est lancée lorsque l'usager gagne. On affiche son score et on lui demande d'entrer son nom
//Pour l'ajouter à la BD.
public class ScoreAddActivity extends AppCompatActivity {

    private TextView tvCongrats;
    private EditText etName;


    public static final String BUNDLE_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_add);

        tvCongrats = findViewById(R.id.lbCongrats);
        etName = findViewById(R.id.etName);

        Bundle extras = getIntent().getExtras();

        final int score = extras.getInt(MainActivity.BUNDLE_SCORE);

        String prompt = String.format("%s %d \n %s", getResources().getString(R.string.win_prompt_1),
                score,
                getResources().getString(R.string.win_prompt_2));

        tvCongrats.setText(prompt);

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
