package client.ui.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class MenuView extends JPanel {

    private final IMenuCallback callback;

    private final JLabel serverWrongIp = new JLabel();
    private final JLabel serverWrongName = new JLabel();
    private final JButton playButton = new JButton("Play");
    private final JLabel logo = new JLabel("Slither.io");
    private final JLabel enterIP = new JLabel("Enter IP:");
    private final JTextArea ip = new JTextArea("localhost");
    private final JLabel enterName = new JLabel("Enter your name:");
    private final JTextArea name = new JTextArea("\\\\.....");
    private final JScrollPane scrollPane = new JScrollPane(name);

    public MenuView(Dimension frameSize, IMenuCallback callback) {
        this.callback = callback;
        configure(frameSize);
        configureLogo();
        configureEnteringIp();
        configureIpEntering();
        configureIp();
        configureEnterName();
        configureWrongName();
        configureName();
        configureScrollableName();
        configurePlay();
        initiate();
    }

    private void configure(Dimension frameSize) {
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        setSize(frameSize);
        setLocation(0, 0);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!playButton.getBounds().contains(e.getPoint())) {
                    if (ip.getText().isEmpty()) {
                        ip.setText("\\\\.....");
                    }
                    if (name.getText().isEmpty()) {
                        name.setText("\\\\.....");
                    }
                }
            }
        });
    }

    private void configureLogo() {
        logo.setHorizontalAlignment(SwingUtilities.CENTER);
        logo.setForeground(Color.CYAN);
        logo.setFont(new Font(logo.getFont().getFontName(), Font.PLAIN, 50));
        logo.setBounds(
                0,
                (int) (0.1 * getHeight()),
                getWidth(),
                (int) (0.1 * getHeight())
        );
    }

    private void configureEnteringIp() {
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
    }

    private void configureIpEntering(){
        serverWrongIp.setForeground(Color.RED);
        serverWrongIp.setFont(new Font(enterIP.getFont().getFontName(), Font.PLAIN, 30));
        serverWrongIp.setLocation(enterIP.getWidth(), enterIP.getY());
        serverWrongIp.setSize(
                getWidth() - enterIP.getWidth(),
                enterIP.getHeight()
        );
    }

    private void configureIp(){
        ip.setForeground(Color.WHITE);
        ip.setBackground(Color.DARK_GRAY);
        ip.setFont(new Font(ip.getFont().getFontName(), Font.PLAIN, 30));
        ip.setBounds(
                getWidth() / 2 - (getWidth() - 100) / 2,
                enterIP.getY() + enterIP.getHeight() + 10,
                getWidth() - 100,
                (int) (0.1 * getHeight())
        );
        ip.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ip.getText().equals("\\\\.....")) {
                    ip.setText("");
                }
                if (name.getText().isEmpty()) {
                    name.setText("\\\\.....");
                }
            }
        });

    }

    private void configureEnterName(){
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
    }

    public void configureWrongName(){
        serverWrongName.setForeground(Color.RED);
        serverWrongName.setFont(new Font(enterIP.getFont().getFontName(), Font.PLAIN, 30));
        serverWrongName.setLocation(enterName.getWidth(), enterName.getY());
        serverWrongName.setSize(
                getWidth() - enterName.getWidth(),
                enterName.getHeight()
        );
    }

    public void configureName(){
        name.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        name.setForeground(Color.WHITE);
        name.setBackground(Color.DARK_GRAY);
        name.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 30));
        name.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (name.getText().equals("\\\\.....")) {
                    name.setText("");
                }
                if (ip.getText().isEmpty()) {
                    ip.setText("\\\\.....");
                }
            }
        });
    }

    public void configureScrollableName(){
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SwingUtilities.invokeLater(() -> name.setCaretPosition(name.getDocument().getLength()));
        scrollPane.setBounds(
                getWidth() / 2 - (getWidth() - 100) / 2,
                enterName.getY() + enterName.getHeight() + 10,
                getWidth() - 100,
                (int) (0.1 * getHeight())
        );
    }

    public void configurePlay() {
        playButton.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 30));
        playButton.setBounds(
                getWidth() / 2 - 200 / 2,
                scrollPane.getY() + scrollPane.getHeight() + 10,
                200,
                50
        );
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                callback.onPlayClicked(name.getText(), ip.getText());
            }
        });
    }
    
    private void initiate(){
        add(playButton);
        add(scrollPane);
        add(serverWrongName);
        add(enterName);
        add(ip);
        add(serverWrongIp);
        add(enterIP);
        add(logo);
    }
}
