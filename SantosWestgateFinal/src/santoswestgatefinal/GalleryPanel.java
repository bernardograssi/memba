package santoswestgatefinal;

import javax.swing.JPanel;

/**
 * This class represents a JPanel inside the MemoryGallery that contains a
 * memory associated to it and a boolean that is either selected (true) or not
 * (false).
 *
 * @author Allen Westgate & Bernardo Santos
 */
public class GalleryPanel extends JPanel {

    private Memory memoryAssociated; // memory associated to the JPanel.
    private boolean isSelected = false; // states if the JPanel is selected or not.

    /**
     * The constructor.
     *
     * @param memory the memory associated to the JPanel.
     */
    public GalleryPanel(Memory memory) {
        memoryAssociated = memory;
    }

    /**
     * Returns whether or not the JPanel has been selected.
     *
     * @return true if selected, false otherwise.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * This method sets the selected value of the object.
     *
     * @param b true or false.
     */
    public void setSelected(boolean b) {
        this.isSelected = b;
    }

    /**
     * This method returns the Memory associated to the JPanel.
     *
     * @return the Memory associated to it.
     */
    public Memory getMemoryAssociated() {
        return memoryAssociated;
    }
}
