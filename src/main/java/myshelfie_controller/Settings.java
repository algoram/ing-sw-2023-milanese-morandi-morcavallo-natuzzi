package myshelfie_controller;

public class Settings {
    //TODO must be effectively final
    private String username;

    private String wiewType;

    private String ConnectionType;

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

    public void setWiewType(String wiewType) {
        this.wiewType = wiewType;
    }

    public void setConnectionType(String ConnectionType) {
        this.ConnectionType = ConnectionType;
    }

    public String getUsername() {
        return username;
    }

    public String getWiewType() {
        return wiewType;
    }

    public String getConnectionType() {
        return ConnectionType;
    }

}
