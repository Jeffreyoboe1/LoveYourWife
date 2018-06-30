package jeffpadgett.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.extra.Scale;


// practice push
public class ChallengeComplete extends AppCompatActivity {

    TextView tvCongratulations;
    TextView tvChallengeComplete;
    TextView tvInspiration;
    ImageView imgTrophy;
    ImageView imgRings;
    ImageView imgRings2;
    int dayCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_complete);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgTrophy = findViewById(R.id.imgTrophy);
        imgRings = findViewById(R.id.imgRings);
        imgRings2 = findViewById(R.id.imgRings);

        tvChallengeComplete = findViewById(R.id.tvCompletedDay);
        tvInspiration = findViewById(R.id.tvCompletedInspiration);
        tvCongratulations = findViewById(R.id.tvCongrats);
        ViewGroup transitionContainer = findViewById(R.id.transitionCompletedContainer);

        //set the completion day
        dayCompleted = getIntent().getIntExtra("DAY", 1);

        tvChallengeComplete.append("" + dayCompleted);

        switch (dayCompleted) {
            case 1:
                tvInspiration.setText("day 1 is so inspirational, you wouldn't believe it.");
                break;
                case 18:
                    tvInspiration.setText(R.string.inspire18);
                    break;

        }

        TextViewCompat.setAutoSizeTextTypeWithDefaults(tvCongratulations, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        ScaleAnimation anim2 = new ScaleAnimation(0f, 1f, 0f,1f,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim2.setFillAfter(true);
        anim2.setDuration(2000);

        imgTrophy.startAnimation(anim2);

    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(ChallengeComplete.this, MainActivity.class);
        backIntent.putExtra("NEXTDAY", dayCompleted);
        startActivity(backIntent);

        super.onBackPressed();
    }

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        v.startAnimation(anim);
    }
}
