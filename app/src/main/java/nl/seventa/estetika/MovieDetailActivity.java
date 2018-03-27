package nl.seventa.estetika;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MovieDetailActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private int id;

    public static final String ID_INSTANCE = "Id";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt(ID_INSTANCE);
    }
}
