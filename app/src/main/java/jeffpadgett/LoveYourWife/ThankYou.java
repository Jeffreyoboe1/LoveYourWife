package jeffpadgett.LoveYourWife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    public void onBackPressed() {
        Intent continueIntent = new Intent(ThankYou.this, MainActivity.class);
        startActivity(continueIntent);
        super.onBackPressed();
    }
}
