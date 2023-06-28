package myshelfie_view.gui.controllers.chat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import myshelfie_controller.EventDispatcher;
import myshelfie_view.gui.GuiView;
import myshelfie_view.gui.controllers.ChatMessage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML private TextArea chatArea;
    @FXML private ChoiceBox<String> recipient;
    @FXML private TextField input;

    @FXML protected void sendMessage() {
        String message = input.getText().trim();
        String to = recipient.getSelectionModel().getSelectedItem();

        if (!message.equals("")) {
            to = to.equals("ALL") ? null : to;

            EventDispatcher.getInstance().chat(null, message);
            GuiView.getInstance().getMainController().getGameController().sendMessage(to, message);
        }

        input.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recipient.setValue("ALL");
        recipient.getItems().add("ALL");
    }

    public void setUsernames(ArrayList<String> usernames) {
        recipient.getItems().clear();

        recipient.getItems().add("ALL");
        recipient.getItems().addAll(usernames);

        recipient.setValue("ALL");
    }

    public void setMessages(ArrayList<ChatMessage> messages) {
        chatArea.clear();

        for (ChatMessage msg : messages) {
            chatArea.appendText(msg.toString() + "\n");
        }
    }
}
