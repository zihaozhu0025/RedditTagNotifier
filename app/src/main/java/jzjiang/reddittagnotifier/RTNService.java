package jzjiang.reddittagnotifier;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class RTNService extends IntentService{


    public static final int NOTIFICATION_ID = 1;

    public RTNService() {
        super("RTNService");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        JsonParsing parse = new JsonParsing("hiphopheads", "fresh");
        while (true) { // lol infinite loop
            try {
                parse.update();
                String k = parse.check();
                while (!k.equals("")) {
                    sendNotification(k);
                    k = parse.check();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.sleep(60000); // delay for 1 minute
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * Send a sample notification using the NotificationCompat API.
     */
    public void sendNotification(String url) {

        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
         * notification service can fire it on our behalf.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.reddit.com" + url));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_stat_name);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         *    versions of Android prior to 4.2 will ignore this field, so don't use it for
         *    anything vital!
         */
        builder.setContentTitle("Reddit Tag Notification");
        builder.setContentText("FRE$H songs are available!");
        builder.setSubText("Tap to view FRE$H songs.");


        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
