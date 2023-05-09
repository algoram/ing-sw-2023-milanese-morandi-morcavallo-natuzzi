package myshelfie_view.cli;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Messages {
    private final String message;
    private final String sender;
    private final boolean isPublic;
    private final Date date;
    private boolean isMine;

    //isMine is true if the message is sent by the player -> sender is the destination
    public Messages(String message, String sender, boolean isPublic, Date date, boolean isMine) {
        this.message = message;
        this.sender = sender;
        this.isPublic = isPublic;
        this.date = date;
        this.isMine = isMine;
    }

    private String getMessage() {
        return message;
    }

    private String getSender() {
        return sender;
    }

    private boolean isPublic() {
        return isPublic;
    }

    private Date getDate() {
        return date;
    }

    public String toShow() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Specify the desired format
        String formattedTime = sdf.format(date); // Format the date

        sb = sb.append("[").append(formattedTime).append("] ");
        if(!isMine) {
            sb = sb.append("from ").append(getSender()).append(" to ");
            if (isPublic()) {
                sb = sb.append("everyone: ");
            } else {
                sb = sb.append("you: ");
            }
        }
        else {
            sb = sb.append("to ");
            if (isPublic()) {
                sb = sb.append("everyone from you: ");
            } else {
                sb = sb.append(getSender()+" from you: ");
            }
        }

        sb = sb.append(getMessage());
        return sb.toString();
    }
}
