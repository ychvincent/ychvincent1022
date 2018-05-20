package com.ychvincent.itproject2;

import java.io.Serializable;

/**
 * Created by ychvincent on 10/5/2018.
 */

public class MessageHolder implements Serializable {

    String id;
    String message;

    public MessageHolder()
    {

    }
    public MessageHolder(String id, String message)
    {
        this.id=id;
        this.message=message;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
