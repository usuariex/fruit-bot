package com.devs.frutybot.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PROFILE_PHOTO = "profile_photo";

    private final SharedPreferences prefs;

    public UserSession(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public void saveLogin(String username) {
        prefs.edit()
                .putBoolean(KEY_LOGGED_IN, true)
                .putString(KEY_USERNAME, username)
                .apply();
    }

    public void logout() {
        prefs.edit().clear().apply();
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "Sesion");
    }

    public void updateUsername(String newName) {
        prefs.edit().putString(KEY_USERNAME, newName).apply();
    }

    public void saveProfilePhoto(String uri) {
        prefs.edit().putString(KEY_PROFILE_PHOTO, uri).apply();
    }

    public String getProfilePhoto() {
        return prefs.getString(KEY_PROFILE_PHOTO, null);
    }
}
