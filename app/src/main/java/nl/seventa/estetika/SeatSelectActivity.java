package nl.seventa.estetika;
import android.content.ContentValues;
import android.content.Context;
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

import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DATABASE_NAME;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_EMAIL;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_MOVIE_ID;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_MOVIE_NAME;
import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_SEAT_NUMBER;

public class SeatSelectActivity extends AppCompatActivity implements OnSeatSelected {

    private final String TAG = this.getClass().getSimpleName();

    private static final int COLUMNS = 5;
    private TextView txtSeatSelected;
    private Context mcontext;
    private Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.mcontext = this;

        Log.i(TAG, "seatselect oncreate" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_select);
        txtSeatSelected = (TextView)findViewById(R.id.txt_seat_selected);


        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(mcontext.getDatabasePath("reservations").getPath(),
                    null,
                    SQLiteDatabase.OPEN_READWRITE);
            Log.i(TAG, "try");



//            ContentValues testRecord = new ContentValues();
//            testRecord.put(DB_MOVIE_ID, 1);
//            testRecord.put(DB_MOVIE_NAME, "test");
//            testRecord.put(DB_SEAT_NUMBER, 3);
//            testRecord.put(DB_EMAIL, "you@live.nl");
//
//            db.insertWithOnConflict(DATABASE_NAME, null, testRecord, SQLiteDatabase.CONFLICT_REPLACE);


            String query = "SELECT * FROM " + DATABASE_NAME +
                    " WHERE " + DB_MOVIE_ID + " = 1";
            cursor = db.rawQuery(query, null);

        }
        catch (SQLiteCantOpenDatabaseException e){
            Log.i(TAG, "catch");
            Reserved_db reservations_db = new Reserved_db(mcontext);
            SQLiteDatabase db = reservations_db.getWritableDatabase();

            ContentValues testRecord = new ContentValues();
            testRecord.put(DB_MOVIE_ID, 1);
            testRecord.put(DB_MOVIE_NAME, "test");
            testRecord.put(DB_SEAT_NUMBER, 1);
            testRecord.put(DB_EMAIL, "me@live.nl");

            db.insertWithOnConflict(DATABASE_NAME, null, testRecord, SQLiteDatabase.CONFLICT_REPLACE);


        }



        List<AbstractItem> items = new ArrayList<>();
        for (int i=0; i<30; i++) {

            if (i%COLUMNS==0 || i%COLUMNS==4) {
                items.add(new EdgeItem(String.valueOf(i)));
            } else if (i%COLUMNS==1 || i%COLUMNS==2 || i%COLUMNS==3) {
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

            }
        });
    }

    @Override
    public void onSeatSelected(int count) {
        txtSeatSelected.setText("Book "+count+" seats");
    }


}

