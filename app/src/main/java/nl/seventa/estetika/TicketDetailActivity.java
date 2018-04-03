package nl.seventa.estetika;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

import nl.seventa.estetika.domain.Movie;

import static nl.seventa.estetika.datalayer.Reserved_db.DATABASE_NAME;
import static nl.seventa.estetika.datalayer.Reserved_db.DB_EMAIL;
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
    private Integer selectedTicket;

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
        Log.i(TAG, "SELECTED NOW: " + seatsLV.getSelectedItemPosition());
        getTickets(email, movie.getMovieId());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tickets);
        this.seatsLV.setAdapter(adapter);
        this.qrCodeIV = findViewById(R.id.qrCodeTV);

        this.seatsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTicket = tickets.get(position);
                Bitmap bitmap = createQR(selectedTicket);
                qrCodeIV.setImageBitmap(bitmap);
            }
        });

        this.qrCodeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTicket != null) {
                    sendMail(createQR(tickets.get(selectedTicket)), selectedTicket);
                } else {
                    Toast.makeText(mcontext, getResources().getString(R.string.noSeatSelected), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Selected Ticket " + selectedTicket.toString() + " was NULL");
                }
            }
        });

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

    private Bitmap createQR(int seatNumber) {
        String movieID = String.valueOf(movie.getMovieId());
        String seat = String.valueOf(seatNumber);
        String qrText = "{\"MOVIE\": \"" + movieID + "\", \"SEAT\": \"" + seat + "\"}";
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(qrText, BarcodeFormat.QR_CODE, 150, 150);
            return bitmap;
        } catch(Exception e) {
            Log.i(TAG, "QR Code generator failed!");
        }
        return null;
    }

    private void sendMail(Bitmap qrBm, int seat) {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mcontext, "Error: Can't write image to device", Toast.LENGTH_LONG).show();
            return;
        }
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), qrBm,"QRTicket", null);
        Uri screenshotUri = Uri.parse(path);
        final Intent emailIntent1 = new Intent(     android.content.Intent.ACTION_SEND);
        emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent1.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        emailIntent1.putExtra(Intent.EXTRA_SUBJECT, "Ticket for " + movie.getTitle());
        emailIntent1.putExtra(Intent.EXTRA_TEXT, "Seat: " + seat);
        emailIntent1.setType("image/png");
        Log.i(TAG, "Sending email with ticket..");
        startActivity(Intent.createChooser(emailIntent1, "Send email using"));
    }
}
