package client.data.datasource;

public interface ClientCallback {
    void onError(String error);
    void onMessageReceived(String message);
}
