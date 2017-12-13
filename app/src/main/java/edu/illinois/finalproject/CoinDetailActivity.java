package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by dan5082 on 12/6/2017.
 */

public class CoinDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_coin_detail);

        // Passing in all the needed information
        final Intent intent = getIntent();
        Coin coin = intent.getParcelableExtra("coin");
        final ImageView coinImage = (ImageView) findViewById(R.id.detail_coin_img);
        final TextView coinName = (TextView) findViewById(R.id.detailed_name);
        final TextView coinCap = (TextView) findViewById(R.id.marketCapDet);
        final TextView coinBtcPrice = (TextView) findViewById(R.id.priceBTCdet);
        final Button buyButton = (Button) findViewById(R.id.buy_button);
        final Button sellButton = (Button) findViewById(R.id.sell_button);
        coinName.setText(coin.getName());
        coinCap.setText("market cap: $" + coin.getMarket_cap_usd());
        coinBtcPrice.setText("Price in BTC: " + coin.getPrice_btc());

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Context context = v.getContext();
                Intent transaction = new Intent(context, TransactionActivity.class);
                context.startActivity(transaction);
            }
        });





        // If the url is !null we will use picasso to fetch the image
        final String imageUrl = coin.getImageUrl();
        if ( imageUrl != null && imageUrl.length() > 0){
                Picasso.with(this).load(imageUrl).into(coinImage);
            coinImage.setVisibility(View.VISIBLE);
        }else{
            coinImage.setVisibility(View.INVISIBLE);
        }




    }
}
