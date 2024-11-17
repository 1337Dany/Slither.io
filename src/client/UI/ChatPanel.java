package client.UI;

import client.DOMAIN.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatPanel extends JPanel implements Runnable {
    private final GameManager gameManager;
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextArea userInput;
    private static final Dimension chatSize = new Dimension(300, 250);

    public ChatPanel(GameManager gameManager) {
        this.gameManager = gameManager;
        this.setSize(chatSize);
        this.setBackground(new Color(0, 0, 0, 125));
        this.setLayout(new BorderLayout());
        draw();
    }

    private void draw() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 125));

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setOpaque(false);

        scrollPane = new JScrollPane(messagePanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        makeScrollBarTransparent(scrollPane);

        add(scrollPane, BorderLayout.CENTER);

        userInput = new JTextArea("Tap to write a message...");
        userInput.setOpaque(false);
        userInput.setForeground(Color.LIGHT_GRAY);
        add(userInput, BorderLayout.SOUTH);

        userInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(userInput.getText().equals("Tap to write a message...")){
                    userInput.setText("");
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
                        userInput.setText("");
                        addMessage("Me: " + checkMessage, Color.WHITE);
                        gameManager.sendMessageToServer("Chat: " + checkMessage);
                    }
                }
            }
        });
    }

    public void addMessage(String message, Color color) {

        System.out.println(message);

        JTextArea messageLabel = new JTextArea(message);
        messageLabel.setEditable(false);
        messageLabel.setWrapStyleWord(true);
        messageLabel.setLineWrap(true);
        messageLabel.setOpaque(false);
        messageLabel.setForeground(color);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messageLabel.setFont(new Font(messageLabel.getFont().getFontName(), Font.PLAIN, 14));

        messagePanel.add(messageLabel);
        messagePanel.revalidate();
        messagePanel.repaint();

        // Automatically scrolling to the newest messages
        SwingUtilities.invokeLater(() -> {
                    scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                }
        );
    }
    private void makeScrollBarTransparent(JScrollPane scrollPane) {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();

        verticalBar.setOpaque(false);
    }

    @Override
    public void run() {
        //  Thread for only graphical interface


    }
}
