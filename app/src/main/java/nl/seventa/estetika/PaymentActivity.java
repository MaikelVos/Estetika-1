package nl.seventa.estetika;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DATABASE_NAME;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_EMAIL;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_MOVIE_ID;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_MOVIE_NAME;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_SEAT_NUMBER;

public class PaymentActivity extends AppCompatActivity {
    private int movieId;
    private ArrayList<Integer> selectedSeats;
    private Integer price;
    private String movieTitle;
    private String payMethod;
    private String email;
    private TextView titleTV;
    private TextView payMethodTV;
    private Button btnSucceed;
    private Button btnFail;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mcontext = this;

        Bundle extras = getIntent().getExtras();
        this.movieId = (Integer) extras.getSerializable("MOVIEID");
        this.selectedSeats = (ArrayList<Integer>) extras.getSerializable("SEATS");
        this.price = (Integer) extras.getSerializable("PRICE");
        this.movieTitle = (String) extras.getSerializable("MOVIETITLE");
        this.payMethod = (String) extras.getSerializable("PAYMETHOD");
        this.email = (String) extras.getSerializable("EMAIL");

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


                SQLiteDatabase db = SQLiteDatabase.openDatabase(mcontext.getDatabasePath("reservations").getPath(),
                        null,
                        SQLiteDatabase.OPEN_READWRITE);

                for (int i = 0; i < selectedSeats.size(); i++) {

                    ContentValues testRecord = new ContentValues();
                    testRecord.put(DB_MOVIE_ID, movieId);
                    testRecord.put(DB_MOVIE_NAME, movieTitle);
                    testRecord.put(DB_SEAT_NUMBER, selectedSeats.get(i));
                    testRecord.put(DB_EMAIL, email);

                    db.insertWithOnConflict(DATABASE_NAME, null, testRecord, SQLiteDatabase.CONFLICT_REPLACE);
                }

                //DAARNA MOET ER EEN DETAILVIEW VAN DE TICKET GE OPEND WORDEN
                Toast.makeText(PaymentActivity.super.getApplicationContext(), getResources().getString(R.string.paySucceed), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.MOVIE_NAME, movieTitle);
                intent.putExtra(MovieDetailActivity.ID_INSTANCE, movieId);
                startActivity(intent);
            }
        });



    }
}