package com.cudaf.myappportfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButtonsListener();

    }

    private void addButtonsListener() {
        Button buttonSpotify = (Button) findViewById(R.id.button_spotify);
        buttonSpotify.setOnClickListener(this);
        Button buttonScores = (Button) findViewById(R.id.button_scores);
        buttonScores.setOnClickListener(this);
        Button buttonLibrary = (Button) findViewById(R.id.button_library);
        buttonLibrary.setOnClickListener(this);
        Button buttonBigger = (Button) findViewById(R.id.button_bigger);
        buttonBigger.setOnClickListener(this);
        Button buttonCapstone = (Button) findViewById(R.id.button_capstone);
        buttonCapstone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            String message = String.format(getResources().getString(R.string.toast_message),
                ((Button) v).getText().toString());
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
