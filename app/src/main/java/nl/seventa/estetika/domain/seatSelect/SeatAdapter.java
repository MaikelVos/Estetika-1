package nl.seventa.estetika.domain.seatSelect;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.List;

import nl.seventa.estetika.R;

import static nl.seventa.estetika.domain.seatSelect.Reserved_db.DB_SEAT_NUMBER;


public class SeatAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private OnSeatSelected mOnSeatSelected;
    private int i;

    private static class EdgeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSeat;
        private final ImageView imgSeatSelected;
        private final ImageView imgSeatReserved;

        public EdgeViewHolder(View itemView) {
            super(itemView);
            imgSeat = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            imgSeatReserved = (ImageView) itemView.findViewById(R.id.img_seat_reserved);
        }

    }



    private static class CenterViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSeat;
        private final ImageView imgSeatSelected;
        private final ImageView imgSeatReserved;

        public CenterViewHolder(View itemView) {
            super(itemView);
            imgSeat = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            imgSeatReserved = (ImageView) itemView.findViewById(R.id.img_seat_reserved);
        }

    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

    }

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AbstractItem> mItems;
    private Cursor cursor;
    //private ArrayList<Integer> taken = new ArrayList<>();
    private HashSet<Integer> taken = new HashSet<>();


    public SeatAdapter(Context context, List<AbstractItem> items, Cursor cursor) {
        mOnSeatSelected = (OnSeatSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
        this.cursor = cursor;

        for (i = 0; i < cursor.getCount(); i++) {


        cursor.moveToPosition(i);
        int test = cursor.getInt(cursor.getColumnIndex(DB_SEAT_NUMBER));
            taken.add(test);
            Log.i(TAG, "count " + cursor.getCount());
            Log.i(TAG, "whatever");

        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        //mItems.set(2, new ReservedItem(String.valueOf(position)));
        return mItems.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AbstractItem.TYPE_CENTER) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
            return new CenterViewHolder(itemView);
        } else if (viewType == AbstractItem.TYPE_EDGE) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
            return new EdgeViewHolder(itemView);
        }
        else {
            View itemView = new View(mContext);
            return new EmptyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        int type = mItems.get(position).getType();

//        for (i = 0; i < cursor.getCount(); i++) {
//
//        //Log.i(TAG, test.toString());
//        cursor.moveToPosition(0);
//
//        Integer test = (cursor.getColumnIndex(DB_SEAT_NUMBER));
//
//        System.out.println(test);


        if (taken.contains(position)){


                if (type == AbstractItem.TYPE_CENTER) {
                    Log.i(TAG, "seats " + "are reserved");
                    //final ReservedItem item = (ReservedItem) mItems.get(position);
                    CenterViewHolder holder = (CenterViewHolder) viewHolder;
                    holder.imgSeatReserved.setVisibility(View.VISIBLE);

                } else if (type == AbstractItem.TYPE_EDGE) {
                    final EdgeItem item = (EdgeItem) mItems.get(position);
                    EdgeViewHolder holder = (EdgeViewHolder) viewHolder;
                    holder.imgSeatReserved.setVisibility(View.VISIBLE);
                }
            }
         else {


//      here the onclick gets removed for the seats that are reserved needs async database implementation!, for each value in database


//          setting onclick listener per seat
            if (type == AbstractItem.TYPE_CENTER) {
                final CenterItem item = (CenterItem) mItems.get(position);
                CenterViewHolder holder = (CenterViewHolder) viewHolder;


                holder.imgSeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleSelection(position);
                        mOnSeatSelected.onSeatSelected(getSelectedItemCount());
                    }
                });

                holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

            } else if (type == AbstractItem.TYPE_EDGE) {
                final EdgeItem item = (EdgeItem) mItems.get(position);
                EdgeViewHolder holder = (EdgeViewHolder) viewHolder;


                holder.imgSeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        toggleSelection(position);
                        mOnSeatSelected.onSeatSelected(getSelectedItemCount());
                    }
                });

                holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
            }

        }
    }
}





