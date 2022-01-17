package gamefour;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Falusi Gergő Gábor
 * @since 2020. november 08.
 *
 * Az alkalmazás főablaka, BaseWindowból származtatjuk
 * 
 * Menüsorból és két panelből áll az ablak
 * A menüsorhoz egy fájl->kilépés, és egy súgó->névjegy menüpont tartozik
 * A topPanel egy JLabelből áll, melynek szöveget állítunk be
 * A bottomPanel 3 gombból áll, melyekkel a táblaméretet lehet kiválasztani
 */
public class MainWindow extends BaseWindow {

    private JPanel topPanel = new JPanel();
    private JLabel mainLabel = new JLabel();

    private JPanel bottomPanel = new JPanel();
    private JButton smallButton = new JButton();
    private JButton mediumButton = new JButton();
    private JButton largeButton = new JButton();

    private JMenuBar menuBar = new JMenuBar();

    private JMenu fileMenu = new JMenu("Fájl");
    private JMenuItem exitMenuItem = new JMenuItem("Kilép");

    private JMenu helpMenu = new JMenu("Súgó");
    private JMenuItem aboutMenuItem = new JMenuItem("Névjegy");

    MainWindow() {
        setSize(500, 300);
        setLocationRelativeTo(null);

        mainLabel.setText("Válassza ki a táblaméretet!");
        mainLabel.setFont(new Font("Serif", Font.BOLD, 20));

        smallButton.setText("3 x 3");
        smallButton.addActionListener(buttonActionListener(3));

        mediumButton.setText("5 x 5");
        mediumButton.addActionListener(buttonActionListener(5));

        largeButton.setText("7 x 7");
        largeButton.addActionListener(buttonActionListener(7));

        setLayout(new GridLayout(2, 1, 20, 20));

        topPanel.add(mainLabel);
        topPanel.setBorder(new EmptyBorder(30, 10, 10, 10));

        bottomPanel.add(smallButton);
        bottomPanel.add(mediumButton);
        bottomPanel.add(largeButton);

        add(topPanel);
        add(bottomPanel);

        setJMenuBar(menuBar);

        menuBar.add(fileMenu);
        fileMenu.add(exitMenuItem);

        menuBar.add(helpMenu);
        helpMenu.add(aboutMenuItem);

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                exitConfirmation();
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                showAboutMessage();
            }
        });
    }

    private ActionListener buttonActionListener(final int size) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                GameWindow gameWindow = new GameWindow(size);
                gameWindow.setVisible(true);
                MainWindow.this.dispose();
            }
        };
    }

    private void showAboutMessage() {
        JOptionPane.showMessageDialog(this, "Tulajdonos: Falusi Gergő Gábor", "Névjegy",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
