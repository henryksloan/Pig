package io.github.henryksloan.Pig;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {
    public static final String EXTRA_PLAYER_COUNT = "io.github.henryksloan.Pig.WINNER_NUMBER";

    Button startButton;
    SeekBar playerCountBar;
    LinearLayout labelLayout;

    private void addLabelsBelowSeekBar() {
        labelLayout.setPadding(10, 0, 10, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(35, 10, 35, 0);
        labelLayout.setLayoutParams(params);

        for (int count = 0; count <= playerCountBar.getMax(); count++) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.START);
            textView.setText(String.valueOf(count + 2));
            labelLayout.addView(textView);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    (count == playerCountBar.getMax()) ? 0.0f : 1.0f));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        startButton = (Button) findViewById(R.id.startButton);
        playerCountBar = (SeekBar) findViewById(R.id.playerCountBar);
        labelLayout = (LinearLayout) findViewById(R.id.labelLayout);

        addLabelsBelowSeekBar();

        startButton.getBackground().setColorFilter(
                ContextCompat.getColor(this, R.color.colorP1),
                PorterDuff.Mode.MULTIPLY);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                intent.putExtra(EXTRA_PLAYER_COUNT, playerCountBar.getProgress() + 2);
                startActivity(intent);
            }
        });
    }
}
