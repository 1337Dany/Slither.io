package client.data.message;

public abstract class Message {
    private String receiver;
    private String message;

    public Message(String receiver, String message) {
        this.receiver = receiver;
        this.message = message;
    }

    public abstract String buildMessage();

    public String getReceiver() {
        return receiver;
//    } catch (StringIndexOutOfBoundsException exception) {
//        return;
//    }
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
