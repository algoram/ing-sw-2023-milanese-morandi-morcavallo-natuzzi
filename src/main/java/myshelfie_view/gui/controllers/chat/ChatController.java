package myshelfie_view.gui.controllers.chat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import myshelfie_controller.EventDispatcher;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML private TextArea chatArea;
    @FXML private ChoiceBox<String> recipient;
    @FXML private TextField input;

    @FXML protected void sendMessage() {
        String message = input.getText().trim();
        String to = recipient.getValue();

        if (!message.equals("")) {
            if (to.equals("ALL")) {
                EventDispatcher.getInstance().chat(null, message);
            } else {
                EventDispatcher.getInstance().chat(to, message);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recipient.setValue("ALL");
        recipient.getItems().add("ALL");
    }

    public void setUsernames(String[] usernames) {
        recipient.getItems().clear();

        recipient.getItems().add("ALL");
        recipient.getItems().addAll(usernames);
    }

    public void addMessage(String from, String message) {
        // TODO: add timestamp
        chatArea.appendText(from + ": " + message + "\n");
    }
}
