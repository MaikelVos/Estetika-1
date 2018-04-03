package nl.seventa.estetika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.seventa.estetika.async.MovieAsyncTask;
import nl.seventa.estetika.async.MovieListener;
import nl.seventa.estetika.domain.Movie;

public class OrderActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private final List<String> payMethods = Arrays.asList("iDeal", "PayPal", "CreditCard");
    private Integer movieId;
    private ArrayList<Integer> selectedSeats;
    private TextView titleTV;
    private ListView ticketList;
    private TextView ticketsRegularTV;
    private TextView ticketsReductionTV;
    private TextView ticketsChildsTV;
    private TextView totalPriceTV;
    private EditText emailET;
    private Spinner methodSpinner;
    private Button placeOrderBtn;
    private String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Bundle extras = getIntent().getExtras();
        this.movieId = (Integer) extras.getSerializable("MOVIEID");
        this.movieTitle = (String) extras.getSerializable("MOVIENAME");
        this.selectedSeats = (ArrayList<Integer>) extras.getSerializable("SEATS");

        this.titleTV = findViewById(R.id.OrderTitle);
        this.titleTV.setText(this.selectedSeats.size() + " " + this.titleTV.getText());
        Log.i(TAG, "Adding movie title to page title");
        this.getMovie(); //Adds the movie title to the title

        this.ticketList = findViewById(R.id.TicketList);
        ArrayList<String> seatNumbersText = new ArrayList<>();
        String seat = getResources().getString(R.string.OrderActivity_seat);
        for (int i : this.selectedSeats) {
            seatNumbersText.add(seat + i);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, seatNumbersText);
        this.ticketList.setAdapter(adapter);

        this.ticketsRegularTV = findViewById(R.id.ticketsRegularTV);
        this.ticketsReductionTV = findViewById(R.id.ticketsReductionTV);
        this.ticketsChildsTV = findViewById(R.id.ticketsChildsTV);
        this.totalPriceTV = findViewById(R.id.totalPriceTV);
        this.ticketsRegularTV.setText(this.ticketsRegularTV.getText() + String.valueOf(this.selectedSeats.size()));
        this.ticketsReductionTV.setText(this.ticketsReductionTV.getText() + "0");
        this.ticketsChildsTV.setText(this.ticketsChildsTV.getText() + "0");
        final int totalPrice = 10 * this.selectedSeats.size();
        this.totalPriceTV.setText(this.totalPriceTV.getText() + String.valueOf(totalPrice));
        this.emailET = findViewById(R.id.emailET);
        this.placeOrderBtn = findViewById(R.id.placeOrderBtn);
        this.placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Creating intent and passing arguments");
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("SEATS", selectedSeats);
                intent.putExtra("PRICE", totalPrice);
                intent.putExtra("MOVIEID", movieId);
                intent.putExtra("MOVIETITLE", movieTitle);
                intent.putExtra("PAYMETHOD", (String) methodSpinner.getSelectedItem());
                intent.putExtra("EMAIL", emailET.getText().toString());
                Log.i(TAG, "Starting intent");
                startActivity(intent);
            }
        });

        this.methodSpinner = findViewById(R.id.spinnerMethod);
        ArrayAdapter<String> payMethodAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        payMethodAdapter.addAll(this.payMethods);
        this.methodSpinner.setAdapter(payMethodAdapter);
    }

    private void getMovie() {
        Log.i(TAG, "Got movie: " + movieId + " from API");
        //movieTitle = movie.getTitle();
        this.titleTV.setText(this.titleTV.getText() + " " + movieTitle);
    }

}
