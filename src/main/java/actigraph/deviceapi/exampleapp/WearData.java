package actigraph.deviceapi.exampleapp;

import java.util.Date;

/**
 * Created by Andy on 3/2/2018.
 */

class WearData {
    private Date timestamp;
    private int wearDetect;
    private int xCounts;
    private int yCounts;
    private int zCounts;
    private int steps;
    private int heartRate;

    public WearData(Date timestamp, int wearDetect, int xCounts, int yCounts, int zCounts, int steps, int heartRate) {
        this.timestamp = timestamp;
        this.wearDetect = wearDetect;
        this.xCounts = xCounts;
        this.yCounts = yCounts;
        this.zCounts = zCounts;
        this.steps = steps;
        this.heartRate = heartRate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getWearDetect() {
        return wearDetect;
    }

    public int getxCounts() {
        return xCounts;
    }

    public int getyCounts() {
        return yCounts;
    }

    public int getzCounts() {
        return zCounts;
    }

    public int getSteps() {
        return steps;
    }

    public int getHeartRate() {
        return heartRate;
    }
}
