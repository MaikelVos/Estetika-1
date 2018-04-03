package nl.seventa.estetika;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.seventa.estetika.domain.seatSelect.AbstractItem;
import nl.seventa.estetika.domain.seatSelect.SeatAdapter;
import nl.seventa.estetika.domain.seatSelect.CenterItem;
import nl.seventa.estetika.domain.seatSelect.EdgeItem;
import nl.seventa.estetika.domain.seatSelect.EmptyItem;
import nl.seventa.estetika.domain.seatSelect.OnSeatSelected;
import nl.seventa.estetika.domain.seatSelect.Reserved_db;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DATABASE_NAME;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_EMAIL;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_MOVIE_ID;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_MOVIE_NAME;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_SEAT_NUMBER;

public class SeatSelectActivity extends AppCompatActivity implements OnSeatSelected {

    private final String TAG = this.getClass().getSimpleName();

    private static final int COLUMNS = 7;
    private TextView txtSeatSelected;
    private Context mcontext;
    private Cursor cursor;
    private Integer movieId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.mcontext = this;
        Bundle extras = getIntent().getExtras();
        movieId = (Integer) extras.getSerializable(MovieDetailActivity.ID_INSTANCE);
        Log.i(TAG, "seatselect oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_select);
        txtSeatSelected = (TextView) findViewById(R.id.txt_seat_selected);



        try {
            //see if database exists (for viewing purposes database is on device
            SQLiteDatabase db = SQLiteDatabase.openDatabase(mcontext.getDatabasePath("reservations").getPath(),
                    null,
                    SQLiteDatabase.OPEN_READWRITE);
            Log.i(TAG, "try");


            ContentValues testRecord = new ContentValues();
            testRecord.put(DB_MOVIE_ID, 269149);
            testRecord.put(DB_MOVIE_NAME, "test");
            testRecord.put(DB_SEAT_NUMBER, 5);
            testRecord.put(DB_EMAIL, "you@live.nl");

            db.insertWithOnConflict(DATABASE_NAME, null, testRecord, SQLiteDatabase.CONFLICT_REPLACE);


            try {
                //see if the database has reservations for selected movies
                String query = "SELECT * FROM " + DATABASE_NAME +
                        " WHERE " + DB_MOVIE_ID + " = " + movieId;
                cursor = db.rawQuery(query, null);

            }
            catch (NullPointerException e){
                Log.i(TAG, "database empty");
            }


        } catch (SQLiteCantOpenDatabaseException e) {
            Log.i(TAG, "catch database non existent");
            Reserved_db reservations_db = new Reserved_db(mcontext);
            SQLiteDatabase db = reservations_db.getWritableDatabase();

            try {
                String query = "SELECT * FROM " + DATABASE_NAME +
                        " WHERE " + DB_MOVIE_ID + " = 1";
                cursor = db.rawQuery(query, null);

            }
            catch (NullPointerException o){
                Log.i(TAG, "database empty");
            }
//            ContentValues testRecord = new ContentValues();
//            testRecord.put(DB_MOVIE_ID, 1);
//            testRecord.put(DB_MOVIE_NAME, "test");
//            testRecord.put(DB_SEAT_NUMBER, 1);
//            testRecord.put(DB_EMAIL, "me@live.nl");
//
//            db.insertWithOnConflict(DATABASE_NAME, null, testRecord, SQLiteDatabase.CONFLICT_REPLACE);


        }


        List<AbstractItem> items = new ArrayList<>();
        for (int i = 0; i < 49; i++) {

            if (i % COLUMNS == 0 || i % COLUMNS == 7 ) {
                items.add(new EdgeItem(String.valueOf(i)));
            } else if (i % COLUMNS == 1 || i % COLUMNS == 2 || i % COLUMNS == 4 || i % COLUMNS == 5 || i % COLUMNS == 6 || i % COLUMNS == 7) {
                items.add(new CenterItem(String.valueOf(i)));
            } else {
                items.add(new EmptyItem(String.valueOf(i)));
            }
        }


        GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);
        recyclerView.setLayoutManager(manager);

        final SeatAdapter adapter = new SeatAdapter(this, items, cursor);
        recyclerView.setAdapter(adapter);

        txtSeatSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "book button clicked seats: " + adapter.getSelectedItems().toString());
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("MOVIEID", movieId);
                ArrayList<Integer> selectedSeats = (ArrayList<Integer>) adapter.getSelectedItems();
                intent.putExtra("SEATS", selectedSeats);
                Log.i(TAG, "TESTING ARRAYLIST BOOKED SEATS: " + selectedSeats.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSeatSelected(int count) {
        txtSeatSelected.setText("Book " + count + " seats");
    }


}

