package nl.seventa.estetika;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.seventa.estetika.domain.Review;

public class MovieReviewAdapter extends ArrayAdapter {
    private final String TAG = this.getClass().getSimpleName();
    private List<Review> reviews = new ArrayList();
    private TextView movieReviewItemAuthorText;
    private TextView movieReviewItemReviewText;

    public MovieReviewAdapter(@NonNull Context context, int resource, @NonNull List reviews) {
        super(context, resource, reviews);
        this.reviews = reviews;
    }


    @Override
    public int getCount() {
        Log.i(TAG, "Amount of reviews: " + reviews.size());
        return reviews.size();
    }

    //Inflate item for ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Review review = reviews.get(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.moviereview_item, parent, false);

        movieReviewItemAuthorText = convertView.findViewById(R.id.movieReviewItemAuthorText);
        movieReviewItemReviewText = convertView.findViewById(R.id.movieReviewItemReviewText);

        String author = review.getAuthor();
        String content = review.getText();

        movieReviewItemAuthorText.setText(author);
        movieReviewItemReviewText.setText(content);

        Log.i(TAG, "Inflated review item for ListView");
        return convertView;
    }
}
