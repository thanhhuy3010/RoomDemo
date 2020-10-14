/**
 * @file LoggerManager.java
 * @brief Implement save log cat to  and handle
 * <p>
 * Copyright(C) Hitachi Information & Communication Engineering, Ltd. 2020. All Rights Reserved.
 */
package com.example.demoroomdb.Common.Logger;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class LoggerManager {
  /**
   * Declare Tag for using Log
   */
  private static final String TAG = LoggerManager.class.getSimpleName();
  //Define Logger instance
  private static LoggerManager loggerManager;
  //Define Context instance
  private Context context;

  /**
   * Get instance of LoggerManager; create a new one if it's null
   * @param   context  Application Context instance
   * @return  instance of LoggerManager
   */
  public static LoggerManager getInstance(Context context) {
    if (loggerManager == null) { loggerManager = new LoggerManager(context); }
    return loggerManager;
  }
  /**
   * Constructor
   * @param context Context instance
   */
  private LoggerManager(Context context) { this.context = context; }

  private String getCurrenDateTime() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date currentTime = Calendar.getInstance().getTime();
    String date = currentTime.toString();
    return date;
  }

  /**
   * Show expected log messages for regular usage, as well as the message levels lower in this list.
   * @param Tag
   * @param Message
   */
  public void Information(String Tag, String Message) {
    Log.i(Tag,Message);
    saveLog("logcat.txt",Tag,Message);
  }

  /**
   * Show possible issues that are not yet errors, as well as the message levels lower in this list.
   * @param Tag
   * @param Message
   */
  public void  Warning(String Tag, String Message) {
    Log.w(Tag, Message);
    saveLog("logcat.txt",Tag,Message);
  }

  /**
   * Show issues that have caused errors, as well as the message level lower in this list.
   * @param Tag
   * @param Message
   */
  public void  Error(String Tag, String Message) {
    Log.e(Tag,Message);
    saveLog("logcat.txt",Tag,Message);
  }

  /**
   * Show debug log messages that are useful during development only, as well as the message levels
   * lower in this list.
   * @param Tag
   * @param Message
   */
  public void Debug(String Tag, String Message) {
    Log.d(Tag,Message);
    saveLog("logcat.txt",Tag,Message);
  }

  /**
   * Show all log messages (the default).
   * @param Tag
   * @param Message
   */
  public void  Verbose(String Tag, String Message) {
    Log.v(Tag, Message);
    saveLog("logcat.txt",Tag,Message);
  }

  /**
   * Show all log messages (the default).
   * @param Tag
   * @param Message
   */
  public void Message(String Tag, String Message) {
    Log.v(Tag, Message);
    saveLog("logMessage.txt",Tag,Message);
  }

  /**
   * Handle save log text to file
   * @param Tag
   * @param Message
   */
  private void saveLog (String fileName, String Tag, String Message) {
    String message = getCurrenDateTime() + " " + Tag + " - " + Message + "\n";
    try {
      File file = new File(context.getExternalFilesDir(null).getAbsolutePath() + fileName);
      if(!file.exists()) {
        file.createNewFile();
      } else {
        FileOutputStream fos = new FileOutputStream(file,true);
        fos.write(message.getBytes());
        fos.close();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      Log.v(TAG, e.getMessage());
    } catch (Exception e ) {
      e.printStackTrace();
      Log.v(TAG, e.getMessage());
    }
  }

}
