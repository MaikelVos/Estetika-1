package nl.seventa.estetika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TicketDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_detail);

        //IN THIS ACTIVITY THE TICKETS FOR A CERTAIN MOVIE WILL BE DISPLAYED IN A LISTVIEW
        //THERE WILL BE AN IMAGEVIEW UNDER THE LISTVIEW IN ORDER TO VIEW THE QR CODE (ONCLICK LISTENER)
    }
}
