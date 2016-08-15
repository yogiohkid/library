package com.joker.crashhandler;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by yogio on 2016/8/5.
 */
public class CrashLogUtil
{
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());

    public static synchronized void writeLog(File logFile,String tag,String message,Throwable throwable)
    {
        logFile.getParentFile().mkdirs();
        if(!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        String time = timeFormat.format(Calendar.getInstance().getTime());

        synchronized (logFile)
        {
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            PrintWriter printWriter = null;
            try
            {
                fileWriter = new FileWriter(logFile,true);
                bufferedWriter = new BufferedWriter(fileWriter);
                printWriter = new PrintWriter(fileWriter);
                bufferedWriter.append(time).append(" ").append("E").append("/").append(tag).append(" ").append(message).append("\n");
                bufferedWriter.flush();
                throwable.printStackTrace(printWriter);
                printWriter.flush();
                fileWriter.flush();
            } catch (IOException e)
            {
                closeQuietly(fileWriter);
                closeQuietly(bufferedWriter);
                closeQuietly(printWriter);
            }
        }
    }

    public static void closeQuietly(Closeable closeable)
    {
        if(closeable != null)
        {
            try
            {
                closeable.close();
            } catch (IOException e)
            {
            }
        }
    }
}
