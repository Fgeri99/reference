package gamefour;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Falusi Gergő Gábor
 * @since 2020. november 08.
 *
 * Alkalmazás ablakainak az alapját definiáljuk
 *
 * Beállítjuk a címet és az ikont
 * Felülírjuk a kilépés gomb működédét
 */
public class BaseWindow extends JFrame {

    BaseWindow() {
        setTitle("4-es játék");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitConfirmation();
            }
        });

        URL url = getClass().getResource("icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }

    protected void exitConfirmation() {
        Object[] buttons = {"Igen", "Nem"};
        int choice = JOptionPane.showOptionDialog(this, "Valóban ki akar lépni?",
                "Megerősítés",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                buttons, buttons[1]);
        if (choice == 0) {
            System.exit(0);
        }
    }

}
