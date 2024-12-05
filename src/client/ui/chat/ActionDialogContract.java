package client.ui.chat;

public interface ActionDialogContract {
    void showActionDialog(int x, int y);
    void hideActionDialog();
    void setSender(String sender);
}
