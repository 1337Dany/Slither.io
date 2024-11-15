package client.UI;

import client.DOMAIN.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame {
    private static final Dimension frameSize = new Dimension(600,600);

    private final GameManager gameManager;
    private final JPanel menuPanel = new JPanel();
    private final JButton playButton = new JButton("Play");

    public GameWindow(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    public void openMainJFrame() {
        SwingUtilities.invokeLater(() -> {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(frameSize);
            this.setLayout(null);

            drawMenu();
        });
    }

    private void drawMenu() {
        menuPanel.setBackground(Color.DARK_GRAY);
        menuPanel.setLayout(null);

        menuPanel.setSize(frameSize);
        menuPanel.setLocation(0,0);

        JLabel logo = new JLabel("Slither.io");
        logo.setHorizontalAlignment(SwingUtilities.CENTER);
        logo.setForeground(Color.CYAN);
        logo.setFont(new Font(logo.getFont().getFontName(), Font.PLAIN, 50));
        logo.setBounds(
                0,
                (int) (0.1 * menuPanel.getHeight()),
                menuPanel.getWidth(),
                (int) (0.1 * menuPanel.getHeight())
        );

        JLabel enterIP = new JLabel("Enter IP:");
        enterIP.setForeground(Color.CYAN);
        enterIP.setFont(new Font(enterIP.getFont().getFontName(), Font.PLAIN, 30));
        enterIP.setBounds(
                0,
                logo.getY() + logo.getHeight() + 50,
                menuPanel.getWidth(),
                (int) (0.1 * menuPanel.getHeight())
        );

        JTextArea ip = new JTextArea("\\\\.....");
        ip.setForeground(Color.WHITE);
        ip.setBackground(Color.DARK_GRAY);
        ip.setFont(new Font(ip.getFont().getFontName(), Font.PLAIN, 30));
        ip.setBounds(
                menuPanel.getWidth()/2-(menuPanel.getWidth() - 100)/2,
                enterIP.getY() + enterIP.getHeight() + 10,
                menuPanel.getWidth() - 100,
                (int) (0.1 * menuPanel.getHeight())
        );

        JLabel enterName = new JLabel("Enter your name:");
        enterName.setForeground(Color.CYAN);
        enterName.setFont(new Font(enterName.getFont().getFontName(), Font.PLAIN, 30));
        enterName.setBounds(
                0,
                ip.getY() + ip.getHeight() + 10,
                menuPanel.getWidth(),
                (int) (0.1 * menuPanel.getHeight())
        );

        JTextArea name = new JTextArea("\\\\.....");
        name.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        name.setForeground(Color.WHITE);
        name.setBackground(Color.DARK_GRAY);
        name.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 30));
        name.setBounds(
                menuPanel.getWidth()/2-(menuPanel.getWidth() - 100)/2,
                enterName.getY() + enterName.getHeight() + 10,
                menuPanel.getWidth() - 100,
                (int) (0.1 * menuPanel.getHeight())
        );

        playButton.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 30));
        playButton.setBounds(
                menuPanel.getWidth()/2 - 200/2,
                name.getY() + name.getHeight() + 10,
                200,
                50
        );


        ip.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(ip.getText().equals("\\\\.....")){
                    ip.setText("");
                }
                if(name.getText().equals("")){
                    name.setText("\\\\.....");
                }
            }
        });

        name.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(name.getText().equals("\\\\.....")){
                    name.setText("");
                }
                if(ip.getText().equals("")){
                    ip.setText("\\\\.....");
                }
            }
        });


        menuPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!playButton.getBounds().contains(e.getPoint())) {
                    if (ip.getText().equals("")) {
                        ip.setText("\\\\.....");
                    }
                    if (name.getText().equals("")) {
                        name.setText("\\\\.....");
                    }
                }
            }
        });

        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameManager.establishConnection(name.getText(), ip.getText());
            }
        });


        menuPanel.add(playButton);
        menuPanel.add(name);
        menuPanel.add(enterName);
        menuPanel.add(ip);
        menuPanel.add(enterIP);
        menuPanel.add(logo);
        this.add(menuPanel);
    }

    public void hideMenu(){
        this.remove(menuPanel);
    }
    public void showMenu(){
        this.add(menuPanel);
    }

}