package edu.illinois.finalproject;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static edu.illinois.finalproject.LeaderboardDataFire.getLeaderboard;


/**
 * Created by dan5082 on 11/28/2017.
 */

public class UsersDataFire {

    public static Map<String, Object> userCoinMap = new HashMap<>();

    public static void addNewCoin(String coinId, double amount, double pricePaid, String userID) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userID).child("portfolio").child(coinId).child("amount").setValue(amount);
        mDatabase.child("users").child(userID).child("portfolio").child(coinId).child("priceBought").setValue(pricePaid);
    }

    public static void updateCoinAmount(String coinId, double amount, String userID) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userID).child("portfolio").child(coinId).child("amount").setValue(amount);

    }



    public static void removeCoin(String coinId, String userID) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userID).child("portfolio").child(coinId).removeValue();

    }

    public static void getAUsersCoins(final String userId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance()
                .getReference().child("users").child(userId).child("portfolio");


        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of coin attributes in datasnapshot
                        printOutCoinsHelper((Map<String, Object>) dataSnapshot.getValue(), userId);
                        userCoinMap = (Map<String, Object>) dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }


    public static void inititateAccount(final String userId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance()
                .getReference().child("users");
        final DatabaseReference LDatabase = FirebaseDatabase.getInstance()
                .getReference().child("leaderboard");


        mDatabase.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of coin attributes in datasnapshot
                       if (!dataSnapshot.exists()) {
                           mDatabase.child(userId).child("portfolio").child("bitcoin").child("amount").setValue(10.0000);
                           mDatabase.child(userId).child("portfolio").child("bitcoin").child("priceBought").setValue(1);
                           mDatabase.child(userId).child("portValue").setValue(10.0000);
                           mDatabase.child(userId).child("alias").setValue(LoginActivity.currUser.getDisplayName());
                           LDatabase.child(LoginActivity.currUser.getDisplayName()).setValue(10);
                       }else{
                           getAUsersCoins(userId);
                           getLeaderboard();
                       }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    public static void printOutCoinsHelper(Map<String, Object> usersCoins, String userId) {

        //iterate through each coin
        for (Map.Entry<String, Object> entry : usersCoins.entrySet()) {

            //Get a map of coin data
            Map singleCoin = (Map) entry.getValue();
            System.out.println(userId + " owns " + singleCoin.get("amount") + " " + entry.getKey());
            System.out.println("They were purchased for " + singleCoin.get("priceBought") + "\n");

        }

    }

    public static boolean doesOwnCoin(String coinId){
        //getAUsersCoins(LoginActivity.currUser.getUid());
        if(userCoinMap.containsKey(coinId)){
            return true;
        }
        return false;
    }

    public static boolean tryToBuyCoin(String coinId, Double amount){

        getAUsersCoins(LoginActivity.currUser.getUid());

        Map btc = (Map) userCoinMap.get("bitcoin");
        Double btcAvailable = (Double) btc.get("amount");
        Double coinCost = coinAsyncTask.coinBtcPriceMap.get(coinId);
        Double tCost = amount * coinCost;
        if(btcAvailable >= tCost){
            Double remainingBtc = btcAvailable - tCost;
            if(doesOwnCoin(coinId)){
                Map coin = (Map) userCoinMap.get(coinId);
                Double coinOwned = (Double) coin.get("amount");
                coinOwned += amount;
                updateCoinAmount(coinId,coinOwned,LoginActivity.currUser.getUid());
            }else {
                addNewCoin(coinId, amount, coinCost, LoginActivity.currUser.getUid());
                //userCoinMap.put(coinId,)

            }
            updateCoinAmount("bitcoin", remainingBtc, LoginActivity.currUser.getUid());
            getAUsersCoins(LoginActivity.currUser.getUid());
            LeaderboardDataFire.updateLeaderboard();

            return true;
        }

        return false;

    }

    public static boolean tryToSellCoin(String coinId, Double amount){

        getAUsersCoins(LoginActivity.currUser.getUid());

        Map btc = (Map) userCoinMap.get("bitcoin");
        Double btcAvailable = (Double) btc.get("amount");
        Double coinCost = coinAsyncTask.coinBtcPriceMap.get(coinId);
        Double tCost = amount * coinCost;
        if(true){
            Double remainingBtc = btcAvailable + tCost;
            if(doesOwnCoin(coinId)){
                Map coin = (Map) userCoinMap.get(coinId);
                Double coinOwned = (Double) coin.get("amount");
                coinOwned -= amount;
                updateCoinAmount(coinId,coinOwned,LoginActivity.currUser.getUid());
            }else {
                addNewCoin(coinId, amount, coinCost, LoginActivity.currUser.getUid());
                //userCoinMap.put(coinId,)

            }
            updateCoinAmount("bitcoin", remainingBtc, LoginActivity.currUser.getUid());
            getAUsersCoins(LoginActivity.currUser.getUid());
            LeaderboardDataFire.updateLeaderboard();

            return true;
        }

        return false;

    }



    public static Double getPortfolioValue(){
        Double retVal = 0.0;
        getAUsersCoins(LoginActivity.currUser.getUid());


         for (Map.Entry<String, Object> entry : userCoinMap.entrySet()) {

            //Get a map of coin data
            Map singleCoin = (Map) entry.getValue();
            //System.out.println(userId + " owns " + singleCoin.get("amount") + " " + entry.getKey());
            retVal += ((Double)singleCoin.get("amount") * coinAsyncTask.coinBtcPriceMap.get(entry.getKey()));
            //System.out.println("They were purchased for " + singleCoin.get("priceBought") + "\n");

        }

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child("users").child(LoginActivity.currUser.getUid()).child("portValue").setValue(retVal);
//        mDatabase.child("leaderboard").child(LoginActivity.currUser.getDisplayName()).setValue(retVal);

        return retVal;
    }



}
