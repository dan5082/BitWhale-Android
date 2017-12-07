package edu.illinois.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        coinName.setText(coin.getName());
        coinCap.setText("market cap: $" + coin.getMarket_cap_usd());
        coinBtcPrice.setText("Price in BTC: " + coin.getPrice_btc());



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
