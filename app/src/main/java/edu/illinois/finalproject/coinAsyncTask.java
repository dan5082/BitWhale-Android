package edu.illinois.finalproject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

//import static android.content.ContentValues.TAG;


/**
 * Created by dan5082 on 11/23/2017.
 */

public class coinAsyncTask extends AsyncTask<String, Integer, Coins> {

    public static final String TAG = coinAsyncTask.class.getSimpleName();
    private CoinAdapter coinAdapter;
    Coin[] coinArray;
    public static Map<String,Double> coinBtcPriceMap = new HashMap<>();

    //Coins theCoins;


    public coinAsyncTask(CoinAdapter coinAdapter) {

        this.coinAdapter = coinAdapter;
    }

    @Override
    protected Coins doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();
            //connection.setRequestProperty("user-key", ApiKey.API_KEY);
            connection.connect();

            InputStream inStream = connection.getInputStream();
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));

            Gson gson = new Gson();
            coinArray = gson.fromJson(inStreamReader, Coin[].class);

             //coinArray = gson.fromJson(TestJson.test,Coin[].class);

             Coins theCoins = new Coins(coinArray);
            return theCoins;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Coins baseJsonData) {
        // super.onPostExecute(baseJsonData);
        if (baseJsonData == null) {
            return;
        }
        coinAdapter.setRestaurantAdapter(baseJsonData.getCoins());
        //ownedCoinAdapter.setRestaurantAdapter(baseJsonData.getCoins());
        for (int i = 0; i < baseJsonData.getCoins().length; i++) {
            String coinName = baseJsonData.getCoins()[i].getName();
            Log.d(TAG, "Coin Name: " + coinName);
            coinBtcPriceMap.put(baseJsonData.getCoins()[i].getId(),baseJsonData.getCoins()[i].getPrice_btc());
        }
        coinAdapter.notifyDataSetChanged();
    }
}
