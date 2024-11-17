package client.UI;

import client.DOMAIN.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        makeScrollBarTransparent(scrollPane);

        add(scrollPane, BorderLayout.CENTER);

        userInput = new JTextArea();
        add(userInput, BorderLayout.SOUTH);

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
        JLabel label = new JLabel(message);
        label.setForeground(color);
        label.setOpaque(false);
        label.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, 14));

        messagePanel.add(label);
        messagePanel.revalidate();
        messagePanel.repaint();

        // Automatically scrolling to the newest messages
        SwingUtilities.invokeLater(() ->
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum())
        );
    }
    private void makeScrollBarTransparent(JScrollPane scrollPane) {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();

        verticalBar.setOpaque(false);
        horizontalBar.setOpaque(false);
    }

    @Override
    public void run() {
        //  Thread for only graphical interface


    }
}
