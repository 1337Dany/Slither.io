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
    private final GameWindow gameWindow;
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextArea userInput;
    private final Dimension chatSize = new Dimension(300, 300);

    public ChatPanel(GameManager gameManager, GameWindow gameWindow) {
        this.gameManager = gameManager;
        this.gameWindow = gameWindow;
        this.setSize(chatSize);
        this.setBackground(new Color(0, 0, 0, 125));
        this.setLayout(new BorderLayout());

        draw();
    }

    private void draw() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 125));
        gameWindow.getActionDialog().setChatPanel(this);

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
        userInput.setWrapStyleWord(true);
        userInput.setLineWrap(true);
        userInput.setOpaque(false);
        userInput.setForeground(Color.LIGHT_GRAY);
        add(userInput, BorderLayout.SOUTH);


        userInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (userInput.getText().equals("Tap to write a message...")) {
                    userInput.setText("To all: ");
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
                        userInput.setText("To all: ");
                        gameManager.sendMessageToServer(checkMessage);
                    }
                } else if (event.getKeyCode() == KeyEvent.VK_TAB) {
                    event.consume();
                    SlitherPanel.callPlayerList();
                }
                repaint();
            }
        });
    }

    public void addMessage(String message, Color color) {

        System.out.println(message);

        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setBackground(new Color(0, 255, 255, 100));
        messageArea.setOpaque(false);
        messageArea.setForeground(color);
        messageArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messageArea.setFont(new Font(messageArea.getFont().getFontName(), Font.PLAIN, 14));

        messagePanel.add(messageArea);
        messagePanel.revalidate();
        messagePanel.repaint();

        messageArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                messageArea.setOpaque(true);
                messageArea.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                messageArea.setOpaque(false);
                messageArea.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    gameWindow.getActionDialog().setReceiver(message.substring(message.indexOf('(') + 1, message.indexOf(')')));
                } catch (StringIndexOutOfBoundsException exception) {
                    return;
                }
                Point mouseLocation = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), ChatPanel.this);

                gameWindow.getActionDialog().setLocation(
                        mouseLocation.x,
                        (int) (mouseLocation.y + chatSize.getHeight() / 2)
                );
                gameWindow.getActionDialog().setVisible(true);
            }
        });

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

    public JTextArea getUserInput() {
        return userInput;
    }
}
