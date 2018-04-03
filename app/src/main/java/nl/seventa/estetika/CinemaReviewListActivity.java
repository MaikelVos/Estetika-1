package nl.seventa.estetika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CinemaReviewListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_review_list);

        createReview = findViewById(R.id.cinemaReviewCreateReviewButton);
        createReview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), CinemaReviewActivity.class);
        startActivity(intent);
    }
}
