package com.joker.crashhandler;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by yogio on 2016/8/5.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler
{
    private static final CrashHandler sHandler = new CrashHandler();
    private static final Thread.UncaughtExceptionHandler sDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    private static final ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();
    private Future<?> future;
    private CrashListener mListener;
    private File mLogFile;

    public static CrashHandler getInstance()
    {
        return sHandler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable)
    {
        CrashLogUtil.writeLog(mLogFile,"CrashHandler",throwable.getMessage(),throwable);
        future = THREAD_POOL.submit(new Runnable()
        {
            @Override
            public void run()
            {
                if(mListener != null)
                {
                    mListener.afterSaveCrash(mLogFile);
                }
            }
        });
        if(!future.isDone())
        {
            try
            {
                future.get();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        sDefaultHandler.uncaughtException(thread, throwable);
    }

    public void init(File logFile,CrashListener listener)
    {
        mListener = listener;
        mLogFile = logFile;
    }
}
