package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        final CoinAdapter coinAdapter = new CoinAdapter();

        coinAsyncTask cAsyncTask = new coinAsyncTask(coinAdapter);
        cAsyncTask.execute("https://api.coinmarketcap.com/v1/ticker/?limit=100");

        final RecyclerView coinRe = (RecyclerView) findViewById(R.id.recyclerView);

        coinRe.setAdapter(coinAdapter);
        coinRe.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        coinRe.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("My Portfolio");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Top 100 Coins");
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName("Leaderboard");

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3,
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
                                Intent openPort = new Intent(context, MyPortfolio.class);
                                context.startActivity(openPort);
                            }
                            if (drawerItem.getIdentifier() == 2) {
                                final Context context = view.getContext();
                                Intent openTopCoins = new Intent(context, MainActivity.class);
                                context.startActivity(openTopCoins);
                            }
                            if (drawerItem.getIdentifier() == 3) {
                                final Context context = view.getContext();
                                Intent openleaderboard = new Intent(context, LeaderBoard.class);
                                context.startActivity(openleaderboard);
                            }


                        }
                        return true;

                    }
                })
                .build();
    }
}