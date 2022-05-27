package jeffpadgett.LoveYourWife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TrophyPage extends AppCompatActivity {

    String TAG;

    TextView tvNoTrophy;
    RecyclerView recyclerView;
    MyRecyclerAdapter recyclerAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<String> stringArray;
    int lastCompletedChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TAG = "TrophyPage";

        recyclerView = findViewById(R.id.recyclerTrophies);
        tvNoTrophy = findViewById(R.id.tvMustCompleteChallenge);

        //recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);


        recyclerView.setLayoutManager(layoutManager);


        recyclerView.addItemDecoration(new DividerItemDecoration(TrophyPage.this, DividerItemDecoration.VERTICAL));

        stringArray = new ArrayList<>();

        stringArray.add(getString(R.string.inspire1));
        stringArray.add(getString(R.string.inspire2));
        stringArray.add(getString(R.string.inspire3));
        stringArray.add(getString(R.string.inspire4));
        stringArray.add(getString(R.string.inspire5));
        stringArray.add(getString(R.string.inspire6));
        stringArray.add(getString(R.string.inspire7));
        stringArray.add(getString(R.string.inspire8));
        stringArray.add(getString(R.string.inspire9));
        stringArray.add(getString(R.string.inspire10));
        stringArray.add(getString(R.string.inspire11));
        stringArray.add(getString(R.string.inspire12));
        stringArray.add(getString(R.string.inspire13));
        stringArray.add(getString(R.string.inspire14));
        stringArray.add(getString(R.string.inspire15));
        stringArray.add(getString(R.string.inspire16));
        stringArray.add(getString(R.string.inspire17));
        stringArray.add(getString(R.string.inspire18));
        stringArray.add(getString(R.string.inspire19));
        stringArray.add(getString(R.string.inspire20));
        stringArray.add(getString(R.string.inspire21));
        stringArray.add(getString(R.string.inspire22));
        stringArray.add(getString(R.string.inspire23));
        stringArray.add(getString(R.string.inspire24));
        stringArray.add(getString(R.string.inspire25));
        stringArray.add(getString(R.string.inspire26));
        stringArray.add(getString(R.string.inspire27));
        stringArray.add(getString(R.string.inspire28));
        stringArray.add(getString(R.string.inspire29));
        stringArray.add(getString(R.string.inspire30));

        Log.d(TAG, "string Array after adding 30 strings: " + stringArray.toString());
        SharedPreferences sharedPreferences = getSharedPreferences("LASTCOMPLETED", 0);
        lastCompletedChallenge = sharedPreferences.getInt("LASTCOMPLETED", 0);

        int removeThisPositionAndLater = lastCompletedChallenge;

        Log.d(TAG, "remove this position and later: " + removeThisPositionAndLater);

        stringArray.subList(removeThisPositionAndLater, stringArray.size()).clear();

        if(stringArray.size() == 0) {
            tvNoTrophy.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "string Array after removing strings: " + stringArray.toString());

        recyclerAdapter = new MyRecyclerAdapter(stringArray);

        recyclerView.setAdapter(recyclerAdapter);















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
                Intent intent = new Intent(TrophyPage.this, MainActivity.class);
                intent.putExtra("NEXTDAY", 123);
                startActivity(intent);
                break;
            case R.id.action_jumpEnd:
                Intent intent2 = new Intent(TrophyPage.this, MainActivity.class);intent2.putExtra("NEXTDAY", 29);
                startActivity(intent2);
                break;
            case R.id.action_jumpCurrent:
                Intent intent3 = new Intent(TrophyPage.this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.action_trophyPage:
                Intent trophyIntent = new Intent(TrophyPage.this, TrophyPage.class);
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
                            Toast.makeText(TrophyPage.this, getString(R.string.Invitation_Error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
