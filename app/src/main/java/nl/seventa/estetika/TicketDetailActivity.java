package nl.seventa.estetika;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

import nl.seventa.estetika.domain.Movie;
import nl.seventa.estetika.domain.Ticket;

import static nl.seventa.estetika.datalayer.Reserved_db.DATABASE_NAME;
import static nl.seventa.estetika.datalayer.Reserved_db.DB_EMAIL;
import static nl.seventa.estetika.datalayer.Reserved_db.DB_INDEX;
import static nl.seventa.estetika.datalayer.Reserved_db.DB_MOVIE_ID;
import static nl.seventa.estetika.datalayer.Reserved_db.DB_SEAT_NUMBER;

public class TicketDetailActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Integer> tickets;
    private Context mcontext;
    private Movie movie;
    private String email;
    private ListView seatsLV;
    private ImageView qrCodeIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_detail);
        this.mcontext = this;
        this.tickets = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        this.movie = (Movie) extras.getSerializable("MOVIE");
        this.email = (String) extras.getSerializable("EMAIL");
        this.seatsLV = findViewById(R.id.seatsLV);
        getTickets(email, movie.getMovieId());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tickets);
        this.seatsLV.setAdapter(adapter);
        this.qrCodeIV = findViewById(R.id.qrCodeTV);

        this.seatsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int seatNumber = tickets.get(position);
                insertQR(seatNumber);
            }
        });

        //IN THIS ACTIVITY THE TICKETS FOR A CERTAIN MOVIE WILL BE DISPLAYED IN A LISTVIEW
        //THERE WILL BE AN IMAGEVIEW UNDER THE LISTVIEW IN ORDER TO VIEW THE QR CODE (ONCLICK LISTENER)
    }

    private void getTickets(String email, int movieId) {
        tickets.clear();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(mcontext.getDatabasePath("reservations").getPath(),
                null,
                SQLiteDatabase.OPEN_READWRITE);

        String query = "SELECT DISTINCT " + DB_SEAT_NUMBER + " FROM " + DATABASE_NAME +
                " WHERE " + DB_EMAIL + " = " + " '" + email + "' " +
                " AND " + DB_MOVIE_ID + " = " + " '" + movieId + "' " +
                " ORDER BY " + DB_SEAT_NUMBER + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        for (int i = 0; i < cursor.getCount(); i++) {

            cursor.moveToPosition(i);
            int seatNumber = cursor.getInt(cursor.getColumnIndex(DB_SEAT_NUMBER));
            if(!tickets.contains(seatNumber)){
                tickets.add(seatNumber);
            }
        }
        Log.i(TAG, "movieId" + tickets.toString());
    }

    private void insertQR(int seatNumber) {
        String movieID = String.valueOf(movie.getMovieId());
        String seat = String.valueOf(seatNumber);
        String qrText = "{\"MOVIE\": \"" + movieID + "\", \"SEAT\": \"" + seat + "\"}";
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(qrText, BarcodeFormat.QR_CODE, 150, 150);
            qrCodeIV.setImageBitmap(bitmap);
        } catch(Exception e) {
            Log.i(TAG, "QR Code generator failed!");
        }

    }
}
