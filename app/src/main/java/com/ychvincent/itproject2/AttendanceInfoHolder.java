package com.ychvincent.itproject2;

import java.io.Serializable;

/**
 * Created by ychvincent on 17/5/2018.
 */

public class AttendanceInfoHolder implements Serializable
{
    public String moduleid, modulename, modulestarttime,getModuleendtime,  attendtime;

    public AttendanceInfoHolder(String moduleid, String modulename, String modulestarttime, String getModuleendtime, String attendtime)
    {
        this.moduleid = moduleid;
        this.modulename = modulename;
        this.modulestarttime = modulestarttime;
        this.getModuleendtime = getModuleendtime;
        this.attendtime = attendtime;
    }

    public String getModuleid()
    {
        return moduleid;
    }

    public void setModuleid(String moduleid)
    {
        this.moduleid = moduleid;
    }

    public String getModulename()
    {
        return modulename;
    }

    public void setModulename(String modulename)
    {
        this.modulename = modulename;
    }

    public String getModulestarttime()
    {
        return modulestarttime;
    }

    public void setModulestarttime(String modulestarttime)
    {
        this.modulestarttime = modulestarttime;
    }

    public String getGetModuleendtime()
    {
        return getModuleendtime;
    }

    public void setGetModuleendtime(String getModuleendtime)
    {
        this.getModuleendtime = getModuleendtime;
    }

    public String getAttendtime()
    {
        return attendtime;
    }

    public void setAttendtime(String attendtime)
    {
        this.attendtime = attendtime;
    }
}
