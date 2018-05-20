package com.ychvincent.itproject2;

import java.io.Serializable;

/**
 * Created by ychvi on 18/5/2018.
 */

public class TimeTableHolder implements Serializable {
    Long timestamp;
    String data;

    public TimeTableHolder(Long timestamp, String data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
