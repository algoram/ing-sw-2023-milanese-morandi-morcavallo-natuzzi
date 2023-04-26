package myshelfie_controller.response;

public class MessageSendFailure extends Response {
        private String message;
        public MessageSendFailure(String player, String message) {
            super(player);

            this.message = message;
        }

        public String getMessage() {
            return message;
        }
}
