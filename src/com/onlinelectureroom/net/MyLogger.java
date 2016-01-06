package com.onlinelectureroom.net;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class MyLogger  {
	 public static int LOG_LEVEL_ERROR = 1;
	 public static int LOG_LEVEL_DEBUG = 2;
	 public static int LOG_LEVEL_INFO = 3;
	 
	 private static int mLevel = LOG_LEVEL_INFO;
	 private static MyLogger LOGGER = null;
     private static FileWriter sLogWriter = null;
     public static String LOG_FILE_NAME = Environment.getExternalStorageDirectory()+ "/carigamilog.txt";

     public synchronized static MyLogger getLogger(Context c) {
         LOGGER = new MyLogger();
         return LOGGER;
     }

     private MyLogger() {
         try {
             sLogWriter = new FileWriter(LOG_FILE_NAME, true);
         } catch (IOException e) {
             // Doesn't matter
         }
     }

     static public synchronized void close() {
         if (sLogWriter != null) {
             try {
                 sLogWriter.close();
             } catch (IOException e) {
                 // Doesn't matter
             }
             sLogWriter = null;
         }
     }

     static public synchronized void log(int aLevel, Exception e) {
         if (sLogWriter != null && mLevel >= aLevel) {
             log(aLevel, "Exception", "Stack trace follows...");
             PrintWriter pw = new PrintWriter(sLogWriter);
             e.printStackTrace(pw);
             pw.flush();
         }
     }

     @SuppressWarnings("deprecation")
     static public synchronized void log(int aLevel, String prefix, String str) {
         if (LOGGER == null) {
             LOGGER = new MyLogger();
             log(aLevel, "Logger", "\r\n\r\n --- New Log ---");
         }
         if(mLevel < aLevel)
        	 return;
         Date d = new Date();
         String s = d.toString();
//         int hr = d.getHours();
//         int min = d.getMinutes();
//         int sec = d.getSeconds();
//
//         // I don't use DateFormat here because (in my experience), it's much slower
//         StringBuffer sb = new StringBuffer(256);
//         sb.append('[');
//         sb.append(hr);
//         sb.append(':');
//         if (min < 10)
//             sb.append('0');
//         sb.append(min);
//         sb.append(':');
//         if (sec < 10) {
//             sb.append('0');
//         }
//         sb.append(sec);
//         sb.append("] ");
//         if (prefix != null) {
//             sb.append(prefix);
//             sb.append("| ");
//         }
//         sb.append(str);
//         sb.append("\r\n");
//         String s = sb.toString();

    	 Log.i(prefix, str);
         if (sLogWriter != null) {
             try {
                 sLogWriter.write(s + " " + prefix + " " + str + "\r\n");
                 sLogWriter.flush();
             } catch (IOException e) {
                 // Something might have happened to the sdcard
                 if (Environment.MEDIA_MOUNTED.equals(Environment
                         .getExternalStorageState())) {
                     // If the card is mounted and we can create the writer, retry
                     LOGGER = new MyLogger();
                     if (sLogWriter != null) {
                         try {
                             log(aLevel, "MyLogger", "Exception writing log; recreating...");
                             log(aLevel, prefix, str);
                         } catch (Exception e1) {
                             // Nothing to do at this point
                         }
                     }
                 }
             }
         }
     }

}
