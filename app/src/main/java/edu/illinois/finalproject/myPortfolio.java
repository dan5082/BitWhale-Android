package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

/**
 * Created by dan5082 on 12/6/17.
 */

public class myPortfolio extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.portfolio);
            Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
            setSupportActionBar (toolbar);

            // Passing in all the needed information
            //final Intent intent = getIntent();
            // Coin coin = intent.getParcelableExtra("coin");
            //final ImageView coinImage = (ImageView) findViewById(R.id.detail_coin_img);
            // final TextView coinName = (TextView) findViewById(R.id.detailed_name);
            //final TextView coinCap = (TextView) findViewById(R.id.marketCapDet);
            // final TextView coinBtcPrice = (TextView) findViewById(R.id.priceBTCdet);
            // coinName.setText(coin.getName());
            // coinCap.setText("market cap: $" + coin.getMarket_cap_usd());
            // coinBtcPrice.setText("Price in BTC: " + coin.getPrice_btc());
            final CoinAdapter coinAdapter = new CoinAdapter();

            coinAsyncTask cAsyncTask = new coinAsyncTask(coinAdapter);
            cAsyncTask.execute("https://api.coinmarketcap.com/v1/ticker/?limit=6" );

            final RecyclerView coinRe = (RecyclerView) findViewById(R.id.recyclerView);

            coinRe.setAdapter(coinAdapter);
            coinRe.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            coinRe.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
            ImageView profilePicture = (ImageView) findViewById(R.id.portProfilePic);


            //If the url is !null we will use picasso to fetch the image
            final String imageUrl = "https://avatars2.githubusercontent.com/u/1738461?s=400&v=4";
            if ( imageUrl != null && imageUrl.length() > 0){
                Picasso.with(this).load(imageUrl).into(profilePicture);
                profilePicture.setVisibility(View.VISIBLE);
            }else{
                profilePicture.setVisibility(View.INVISIBLE);
            }

            PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("My Portfolio");
            SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Top 100 Coins");

//create the drawer and remember the `Drawer` result object
            Drawer result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .addDrawerItems(
                            item1,
                            new DividerDrawerItem(),
                            item2,
                            new SecondaryDrawerItem().withIdentifier(3).withName("Leaderboard"),
                            new DividerDrawerItem()
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            // do something with the clicked item :D

                            if(drawerItem != null){
                                Intent intent = null;
                                if (drawerItem.getIdentifier() == 1) {
                                    final Context context = view.getContext();
                                    Intent openPort = new Intent(context, myPortfolio.class);
                                    context.startActivity(openPort);
                                }
                                if (drawerItem.getIdentifier() == 2) {
                                    final Context context = view.getContext();
                                    Intent openTopCoins = new Intent(context, MainActivity.class);
                                    context.startActivity(openTopCoins);
                                }
                                if (drawerItem.getIdentifier() == 3) {
                                    final Context context = view.getContext();
                                    Intent openLeaderboard = new Intent(context, leaderBoard.class);
                                    context.startActivity(openLeaderboard);
                                }


                            }
                            return true;

                        }
                    })
                    .build();
        }


    }
