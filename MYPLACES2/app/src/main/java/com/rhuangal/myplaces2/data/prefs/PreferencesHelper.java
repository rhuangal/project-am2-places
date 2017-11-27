package com.rhuangal.myplaces2.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rober on 27-Nov-17.
 */

public class PreferencesHelper {

    private static final String MYPLACES_PREFERENCES = "MyPlacesSPreferences";
    private static final String PREFERENCES_USERNAME = MYPLACES_PREFERENCES + ".username";
    private static final String PREFERENCES_PASSWORD = MYPLACES_PREFERENCES + ".password";
    private static final String PREFERENCES_ID = MYPLACES_PREFERENCES + ".objid";

    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCES_USERNAME);
        editor.remove(PREFERENCES_PASSWORD);
        editor.remove(PREFERENCES_ID);
        editor.apply();
    }

    public static void saveSession(Context context, String email, String pass, String id) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCES_USERNAME, email);
        editor.putString(PREFERENCES_PASSWORD, pass);
        editor.putString(PREFERENCES_ID, id);
        editor.apply();
    }

    public static String getUserNameSession(Context context)
    {
        SharedPreferences sharedPreferences= getSharedPreferences(context);
        String username= sharedPreferences.getString(PREFERENCES_USERNAME,null);
        return username;
    }
    public static String getPasswordSession(Context context)
    {
        SharedPreferences sharedPreferences= getSharedPreferences(context);
        String pass= sharedPreferences.getString(PREFERENCES_PASSWORD,null);
        return pass;
    }
    public static String getIdSession(Context context)
    {
        SharedPreferences sharedPreferences= getSharedPreferences(context);
        String id= sharedPreferences.getString(PREFERENCES_ID,null);
        return id;
    }

    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(PREFERENCES_USERNAME) &&
                preferences.contains(PREFERENCES_PASSWORD) &&
                preferences.contains(PREFERENCES_ID);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(MYPLACES_PREFERENCES, Context.MODE_PRIVATE);
    }


}
