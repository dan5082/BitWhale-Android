package edu.illinois.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

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


        // Setting the views
//        final TextView restaurantNameButton = (TextView) findViewById(R.id.restaurantNameButton);
//        final TextView costForTwo = (TextView) findViewById(R.id.costForTwo);
//        final ImageButton foodImageButtonLink = (ImageButton) findViewById(R.id.pictureLinkButton);
//        final Button locationButtonLink = (Button) findViewById(R.id.locationButton);
//        final TextView cuisines = (TextView) findViewById(R.id.cuisinesText);

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
