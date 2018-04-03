package nl.seventa.estetika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private int movieId;
    private ArrayList<Integer> selectedSeats;
    private Integer price;
    private String movieTitle;
    private String payMethod;
    private TextView titleTV;
    private TextView payMethodTV;
    private Button btnSucceed;
    private Button btnFail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle extras = getIntent().getExtras();
        this.movieId = (Integer) extras.getSerializable("MOVIEID");
        this.selectedSeats = (ArrayList<Integer>) extras.getSerializable("SEATS");
        this.price = (Integer) extras.getSerializable("PRICE");
        this.movieTitle = (String) extras.getSerializable("MOVIETITLE");
        this.payMethod = (String) extras.getSerializable("PAYMETHOD");

        this.titleTV = findViewById(R.id.titleTV);
        this.titleTV.setText(this.titleTV.getText() + String.valueOf(this.price));

        this.payMethodTV = findViewById(R.id.payMethodTV);
        this.payMethodTV.setText(this.payMethod);

        this.btnSucceed = findViewById(R.id.btnSucceed);
        this.btnFail = findViewById(R.id.btnFail);

        this.btnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(OrderActivity.super.getApplicationContext(), "Ordered " + selectedSeats.size() + " tickets for " + movieTitle + " for €" + String.valueOf(10 * selectedSeats.size()), Toast.LENGTH_LONG).show();
                Toast.makeText(PaymentActivity.super.getApplicationContext(), payMethod + " " + getResources().getString(R.string.payFailTryAgain), Toast.LENGTH_LONG).show();
            }
        });

        this.btnSucceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DATABASE STUKJE HIER MAIKEL
                //DAARNA MOET ER EEN DETAILVIEW VAN DE TICKET GE OPEND WORDEN
                Toast.makeText(PaymentActivity.super.getApplicationContext(), payMethod + " " + getResources().getString(R.string.paySucceed), Toast.LENGTH_LONG).show();
            }
        });



    }
}