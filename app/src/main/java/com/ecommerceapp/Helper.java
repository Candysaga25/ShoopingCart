package com.ecommerceapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerceapp.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Helper {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static DisplayMetrics displayMetrics;


    public enum RowType {
        // Here we have multiple items types
        LISTITEM,
        SYNC_USERDATA_LISTITEM, ADDED_WORKOUT_LISTITEM, WORKOUT_HEADER_LISTITEM, CATEGORY_HEADER_LISTITEM
    }

    public static String getSafeStringObjectFromJson(JSONObject jsonObject, String key) {
        String result;
        try {
            result = jsonObject.getString(key);
            if (result == "null") {
                result = "";
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }

        return result;
    }

    public static int getSafeIntObjectFromJson(JSONObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            String value = null;
            try {
                value = jsonObject.getString(key);
            } catch (JSONException e) {
            }

            if (value == null || value.equals("null")) {
                return -1;
            } else {
                return Integer.parseInt(value);
            }
        } else {
            return -1;
        }
    }

    public static double getSafeDoubleObjectFromJson(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.has(key)) {
            String value = jsonObject.getString(key);
            if (value == null || value.equals("null")) {
                return 0;
            } else {
                return Double.parseDouble(value);
            }
        } else {
            return 0;
        }
    }

    static public JSONObject getSafeObjectFromJson(JSONObject jsonObject,
                                                   String key) {
        JSONObject result;
        try {
            if (jsonObject.isNull(key)) {
                return null;
            }
            result = jsonObject.getJSONObject(key);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }

        return result;
    }

    static public JSONArray getSafeArrayFromJson(JSONObject jsonObject,
                                                 String key) {
        JSONArray result;
        try {
            if (jsonObject.isNull(key)) {
                return null;
            }
            result = jsonObject.getJSONArray(key);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }

        return result;
    }

    static public boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void noConnection(Context context) {
        Toast.makeText(context, "No Internet Connection Available",
                Toast.LENGTH_SHORT).show();
    }


    public static String getDeviceId(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * This method returns new MUID as combination of deviceId + timeStam.
     * This method internally calls getDeviceId(context).
     *
     * @param context
     * @return new MUId String
     */
    public static String getNewMUID(Context context) {
        return getDeviceId(context) + System.currentTimeMillis();
    }

    /**
     * This method receives Edittext as String againts which keybord is open
     */
    public static void hideKeybord(Context context, EditText edt) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }

    /**
     * This method toasts the supplied String
     *
     * @param context
     * @param msgToToast
     */
    public static void toastMessage(Context context, String msgToToast) {
        Toast.makeText(context, "Message : " + msgToToast, Toast.LENGTH_LONG).show();
    }


    /**
     * This method returns ProgressDialog with msg as Loading... to calling activity.
     *
     * @param context
     * @param msg
     * @return progressDialog instance for given context
     */
    public static ProgressDialog getProgressDialog(Context context, String msg) {
        ProgressDialog pDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        if (msg.equals("")) {
            msg = "Loading...";
        }
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        return pDialog;
    }


    /**
     * This method returns device width
     *
     * @param activity
     * @return
     */

    public static DisplayMetrics getDeviceDisplayMetrics(Activity activity) {
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }

        return displayMetrics;
    }


    public static void showDialog(ProgressDialog dialog, String msg) {
        dialog.setMessage(msg);
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public static void hideDialog(ProgressDialog dialog) {
        if (dialog.isShowing()) {
            dialog.hide();
        }
    }

    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service already", "running");
                return true;
            }
        }
        Log.i("Service not", "running");
        return false;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * This function will take an URL as input and return the file name.
     * <p>Examples :</p>
     * <ul>
     * <li>http://example.com/a/b/c/test.txt -> test.txt</li>
     * <li>http://example.com/ -> an empty string </li>
     * <li>http://example.com/test.txt?param=value -> test.txt</li>
     * <li>http://example.com/test.txt#anchor -> test.txt</li>
     * </ul>
     *
     * @param url The input URL
     * @return The URL file name
     */
    public static String getFileNameFromUrl(URL url) {

        String urlString = url.getFile();

        return urlString.substring(urlString.lastIndexOf('/') + 1).split("\\?")[0].split("#")[0];
    }


    public static void logDebug(String TAG, String message) {
        Log.e(TAG, "**** Payment Gateway: " + message);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /*This methode shows snackbar alert at bottom of the screen*/
    public static void alertSnackbar(Context context, View coordinatelayout, String msg, boolean iserror) {
        Snackbar snackbar = Snackbar.make(coordinatelayout, msg, Snackbar.LENGTH_SHORT);
        ViewGroup view = (ViewGroup) snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        if (iserror) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        snackbar.show();
    }

    /*This methode shows snackbar alert at bottom of the screen*/
    public static void alertSnackbar(final Context context, View coordinatelayout, String msg, boolean iserror, String action) {
        if (action.equalsIgnoreCase("setting")) {
            Snackbar snackbar = Snackbar
                    .make(coordinatelayout, msg, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Setting", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            context.startActivity(intent);
                        }
                    });
            snackbar.setActionTextColor(Color.GRAY);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar.make(coordinatelayout, msg, Snackbar.LENGTH_SHORT);
            ViewGroup view = (ViewGroup) snackbar.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            if (iserror) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            snackbar.show();
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    /*public static boolean checkPlayServices(String TAG, Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog((Activity) context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                ((Activity) context).finish();
            }
            return false;
        }
        return true;
    }
*/
    public static boolean isValidMobile(String phone) {
        // mobile number should be 10 digit
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matchr = pattern.matcher(phone.trim());
        if (matchr.matches()) {
            return true;
        }
        return false;
//        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    public static boolean isValidPicture(String link) {
        return URLUtil.isValidUrl(link);
    }
    public static void hideKeyBoard(Context mContext) {
        View view = ((Activity) mContext).getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String convertDate(String date) {
        //best practice is to convert data by simpledateformat
       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String newFormat = df.format(testDate);
        return newFormat;*/
        StringTokenizer st = new StringTokenizer(date);
        String newFormat = st.nextToken(" ");
        return newFormat;
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1234;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static void saveJsonTocache(Context context, List<Product> productList){
// Instantiate a JSON object from the request response
        try {
            String jsonObject = new Gson().toJson(productList);
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File(context.getCacheDir(),"")+"cacheFile.srl"));
        out.writeObject( jsonObject );
        out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> retrieveJsonTocache(Context context){
        // Load in an object
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(new File(context.getCacheDir(),"")+"cacheFile.srl")));
            String jsonObject = (String) in.readObject();
            Type type = new TypeToken<List<Product>>(){}.getType();
            List<Product> productList = new Gson().fromJson(jsonObject, type);
             in.close();
            return productList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return new ArrayList<>();
    }
}



