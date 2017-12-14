package edu.illinois.finalproject;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


/**
 * Created by dan5082 on 11/28/2017.
 */

public class UsersDataFire {

    public static Map<String, Object> userCoinMap;

    public static void addNewCoin(String coinId, double amount, double pricePaid, String userID) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userID).child("portfolio").child(coinId).child("amount").setValue(amount);
        mDatabase.child("users").child(userID).child("portfolio").child(coinId).child("priceBought").setValue(pricePaid);
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
}
