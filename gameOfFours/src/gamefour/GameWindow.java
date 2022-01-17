package gamefour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Falusi Gergő Gábor
 * @since 2020. november 08.
 *
 * Az alkalmazás játékablaka, BaseWindowból származtatjuk
 * Az ablak mérete függ attól, mekkora játékmezőt választottunk
 *
 * Az ablak egy felső és egy alsó JLabelt tartalmaz és egy játékpanelt
 * A topLabel tartalmazza az Új Játék gombot és az aktuális játékost
 * Az Új Játék gomb visszairányít a főablakra
 * A bottomLabel tartalmazza a játékosok pontszámait
 * A játékpanel n*n-es felosztásban tartalmazza a gombokat
 */
public class GameWindow extends BaseWindow {

    private int size;
    private JLabel topLabel;
    private JLabel bottomLabel;
    private Model model;
    private int[] newValues = {0,0,0,0,0};
    protected JPanel mainPanel;
    protected ArrayList<JButton> buttonList = new ArrayList<>();  

    GameWindow(int size) {
        this.size = size;
        model = new Model(size);

        switch (size) {
            case 3:
                setSize(490, 560);
                break;
            case 5:
                setSize(580, 660);
                break;
            case 7:
                setSize(690, 750);
                break;
        }

        setLocationRelativeTo(null);
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();

        topLabel = new JLabel();
        updateFirstLabelText();

        bottomLabel = new JLabel();
        updateSecondLabelText();

        JButton newGameButton = new JButton();
        newGameButton.setText("Új játék");
        newGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                newGame();
            }

        });

        top.add(newGameButton);
        top.add(topLabel);
        bottom.add(bottomLabel);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
            }
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }
    
    

    private void addButton(JPanel panel, final int i, final int j) {
        
        JButton button = new JButton();
        button.setText("0");
        buttonList.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Color color;

                newValues = model.step(i, j);

                if (!model.actualPlayer.name.matches("Red")) {
                    color = Color.RED;
                } else {
                    color = Color.BLUE;
                }

                if (!getButton(i, j).getText().matches("4") && newValues[0] != 0) {
                    getButton(i, j).setText(String.valueOf(newValues[0]));
                    setButtonColor(i, j, 0, color);
                }
                if (i - 1 >= 0 && !getButton(i - 1, j).getText().matches("4")
                        && newValues[1] != 0) {
                    getButton(i - 1, j).setText(String.valueOf(newValues[1]));
                    setButtonColor(i-1, j, 1, color);
                }
                if (i + 1 < size && !getButton(i + 1, j).getText().matches("4")
                        && newValues[2] != 0) {
                    getButton(i + 1, j).setText(String.valueOf(newValues[2]));
                    setButtonColor(i+1, j, 2, color);
                }
                if (j - 1 >= 0 && !getButton(i, j - 1).getText().matches("4")
                        && newValues[3] != 0) {
                    getButton(i, j - 1).setText(String.valueOf(newValues[3]));
                    setButtonColor(i, j-1, 3, color);
                }
                if (j + 1 < size && !getButton(i, j + 1).getText().matches("4")
                        && newValues[4] != 0) {
                    getButton(i, j + 1).setText(String.valueOf(newValues[4]));
                    setButtonColor(i, j+1, 4, color);
                }
                
                updateFirstLabelText();
                updateSecondLabelText();

                String winner = model.findWinner();
                if (!winner.matches("Nobody")) {
                    showGameOverMessage(winner);
                }
            }
        });
        panel.add(button);
    }
    
    private void setButtonColor(int row, int column, int i, Color color) {
        if (newValues[i] == 4) {
            getButton(row, column).setForeground(color);
        }
    }

    private void showGameOverMessage(String winner) {
        JOptionPane.showMessageDialog(this,
                "Játék vége. Nyert: " + winner);
        GameWindow gameWindow = new GameWindow(size);
        gameWindow.setVisible(true);
        this.dispose();
    }

    private JButton getButton(int row, int column) {
        int index = row * size + column;
        return buttonList.get(index);
    }

    private void updateFirstLabelText() {
        topLabel.setText("  Aktuális játékos: "
                + model.actualPlayer.name);
    }

    private void updateSecondLabelText() {
        bottomLabel.setText(model.redPlayer.name + ": "
                + model.redPlayer.score + "     " + model.bluePlayer.name
                + ": " + model.bluePlayer.score);
    }

    private void newGame() {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);

        this.dispose();
    }

}
