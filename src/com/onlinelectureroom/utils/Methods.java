package com.onlinelectureroom.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * Created by Rahul on 30/12/15.
 */
public class Methods {


    public static final String TAG = "OnlineLectureRoom";

    /**
     * To Check if Google Play Services is available in the device. <br>
     * <br>
     *
     * @return true if Google Play Services is available in the device.
     * */
    /*
	 * public static boolean isGmsAvailable() { int status =
	 * GooglePlayServicesUtil
	 * .isGooglePlayServicesAvailable(App.getInstance().getApplicationContext
	 * ()); if(status == ConnectionResult.SUCCESS) { return true; } return
	 * false; }
	 */


    public static void turnGPSOn(Context c) {
        @SuppressWarnings("deprecation")
        String provider = Settings.Secure.getString(c.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) {

            Toast.makeText(c, "Switching on GPS", Toast.LENGTH_SHORT).show();

            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            c.sendBroadcast(poke);
        }
    }

    public static void turnGPSOff(Context c) {
        @SuppressWarnings("deprecation")
        String provider = Settings.Secure.getString(c.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) {

            Toast.makeText(c, "Switching off GPS", Toast.LENGTH_SHORT).show();

            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            c.sendBroadcast(poke);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Bitmap byteArrayToBitmap(byte[] a) {

        Bitmap bmp;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        bmp = BitmapFactory.decodeByteArray(a, 0, a.length, options);

        return bmp;
    }

    public static Bitmap getBitmapForImageView(byte[] a, ImageView photo) {

        Bitmap bmp = Methods.byteArrayToBitmap(a);

        int w = photo.getWidth();
        int h = photo.getHeight();

        int bw = bmp.getWidth();
        int bh = bmp.getHeight();

        int nbw = 0;
        int nbh = 0;

        if (w > h) {

            float r = (float) w / (float) bw;

            nbw = w;
            nbh = (int) ((float) bh * r);

        } else if (w < h) {

            float r = (float) h / (float) bh;

            nbh = h;
            nbw = (int) ((float) bw * r);

        } else {

            nbw = nbh = w;
        }

        return Bitmap.createScaledBitmap(bmp, nbw, nbh, false);
    }

    public static Bitmap getBitmapForImageView(Bitmap bmp, ImageView photo) {

        int w = photo.getWidth();
        int h = photo.getHeight();

        int bw = bmp.getWidth();
        int bh = bmp.getHeight();

        int nbw = 0;
        int nbh = 0;

        if (w > h) {

            float r = (float) w / (float) bw;

            nbw = w;
            nbh = (int) ((float) bh * r);

        } else if (w < h) {

            float r = (float) h / (float) bh;

            nbh = h;
            nbw = (int) ((float) bw * r);

        } else {

            nbw = nbh = w;
        }

        return Bitmap.createScaledBitmap(bmp, nbw, nbh, false);
    }

    public static int getImageViewHeightSizeForBitmap(Bitmap bmp, int iw) {

        int bw = bmp.getWidth();
        int bh = bmp.getHeight();

        int ih = 0;

        float r = (float) iw / (float) bw;

        ih = (int) ((float) bh * r);

        return ih;
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bitmap = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * Encodes Bitmap to BASE64 string.
     *
     * @param Uri Uri of image file to be encoded.
     * @return Base64 encoded string (from given image Uri).
     */
    public static String imageUriToString(Uri uri) {
        try {

            InputStream inputStream = new FileInputStream(uri.getPath());// You

            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = output.toByteArray();
            String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
            inputStream.close();
            output.close();
            return encodedString;

        } catch (Exception e) {

            e.getMessage();
            return null;
        }
    }

    /**
     * Encodes Bitmap to BASE64 string.
     *
     * @param Uri Uri of image file to be encoded.
     * @return Base64 encoded string (from given image Uri).
     */
    public static byte[] imageUriToByteArr(Context context, Uri uri) {
        try {
            InputStream iStream = context.getContentResolver().openInputStream(
                    uri);
            byte[] inputData = getBytes(iStream);

            String encodedString = Base64.encodeToString(inputData,
                    Base64.DEFAULT);

            iStream.close();

            return inputData;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static boolean isStringEmpty(String str) {

        if (str == null || str.equalsIgnoreCase(""))
            return true;
        else
            return false;
    }

    /**
     * To get it the connected to internet.
     *
     * @return true if connected to internet
     */
    public static boolean isDeviceConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static boolean validateString(EditText txt, String msgOnError) {

        String str = null;

        str = txt.getText().toString().trim();

        if (str == null || str.length() <= 0) {

            txt.setError(msgOnError);
        } else {
            if (str.length() < 6) {
                txt.setError("Password is too short");
            } else {
                if (str.contains(" ")) {

                    txt.setError("No whitespaces allowed");
                } else {

                    return true;
                }
            }
        }

        return false;
    }

    public static boolean validateEmptyString(EditText txt, String msgOnError) {

        String str = null;

        str = txt.getText().toString().trim();

        if (str == null || str.length() <= 0) {

            txt.setError(msgOnError);
        } else {

            return true;
        }

        return false;
    }

    public static boolean isValidEmail(EditText txt, String msgOnError) {

        if (validateString(txt, msgOnError)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(
                txt.getText().toString().trim()).matches()) {

            return true;
        } else {

            txt.setError(msgOnError);
            txt.requestFocus();
            return false;
        }
    }
    @SuppressWarnings("deprecation")
    public static int getWidth(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        return display.getWidth();
    }

    @SuppressWarnings("deprecation")
    public static int getHeight(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        return display.getHeight();
    }

    public static void dpToPx(float ht, float wt, Context context) {

        float ht_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                ht, context.getResources().getDisplayMetrics());
        float wt_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                wt, context.getResources().getDisplayMetrics());
        wt = wt_px;
        ht = ht_px;
    }

    public static float heightpxToDp(float ht, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        // float ht_dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
        // ht, context.getResources().getDisplayMetrics());
        return (ht / density);
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getDeviceWidth(Context context) {

        int width = 400;
        int height = 600; // just intialising it to default value in case if
        // there is any problem getting screen width
        WindowManager wm = (WindowManager) context // activity is the context if
                // you are using this method
                // in adapter. Otherwise in
                // Activity you can simply
                // ignore this
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }
        return width;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getDeviceHeight(Context context) {

        int width = 400;
        int height = 600; // just intialising it to default value in case if
        // there is any problem getting screen width
        WindowManager wm = (WindowManager) context // activity is the context if
                // you are using this method
                // in adapter. Otherwise in
                // Activity you can simply
                // ignore this
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }
        return height;

    }

    public static boolean isApplicationRunningBackground(final Context context) {

        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(am
                .getRunningAppProcesses().size());
        for (ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
            if (runningTaskInfo.topActivity.getPackageName().equals(
                    context.getPackageName())) {
                Log.d(TAG,
                        "packageName:"
                                + runningTaskInfo.topActivity.getPackageName());
                Log.d(TAG,
                        "className"
                                + runningTaskInfo.topActivity.getClassName());
                return true;
            }
        }
        return false;
    }

    public static String bundle2string(Bundle bundle) {

        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }

    /**
     * Returns the IMEI number of Device
     */
    public static String getIMEI(Context context) {

        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();

        Log.d(TAG, "Inside getIMEI - MAC Address is = " + macAddress);
        return macAddress;
    }

    public static Uri getUriFromImageView(Context context, Bitmap mBitmap) {

        Uri uri = null;
        // cannot be cast to android.graphics.drawable.BitmapDrawable

		/*
		 * Drawable mDrawable = imageView.getDrawable(); Bitmap mBitmap =
		 * ((BitmapDrawable)mDrawable).getBitmap();
		 */

        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                mBitmap, "Image Description", null);

        if (path != null) {

            uri = Uri.parse(path);

        }

        return uri;
    }

    public static boolean deleteFileUri(Context context, Uri uri) {

        ContentResolver resolver = context.getContentResolver();

        // change type to image, otherwise nothing will be deleted
        ContentValues contentValues = new ContentValues();
        int media_type = 1;
        contentValues.put("media_type", media_type);
        resolver.update(uri, contentValues, null, null);

        return resolver.delete(uri, null, null) > 0;
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {

        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null,
                null, null, null);

        if (cursor == null) { // Source is local file path

            result = contentURI.getPath();
        } else {

            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String getMimeType(String imagePath) {

        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(imagePath);
        if (extension != null) {

            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    extension);
        }
        return type;
    }
}
