package com.joker.crashhandler;

import java.io.File;

/**
 * Created by yogio on 2016/8/5.
 */
public interface CrashListener
{
    public void afterSaveCrash(File file);
}
