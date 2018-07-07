package jeffpadgett.LoveYourWife;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {

        switch (position){
            case 0:
                holder.tvQuote.setText(R.string.inspire1);
                break;
            case 1:
                holder.tvQuote.setText(R.string.inspire2);
                break;
            case 2:
                holder.tvQuote.setText(R.string.inspire3);
                break;
            case 3:
                holder.tvQuote.setText(R.string.inspire4);
                break;
            case 4:
                holder.tvQuote.setText(R.string.inspire5);
                break;
            case 5:
                holder.tvQuote.setText(R.string.inspire6);
                break;
            case 6:
                holder.tvQuote.setText(R.string.inspire7);
                break;
            case 7:
                holder.tvQuote.setText(R.string.inspire8);
                break;
            case 8:
                holder.tvQuote.setText(R.string.inspire9);
                break;
            case 9:
                holder.tvQuote.setText(R.string.inspire10);
                break;
            case 10:
                holder.tvQuote.setText(R.string.inspire11);
                break;
            case 11:
                holder.tvQuote.setText(R.string.inspire12);
                break;
            case 12:
                holder.tvQuote.setText(R.string.inspire13);
                break;
            case 13:
                holder.tvQuote.setText(R.string.inspire14);
                break;
            case 14:
                holder.tvQuote.setText(R.string.inspire15);
                break;
            case 15:
                holder.tvQuote.setText(R.string.inspire16);
                break;
            case 16:
                holder.tvQuote.setText(R.string.inspire17);
                break;
            case 17:
                holder.tvQuote.setText(R.string.inspire18);
                break;
            case 18:
                holder.tvQuote.setText(R.string.inspire19);
                break;
            case 19:
                holder.tvQuote.setText(R.string.inspire20);
                break;
            case 20:
                holder.tvQuote.setText(R.string.inspire21);
                break;
            case 21:
                holder.tvQuote.setText(R.string.inspire22);
                break;
            case 22:
                holder.tvQuote.setText(R.string.inspire23);
                break;
            case 23:
                holder.tvQuote.setText(R.string.inspire24);
                break;
            case 24:
                holder.tvQuote.setText(R.string.inspire25);
                break;
            case 25:
                holder.tvQuote.setText(R.string.inspire26);
                break;
            case 26:
                holder.tvQuote.setText(R.string.inspire27);
                break;
            case 27:
                holder.tvQuote.setText(R.string.inspire28);
                break;
            case 28:
                holder.tvQuote.setText(R.string.inspire29);
                break;
            case 29:
                holder.tvQuote.setText(R.string.inspire30);
                break;


        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvQuote;
        public Context viewHolderContext;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuote = itemView.findViewById(R.id.tvRecyclerQuote);
            final Context viewHolderContext= tvQuote.getContext();



        }
    }

}
