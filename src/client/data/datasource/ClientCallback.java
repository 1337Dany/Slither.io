package client.data.datasource;

public interface ClientCallback {
    void onError(ClientException exception);
    void onMessageReceived(String message);
}
