package client.ui.chat;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import client.data.message.Message;
import client.data.message.ToAllMessage;
import client.domain.SettingsSetter;
import client.ui.slither.PlayerListContract;

public class ChatPanel extends JPanel {

    private static final int HEIGH_OF_CLICKABLE_MESSAGE = 30;
    private final IChatCallback iChatCallback;
    private ActionDialogContract actionDialogContract;
    private PlayerListContract playerListContract;
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextArea userInput;

    public ChatPanel(IChatCallback iChatCallback) {
        this.iChatCallback = iChatCallback;
        configure();
        configureMessagePanel();
        configureScrollPanel();
        configureUserInput();

        SettingsSetter.setParametersToObjects(this);
    }

    private void configure() {
        this.setSize(new Dimension(300, 300));
        this.setBackground(new Color(0, 0, 0, 125));
        this.setLayout(new BorderLayout());
    }

    private void configureMessagePanel() {
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setOpaque(false);
    }

    private void configureScrollPanel() {
        scrollPane = new JScrollPane(messagePanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
        // Automatically scrolling to the newest messages
        SwingUtilities.invokeLater(() -> {
                    scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                }
        );
    }

    private void configureUserInput() {
        userInput = new JTextArea("Tap to write a message...");
        userInput.setWrapStyleWord(true);
        userInput.setLineWrap(true);
        userInput.setOpaque(false);
        userInput.setForeground(Color.LIGHT_GRAY);
        userInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (userInput.getText().equals("Tap to write a message...")) {
                    userInput.setForeground(Color.WHITE);
                }
            }
        });
        userInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    event.consume();
                    String checkMessage = userInput.getText();
                    if (!checkMessage.isEmpty()) {

                        //read left part to define message type
                        /*
                                String recevier = leftPanel.getText();
                                Message message = MessageUtils().buildMessageFromReceiver(receiver, checkMessage)
                         */

                        ToAllMessage message = new ToAllMessage(checkMessage);
                        iChatCallback.sendMessage(message);
                        userInput.setText("");
                    }
                } else if (event.getKeyCode() == KeyEvent.VK_TAB) {
                    event.consume();
                    playerListContract.callPlayerList();
                }else if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                    actionDialogContract.hideActionDialog();
                }
            }
        });
        add(userInput, BorderLayout.SOUTH);
    }

    public void addMessage(Message message) {
        System.out.println(message);
        JTextArea messageArea = new JTextArea(message.getMessage());
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setBackground(new Color(0, 255, 255, 100));
        messageArea.setOpaque(false);
        if (message instanceof ToAllMessage) {
            messageArea.setForeground(Color.WHITE);
        }
        messageArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messageArea.setFont(new Font(messageArea.getFont().getFontName(), Font.PLAIN, 14));

        messagePanel.add(messageArea);
        messageArea.revalidate();
        messageArea.repaint();

        messageArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.consume();
                messageArea.setOpaque(true);
                messagePanel.repaint();
                revalidate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.consume();
                messageArea.setOpaque(false);
                messagePanel.repaint();
                revalidate();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                    actionDialogContract.setSender(message.getReceiver());
                actionDialogContract.showActionDialog(e.getX(),
                        e.getLocationOnScreen().y - HEIGH_OF_CLICKABLE_MESSAGE
                );
            }
        });
    }

    public void setActionDialogContract(ActionDialogContract actionDialogContract) {
        this.actionDialogContract = actionDialogContract;
    }
    public void setPlayerListContract(PlayerListContract playerListContract){
        this.playerListContract = playerListContract;
    }
}
