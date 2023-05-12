package myshelfie_controller.response;

import java.util.UUID;

public class PlayerConnectFailure extends Response {

    private String message;
    private UUID targetUUID;
    public PlayerConnectFailure(String player, String message, UUID uuid) {
        super(player);

        this.message = message;
        this.targetUUID = uuid;
    }

    public String getMessage() {
        return message;
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }
}
