package nl.seventa.estetika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nl.seventa.estetika.async.MovieAsyncTask;
import nl.seventa.estetika.async.MovieListener;
import nl.seventa.estetika.domain.Movie;

public class MovieDetailActivity extends AppCompatActivity implements MovieListener {
    private final String TAG = this.getClass().getSimpleName();
    private int id;
    private String pegi;

    private ImageView image;
    private TextView title;
    private TextView genre;
    private TextView pegiTextView;
    private TextView duration;
    private TextView description;
    private Button reviewButton;
    private Button ticketButton;

    public static final String ID_INSTANCE = "Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt(ID_INSTANCE);


        image = findViewById(R.id.movieDetailImageView);
        title = findViewById(R.id.movieDetailTitleTextView);
        genre = findViewById(R.id.movieDetailGenreTextView);
        //pegiTextView = findViewById(R.id.movieDetailPegiTextView);
        duration = findViewById(R.id.movieDetailDurationTextView);
        description = findViewById(R.id.movieDetailDescriptionTextView);
        reviewButton = findViewById(R.id.movieDetailReviewButton);
        ticketButton = findViewById(R.id.movieDetailBuyTicketButton);


        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailReviewActivity.class);
                intent.putExtra(ID_INSTANCE, id);
                startActivity(intent);
            }
        });

        ticketButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SeatSelectActivity.class);
                intent.putExtra(ID_INSTANCE, id);
                startActivity(intent);
            }
        });



        getMovie();
    }

    public void getMovie() {
        String filter = getResources().getString(R.string.language_filter);
        String url = "http://api.themoviedb.org/3/movie/" + id + "?api_key=a50da447e13e19ad7c800e66c94868e7&language=" + filter;
        MovieAsyncTask task = new MovieAsyncTask(this);
        task.execute(url);
    }

    @Override
    public void onMovieListener(Movie movie) {
        Log.i(TAG, "Got movie: " + id + " from API");

        Picasso.with(this).load(movie.getUrl()).into(image);
        title.setText(movie.getTitle());
        genre.setText(movie.getGenre());
        //pegiTextView.setText(movie.getPegi());
        duration.setText(movie.getDuration() + " " + getResources().getString(R.string.minutes));
        description.setText(movie.getDescription());

    }
}
