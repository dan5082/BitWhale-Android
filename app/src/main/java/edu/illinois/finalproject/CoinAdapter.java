package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dan5082 on 11/22/2017.
 */



 public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.ViewHolder> {

        private List<Coin> coinDatas = new ArrayList<>();

        public void setRestaurantAdapter(Coin[] coins){
            coinDatas.addAll(Arrays.asList(coins));
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View coinListItem = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.coin_in_list, parent, false);
            return new ViewHolder(coinListItem);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
           final Coin coin = coinDatas.get(position);
            holder.nameTextView.setText(coin.getName());

            final String imageUrl = coin.getImageUrl();
            if ( imageUrl != null && imageUrl.length() > 0){
                final Context context = holder.itemView.getContext();
                Picasso.with(context).load(imageUrl).into(holder.imageView);
                holder.imageView.setVisibility(View.VISIBLE);
            }else{
                holder.imageView.setVisibility(View.INVISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    Intent detailedIntent = new Intent(context, CoinDetailActivity.class);
                    detailedIntent.putExtra("coin",coin);
                    context.startActivity(detailedIntent);
                }
            });


        }

        @Override
        public int getItemViewType(int position) {
            Coin coin = coinDatas.get(position);
            return R.layout.coin_in_list;
        }

        @Override
        public int getItemCount() {

            return coinDatas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public View itemView;
            public TextView nameTextView;
            public TextView priceByBtcTextView;
            public ImageView imageView;


            public ViewHolder(View itemView){
                super(itemView);
                this.itemView = itemView;
                this.nameTextView = (TextView) itemView.findViewById(R.id.nameView);
               // this.priceByBtcTextView = (TextView) itemView.findViewById(R.id.);
                //this.userRatingTextView = (TextView) itemView.findViewById(R.id.rating);
                this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            }
        }
    }


