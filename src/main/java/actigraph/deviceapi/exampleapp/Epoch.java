package actigraph.deviceapi.exampleapp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andy on 3/2/2018.
 */

public class Epoch {
    private Date startDateTime;
    private Date stopDateTime;
    private ArrayList<WearData> epochData;

    public Epoch(ArrayList<WearData> epochData, Date startDateTime, Date stopDateTime) {
        this.startDateTime = startDateTime;
        this.stopDateTime = stopDateTime;
        this.epochData = epochData;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Date getStopDateTime() {
        return stopDateTime;
    }

    public ArrayList<WearData> getEpochData() {
        return epochData;
    }
}
