package com.princeton.hack.paintapp;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private boolean yayOrNay(int day, int hour, int minute)
    {
        // no late meal on Saturday and Sunday
        if (day == 1 || day == 7)
            return false;
        // there's only lunch on Friday
        else if (day == 6)
        {
            // late lunch is from 2:00-3:45pm
            if (hour == 14)
                return true;
            else if (hour == 15)
            {
                if (minute <= 45)
                    return true;
            }
            // not time for late lunch so return false
            return false;
        }
        // otherwise, it's Monday-Thursday and there's lunch and dinner
        else
        {
            // late lunch is from 2:00-3:45pm
            if (hour == 14)
                return true;
            else if (hour == 15)
            {
                if (minute <= 45)
                    return true;
            }
            // late dinner is from 8:30-10:00pm
            else if (hour == 20)
            {
                if (minute >= 30)
                    return true;
                return false;
            }
            else if (hour == 21 || (hour == 22 && minute == 0))
                return true;
            // not time for late lunch or late dinner, so return false
            return false;
        }
    }

    public void onButtonClick(View v)
    {
        TextView t1 = (TextView)findViewById(R.id.textView);
        DateFormat gethour = new SimpleDateFormat("HH");
        DateFormat getminute = new SimpleDateFormat("mm");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = Integer.parseInt(gethour.format(calendar.getTime()));
        int minute = Integer.parseInt(getminute.format(calendar.getTime()));
        if (yayOrNay(day, hour, minute))
        {
            t1.setText("Yeeee");
            // set color to green
            t1.setTextColor(Color.rgb(25, 156, 66));
        }
        else
        {
            t1.setText("Nahhh");
            // set color to red
            t1.setTextColor(Color.rgb(245, 32, 32));
        }
    }

    public void onButtonClickClear(View v)
    {
        TextView t1 = (TextView)findViewById(R.id.textView);
        t1.setText(" ");
    }

    public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, ShowNotificationDetailActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.notification_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(r.getString(R.string.notification_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
