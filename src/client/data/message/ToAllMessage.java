package client.data.message;

public class ToAllMessage extends Message {

    private static String PREFIX = "To all: ";

    public ToAllMessage(String message) {
        super(PREFIX, message);
    }

    @Override
    public String buildMessage() {
        return PREFIX + getMessage();
    }
}
