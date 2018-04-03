package nl.seventa.estetika.datalayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Reserved_db extends SQLiteOpenHelper {
    private static final  int DATABASE_VERSION = 7;

    public static final String DATABASE_NAME = "reservations";
    public final static String DB_SEAT_NUMBER = "seatId";
    public final static String DB_MOVIE_NAME = "movieName";
    public final static String DB_MOVIE_ID = "movieId";
    public  final static String DB_INDEX = "_id";
    public final static String DB_EMAIL = "Email";

    public Reserved_db(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        String query = "CREATE TABLE " + DATABASE_NAME + " (" +
                DB_INDEX +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DB_MOVIE_ID + " INTEGER  , " +
                DB_MOVIE_NAME + " TEXT, " +
                DB_SEAT_NUMBER + " INTEGER, " +
                DB_EMAIL + " TEXT" +
                "); ";
        db.execSQL(query);

    }

    //when version number changes this method is called use an ALTER db statement
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS reservations;");
        //onCreate(db);

    }
}

