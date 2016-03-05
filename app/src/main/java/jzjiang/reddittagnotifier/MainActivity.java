package jzjiang.reddittagnotifier;

import android.os.Bundle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import java.net.*;
import java.lang.reflect.Array;
import java.util.*;

import java.io.IOException;

public class MainActivity extends Activity implements Animation.AnimationListener{

    private static final String TASK = "main";
    public static final int NOTIFICATION_ID = 1;
    public static JsonParsing parse = new JsonParsing("hiphopheads", "fresh");
    Button about_button;
    ListView trackList;
    Button settings_button;
    public static String currentBg;
    public static boolean bgOn = true;
    Animation animationfadeout;
    ImageView fireFade;
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = new Intent(this, RTNService.class);
        startService(intent);
        setContentView(R.layout.activity_main);
        animationfadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        fireFade = (ImageView)findViewById(R.id.fire);
        currentBg="white";
        about_button= (Button)(findViewById(R.id.AboutButton));
        about_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToAbout = new Intent(getApplicationContext(), About.class);
                startActivity(intentToAbout);
            }
        });

        settings_button = (Button)(findViewById(R.id.SettingsButton));
        settings_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToSettings = new Intent(getApplicationContext(), Settings.class);
                startActivity(intentToSettings);
            }
        });
        trackList = (ListView)(findViewById(R.id.trackListID));
        LinkedList<ParsedString>goonStrings = new LinkedList<ParsedString>();
        for(String[]thread: parse.total_thread_list)
            goonStrings.add(new ParsedString(thread));
        ArrayAdapter<ParsedString>tracksAdapter = new ArrayAdapter<ParsedString>
                (getApplicationContext(), android.R.layout.simple_list_item_1,goonStrings);
        trackList.setAdapter(tracksAdapter);
        trackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>parent,final View view, int position, long Id)
            {

                final ParsedString item = (ParsedString)parent.getItemAtPosition(position);
                //Log.i(TASK, "called");
                Uri uri = Uri.parse(item.returnUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });
        //TYPE ARTIST TITLE UPVOTES URL

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        //fireFade.setVisibility(View.GONE);
        fireFade.startAnimation(animationfadeout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}