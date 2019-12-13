package com.example.snake.other;

import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static android.content.ContentValues.TAG;

public class TwitterSender {

    private static Twitter twitter;

    public void configureTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("oEJEAxEPjNgWb0O35QNVaA")
                .setOAuthConsumerSecret("2TyiPmQMpnYHPE3S8ITkIQWld5fjk6jQ5eGfTsG8kg")
                .setOAuthAccessToken("927024486-4X07W3nTicx2SG0dTccqsNzraAyT1G8Ffc4VvNqN")
                .setOAuthAccessTokenSecret("neehbYt9lBY6o29UdcMLsZ1Zs9vVLPPncOpivLoyXtA");

        //cb.setUseSSL(true);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public void sendTweet(int score, String city) {
        String latestStatus = "Prave jsem ziskal " + score + " skore v Snakovi v " + city + (score >= 10 ? ", jsem boss" : "");
        Log.d(TAG, "sendTweet: " + latestStatus);
        try {
            Status status = twitter.updateStatus(latestStatus);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

}
