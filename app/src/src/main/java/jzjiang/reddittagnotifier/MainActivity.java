package jzjiang.reddittagnotifier;

import android.os.Bundle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.Activity;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.*;
import java.util.*;

import java.io.IOException;

public class MainActivity extends Activity {

    public static final int NOTIFICATION_ID = 1;
    Button about_button;
    ListView trackList;
    Button settings_button;
    public static String currentBg;
    public static boolean bgOn = true;
    public LinkedList<String[]>songQ = new LinkedList<String[]>();
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = new Intent(this, RTNService.class);
        startService(intent);
        setContentView(R.layout.activity_main);
        currentBg="white";
        about_button= (Button)(findViewById(R.id.AboutButton));
        about_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intentToAbout = new Intent(getApplicationContext(),About.class);
                startActivity(intentToAbout);
            }
        });

        settings_button = (Button)(findViewById(R.id.SettingsButton));
        settings_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intentToSettings = new Intent (getApplicationContext(),Settings.class);
                startActivity(intentToSettings);
            }
        });
        trackList = (ListView)(findViewById(R.id.trackList));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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