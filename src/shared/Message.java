package shared;

public class Message implements Packet {
    private static final long serialVersionUID = 1L;
    private MessagePrefixes prefix;
    private String receiver;
    private String sender;
    private String message;

    public Message(MessagePrefixes prefix, String receiver, String message) {
        this.prefix = prefix;
        this.receiver = receiver;
        this.message = message;
    }

    public MessagePrefixes getPrefix() {
        return prefix;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
