package io.github.henryksloan.Pig;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    public static final String EXTRA_COLOR = "io.github.henryksloan.Pig.COLOR";
    public static final String EXTRA_WINNER_NUMBER = "io.github.henryksloan.Pig.WINNER_NUMBER";

    final int playerColors[] = {R.color.colorP1,
                                R.color.colorP2,
                                R.color.colorP3,
                                R.color.colorP4,
                                R.color.colorP5,
                                R.color.colorP6,
                                R.color.colorP7,
                                R.color.colorP8};

    int playerCount;
    int currentPlayerIndex;
    int currentTurnPoints;
    int points[];

    Random rand;

    LinearLayout infoArea;
    TextView playerText;
    TextView turnPointsText;
    LinearLayout pointsArea;
    TextView playerTexts[];
    /* TextView player1Text;
    TextView player2Text; */
    Button rollButton;
    Button holdButton;

    private void updateText() {
        playerText.setText(getString(R.string.player_text, (currentPlayerIndex + 1)));
        for (int i = 0; i < playerTexts.length; i++) {
            playerTexts[i].setText(getString(R.string.player_points_text, points[i]));
        }
        turnPointsText.setText(getString(R.string.turn_points_text, currentTurnPoints));
    }

    private void setInfoColor() {
        findViewById(android.R.id.content).setBackgroundColor(ContextCompat.getColor(this, playerColors[currentPlayerIndex]));
        rollButton.getBackground().setColorFilter(
                ContextCompat.getColor(this, playerColors[currentPlayerIndex]),
                PorterDuff.Mode.MULTIPLY);
        holdButton.getBackground().setColorFilter(
                ContextCompat.getColor(this, playerColors[currentPlayerIndex]),
                PorterDuff.Mode.MULTIPLY);
    }

    private void roll() {
        int roll = rand.nextInt(6) + 1;
        if (roll == 1) {
            currentTurnPoints = 0;
            nextTurn();
        }
        else {
            currentTurnPoints += roll;
        }
        updateText();
    }

    private void updatePoints() {
        points[currentPlayerIndex] += currentTurnPoints;
        currentTurnPoints = 0;
    }

    private void nextTurn() {
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        holdButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                rollButton.setEnabled(true);
                holdButton.setEnabled(true);
            }
        }, 500);
        currentPlayerIndex += 1;
        currentPlayerIndex %= playerCount;
        updateText();
        setInfoColor();
    }

    private boolean checkWin() {
        for (int i = 0; i < points.length; i++) {
            if (points[i] >= 100) {
                onWin(i);
                return true;
            }
        }

        return false;
    }

    private void onWin(int winner_index) {
        currentPlayerIndex = 0;
        currentTurnPoints = 0;
        points = new int[playerCount];
        updateText();
        setInfoColor();

        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra(EXTRA_COLOR, playerColors[winner_index]);
        intent.putExtra(EXTRA_WINNER_NUMBER, winner_index + 1);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        playerCount = intent.getIntExtra(MainMenuActivity.EXTRA_PLAYER_COUNT, 2);
        currentPlayerIndex = 0;
        currentTurnPoints = 0;
        points = new int[playerCount];

        rand = new Random();

        infoArea = (LinearLayout) findViewById(R.id.infoArea);
        playerText = (TextView) findViewById(R.id.playerText);
        turnPointsText = (TextView) findViewById(R.id.turnPointsText);
        pointsArea = (LinearLayout) findViewById(R.id.pointsArea);

        playerTexts = new TextView[playerCount];
        for (int i = 0; i < playerCount; i++) {
            playerTexts[i] = new TextView(new ContextThemeWrapper(this, R.style.GameText));
            playerTexts[i].setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f/playerCount));
            playerTexts[i].setBackgroundColor(ContextCompat.getColor(this, playerColors[i]));
            pointsArea.addView(playerTexts[i]);
        }

        rollButton = (Button)findViewById(R.id.rollButton);
        holdButton = (Button)findViewById(R.id.holdButton);



        updateText();
        setInfoColor();

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roll();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePoints();
                if (!checkWin()) {
                    nextTurn();
                }
            }
        });
    }
}
