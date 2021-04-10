package santoswestgatefinal;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * This method implements MouseListener to the tags' panel.
 *
 * @author Allen Westgate & Bernardo Santos
 */
public class TagMouseListener implements MouseListener {

    private List<String> listOfTags = new ArrayList<>(); // the list of tags.
    private JPanel parent; // the parent JPanel.
    private Component child; // the child Component.
    private String label; // the label, which is the tag.

    // the colors .
    private Color b1Reg = new Color(255, 255, 255);
    private Color b1Mouseover = new Color(225, 225, 225);
    private Color b1Mouseclick = new Color(195, 195, 195);
    private JComboBox cb; // the JComboBox used in the GUI.

    /**
     * The constructor.
     *
     * @param a the JPanel associated to the MouseListener.
     * @param b the Component associated to the MouseListener.
     * @param lbl the tag JLabel.
     * @param tagsList the list of tags.
     * @param bk1 the background color.
     * @param mOver the mouseover color.
     * @param mClick the mouseclick color.
     */
    public TagMouseListener(JPanel a, Component b, String lbl, List<String> tagsList, Color bk1, Color mOver, Color mClick) {
        parent = a;
        child = b;
        label = lbl;
        listOfTags = tagsList;
        b1Reg = bk1;
        b1Mouseover = mOver;
        b1Mouseclick = mClick;
    }

    /**
     * The constructor.
     *
     * @param a the JPanel associated to the MouseListener.
     * @param b the Component associated to the MouseListener.
     * @param lbl the tag JLabel.
     * @param tagsList the list of tags.
     * @param box the JComboBox used in the GUI.
     */
    public TagMouseListener(JPanel a, Component b, String lbl, List<String> tagsList, JComboBox box, Color bk1, Color mOver, Color mClick) {
        parent = a;
        child = b;
        label = lbl;
        listOfTags = tagsList;
        cb = box;
        b1Reg = bk1;
        b1Mouseover = mOver;
        b1Mouseclick = mClick;
    }

    /**
     * Change background color when panel is clicked.
     *
     * @param e the MouseEvent.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        child.setBackground(b1Mouseclick);
    }

    /**
     * This method removes a tag from the tag list and from the GUI if it gets
     * pressed, and also sets the selected index of the ComboBox to 0.
     *
     * @param e the MouseEvent.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        parent.remove(child);
        listOfTags.remove(label);
        if (listOfTags.contains(label)) {
            listOfTags.remove(label);    
        }
        if (cb != null) {
            if (cb.getSelectedIndex() == 0) {
                    cb.setSelectedIndex(1);
            }
            cb.setSelectedIndex(0);
        }
        parent.repaint();
        parent.revalidate();
    }

    /**
     * Method left blank in purpose.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * This method changes the background color of the JPanel when mouse enters
     * it.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        child.setBackground(b1Mouseover);
    }

    /**
     * This method changes the background color of the JPanel when mouse exits
     * it.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        child.setBackground(b1Reg);
    }
}
