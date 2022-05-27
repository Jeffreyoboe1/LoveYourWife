package jeffpadgett.LoveYourWife;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    String TAG = "TrophyPage";

    private ArrayList<String> myArray;

    public MyRecyclerAdapter(ArrayList<String> stringArray) {
        Log.d(TAG, "found within public constructor.");
        this.myArray = stringArray;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView tvQuote;
        public TextView tvTrophyNumber;
        public Context viewHolderContext;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuote = itemView.findViewById(R.id.tvRecyclerQuote);
            tvTrophyNumber = itemView.findViewById(R.id.tvDayTrophy);
            viewHolderContext= tvQuote.getContext();
            Log.d(TAG, "found within ViewHolder constructor..");


        }
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "found within onCreateViewHolder.");


        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view_holder,
                        parent, false);
        // set the view's size, margins, paddings and layout parameters  ...
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }



    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {

        Log.d(TAG, "found within onBind.");
        int trophyNumber = position+1;
        String stTrophyNumber = "" + trophyNumber;

        holder.tvQuote.setText(myArray.get(position));
        holder.tvTrophyNumber.setText(stTrophyNumber);
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }



}
