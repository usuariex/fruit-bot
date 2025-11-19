package com.devs.frutybot.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.devs.frutybot.data.user.UserDto;
import com.google.gson.Gson;

/**
 * UserSession: guarda/lee datos del usuario en SharedPreferences.
 * Provee métodos compatibles con LoginFragment / ProfileViewModel / ProfileFragment.
 */
public class UserSession {
    private static final String PREFS_NAME = "frutybot_session_prefs";
    private static final String KEY_USER_JSON = "key_user_json";
    private static final String KEY_USER = "key_user";
    private static final String KEY_PROFILE_PHOTO = "key_profile_photo";
    private static final String KEY_LOGGED = "key_logged";

    private final SharedPreferences prefs;
    private final Gson gson = new Gson();

    public UserSession(Context context) {
        this.prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Guarda username + marca logged (compatibilidad con saveLogin)
    public void saveLogin(String username) {
        if (username == null) return;
        prefs.edit()
                .putString(KEY_USER, username)
                .putBoolean(KEY_LOGGED, true)
                .apply();
    }

    // Guarda URL/URI de foto de perfil
    public void saveProfilePhoto(String uri) {
        if (uri == null) return;
        prefs.edit()
                .putString(KEY_PROFILE_PHOTO, uri)
                .apply();
    }

    // Guarda UserDto completo (JSON). Útil si quieres persistir más campos.
    public void saveUserDto(UserDto user) {
        if (user == null) return;
        String json = gson.toJson(user);
        prefs.edit()
                .putString(KEY_USER_JSON, json)
                .putString(KEY_USER, user.getUsername())
                .putString(KEY_PROFILE_PHOTO, user.getAvatarUrl())
                .putBoolean(KEY_LOGGED, true)
                .apply();
    }

    // Recupera UserDto completo (o null)
    public UserDto getUserDto() {
        String json = prefs.getString(KEY_USER_JSON, null);
        if (json == null) return null;
        try {
            return gson.fromJson(json, UserDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    // --- Getters simples usados por ViewModel / Fragments ---
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED, false) || getUserDto() != null;
    }

    public String getUsername() {
        // preferir UserDto si existe
        UserDto u = getUserDto();
        if (u != null && u.getUsername() != null) return u.getUsername();
        return prefs.getString(KEY_USER, null);
    }

    public String getProfilePhoto() {
        UserDto u = getUserDto();
        if (u != null && u.getAvatarUrl() != null) return u.getAvatarUrl();
        return prefs.getString(KEY_PROFILE_PHOTO, null);
    }

    // Actualizar username
    public void updateUsername(String newName) {
        if (newName == null) return;
        // actualizar JSON si existe
        UserDto u = getUserDto();
        if (u != null) {
            u.setUsername(newName);
            u.setFullName(newName); // opcional
            saveUserDto(u);
        } else {
            prefs.edit().putString(KEY_USER, newName).apply();
        }
    }

    // Borrar sesión (compatibilidad con clear() y logout())
    public void clear() {
        prefs.edit()
                .remove(KEY_USER_JSON)
                .remove(KEY_USER)
                .remove(KEY_PROFILE_PHOTO)
                .putBoolean(KEY_LOGGED, false)
                .apply();
    }

    public void logout() {
        clear();
    }
}
