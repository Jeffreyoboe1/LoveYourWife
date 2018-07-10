package jeffpadgett.LoveYourWife;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageView imgRings;
    ImageView imgRings2;
    Button btnNext;
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
        btnNext = findViewById(R.id.btnNext);

        //set the completion day
        dayCompleted = getIntent().getIntExtra("DAY", 1);

        tvChallengeComplete.append("" + dayCompleted);

        switch (dayCompleted) {
            case 1:
                tvInspiration.setText("\"But love your enemies, do good to them, and lend to them without expecting to get anything back. Then your reward will be great, and you will be children of the Most High, because he is kind to the ungrateful and wicked.\" -- Luke 6:35");
                break;
            case 2:
                tvInspiration.setText("\"Death cannot stop true love.  All it can do is delay it for a while.\" \n -- Wesley, The Princess Bride");
                break;
            case 3:
                tvInspiration.setText("\"2 Be completely humble and gentle; be patient, bearing with one another in love.\" \n --Ephesians 4:2");
                break;
            case 4:
                tvInspiration.setText("\"Love is always bestowed as a gift -freely, willingly, and without expectation. We don’t love to be loved, we love to love.\" \n --Leo Buscagalia");
                break;
            case 5:
                tvInspiration.setText("\"My most brilliant achievement was my ability to persuade my wife to marry me.\" \n -- Winston Churchill");
                break;
            case 6:
                tvInspiration.setText("\"Husbands, love your wives, just as Christ loved the church and gave himself up for her.\" \n -- Ephesians 5:25");
                break;
            case 7:
                tvInspiration.setText("\"I am my beloved's, and my beloved is mine.\" \n -- Song of Solomon 8:3");
                break;
            case 8:
                tvInspiration.setText("\"My most brilliant achievement was my ability to persuade my wife to marry me.\" \n -- Winston Churchill");
                break;
            case 9:
                tvInspiration.setText("\"If I speak in the tongues of men or of angels, but do not have love, I am only a resounding gong or a clanging cymbal. If I have the gift of prophecy and can fathom all mysteries and all knowledge, and if I have a faith that can move mountains, but do not have love, I am nothing. If I give all I possess to the poor and give over my body to hardship that I may boast, but do not have love, I gain nothing.\" \n -- 1 Cor. 13:1-3");
                break;
            case 10:
                tvInspiration.setText("\"Honor her for all that her hands have done, " +
                        "and let her works bring her praise at the city gate.\" \n -- Proverbs 31:31");
                break;
            case 11:
                tvInspiration.setText("\"Darkness cannot drive out darkness; only light can do that. Hate cannot drive out hate; only love can do that.\" \n -- Martin Luther King Jr.");
                break;
            case 12:
                tvInspiration.setText("\"I love thee, I love but thee\n" +
                        "With a love that shall not die\n" +
                        "Till the sun grows cold\n" +
                        "And the stars grow old.\"");
                break;
            case 13:
                tvInspiration.setText("\"You come to love not by finding the perfect person, but by learning to see an imperfect person perfectly.\" \n -- Sam Keen ");
                break;
            case 14:
                tvInspiration.setText("\"There are only two days in the year that nothing can be done. One is called Yesterday and the other is called Tomorrow.\" \n -- Dalai Lama XIV");
                break;
            case 15:
                tvInspiration.setText("\"Marriage is a workshop… where the husband works and the wife shops.\" \n -- The Internet");
                break;
            case 16:
                tvInspiration.setText("\"I destroy my enemy if I make him my friend.\" \n -- Abraham Lincoln");
                break;
            case 17:
                tvInspiration.setText("\"I think the main lesson that we have learnt is that tolerance is the one essential ingredient of any happy marriage. It may not be quite so important when things are going well, but it is absolutely vital when the going gets difficult. You can take it from me that the Queen has the quality of tolerance in abundance.\" \n -- Prince Philip, Duke of Edinburgh");
                break;
            case 18:
                tvInspiration.setText("\"I would rather share one lifetime with you than face all the ages of this world alone.\" \n -- Arwen, The Fellowship of The Ring");
                      break;
            case 19:
                tvInspiration.setText("\"To get the full value of joy you must have someone to divide it with.\" \n -- Mark Twain");
                break;
            case 20:
                tvInspiration.setText("\"Though our feelings come and go, God's love for us does not.\" \n -- C.S. Lewis");
                break;
            case 21:
                tvInspiration.setText("\"God is love. He didn’t need us. But he wanted us. And that is the most amazing thing.\" -- Rick Warren");
                break;
            case 22:
                tvInspiration.setText("\"Love is a mighty pretty thing; but like all other delicious things, it is cloying; and when the first transports of the passion begins to subside, which it assuredly will do, and yield, oftentimes too late, to more sober reflections, it serves to evince, that love is too dainty a food to live upon alone, and ought not to be considered farther than as a necessary ingredient for that matrimonial happiness which results from a combination of causes.\" \n -- George Washington");
                break;
            case 23:
                tvInspiration.setText("\"Stay with me to-night; you must see me die. I have long had the taste of death on my tongue, I smell death, and who will stand by my Constanze, if you do not stay?\" \n -- Wolfgang Amadeus Mozart");
                break;
            case 24:
                tvInspiration.setText("\"It takes courage to love, but pain through love is the purifying fire which those who love generously know. We all know people who are so much afraid of pain that they shut themselves up like clams in a shell and, giving out nothing, receive nothing and therefore shrink until life is a mere living death.\"\n -- Eleanor Roosevelt");
                break;
            case 25:
                tvInspiration.setText("I believe myself that romantic love is the source of the most intense delights that life has to offer. In the relation of a man and woman who love each other with passion and imagination and tenderness, there is something of inestimable value, to be ignorant of which is a great misfortune to any human being.\" \n -- Bertrand Russell");
                break;
            case 26:
                tvInspiration.setText("\"Love is a fruit in season at all times, and within reach of every hand. Anyone may gather it and no limit is set. Everyone can reach this love through meditation, spirit of prayer, and sacrifice, by an intense inner life.\" \n -- Mother Theresa");
                break;
            case 27:
                tvInspiration.setText("\"Whatever we do or suffer for a friend is pleasant, because love is the principal cause of pleasure.\n -- Thomas Aquinas");
                break;
            case 28:
                tvInspiration.setText("\"The Caladrius is a bird of which it is related that, when it is carried into the presence of a sick person, if the sick man is going to die, the bird turns away its head and never looks at him; but if the sick man is to be saved the bird never loses sight of him but is the cause of curing him of all his sickness. Like unto this is the love of virtue. It never looks at any vile or base thing, but rather clings always to pure and virtuous things and takes up its abode in a noble heart; as the birds do in green woods on flowery branches. And this Love shows itself more in adversity than in prosperity; as light does, which shines most where the place is darkest.\"\n -- Leonardo Davinci");
                break;
            case 29:
                tvInspiration.setText("\"Love is not a feeling to pass away \n" +
                        "Like the balmy breath of a Summer's day....... \n" +
                        "Love is not a passion of earthly mould \n" +
                        "As a thirst for honour, or fame, or gold\"\n -- Charles Dickens");
                break;
            case 30:
                tvInspiration.setText("\"Greater love has no one than this, that someone lay down his life for his friends.\"\n -- Jesus, John 15:13");
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



        ScaleAnimation anim2 = new ScaleAnimation(0f, 1f, 0f,1f,
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

        switch (id){
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
                .setLink(Uri.parse("https://www.google.com/")) // put website here
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


                            String msg = "Hi, I have been using this app called LoveYourWife.  It can help improve your marriage. " + dynamicString;
                            String subject = "Love Your Wife";
                            String msgHtml = String.format("<p>Hi!  I've been using this app called Love Your Wife, and wanted to recommend it to you.  "
                                    + "It has great ideas to improve your marriage and love your wife better. " +
                                    " Click on this link to check it out. " + "<a href=\"%s\">Love Your Wife</a>!</p>", dynamicString);

                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                            sendIntent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml);
                            sendIntent.setType("text/plain");
                            startActivity(Intent.createChooser(sendIntent, "Recommend Love Your Wife to a friend"));

                        } else {
                            // Error
                            // ...
                            Toast.makeText(ChallengeComplete.this, "error " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
