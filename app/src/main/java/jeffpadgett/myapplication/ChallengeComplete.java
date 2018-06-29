package jeffpadgett.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.extra.Scale;

public class ChallengeComplete extends AppCompatActivity {

    TextView tvCongratulations;
    TextView tvChallengeComplete;
    TextView tvInspiration;
    int dayCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_complete);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvChallengeComplete = findViewById(R.id.tvCompletedDay);
        tvInspiration = findViewById(R.id.tvCompletedInspiration);
        tvCongratulations = findViewById(R.id.tvCongrats);
        ViewGroup transitionContainer = findViewById(R.id.transitionCompletedContainer);

        //set the completion day
        dayCompleted = getIntent().getIntExtra("DAY", 1);

        tvChallengeComplete.append("" + dayCompleted);
        Transition scale = new Scale();
        scale.setDuration(4000);

        TransitionManager.beginDelayedTransition(transitionContainer, new Scale());

        tvChallengeComplete.setVisibility(View.VISIBLE);
        tvInspiration.setVisibility(View.VISIBLE);
        tvCongratulations.setVisibility(View.VISIBLE);



    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(ChallengeComplete.this, MainActivity.class);
        backIntent.putExtra("NEXTDAY", dayCompleted);
        startActivity(backIntent);

        super.onBackPressed();
    }
}
