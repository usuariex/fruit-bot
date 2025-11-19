package com.devs.frutybot.presentation.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.devs.frutybot.data.local.UserSession;

public class ProfileViewModel extends AndroidViewModel {

    private final UserSession userSession;

    public final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    public final MutableLiveData<String> username = new MutableLiveData<>();
    public final MutableLiveData<String> profilePhoto = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        userSession = new UserSession(application);

        // Inicializa los LiveData según sesión actual
        isLoggedIn.setValue(userSession.isLoggedIn());
        username.setValue(userSession.getUsername() != null ? userSession.getUsername() : "Invitado");
        profilePhoto.setValue(userSession.getProfilePhoto());
    }

    // Para actualizar nombre de usuario
    public void updateUsername(String newName) {
        userSession.updateUsername(newName);
        username.setValue(newName);
    }

    // Para actualizar foto de perfil
    public void updatePhoto(String uri) {
        userSession.saveProfilePhoto(uri);
        profilePhoto.setValue(uri);
    }

    // Login (recibe username; UserSession ya debe haber guardado datos)
    public void login(String username) {
        // Si quieres, podrías recibir un UserDto y guardarlo completo en sesión
        userSession.saveLogin(username);
        this.username.setValue(username);
        isLoggedIn.setValue(true);
        profilePhoto.setValue(userSession.getProfilePhoto());
    }

    // Logout
    public void logout() {
        userSession.logout();
        username.setValue("Invitado");
        profilePhoto.setValue(null);
        isLoggedIn.setValue(false);
    }
}
