package edu.illinois.finalproject;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

//import java.util.Vector;

/**
 * Created by dan5082 on 11/28/2017.
 */

public class LeaderboardDataFire {

    public static Map<String, Double> playerScores = new HashMap<>();
    public static Map<Integer, String> playerRanks = new HashMap<>();
    // Tips from: https://stackoverflow.com/questions/38965731/how-to-get-all-childs-data-in-firebase-database

    public static void getLeaderboard() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("leaderboard");

        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                       // collectLeaderBoardNumbers((Map<String, Double>) dataSnapshot.getValue());
                        playerScores = (Map<String, Double>) dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }

    public static void updateLeaderboard() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("leaderboard").child(LoginActivity.currUser.getDisplayName()).setValue(UsersDataFire.getPortfolioValue());

    }

    public static void collectLeaderBoardNumbers(Map<String, Double> users) {


        System.out.println("Size is: " + users.size());

        //iterate through each user
        for (Map.Entry<String, Double> entry : users.entrySet()) {

            //Get user map

            System.out.print(entry.getKey());
            System.out.println(" has a value of: " + entry.getValue());

        }


    }

    // pretty inefficient O(n)   wade would be sad
    public static int getRankOfPlayer(String userId) {



        Double playerScore = playerScores.get(userId);
        int rank = 1;
        //rank = LeaderboardDataFire.playerScores.size();


        for (Map.Entry<String, Double> entry : playerScores.entrySet()) {


            if (entry.getValue() > playerScore) {
                rank++;

            }

        }

        return rank;
    }

    // even worse O(n^2)
    public static Map<Integer,String> getPlayerRanks(){

        for (Map.Entry<String, Double> entry : playerScores.entrySet()) {

            playerRanks.put(getRankOfPlayer(entry.getKey()),entry.getKey());

            System.out.print(entry.getKey());
            System.out.println(" has a value of: " + entry.getValue());

        }
        return  playerRanks;
    }
}
