package com.ychvincent.itproject2;

import java.io.Serializable;

/**
 * Created by ychvincent on 17/5/2018.
 */

public class IP implements Serializable
{
    public String ip = "192.168.0.101";

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }
}
