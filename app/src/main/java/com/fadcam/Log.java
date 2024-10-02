package com.fadcam;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Log {
    private static File logFile;

    private static final String TAG = "Log";

    public static void init(Context context)
    {
        logFile = new File(context.getExternalFilesDir(Constants.RECORDING_DIRECTORY), "logs.html");
        try {
            if(!logFile.exists())
            {
                logFile.createNewFile();
            }
        } catch (IOException e) {
            android.util.Log.e(TAG, "Error creating log file", e);
        }
    }

    private static String getCurrentTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }

    public static void d(String tag, String message) {
        String logMessage = "<font color=\"34495e\">" + getCurrentTimeStamp() + " INFO: [" + tag + "]" + message + "</font>";
        writeLogToFile(logMessage);
    }

    public static void w(String tag, String message) {
        String logMessage = "<font color=\"f1c40f\">" + getCurrentTimeStamp() + " WARNING: [" + tag + "]" + message + "</font>";
        writeLogToFile(logMessage);
    }

    public static void e(String tag, Object... objects) {
        StringBuilder message = new StringBuilder();
        for(Object object: objects)
        {
            if(object instanceof String)
            {
                message.append(object);
            }
            else if(object instanceof Exception)
            {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                ((Exception) object).printStackTrace(printWriter);
                String stackTrace = stringWriter.toString();
                message.append(stackTrace);
            }
        }

        String logMessage = "<font color=\"e74c3c\">" + getCurrentTimeStamp() + " ERROR: [" + tag + "]" + message + "</font>";
        writeLogToFile(logMessage);
    }

    private static void writeLogToFile(String logMessage) {
        try (FileWriter fileWriter = new FileWriter(logFile, true)) {
            fileWriter.append(logMessage).append("\n");
        } catch (IOException e) {
            android.util.Log.e(TAG, "Error writing log to file", e);
        }
    }
}
