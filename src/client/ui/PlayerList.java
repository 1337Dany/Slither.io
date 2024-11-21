package client.ui;

import client.domain.SettingsSetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayerList extends JPanel {
    private final SlitherPanel slitherPanel;
    private final GameWindow gameWindow;
    private static final Dimension playerListSize = new Dimension(300, 200);
    private boolean isVisible = false;
    private final ArrayList<JLabel> players = new ArrayList<>();

    public PlayerList(SlitherPanel slitherPanel, GameWindow gameWindow) {
        this.slitherPanel = slitherPanel;
        this.gameWindow = gameWindow;
        SettingsSetter.ignoreSettingParametersToObjects(this);

        setLayout(new FlowLayout(FlowLayout.LEFT, 10,10));
        draw();
    }

    private void draw() {
        setBackground(new Color(0, 0, 0, 100));
        setSize(playerListSize);
        SettingsSetter.ignoreSettingParametersToObjects(this);
        setVisible(isVisible);
        setLocation(slitherPanel.getWidth() / 2 - playerListSize.width / 2,
                0);
    }

    public void addPlayer(String name) {

        JLabel player = new JLabel(name);
        player.setOpaque(false);
        player.setBackground(new Color(0, 100, 100, 120));
        player.setForeground(Color.WHITE);
        player.setFont(new Font(player.getFont().getFontName(), Font.PLAIN, 20));

        player.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                player.setOpaque(true);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                player.setOpaque(false);
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    gameWindow.getActionDialog().setReceiver(player.getText());
                } catch (StringIndexOutOfBoundsException exception) {
                    return;
                }
                Point mouseLocation = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), PlayerList.this);

                gameWindow.getActionDialog().setLocation(
                        mouseLocation.x + slitherPanel.getWidth() / 2 - playerListSize.width / 2,
                        mouseLocation.y
                );
                gameWindow.getActionDialog().setVisible(true);
                repaint();
            }
        });

        players.add(player);
        this.add(player);
        this.revalidate();
        this.repaint();
    }

    public void removePlayer(String name) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getText().equals(name)) {
                this.remove(players.get(i));
                players.remove(players.get(i));
            }
        }
        this.repaint();
        this.revalidate();
    }

    public void showPlayerList() {
        setVisible(true);
        isVisible = true;
    }

    public void hidePlayerList() {
        setVisible(false);
        isVisible = false;
    }

    public boolean getIsVisible() {
        return isVisible;
    }
}
