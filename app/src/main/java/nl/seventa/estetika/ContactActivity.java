package nl.seventa.estetika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener{
    private String iframeTest = "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2479.0464326283936!2d4.7910600156909675!3d51.585712379648015!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47c6a1d6278ce763%3A0xe43aa841e693f04a!2sLovensdijkstraat+61%2C+4818+AJ+Breda!5e0!3m2!1snl!2snl!4v1522254126354\" width=\"100%\" height=\"100%\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>";
    private WebView webView;
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        this.webView = findViewById(R.id.contactWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        Log.i(TAG, "Loading WebView (Google Maps)");
        webView.loadData(this.iframeTest, "text/html", null);

        Button reviewButton = findViewById(R.id.contact_review_button);
        reviewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), CinemaReviewListActivity.class);
        startActivity(intent);
    }
}
