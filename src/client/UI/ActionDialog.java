package client.UI;

import client.DOMAIN.SettingsSetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionDialog extends JPanel {
    private ChatPanel chatPanel;
    private static final Dimension actionDialogSize = new Dimension(180, 75);
    private String receiver;
    private final JLabel messageTo = new JLabel();
    private final JLabel addToGroupMessage = new JLabel();
    private final JLabel notMessageTo = new JLabel();

    public ActionDialog() {
        draw();
    }

    private void draw() {
        setBackground(Color.BLACK);
        setSize(actionDialogSize);
        SettingsSetter.ignoreSettingParametersToObjects(this);
        setVisible(false);

        messageTo.setBackground(new Color(0, 255, 255, 100));
        messageTo.setOpaque(false);
        messageTo.setForeground(Color.WHITE);
        messageTo.setLocation(0, 0);
        messageTo.setSize(this.getWidth(), this.getHeight() / 2);
        add(messageTo);

        addToGroupMessage.setBackground(new Color(0, 255, 255, 100));
        addToGroupMessage.setOpaque(false);
        addToGroupMessage.setForeground(Color.WHITE);
        addToGroupMessage.setLocation(0, messageTo.getHeight());
        addToGroupMessage.setSize(this.getWidth(), this.getHeight() / 2);
        add(addToGroupMessage);

        notMessageTo.setBackground(new Color(0, 255, 255, 100));
        notMessageTo.setOpaque(false);
        notMessageTo.setForeground(Color.WHITE);
        notMessageTo.setLocation(0, addToGroupMessage.getHeight());
        notMessageTo.setSize(this.getWidth(), this.getHeight() / 2);
        add(notMessageTo);
        messageTo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                messageTo.setOpaque(true);
                messageTo.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                messageTo.setOpaque(false);
                messageTo.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                chatPanel.getUserInput().setText("To " + receiver + ": ");
                setVisible(false);
                repaint();
            }
        });
        addToGroupMessage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addToGroupMessage.setOpaque(true);
                addToGroupMessage.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addToGroupMessage.setOpaque(false);
                addToGroupMessage.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String message = chatPanel.getUserInput().getText();
                if (message.startsWith("To all:") || !message.contains(":") || (message.length() < 5 && message.contains(":"))) {
                    chatPanel.getUserInput().setText("To " + receiver + ": ");
                } else {
                    chatPanel.getUserInput().setText(message.substring(0, message.indexOf(':')) + "," + receiver +
                            message.substring(message.indexOf(':')));
                }
                setVisible(false);
                repaint();
            }
        });
        notMessageTo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                notMessageTo.setOpaque(true);
                notMessageTo.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                notMessageTo.setOpaque(false);
                notMessageTo.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                chatPanel.getUserInput().setText("To not " + receiver + ": ");
                setVisible(false);
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    setVisible(false);
                }
            }
        });
    }

    public void setReceiver(String name) {
        receiver = name;
        messageTo.setText("Chat to " + name);
        addToGroupMessage.setText("Add to group message " + name);
        notMessageTo.setText("Chat not to " + name);
    }
    public void setChatPanel(ChatPanel chatPanel){
        this.chatPanel = chatPanel;
    }
}
