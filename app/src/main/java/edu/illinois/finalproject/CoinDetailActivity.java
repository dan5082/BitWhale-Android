package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import static edu.illinois.finalproject.LeaderboardDataFire.getLeaderboard;

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
        final Coin coin = intent.getParcelableExtra("coin");
        final ImageView coinImage = (ImageView) findViewById(R.id.detail_coin_img);
        final TextView coinName = (TextView) findViewById(R.id.detailed_name);
        final TextView coinCap = (TextView) findViewById(R.id.marketCapDet);
        final TextView coinBtcPrice = (TextView) findViewById(R.id.priceBTCdet);
        final Button buyButton = (Button) findViewById(R.id.buy_button);
        final Button sellButton = (Button) findViewById(R.id.sell_button);
        final EditText amountToTransact = findViewById(R.id.amountToTransact);
        final TextView youOwn = findViewById(R.id.you_own_det);
        final String coinId = coin.getId();

        coinName.setText(coin.getName());
        coinCap.setText("market cap: $" + coin.getMarket_cap_usd());
        coinBtcPrice.setText("Price in BTC: " + coin.getPrice_btc());
        //Map coinThis = (Map) UsersDataFire.userCoinMap.get(coin.getId());
        //Double coinOwned = (Double) coinThis.get("amount");

        //youOwn.setText("You currently own: " + coinOwned);


        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final Context context = v.getContext();
//                Intent transaction = new Intent(context, TransactionActivity.class);
//                context.startActivity(transaction);
                Double amount = Double.parseDouble(amountToTransact.getText().toString());
                if(UsersDataFire.tryToBuyCoin(coin.getId(), amount)){
                    Toast.makeText(CoinDetailActivity.this, "Successfully purchased", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(CoinDetailActivity.this, "Sorry, you need more Bitcoin", Toast.LENGTH_LONG).show();
                }


            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = Double.parseDouble(amountToTransact.getText().toString());
                if(UsersDataFire.tryToSellCoin(coin.getId(), amount)){
                    Toast.makeText(CoinDetailActivity.this, "Successfully Sold", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(CoinDetailActivity.this, "Sorry, you dont have that much", Toast.LENGTH_LONG).show();
                }


            }
        });

        if (!UsersDataFire.doesOwnCoin(coin.getId())) {
            sellButton.setVisibility(View.INVISIBLE);
            youOwn.setText("You dont own any " + coin.getName() );
        }else{
            Map coin3 = (Map) UsersDataFire.userCoinMap.get(coinId);
            Double coinOwned = (Double) coin3.get("amount");
            youOwn.setText("You already own " + coinOwned + " " + coin.getName() );
        }


        // If the url is !null we will use picasso to fetch the image
        final String imageUrl = coin.getImageUrl();
        if (imageUrl != null && imageUrl.length() > 0) {
            Picasso.with(this).load(imageUrl).into(coinImage);
            coinImage.setVisibility(View.VISIBLE);
        } else {
            coinImage.setVisibility(View.INVISIBLE);
        }


    }
}





