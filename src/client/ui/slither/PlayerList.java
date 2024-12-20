package client.ui.slither;

import client.domain.SettingsSetter;
import client.ui.chat.ActionDialog;
import client.ui.chat.ActionDialogContract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayerList extends JPanel {
    private static final Dimension playerListSize = new Dimension(300, 200);
    private final ActionDialogContract actionDialogContract;
    private boolean isVisible = false;
    private final ArrayList<JLabel> players = new ArrayList<>();

    public PlayerList(ActionDialogContract actionDialogContract) {
        this.actionDialogContract = actionDialogContract;
        SettingsSetter.ignoreSettingParametersToObjects(this);
        configurate();
    }

    private void configurate() {
        setBackground(new Color(0, 0, 0, 100));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setSize(playerListSize);
        setVisible(isVisible);
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
                actionDialogContract.setSender(player.getText());
                actionDialogContract.showActionDialog(e.getLocationOnScreen().x,
                        e.getLocationOnScreen().y - ActionDialog.HEIGH_OF_CLICKABLE_MESSAGE
                );
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
