package actigraph.deviceapi.exampleapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import actigraph.deviceapi.AGDeviceLibrary;

/**
 * Created by Andy on 3/18/2018.
 */

public class SyncIntentService extends Service {
    AGDeviceLibrary agDeviceLibrary;
    Boolean tryConnect = false;
    int trys = 0;
    private static int FOREGROUND_ID=1338;
    public SyncIntentService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForeground(FOREGROUND_ID,
                buildForegroundNotification("Sync"));

        agDeviceLibrary = AGDeviceLibrary.getInstance();

        new Thread(new Runnable(){
            public void run() {
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
                wl.acquire();
                // TODO Auto-generated method stub
                while(true)
                {
                    try {

                        Log.d("INFO", "Running...");
                        if (agDeviceLibrary.GetConnectedDevice() == null) {
                            Log.d("WARN", "Device Not Found");
                            DeviceInfo dev = new DeviceInfo("TAS1D50140036", false);
                    /* AGDeviceLibrary:
                        Establish a connection to the selected device from the device list.
                    */
                            if (!tryConnect || trys >= 5) {
                                Log.d("INFO", "Try Connecting...");
                                trys = 0;
                                agDeviceLibrary.ConnectToDevice(dev.mDeviceId);
                                agDeviceLibrary.GetConnectedDevice();
                                tryConnect = true;
                            }
                            else
                                trys++;
                            //agDeviceLibrary.GetDeviceStatus();
                        } else {
                            Log.d("INFO", "Device Connected, getting Epoch data.");
                            agDeviceLibrary.GetDeviceStatus();
                        }
                        Thread.sleep(5 * 60 * 1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //REST OF CODE HERE//
                }

            }
        }).start();
        //smsHandler.sendEmptyMessageDelayed(DISPLAY_DATA, 1000);
        return Service.START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() +100, restartServicePI);
    }

    private Notification buildForegroundNotification(String filename) {
        NotificationCompat.Builder b=new NotificationCompat.Builder(this);

        b.setOngoing(true)
                .setContentTitle("ActiGraph Sync")
                .setContentText(filename)
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setTicker("Syncing");

        return(b.build());
    }

    /*@Override
    protected void onHandleIntent(@Nullable Intent intent) {
        agDeviceLibrary = AGDeviceLibrary.getInstance();
        while (true) {
            if (agDeviceLibrary.GetConnectedDevice() == null) {
                DeviceInfo dev = new DeviceInfo("TAS1D50140036", false);
                    *//* AGDeviceLibrary:
                        Establish a connection to the selected device from the device list.
                    *//*
                if (!tryConnect) {
                    agDeviceLibrary.ConnectToDevice(dev.mDeviceId);
                    agDeviceLibrary.GetConnectedDevice();
                    tryConnect = true;
                }
                //agDeviceLibrary.GetDeviceStatus();
            } else {
                agDeviceLibrary.GetDeviceStatus();
            }
            SystemClock.sleep(5 * 60 * 1000);//run every 5 min.
        }
    }*/

    @Override
    public void onDestroy() {
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class DeviceInfo {
        public DeviceInfo(String devId, Boolean isConnected) {
            mDeviceId = devId;
            mIsConnected = isConnected;
        }

        public String mDeviceId;
        public Boolean mIsConnected;
    }
}
