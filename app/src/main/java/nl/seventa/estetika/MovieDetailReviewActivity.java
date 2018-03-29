package nl.seventa.estetika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import nl.seventa.estetika.async.MovieReviewListAsyncTask;
import nl.seventa.estetika.async.ReviewListener;
import nl.seventa.estetika.domain.Review;

public class MovieDetailReviewActivity extends AppCompatActivity implements ReviewListener {
    private final String TAG = this.getClass().getSimpleName();

    private ListView listView;
    private ArrayList<Review> reviews = new ArrayList<>();
    private MovieReviewAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_reviewlist);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt(MovieDetailActivity.ID_INSTANCE);

        reviews = new ArrayList<>();
        fillReviews();

        adapter = new MovieReviewAdapter(this, 0, reviews);


        listView = findViewById(R.id.movieReviewListView);
        listView.setAdapter(adapter);
    }

    private void fillReviews() {
        String url = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=a50da447e13e19ad7c800e66c94868e7";
        MovieReviewListAsyncTask task = new MovieReviewListAsyncTask(this);
        task.execute(url);
    }

    @Override
    public void onReviewListener(Review review) {
        reviews.add(review);
        adapter.notifyDataSetChanged();
        Log.i(TAG, "Dataset changed");
    }
}