package io.github.henryksloan.Pig;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {
    GameFragment fragment1, fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FragmentManager fragmentManager = getFragmentManager();

        fragment1 = new GameFragment();
        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        fragmentTransaction1.add(R.id.splitLayout, fragment1);
        fragmentTransaction1.commit();

        fragment2 = new GameFragment();
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        fragmentTransaction1.add(R.id.splitLayout, fragment2);
        fragmentTransaction2.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        fragment1.getView().setRotation(180);
    }
}
