package client.UI;

import client.DOMAIN.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame {
    private static final Dimension frameSize = new Dimension(900, 600);

    private final GameManager gameManager;
    private final JPanel menuPanel = new JPanel();
    private final JLabel serverWrongIp = new JLabel();
    private final JLabel serverWrongName = new JLabel();
    private final JButton playButton = new JButton("Play");
    private ActionDialog actionDialog;

    public GameWindow(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void openMainJFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameSize);
        this.setLayout(null);

        drawMenu();

        actionDialog = new ActionDialog();
        add(actionDialog);

    }

    private void drawMenu() {
        menuPanel.setBackground(Color.DARK_GRAY);
        menuPanel.setLayout(null);

        menuPanel.setSize(frameSize);
        menuPanel.setLocation(0, 0);

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
        enterIP.setLocation(
                0,
                logo.getY() + logo.getHeight() + 50
        );
        enterIP.setSize(
                (int) (enterIP.getPreferredSize().getWidth() + 20),
                (int) enterIP.getPreferredSize().getHeight()
        );


        serverWrongIp.setForeground(Color.RED);
        serverWrongIp.setFont(new Font(enterIP.getFont().getFontName(), Font.PLAIN, 30));
        serverWrongIp.setLocation(enterIP.getWidth(), enterIP.getY());
        serverWrongIp.setSize(
                menuPanel.getWidth() - enterIP.getWidth(),
                enterIP.getHeight()
        );

        JTextArea ip = new JTextArea("\\\\.....");
        ip.setForeground(Color.WHITE);
        ip.setBackground(Color.DARK_GRAY);
        ip.setFont(new Font(ip.getFont().getFontName(), Font.PLAIN, 30));
        ip.setBounds(
                menuPanel.getWidth() / 2 - (menuPanel.getWidth() - 100) / 2,
                enterIP.getY() + enterIP.getHeight() + 10,
                menuPanel.getWidth() - 100,
                (int) (0.1 * menuPanel.getHeight())
        );

        JLabel enterName = new JLabel("Enter your name:");
        enterName.setForeground(Color.CYAN);
        enterName.setFont(new Font(enterName.getFont().getFontName(), Font.PLAIN, 30));
        enterName.setLocation(
                0,
                ip.getY() + ip.getHeight() + 10
        );
        enterName.setSize(
                (int) (enterName.getPreferredSize().getWidth() + 40),
                (int) enterName.getPreferredSize().getHeight()
        );

        serverWrongName.setForeground(Color.RED);
        serverWrongName.setFont(new Font(enterIP.getFont().getFontName(), Font.PLAIN, 30));
        serverWrongName.setLocation(enterName.getWidth(), enterName.getY());
        serverWrongName.setSize(
                menuPanel.getWidth() - enterName.getWidth(),
                enterName.getHeight()
        );

        JTextArea name = new JTextArea("\\\\.....");
        name.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        name.setForeground(Color.WHITE);
        name.setBackground(Color.DARK_GRAY);
        name.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 30));

        JScrollPane scrollPane = new JScrollPane(name);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        SwingUtilities.invokeLater(() -> name.setCaretPosition(name.getDocument().getLength()));

        scrollPane.setBounds(
                menuPanel.getWidth() / 2 - (menuPanel.getWidth() - 100) / 2,
                enterName.getY() + enterName.getHeight() + 10,
                menuPanel.getWidth() - 100,
                (int) (0.1 * menuPanel.getHeight())
        );

        playButton.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 30));
        playButton.setBounds(
                menuPanel.getWidth() / 2 - 200 / 2,
                scrollPane.getY() + scrollPane.getHeight() + 10,
                200,
                50
        );


        ip.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ip.getText().equals("\\\\.....")) {
                    ip.setText("");
                }
                if (name.getText().equals("")) {
                    name.setText("\\\\.....");
                }
            }
        });

        name.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (name.getText().equals("\\\\.....")) {
                    name.setText("");
                }
                if (ip.getText().equals("")) {
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
        menuPanel.add(scrollPane);
        menuPanel.add(serverWrongName);
        menuPanel.add(enterName);
        menuPanel.add(ip);
        menuPanel.add(serverWrongIp);
        menuPanel.add(enterIP);
        menuPanel.add(logo);
        showMenu();
    }

    public void wrongName() {
        serverWrongName.setText("Player with this name already exist");
        repaint();
        revalidate();
    }

    public void wrongServer() {
        serverWrongIp.setText("Can not connect to this server");
        this.repaint();
        this.revalidate();
    }

    public void hideMenu() {
        this.remove(menuPanel);
    }

    public void showMenu() {
        this.add(menuPanel);
    }
    public ActionDialog getActionDialog(){
        return actionDialog;
    }
}