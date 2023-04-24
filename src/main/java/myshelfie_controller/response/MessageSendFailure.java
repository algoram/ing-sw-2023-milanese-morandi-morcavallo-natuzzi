package myshelfie_controller.response;

public class MessageSendFailure extends Response {
        private String message;
        public MessageSendFailure(String player, String game, String message) {
            super(player, game);

            this.message = message;
        }

        public String getMessage() {
            return message;
        }
}
