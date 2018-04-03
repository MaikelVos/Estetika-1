package nl.seventa.estetika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import nl.seventa.estetika.ContactActivity;
import nl.seventa.estetika.OrderActivity;
import nl.seventa.estetika.R;

public class CinemaReviewActivity extends AppCompatActivity implements View.OnClickListener{
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_review);

        submitButton = findViewById(R.id.cinema_create_review_submit_button);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String message = getResources().getString(R.string.cinema_create_review_submit_message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
        startActivity(intent);
    }
}
