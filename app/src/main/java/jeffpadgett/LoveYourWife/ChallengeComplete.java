package jeffpadgett.LoveYourWife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;


// practice push
public class ChallengeComplete extends AppCompatActivity {

    TextView tvCongratulations;
    TextView tvChallengeComplete;
    TextView tvInspiration;
    ImageView imgTrophy;
    Button btnNext;
    Button btnRecommend;
    int dayCompleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_complete);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("marginStart", "" + toolbar.getTitleMarginStart());

        imgTrophy = findViewById(R.id.imgTrophy);

        tvChallengeComplete = findViewById(R.id.tvCompletedDay);
        tvInspiration = findViewById(R.id.tvCompletedInspiration);
        tvCongratulations = findViewById(R.id.tvCongrats);
        btnNext = findViewById(R.id.btnNext);
        btnRecommend = findViewById(R.id.btnRecommend);

        //set the completion day
        dayCompleted = getIntent().getIntExtra("DAY", 1);

        tvChallengeComplete.append("" + dayCompleted);

        switch (dayCompleted) {
            case 1:
                tvInspiration.setText(getString(R.string.inspire1));
                break;
            case 2:
                tvInspiration.setText(getString(R.string.inspire2));
                break;
            case 3:
                tvInspiration.setText(getString(R.string.inspire3));
                break;
            case 4:
                tvInspiration.setText(getString(R.string.inspire4));
                break;
            case 5:
                tvInspiration.setText(getString(R.string.inspire5));
                break;
            case 6:
                tvInspiration.setText(getString(R.string.inspire6));
                break;
            case 7:
                tvInspiration.setText(getString(R.string.inspire7));
                break;
            case 8:
                tvInspiration.setText(getString(R.string.inspire8));
                break;
            case 9:
                tvInspiration.setText(getString(R.string.inspire9));
                break;
            case 10:
                tvInspiration.setText(getString(R.string.inspire10));
                break;
            case 11:
                tvInspiration.setText(getString(R.string.inspire11));
                break;
            case 12:
                tvInspiration.setText(getString(R.string.inspire12));
                break;
            case 13:
                tvInspiration.setText(getString(R.string.inspire13));
                break;
            case 14:
                tvInspiration.setText(getString(R.string.inspire14));
                break;
            case 15:
                tvInspiration.setText(getString(R.string.inspire15));
                break;
            case 16:
                tvInspiration.setText(getString(R.string.inspire16));
                break;
            case 17:
                tvInspiration.setText(getString(R.string.inspire17));
                break;
            case 18:
                tvInspiration.setText(getString(R.string.inspire18));
                break;
            case 19:
                tvInspiration.setText(getString(R.string.inspire19));
                break;
            case 20:
                tvInspiration.setText(getString(R.string.inspire20));
                break;
            case 21:
                tvInspiration.setText(getString(R.string.inspire21));
                break;
            case 22:
                tvInspiration.setText(getString(R.string.inspire22));
                break;
            case 23:
                tvInspiration.setText(getString(R.string.inspire23));
                break;
            case 24:
                tvInspiration.setText(getString(R.string.inspire24));
                break;
            case 25:
                tvInspiration.setText(getString(R.string.inspire25));
                break;
            case 26:
                tvInspiration.setText(getString(R.string.inspire26));
                break;
            case 27:
                tvInspiration.setText(getString(R.string.inspire27));
                break;
            case 28:
                tvInspiration.setText(getString(R.string.inspire28));
                break;
            case 29:
                tvInspiration.setText(getString(R.string.inspire29));
                break;
            case 30:
                tvInspiration.setText(getString(R.string.inspire30));
                tvCongratulations.append(getString(R.string.Completed30Days));

                // move next button over to the right.
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) btnNext.getLayoutParams();

                params.startToStart = ConstraintLayout.LayoutParams.UNSET;
                btnNext.setLayoutParams(params);

                btnRecommend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onInviteClicked();
                    }
                });
                break;
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ChallengeComplete.this, MainActivity.class);
                backIntent.putExtra("NEXTDAY", dayCompleted);
                startActivity(backIntent);
            }
        });

        ScaleAnimation anim2 = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim2.setFillAfter(true);
        anim2.setDuration(2000);
        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                btnNext.setVisibility(View.VISIBLE);
                if (dayCompleted == 30) {
                    btnRecommend.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imgTrophy.startAnimation(anim2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_jumpBeginning:
                Intent intent = new Intent(ChallengeComplete.this, MainActivity.class);
                intent.putExtra("NEXTDAY", 123);
                startActivity(intent);
                break;
            case R.id.action_jumpEnd:
                Intent intent2 = new Intent(ChallengeComplete.this, MainActivity.class);
                intent2.putExtra("NEXTDAY", 29);
                startActivity(intent2);
                break;
            case R.id.action_jumpCurrent:
                Intent intent3 = new Intent(ChallengeComplete.this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.action_trophyPage:
                Intent trophyIntent = new Intent(ChallengeComplete.this, TrophyPage.class);
                startActivity(trophyIntent);
                break;
            case R.id.action_share_app:
                onInviteClicked();
                break;
            case R.id.action_recommend:
                onInviteClicked();
                break;
        }

        return super.onOptionsItemSelected(item);
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

    public void onInviteClicked() {


        Task<ShortDynamicLink> shortTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("http://jeffreypadgett.wixsite.com/loveyourwife")) // put website here
                .setDynamicLinkDomain("loveyourwife.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildShortDynamicLink().addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();

                            String dynamicString = shortLink.toString();
                            String msg = getString(R.string.RecommendMessage) + dynamicString;
                            String subject = getString(R.string.RecommendSubject);
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                            sendIntent.setType("text/plain");
                            startActivity(Intent.createChooser(sendIntent, getString(R.string.RecommendToFriendTitle)));

                        } else {
                            // Error
                            Toast.makeText(ChallengeComplete.this, getString(R.string.Invitation_Error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
