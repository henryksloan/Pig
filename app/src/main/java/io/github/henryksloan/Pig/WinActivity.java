package io.github.henryksloan.Pig;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {
    TextView winnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Intent intent = getIntent();
        findViewById(android.R.id.content).setBackgroundColor(ContextCompat.getColor(this,
                intent.getIntExtra(GameActivity.EXTRA_COLOR, R.color.colorPrimary)));

        winnerText = (TextView) findViewById(R.id.winnerText);
        winnerText.setText(getString(R.string.win,
                intent.getIntExtra(GameActivity.EXTRA_WINNER_NUMBER, 0)));
    }
}
