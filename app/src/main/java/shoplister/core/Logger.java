package shoplister.core;

import android.util.Log;

public class Logger
{
    public static void Log(String message)
    {
        Log.println(Log.ASSERT, "Logger", message);
    }

}
