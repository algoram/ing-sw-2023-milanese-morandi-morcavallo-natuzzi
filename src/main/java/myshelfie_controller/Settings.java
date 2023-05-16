package myshelfie_controller;

import java.security.PublicKey;

//this class is used to store the global settings of the user
//Each class saving settings must use this class to save and load settings
public class Settings {

    public static final boolean DEBUG = false;
    //must be effectively final
    private String username;

    private ViewType viewType;

    private ConnectionType connectionType;

    private static Settings instance = null;

    private Settings() {
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public String getUsername() {
        return username;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

}
