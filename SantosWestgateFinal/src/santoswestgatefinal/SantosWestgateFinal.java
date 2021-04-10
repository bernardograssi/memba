package santoswestgatefinal;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * This is the main class where the GUI is initialized and has its default
 * attributes set.
 *
 * @author Allen Westgate & Bernardo Santos
 */
public class SantosWestgateFinal {

    /**
     * This is the main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // initialize ViewController object, which is responsible for creating
        // the GUI.
        ViewController vc = new ViewController();
        vc.setMinimumSize(new Dimension(1000, 800));
        vc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vc.setVisible(true);
        vc.setLocationRelativeTo(null);
    }
}