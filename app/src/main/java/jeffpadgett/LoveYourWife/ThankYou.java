package jeffpadgett.LoveYourWife;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class ThankYou extends AppCompatActivity {

    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        btnContinue = findViewById(R.id.btnContinue);



        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueIntent = new Intent(ThankYou.this, MainActivity.class);
                startActivity(continueIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent continueIntent = new Intent(ThankYou.this, MainActivity.class);
        startActivity(continueIntent);
        super.onBackPressed();
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
                Intent intent = new Intent(ThankYou.this, MainActivity.class);
                intent.putExtra("NEXTDAY", 123);
                startActivity(intent);
                break;
            case R.id.action_jumpEnd:
                Intent intent2 = new Intent(ThankYou.this, MainActivity.class);
                intent2.putExtra("NEXTDAY", 29);
                startActivity(intent2);
                break;
            case R.id.action_jumpCurrent:
                Intent intent3 = new Intent(ThankYou.this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.action_trophyPage:
                Intent trophyIntent = new Intent(ThankYou.this, TrophyPage.class);
                startActivity(trophyIntent);
                break;
            case R.id.action_share_app:
                onInviteClicked();
                break;
            case R.id.action_recommend:
                onInviteClicked();
                break;
            case R.id.action_removeAds:
                Intent removeAdsIntent = new Intent(ThankYou.this, MainActivity.class);
                removeAdsIntent.putExtra("REMOVE_ADS", true);
                startActivity(removeAdsIntent);
                break;

        }

        return super.onOptionsItemSelected(item);
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
                            Toast.makeText(ThankYou.this, "error " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
