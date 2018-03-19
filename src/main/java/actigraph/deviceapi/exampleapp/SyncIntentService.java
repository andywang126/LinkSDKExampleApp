package actigraph.deviceapi.exampleapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import actigraph.deviceapi.AGDeviceLibrary;

/**
 * Created by Andy on 3/18/2018.
 */

public class SyncIntentService extends IntentService {
    AGDeviceLibrary agDeviceLibrary;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SyncIntentService(String name) {
        super(name);
    }

    public SyncIntentService() {
        super("SyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
         agDeviceLibrary = AGDeviceLibrary.getInstance();
        while (true) {
            if (agDeviceLibrary.GetConnectedDevice() == null) {
                DeviceInfo dev = new DeviceInfo("TAS1D50140036", false);
                    /* AGDeviceLibrary:
                        Establish a connection to the selected device from the device list.
                    */
                agDeviceLibrary.ConnectToDevice(dev.mDeviceId);
                agDeviceLibrary.GetConnectedDevice();
                agDeviceLibrary.GetDeviceStatus();
            } else {
                agDeviceLibrary.GetDeviceStatus();
            }
            SystemClock.sleep(5 * 60 * 1000);//run every 5 min.
        }
    }

    @Override
    public void onDestroy(){
        stopSelf();
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
