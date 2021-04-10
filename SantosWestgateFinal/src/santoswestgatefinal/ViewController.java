package santoswestgatefinal;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * This is the class in which the GUI is built.
 *
 * @author Allen Westgate & Bernardo Santos
 */
public class ViewController extends JFrame {

    private SourceDataLine newLine;
    private JDateChooser chooser; // the date chooser.
    private TargetDataLine line; // the line to get audio from system.
    private byte[] currAudio = null, remembaAudio = null; // all the byte arrays.
    private RecordTimer timer; // the RecordTimer object
    private boolean running, canStop = false, remembaEditable = false, playing = false; // all the booleans.
    private ByteArrayOutputStream out;
    private JPanel membaLogoPanel, cards, mainOptionsCard, newMemoryPanel,
            exploreMemoriesPanel, shareMemoriesPanel, customizeAppearancePanel,
            newMemoryCard1, newMemoryCard2, newMemTitlePanel, newMemDatePanel,
            newMemLocPanel, cancelPanel, nextPanel, cancelNextPanel, newMem2Header,
            imgAudioTagsUploadPanel, addAudioPanel, addTagsPanel,
            storyTextPanel, prevCreatePanel, prevPanel, createPanel, recPanel,
            stopRecPanel, playAudioPanel, removeAudioPanel, tagsPanel, tagsListPanel,
            imgUploadPanel, expMemCard1, expMemCard1Header, expMemCornerPanel,
            membaCortexPanel, cornerPanel, expFilterPanel, expAddTagsPanel,
            imgFilterPanel, filterPanel, removeImgPanel, datePanel, filterTagsPanel,
            searchTagsListPanel, memoryGalleryPanel, prevDelselRemPanel, prevPanel2,
            deleteSelected, remembaPanel, expMemCard2, expMem2Header, imgAudioTagsUploadPanel2,
            addAudioPanel2, recPanel2, stopRecPanel2, playAudioPanel2, removeAudioPanel2,
            imgUploadPanel2, imgFilterPanel2, filterPanel2, removeImgPanel2, tagsPanel2,
            addTagsPanel2, tagsListPanel2, storyTextPanel2, prevEditSavePanel, prevPanel3,
            editPanel, editCards, delSavePanel, delMemPanel, savePanel, naviCards,
            naviCard2, prevPanel4, exportSelPanel, exportAllPanel, customizeApp,
            customAppPanel, appearanceSettings, appearanceLabelPanel, appearanceComboPanel,
            themePresetsPanel, backgroundColor1Panel, backgroundColor2Panel, textColorPanel,
            borderColorPanel, borderStylePanel, prevDefaultConfirmPanel, prevPanel5,
            restDefaultPanel, confirmPanel, themePresetComboPanel, backgroundColor1ComboPanel,
            backgroundColor2ComboPanel, textColorComboPanel, borderColorComboPanel,
            borderStyleComboPanel; // all the panels used throughout the program.
    private JLabel membaLogoLabel, newMem, explMem, shareMem, custAppear, memoryTitleLabel,
            memoryDateLabel, memoryLocLabel, cancelLabel, nextLabel, newMem2, memoryTitleLabel2,
            memoryDateLabel2, imgUploadLabel, recAudio, addTags, prev, create, stopRec, time,
            remAudio, expMemCornerImgLabel, membaCortexLabel, cornerLabel, dateLabel,
            expAddTagsLabel, removeImgLabel, filterLabel, prev2, delsel, rememba, expMem2,
            memoryTitleLabel3, memoryDateLabel3, recAudio2, time2, stopRec2, remAudio2, filterLabel2,
            imgUploadLabel2, removeImgLabel2, addTags2, prev3, edit, delMemLabel, saveLabel,
            prev4, exportSel, exportAll, customAppLabel, themePresetsLabel, backgroundColor1Label,
            backgroundColor2Label, textColorLabel, borderColorLabel, borderStyleLabel, prev5,
            restDefaultLabel, confirmLabel; // all the labels used throughout the program.
    private ImageIcon newMemoryBtnImg, cancelBtnImg, nextBtnImg, prevBtnImg, createBtnImg,
            exploreMemoriesBtnImg, shareMemoriesBtnImg, customizeAppearanceBtnImg,
            membaLogoImg, newMemCornerImg, memoryTitleImg, memoryDateLabelImg, memoryLocImg,
            imgUploadImg, recAudioImg, stopRecImg, playImg, remImg, addTagsImg, currImg = null, currImg2 = null,
            membaCortexImg, expAddTagsImg, removeImgGraphic, prevBtnImg2,
            delselBtnImg, remembaBtnImg, recAudioImg2, stopRecImg2, playImg2, remImg2,
            imgUploadImg2, removeImgGraphic2, addTagsImg2, prevBtnImg3, editBtnImg,
            delMemImg, saveImg, prev4Img, exportSelImg, exportAllImg, customAppImg, prev5Img,
            restDefaultImg, confirmImg, originalImg, greyImg, mirrorImg, invertImg, bwImg;// all the image icons used throughout the program.
    private JTextField memoryTitleTextfield, memoryLocTextfield; // all the text fields.
    private JTextArea storyText, storyText2; // the text areas to input memory text.
    private JScrollPane tagsListScroll, storyTextScroll, imgUploadScroll, searchTagsListScroll,
            memoryGalleryScroll, imgUploadScroll2, tagsListScroll2, storyTextScroll2; // all the scroll panes used in the program.
    private Color b1Reg, b1Mouseover, b1Mouseclick, b2Reg, b3Reg, textColor, midnightBlue,
            bluetiful, indigo, mistyBlue, electricLime, screaminGreen, fern, pineGreen,
            carnationPink, vividViolet, orchid, lavender, pastelYellow, yellowOrange,
            yellow, redOrange, membaDef1, membaDef2, membaMouseover, membaMouseclick,
            membaDef3, membaTextColor, borderColor; // all the colors used in the program.
    private JFileChooser fileChooser; // the file chooser.
    private List<String> tags, searchTags, editMemoryTags; // all the list of strings used.
    private String currImgPath = ""; // the path of the current image.
    private Model m = null; // the Model object used to connect to database.
    private List<Memory> memoriesFromDB, currentGalleryMemory = new ArrayList<>(); // the list of Memory objects.
    private String[] DATE_FILTERS = {"None", "Today", "Last 7 Days", "Last 30 Days", "Last 365 Days"}; // date filters.
    private String[] FILTER_OPTIONS = {"None", "Greyscale", "Mirror Image", "Invert Colors", "Black & White"}; // image filters.
    private String[] THEME_PRESETS = {"None", "Early Bird", "Nite Owl", "Slimed", "Bouquet"}; // theme options.
    private String[] COLOR_OPTIONS = {"Midnight Blue", "Bluetiful", "Indigo", "Misty Blue",
        "Electric Lime", "Green", "Screamin' Green", "Fern", "Pine Green", "Carnation Pink",
        "Vivid Violet", "Orchid", "Lavender", "Yellow", "Pastel Yellow", "Yellow Orange", "Orange",
        "Red Orange", "Red", "Black", "White"}; // color options to change appearance of the GUI.
    private String[] BORDER_OPTIONS = {"None", "Line Borders", "Beveled Borders", "Soft Beveled Borders",
        "Etched Borders"};  // color options to change appearance of the GUI.
    private JComboBox dateFilterCombo, imgFilterCombo, imgFilterCombo2, themePresetCombo,
            backgroundColor1Combo, backgroundColor2Combo, textColorCombo, borderColorCombo,
            borderStyleCombo; // all the combo boxes used in the program.
    private Image imgUploadScaledImg, imgUploadScaledImg2; // all the images used in the program.
    private String currDateChosen = "01/01/1990"; // the earliest date a user can choose in a memory.
    private List<GalleryPanel> selectedPanels = new ArrayList<>(); // the list of selected panels from the memory gallery.
    private Border buttonBorder, panelBorder, textfieldBorder; // all the Border objects.
    private Font fieldFont, titleFont; // the fonts used.

    public ViewController() {
        try {

            /* - - - - - - - - - - - - - - \
             *                             |
             * START of GUI layout         |
             *                             |
             * - - - - - - - - - - - - - - /
             */
            // set title and layout of the GUI.
            this.setTitle("CSC2620 - FINAL PROJECT - MEMBA");
            this.setLayout(new GridBagLayout());

            // get memories from the database and load list.
            m = new Model();
            memoriesFromDB = m.loadAndGetMemories();
            GridBagConstraints mainGBC = new GridBagConstraints();

            // the tags lists.
            tags = new ArrayList<>();
            searchTags = new ArrayList<>();
            editMemoryTags = new ArrayList<>();

            // Create color library
            midnightBlue = new Color(26, 72, 118);
            bluetiful = new Color(66, 100, 164);
            indigo = new Color(93, 118, 203);
            mistyBlue = new Color(36, 76, 131);
            electricLime = new Color(29, 249, 20);
            screaminGreen = new Color(118, 255, 122);
            fern = new Color(113, 188, 120);
            pineGreen = new Color(21, 128, 120);
            carnationPink = new Color(255, 170, 204);
            vividViolet = new Color(143, 80, 157);
            orchid = new Color(230, 168, 215);
            lavender = new Color(255, 180, 213);
            pastelYellow = new Color(255, 255, 156);
            yellowOrange = new Color(255, 182, 83);
            yellow = new Color(240, 240, 0);
            redOrange = new Color(255, 83, 73);
            membaDef1 = new Color(140, 200, 240);
            membaDef2 = new Color(100, 140, 180);
            membaDef3 = new Color(120, 170, 210);
            membaMouseover = new Color(165, 225, 255);
            membaTextColor = new Color(70, 100, 120);
            membaMouseclick = membaDef2;

            // Define standard component colors
            b1Reg = membaDef1; // Color for panel backgrounds
            b2Reg = membaDef2; // Color for borders and header
            b3Reg = membaDef3; // Color for secondary header
            textColor = membaTextColor; // Color for text
            borderColor = b2Reg; // Color for borders
            b1Mouseover = membaMouseover; // Color for mouseover
            b1Mouseclick = membaMouseclick; // Color for mouseclick

            // Define standard component borders
            buttonBorder = BorderFactory.createLineBorder(b2Reg);
            panelBorder = BorderFactory.createLineBorder(b2Reg);
            textfieldBorder = BorderFactory.createLineBorder(b2Reg);

            // Define standard component dimensions
            Dimension mainMenuBtns = new Dimension(300, 300);
            Dimension logoDim = new Dimension(321, 75);
            Dimension naviBtns = new Dimension(145, 67);
            Dimension cornerImgDim = new Dimension(80, 80);
            Dimension newMemTextTitleDim = new Dimension(375, 75);
            Dimension uploadImgDim = new Dimension(375, 275);
            Dimension uploadGraphicDim = new Dimension(200, 250);
            Dimension audioBtns = new Dimension(67, 47);
            Dimension addTagsBtn = new Dimension(101, 47);
            Dimension membaCortexDim = new Dimension(375, 50);
            Dimension cornerSpacer = new Dimension(30, 30);

            // Define standard fonts
            titleFont = new Font("Bahnschrift", Font.PLAIN, 20);
            fieldFont = new Font("Bahnschrift", Font.PLAIN, 14);

            // Add membaLogoPanel to main menu
            mainGBC.gridx = 0;
            mainGBC.gridy = 0;
            mainGBC.weighty = 1;
            mainGBC.weightx = 1;
            mainGBC.fill = GridBagConstraints.BOTH;
            membaLogoPanel = new JPanel(new FlowLayout());
            membaLogoPanel.setBackground(b2Reg);
            membaLogoPanel.setSize(logoDim);
            membaLogoPanel.setBorder(BorderFactory.createBevelBorder(0));
            membaLogoImg = new ImageIcon();
            membaLogoLabel = new JLabel(membaLogoImg);
            membaLogoLabel.setSize(logoDim);
            Image membaLogoScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Graphics\\memba-logo-reg.png", logoDim);
            membaLogoImg.setImage(membaLogoScaledImg);
            membaLogoPanel.add(membaLogoLabel);
            this.add(membaLogoPanel, mainGBC);

            // Add cards to main menu
            mainGBC.gridy = 1;
            mainGBC.weighty = 9;
            cards = new JPanel(new CardLayout());
            this.add(cards, mainGBC);

            // Add mainOptionsPanel to cards
            mainOptionsCard = new JPanel(new GridBagLayout());
            GridBagConstraints mainOptionsGBC = new GridBagConstraints();
            mainOptionsGBC.weightx = 1;
            mainOptionsGBC.weighty = 1;

            // Add newMemoryPanel to mainOptionsCard
            newMemoryPanel = new JPanel(new FlowLayout());
            newMemoryPanel.setBackground(b1Reg);
            newMemoryPanel.setSize(mainMenuBtns);
            newMemoryBtnImg = new ImageIcon();
            newMem = new JLabel(newMemoryBtnImg);
            newMem.setSize(mainMenuBtns);
            Image newMemScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\New Memory btn\\new-memory-reg.png", mainMenuBtns);
            newMemoryBtnImg.setImage(newMemScaledImg);
            newMemoryPanel.add(newMem);
            newMemoryPanel.setBorder(buttonBorder);
            mainOptionsGBC.gridx = 0;
            mainOptionsGBC.gridy = 0;
            mainOptionsGBC.fill = GridBagConstraints.BOTH;
            mainOptionsCard.add(newMemoryPanel, mainOptionsGBC);

            // Add exploreMemoriesPanel to mainOptionsCard
            exploreMemoriesPanel = new JPanel(new FlowLayout());
            exploreMemoriesPanel.setBackground(b1Reg);
            exploreMemoriesPanel.setSize(mainMenuBtns);
            exploreMemoriesBtnImg = new ImageIcon();
            explMem = new JLabel(exploreMemoriesBtnImg);
            explMem.setSize(mainMenuBtns);
            Image explMemScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Explore Memories btn\\explore-memories-reg.png", mainMenuBtns);
            exploreMemoriesBtnImg.setImage(explMemScaledImg);
            exploreMemoriesPanel.add(explMem);
            exploreMemoriesPanel.setBorder(buttonBorder);
            mainOptionsGBC.gridx = 1;
            mainOptionsGBC.gridy = 0;
            mainOptionsCard.add(exploreMemoriesPanel, mainOptionsGBC);

            // Add shareMemoriesPanel to mainOptionsCard
            shareMemoriesPanel = new JPanel(new FlowLayout());
            shareMemoriesPanel.setBackground(b1Reg);
            shareMemoriesPanel.setSize(mainMenuBtns);
            shareMemoriesBtnImg = new ImageIcon();
            shareMem = new JLabel(shareMemoriesBtnImg);
            shareMem.setSize(mainMenuBtns);
            Image shareMemScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Share Memories btn\\share-memories-reg.png", mainMenuBtns);
            shareMemoriesBtnImg.setImage(shareMemScaledImg);
            shareMemoriesPanel.add(shareMem);
            shareMemoriesPanel.setBorder(buttonBorder);
            mainOptionsGBC.gridx = 0;
            mainOptionsGBC.gridy = 1;
            mainOptionsCard.add(shareMemoriesPanel, mainOptionsGBC);

            // Add customizeAppearancePanel to mainOptionsCard
            customizeAppearancePanel = new JPanel(new FlowLayout());
            customizeAppearancePanel.setBackground(b1Reg);
            customizeAppearancePanel.setSize(mainMenuBtns);
            customizeAppearanceBtnImg = new ImageIcon();
            custAppear = new JLabel(customizeAppearanceBtnImg);
            custAppear.setSize(mainMenuBtns);
            Image custAppearScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Customize Appearance btn\\customize-appearance-reg.png", mainMenuBtns);
            customizeAppearanceBtnImg.setImage(custAppearScaledImg);
            customizeAppearancePanel.add(custAppear);
            customizeAppearancePanel.setBorder(buttonBorder); // remove after adding image
            mainOptionsGBC.gridx = 1;
            mainOptionsGBC.gridy = 1;
            mainOptionsCard.add(customizeAppearancePanel, mainOptionsGBC);

            // Create newMemoryCard1
            newMemoryCard1 = new JPanel(new GridBagLayout());
            newMemoryCard1.setBackground(b1Reg);
            GridBagConstraints newMem1GBC = new GridBagConstraints();

            // Add newMemTitlePanel to newMemoryCard1
            newMemTitlePanel = new JPanel(new GridLayout(2, 1));
            newMemTitlePanel.setBackground(b1Reg);
            newMemTitlePanel.setSize(1000, 150);
            newMemTitlePanel.setBorder(panelBorder);
            memoryTitleImg = new ImageIcon();
            memoryTitleLabel = new JLabel(memoryTitleImg);
            Image memoryTitleScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Graphics\\title-of-memory-reg.png", newMemTextTitleDim);
            memoryTitleImg.setImage(memoryTitleScaledImg);
            newMemTitlePanel.add(memoryTitleLabel);
            memoryTitleTextfield = new JTextField("That time I...");
            memoryTitleTextfield.setBorder(textfieldBorder);
            memoryTitleTextfield.setSize(new Dimension(200, 30));
            memoryTitleTextfield.setFont(fieldFont);
            memoryTitleTextfield.setForeground(textColor);
            newMemTitlePanel.add(memoryTitleTextfield);
            newMem1GBC.gridx = 0;
            newMem1GBC.gridy = 0;
            newMem1GBC.weightx = 1;
            newMem1GBC.weighty = 6;
            newMem1GBC.fill = GridBagConstraints.VERTICAL;
            newMemoryCard1.add(newMemTitlePanel, newMem1GBC);

            // Add newMemDatePanel to newMemoryCard1
            newMemDatePanel = new JPanel(new GridLayout(2, 1));
            newMemDatePanel.setBackground(b1Reg);
            newMemDatePanel.setBorder(panelBorder);
            memoryDateLabelImg = new ImageIcon();
            memoryDateLabel = new JLabel(memoryDateLabelImg);
            Image memoryDateLabelScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Graphics\\date-of-memory-reg.png", newMemTextTitleDim);
            memoryDateLabelImg.setImage(memoryDateLabelScaledImg);
            newMemDatePanel.add(memoryDateLabel);
            Calendar c = Calendar.getInstance();
            chooser = new JDateChooser(c.getTime());
            JTextFieldDateEditor editor = (JTextFieldDateEditor) chooser.getDateEditor();
            editor.setEditable(false);

            chooser.getJCalendar().setSelectableDateRange(new SimpleDateFormat("MM-DD-YYYY").parse("01-01-1900"), new Date());
            chooser.setAlignmentX(SwingConstants.CENTER);
            chooser.setFont(fieldFont);
            chooser.setBackground(b1Reg);
            chooser.setForeground(textColor);

            for (Component cm : chooser.getComponents()) {
                ((JComponent) cm).setBackground(Color.white);
                ((JComponent) cm).setForeground(textColor);
            }

            newMemDatePanel.add(chooser);
            newMem1GBC.gridx = 0;
            newMem1GBC.gridy = 1;
            newMemoryCard1.add(newMemDatePanel, newMem1GBC);

            // Add newMemLocPanel to newMemoryCard1 - allows user to add a location for a memory
            newMemLocPanel = new JPanel(new GridLayout(2, 1));
            newMemLocPanel.setBackground(b1Reg);
            newMemLocPanel.setBorder(panelBorder);
            memoryLocImg = new ImageIcon();
            memoryLocLabel = new JLabel(memoryLocImg);
            Image memoryLocScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Graphics\\location-of-memory-reg.png", newMemTextTitleDim);
            memoryLocImg.setImage(memoryLocScaledImg);
            newMemLocPanel.add(memoryLocLabel);
            memoryLocTextfield = new JTextField("City, State, Country");
            memoryLocTextfield.setFont(fieldFont);
            memoryLocTextfield.setForeground(textColor);
            newMemLocPanel.add(memoryLocTextfield);
            newMem1GBC.gridx = 0;
            newMem1GBC.gridy = 2;
            newMemoryCard1.add(newMemLocPanel, newMem1GBC);

            // Add cancelNextPanel to newMemoryCard1
            cancelPanel = new JPanel(new FlowLayout()); // Allows user to cancel memory creation.
            cancelPanel.setBackground(b1Reg);
            cancelPanel.setSize(naviBtns);
            cancelBtnImg = new ImageIcon();
            cancelLabel = new JLabel(cancelBtnImg);
            cancelLabel.setSize(naviBtns);
            Image cancelScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Cancel btn\\cancel-reg.png", naviBtns);
            cancelBtnImg.setImage(cancelScaledImg);
            cancelPanel.add(cancelLabel);
            cancelPanel.setBorder(buttonBorder);

            // Allows user to proceed to second stage of memory creation.
            nextPanel = new JPanel(new FlowLayout());
            nextPanel.setBackground(b1Reg);
            nextPanel.setSize(naviBtns);
            nextBtnImg = new ImageIcon();
            nextLabel = new JLabel(nextBtnImg);
            nextLabel.setSize(naviBtns);
            Image nextScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Next btn\\next-reg.png", naviBtns);
            nextBtnImg.setImage(nextScaledImg);
            nextPanel.add(nextLabel);
            nextPanel.setBorder(buttonBorder);

            cancelNextPanel = new JPanel(new GridLayout(1, 2));
            cancelNextPanel.add(cancelPanel);
            cancelNextPanel.add(nextPanel);
            newMem1GBC.gridx = 0;
            newMem1GBC.gridy = 3;
            newMem1GBC.fill = GridBagConstraints.BOTH;
            newMem1GBC.weighty = 2;
            newMemoryCard1.add(cancelNextPanel, newMem1GBC);
            newMemoryCard1.setBackground(b1Reg);

            // Create newMemoryCard2
            newMemoryCard2 = new JPanel(new GridBagLayout());
            newMemoryCard2.setBackground(b1Reg);
            GridBagConstraints newMem2GBC = new GridBagConstraints();

            // Add newMem2Header to newMemoryCard2
            newMem2Header = new JPanel(new GridBagLayout());
            newMem2Header.setBackground(b1Reg);
            GridBagConstraints newMem2HeaderGBC = new GridBagConstraints();
            newMem2HeaderGBC.gridx = 0;
            newMem2HeaderGBC.gridy = 0;
            newMem2HeaderGBC.weightx = 1;
            newMem2HeaderGBC.weighty = 1;
            newMem2HeaderGBC.fill = GridBagConstraints.BOTH;
            newMemCornerImg = new ImageIcon();
            newMem2 = new JLabel(newMemCornerImg);
            newMem2.setSize(cornerImgDim);
            Image newMemCornerScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\New Memory btn\\new-memory-reg.png", cornerImgDim);
            newMemCornerImg.setImage(newMemCornerScaledImg);
            newMem2Header.add(newMem2, newMem2HeaderGBC);
            memoryTitleLabel2 = new JLabel("Memory Title");
            memoryTitleLabel2.setHorizontalAlignment(SwingConstants.CENTER);
            memoryTitleLabel2.setFont(titleFont);
            memoryTitleLabel2.setForeground(textColor);
            newMem2HeaderGBC.gridx = 1;
            newMem2HeaderGBC.gridy = 0;
            newMem2HeaderGBC.weightx = 8;
            newMem2Header.add(memoryTitleLabel2, newMem2HeaderGBC);
            newMem2Header.setBorder(panelBorder);
            memoryDateLabel2 = new JLabel("DD-MM-YYYY");
            memoryDateLabel2.setForeground(textColor);
            newMem2HeaderGBC.gridx = 2;
            newMem2HeaderGBC.gridy = 0;
            newMem2HeaderGBC.weightx = 1;
            newMem2Header.add(memoryDateLabel2, newMem2HeaderGBC);
            newMem2GBC.gridx = 0;
            newMem2GBC.gridy = 0;
            newMem2GBC.weightx = 1;
            newMem2GBC.weighty = 1;
            newMem2GBC.fill = GridBagConstraints.BOTH;
            newMemoryCard2.add(newMem2Header, newMem2GBC);

            // Add imgUploadPanel to newMemoryCard2
            imgAudioTagsUploadPanel = new JPanel(new GridBagLayout());
            imgAudioTagsUploadPanel.setBackground(b1Reg);
            imgAudioTagsUploadPanel.setBorder(panelBorder);
            GridBagConstraints imgAudioTagsGBC = new GridBagConstraints();

            // Add audioTagsPanel to newMemoryCard2 - allows audio and tags to be added.
            addAudioPanel = new JPanel(new GridLayout(5, 1));
            addAudioPanel.setBackground(b1Reg);
            Image recAudioScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Record btn\\record-reg.png", audioBtns);
            recAudioImg = new ImageIcon(recAudioScaledImg);
            recAudio = new JLabel(recAudioImg);
            time = new JLabel("Record Time: 00:00");
            time.setFont(fieldFont);
            time.setForeground(textColor);
            time.setHorizontalAlignment(SwingConstants.CENTER);
            time.setBorder(buttonBorder);
            Image stopRecScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Stop btn\\stop-reg.png", audioBtns);
            stopRecImg = new ImageIcon(stopRecScaledImg);
            stopRec = new JLabel(stopRecImg);
            recPanel = new JPanel(new FlowLayout());
            recPanel.add(recAudio);
            recPanel.setBackground(b1Reg);
            recPanel.setBorder(buttonBorder);

            // Allows user to stop/record audio.
            stopRecPanel = new JPanel(new FlowLayout());
            stopRecPanel.add(stopRec);
            stopRecPanel.setBackground(b1Reg);
            stopRecPanel.setBorder(buttonBorder);

            // Allows user to play audio associated with memory.
            playAudioPanel = new JPanel(new FlowLayout());
            playAudioPanel.setBackground(b1Reg);
            Image playScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Play btn\\play-reg.png", audioBtns);
            playImg = new ImageIcon(playScaledImg);
            playAudioPanel.add(new JLabel(playImg));
            playAudioPanel.setBorder(buttonBorder);
            removeAudioPanel = new JPanel(new FlowLayout());
            removeAudioPanel.setBackground(b1Reg);
            Image remScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Remove btn\\remove-reg.png", audioBtns);
            remImg = new ImageIcon(remScaledImg);
            remAudio = new JLabel(remImg);
            removeAudioPanel.add(remAudio);
            removeAudioPanel.setBorder(buttonBorder);
            imgAudioTagsGBC.gridx = 0;
            imgAudioTagsGBC.gridy = 0;
            imgAudioTagsGBC.weightx = 1;
            imgAudioTagsGBC.weighty = 1;
            imgAudioTagsGBC.fill = GridBagConstraints.BOTH;
            addAudioPanel.add(recPanel);
            addAudioPanel.add(time);
            addAudioPanel.add(playAudioPanel);
            addAudioPanel.add(stopRecPanel);
            addAudioPanel.add(removeAudioPanel);
            imgAudioTagsUploadPanel.add(addAudioPanel, imgAudioTagsGBC);
            imgUploadScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Upload Image btn\\upload-image-reg.png", uploadGraphicDim);
            imgUploadImg = new ImageIcon(imgUploadScaledImg);

            // Allows user to attach an image to the memory.
            imgUploadPanel = new JPanel(new FlowLayout());
            imgUploadLabel = new JLabel(imgUploadImg);
            imgUploadPanel.setBorder(buttonBorder);
            imgUploadPanel.setBackground(b1Reg);
            imgUploadPanel.add(imgUploadLabel);
            imgUploadPanel.setToolTipText("Click to Replace Image");
            imgUploadScroll = new JScrollPane(imgUploadPanel);
            imgUploadScroll.setBackground(b1Reg);
            imgUploadScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            imgUploadScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            imgUploadScroll.setBorder(textfieldBorder);
            imgAudioTagsGBC.gridx = 1;
            imgAudioTagsGBC.weightx = 10;

            // Allows the user to apply image filters.
            imgFilterPanel = new JPanel(new GridBagLayout());
            imgFilterPanel.setBackground(b1Reg);
            GridBagConstraints imgFilterGBC = new GridBagConstraints();
            imgFilterGBC.gridx = 0;
            imgFilterGBC.gridy = 0;
            imgFilterGBC.weightx = 1;
            imgFilterGBC.weighty = 9;
            imgFilterGBC.fill = GridBagConstraints.BOTH;
            imgFilterPanel.add(imgUploadScroll, imgFilterGBC);
            filterPanel = new JPanel();
            filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
            filterPanel.setBackground(b1Reg);
            filterPanel.setBorder(panelBorder);
            filterLabel = new JLabel("   Apply Filter:   ");
            filterLabel.setBackground(b1Reg);
            filterLabel.setFont(fieldFont);
            filterLabel.setForeground(textColor);
            filterPanel.add(filterLabel);
            imgFilterCombo = new JComboBox(FILTER_OPTIONS);
            imgFilterCombo.setMinimumSize(audioBtns);
            imgFilterCombo.setFont(fieldFont);
            imgFilterCombo.setForeground(textColor);
            imgFilterCombo.setBackground(b1Reg);
            imgFilterCombo.setEnabled(false);
            filterPanel.add(imgFilterCombo);
            removeImgPanel = new JPanel(new FlowLayout());
            removeImgPanel.setBackground(b1Reg);
            removeImgPanel.setBorder(panelBorder);
            removeImgPanel.setToolTipText("Click to Remove Image");
            Image removeImgScaledGraphic = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Remove btn\\remove-reg.png", audioBtns);
            removeImgGraphic = new ImageIcon(removeImgScaledGraphic);
            removeImgLabel = new JLabel(removeImgGraphic);
            removeImgPanel.add(removeImgLabel);
            filterPanel.add(removeImgPanel);
            imgFilterGBC.gridy = 1;
            imgFilterGBC.weighty = 1;
            imgFilterPanel.add(filterPanel, imgFilterGBC);
            imgAudioTagsUploadPanel.add(imgFilterPanel, imgAudioTagsGBC); // change to panel that holds image jscrollpane and filter panel

            // tagsPanel holds all tag-related functionality.
            tagsPanel = new JPanel(new GridBagLayout());
            tagsPanel.setBackground(b1Reg);
            tagsPanel.setMinimumSize(new Dimension(275, 200));
            GridBagConstraints tagsPanelGBC = new GridBagConstraints();
            tagsPanelGBC.gridx = 0;
            tagsPanelGBC.gridy = 0;
            tagsPanelGBC.weightx = 1;
            tagsPanelGBC.weighty = 1;
            tagsPanelGBC.fill = GridBagConstraints.BOTH;

            // addTagsPanel allows user to add tags to a memory.
            addTagsPanel = new JPanel();
            addTagsPanel.setBackground(b1Reg);
            addTagsPanel.setBorder(buttonBorder);
            Image addTagsScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Add Tags btn\\add-tags-reg.png", addTagsBtn);
            addTagsImg = new ImageIcon(addTagsScaledImg);
            addTags = new JLabel(addTagsImg);
            addTagsPanel.add(addTags);
            addTagsPanel.setMaximumSize(audioBtns);
            tagsPanel.add(addTagsPanel, tagsPanelGBC);

            // tagsListPanel stores tags associated with a memory.
            tagsListPanel = new JPanel();
            tagsListPanel.setLayout(new BoxLayout(tagsListPanel, BoxLayout.PAGE_AXIS));
            tagsListPanel.setBackground(b1Reg);
            tagsListPanel.setBorder(panelBorder);
            tagsListPanel.setSize(250, 1000);
            tagsListScroll = new JScrollPane(tagsListPanel);
            tagsListScroll.setSize(250, 400);
            tagsListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            tagsPanelGBC.gridy = 1;
            tagsPanelGBC.weighty = 24;
            tagsPanel.add(tagsListScroll, tagsPanelGBC);
            imgAudioTagsGBC.gridx = 2;
            imgAudioTagsGBC.weightx = 1;
            imgAudioTagsUploadPanel.add(tagsPanel, imgAudioTagsGBC);
            newMem2GBC.gridx = 0;
            newMem2GBC.gridy = 1;
            newMem2GBC.weighty = 5;
            newMemoryCard2.add(imgAudioTagsUploadPanel, newMem2GBC);

            // Add storyTextPanel to newMemoryCard2 - allows user to add text to a memory.
            storyTextPanel = new JPanel(new FlowLayout());
            storyTextPanel.setBackground(b1Reg);
            storyTextPanel.setMaximumSize(new Dimension(1000, 120));
            storyTextPanel.setBorder(textfieldBorder);
            storyText = new JTextArea("It all started when...");
            storyText.setFont(fieldFont);
            storyText.setForeground(textColor);
            storyText.setLineWrap(true);
            storyText.setPreferredSize(new Dimension(935, 1000));
            storyTextScroll = new JScrollPane(storyText);
            storyTextScroll.setPreferredSize(new Dimension(970, 154));
            storyTextPanel.add(storyTextScroll);
            newMem2GBC.gridx = 0;
            newMem2GBC.gridy = 2;
            newMem2GBC.weighty = 5;
            newMemoryCard2.add(storyTextPanel, newMem2GBC);

            // Add prevCreatePanel to newMemoryCard2 - allows user to return to stage 1 of memory creation.
            prevCreatePanel = new JPanel(new GridLayout(1, 2));
            prevPanel = new JPanel(new FlowLayout());
            prevPanel.setBackground(b1Reg);
            prevPanel.setSize(naviBtns);
            prevBtnImg = new ImageIcon();
            prev = new JLabel(prevBtnImg);
            prev.setSize(naviBtns);
            Image prevBtnScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Previous btn\\previous-reg.png", naviBtns);
            prevBtnImg.setImage(prevBtnScaledImg);
            prevPanel.add(prev);
            prevPanel.setBorder(buttonBorder);

            // createPanel allows user to create a memory.
            createPanel = new JPanel(new FlowLayout());
            createPanel.setBackground(b1Reg);
            createPanel.setSize(naviBtns);
            createBtnImg = new ImageIcon();
            create = new JLabel(createBtnImg);
            create.setSize(naviBtns);
            Image createBtnScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Create Memory btn\\create-memory-reg.png", naviBtns);
            createBtnImg.setImage(createBtnScaledImg);
            createPanel.add(create);
            createPanel.setBorder(buttonBorder);
            prevCreatePanel.add(prevPanel);
            prevCreatePanel.add(createPanel);
            newMem2GBC.gridx = 0;
            newMem2GBC.gridy = 3;
            newMem2GBC.weighty = 2;
            newMemoryCard2.add(prevCreatePanel, newMem2GBC);

            // Create expMemCard1
            expMemCard1 = new JPanel(new GridBagLayout());
            expMemCard1.setBackground(b1Reg);
            GridBagConstraints expMemCard1GBC = new GridBagConstraints();
            expMemCard1GBC.gridx = 0;
            expMemCard1GBC.gridy = 0;
            expMemCard1GBC.weightx = 1;
            expMemCard1GBC.weighty = 1;
            expMemCard1GBC.fill = GridBagConstraints.BOTH;

            // Add expMemCard1Header to expMemCard1
            expMemCard1Header = new JPanel();
            expMemCard1Header.setLayout(new BoxLayout(expMemCard1Header, BoxLayout.X_AXIS));
            expMemCard1Header.setBackground(b3Reg);
            expMemCornerImgLabel = new JLabel("Make Roooooom");
            expMemCornerImgLabel.setForeground(b3Reg);
            expMemCornerPanel = new JPanel(new FlowLayout());
            expMemCornerPanel.setBackground(b3Reg);
            expMemCornerPanel.add(expMemCornerImgLabel);
            expMemCard1Header.add(expMemCornerPanel);
            membaCortexPanel = new JPanel(new FlowLayout());
            membaCortexPanel.setBackground(b3Reg);
            Image membaCortexScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Graphics\\memba-cortex-reg.png", membaCortexDim);
            membaCortexImg = new ImageIcon(membaCortexScaledImg);
            membaCortexLabel = new JLabel(membaCortexImg);
            membaCortexPanel.add(membaCortexLabel);
            membaCortexPanel.setSize(membaCortexDim);
            expMemCard1Header.add(membaCortexPanel);
            expMemCard1Header.setBorder(panelBorder);
            cornerPanel = new JPanel(new FlowLayout());
            cornerLabel = new JLabel("Make Roooooom");
            cornerLabel.setForeground(b3Reg);
            cornerLabel.setMinimumSize(cornerSpacer);
            cornerPanel.add(cornerLabel);
            cornerPanel.setMinimumSize(cornerSpacer);
            cornerPanel.setBackground(b3Reg);
            expMemCard1Header.add(cornerPanel);
            expMemCard1.add(expMemCard1Header, expMemCard1GBC);

            // Add expFilterPanel to expMemCard1
            expFilterPanel = new JPanel(new FlowLayout());
            expFilterPanel.setBackground(b1Reg);
            expFilterPanel.setPreferredSize(new Dimension(1000, 25));
            dateLabel = new JLabel("Filter by Date             ");
            dateLabel.setFont(fieldFont);
            dateLabel.setForeground(textColor);
            dateLabel.setBackground(b1Reg);
            dateLabel.setHorizontalTextPosition(SwingConstants.CENTER);

            // datePanel displays date of the memory
            datePanel = new JPanel();
            datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
            datePanel.add(dateLabel);
            datePanel.setBackground(b1Reg);

            // dateFilterCombo holds options that allow the user to filter memories by date.
            dateFilterCombo = new JComboBox(DATE_FILTERS);
            dateFilterCombo.setBackground(b1Reg);
            dateFilterCombo.setForeground(textColor);
            dateFilterCombo.setAlignmentX(CENTER_ALIGNMENT);
            dateFilterCombo.addItemListener(new ItemListener() {
                /**
                 * This method fills the memory gallery based on the chosen
                 * filter.
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date1 = new Date();

                        if (dateFilterCombo.getSelectedIndex() == 0) {
                            currDateChosen = "01/01/1900";
                            fillMemoryGallery("01/01/1900", searchTags);
                        } else if (dateFilterCombo.getSelectedIndex() == 1) {
                            currDateChosen = sdformat.format(date1);
                            fillMemoryGallery(sdformat.format(date1), searchTags);
                        } else if (dateFilterCombo.getSelectedIndex() == 2) {
                            currDateChosen = sdformat.format(getDateFrom(7));
                            fillMemoryGallery(sdformat.format(getDateFrom(7)), searchTags);
                        } else if (dateFilterCombo.getSelectedIndex() == 3) {
                            currDateChosen = sdformat.format(getDateFrom(30));
                            fillMemoryGallery(sdformat.format(getDateFrom(30)), searchTags);
                        } else if (dateFilterCombo.getSelectedIndex() == 4) {
                            currDateChosen = sdformat.format(getDateFrom(365));
                            fillMemoryGallery(sdformat.format(getDateFrom(365)), searchTags);
                        }

                    }
                }
            });
            datePanel.add(dateFilterCombo);
            expFilterPanel.add(datePanel);

            // Add filterTagsPanel and expAddTagsPanel to explore memories card.
            filterTagsPanel = new JPanel(new FlowLayout());
            filterTagsPanel.setBackground(b1Reg);
            expAddTagsPanel = new JPanel(new FlowLayout());
            Image expAddTagsScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Add Tags btn\\add-tags-reg.png", addTagsBtn);
            expAddTagsImg = new ImageIcon(expAddTagsScaledImg);
            expAddTagsLabel = new JLabel(expAddTagsImg);
            expAddTagsPanel.add(expAddTagsLabel);
            expAddTagsPanel.setBackground(b1Reg);
            expAddTagsPanel.setBorder(panelBorder);
            expAddTagsPanel.setMaximumSize(addTagsBtn);
            filterTagsPanel.add(expAddTagsPanel);
            expFilterPanel.add(filterTagsPanel);
            expFilterPanel.setBorder(panelBorder);
            expFilterPanel.setMaximumSize(new Dimension(1000, 25));
            expMemCard1GBC.gridy = 1;
            expMemCard1.add(expFilterPanel, expMemCard1GBC);

            // Add searchTagsList to expMemCard1
            searchTagsListPanel = new JPanel();
            searchTagsListPanel.setLayout(new BoxLayout(searchTagsListPanel, BoxLayout.X_AXIS));
            searchTagsListPanel.setBackground(b1Reg);
            searchTagsListPanel.setBorder(panelBorder);
            searchTagsListPanel.setSize(3000, 45);
            searchTagsListScroll = new JScrollPane(searchTagsListPanel);
            searchTagsListScroll.setSize(250, 400);
            searchTagsListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            expMemCard1GBC.gridy = 2;
            expMemCard1.add(searchTagsListScroll, expMemCard1GBC);

            // Add memoryGalleryPanel to expMemCard1
            memoryGalleryPanel = new JPanel();
            memoryGalleryPanel.setPreferredSize(new Dimension(1500, 300));
            memoryGalleryPanel.setLayout(new BoxLayout(memoryGalleryPanel, BoxLayout.X_AXIS));
            memoryGalleryPanel.setBackground(b1Reg);
            memoryGalleryPanel.setBorder(panelBorder);
            memoryGalleryScroll = new JScrollPane(memoryGalleryPanel);
            memoryGalleryScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            memoryGalleryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            memoryGalleryScroll.getHorizontalScrollBar().setUnitIncrement(16);
            expMemCard1GBC.gridy = 3;
            expMemCard1GBC.weighty = 15;
            expMemCard1.add(memoryGalleryScroll, expMemCard1GBC);

            // Add prevDelselRemPanel to expMemCard1
            prevDelselRemPanel = new JPanel();
            prevDelselRemPanel.setLayout(new BoxLayout(prevDelselRemPanel, BoxLayout.X_AXIS));
            prevDelselRemPanel.setBackground(b1Reg);
            prevDelselRemPanel.setBorder(panelBorder);

            // prevPanel2 allows user to return to main menu from memory gallery.
            prevPanel2 = new JPanel(new FlowLayout());
            Image prevBtnScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Previous btn\\previous-reg.png", naviBtns);
            prevBtnImg2 = new ImageIcon(prevBtnScaledImg2);
            prev2 = new JLabel(prevBtnImg2);
            prevPanel2.add(prev2);
            prevPanel2.setBackground(b1Reg);
            prevPanel2.setBorder(buttonBorder);
            prevDelselRemPanel.add(prevPanel2);

            // deleteSelected allows user to delete a group of selected memories.
            deleteSelected = new JPanel(new FlowLayout());
            Image delselScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Delete Selected btn\\delete-selected-reg.png", naviBtns);
            delselBtnImg = new ImageIcon(delselScaledImg);
            delsel = new JLabel(delselBtnImg);
            deleteSelected.add(delsel);
            deleteSelected.setBackground(b1Reg);
            deleteSelected.setBorder(buttonBorder);
            prevDelselRemPanel.add(deleteSelected);

            // remembaPanel allows user to enter single memory view, where user can
            // edit or delete a memory.
            remembaPanel = new JPanel(new FlowLayout());
            Image remembaBtnScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Rememba btn\\rememba-reg.png", naviBtns);
            remembaBtnImg = new ImageIcon(remembaBtnScaledImg);
            rememba = new JLabel(remembaBtnImg);
            remembaPanel.add(rememba);
            remembaPanel.setBackground(b1Reg);
            remembaPanel.setBorder(buttonBorder);
            prevDelselRemPanel.add(remembaPanel);

            // Create navigation cards
            naviCards = new JPanel(new CardLayout());
            naviCards.add(prevDelselRemPanel, "naviCard1");
            expMemCard1GBC.gridy = 4;
            expMemCard1GBC.weighty = 1;
            expMemCard1.add(naviCards, expMemCard1GBC);

            // Create expMemCard2 (single memory view)
            expMemCard2 = new JPanel(new GridBagLayout());
            expMemCard2.setBackground(b1Reg);
            GridBagConstraints expMem2GBC = new GridBagConstraints();

            // Add expMem2Header to expMemCard2 (single memory view)
            expMem2Header = new JPanel(new GridBagLayout());
            expMem2Header.setBackground(b1Reg);
            expMem2Header.setMinimumSize(new Dimension(1000, 80));
            GridBagConstraints expMem2HeaderGBC = new GridBagConstraints();
            newMem2HeaderGBC.gridx = 0;
            newMem2HeaderGBC.gridy = 0;
            newMem2HeaderGBC.weightx = 1;
            newMem2HeaderGBC.weighty = 1;
            newMem2HeaderGBC.fill = GridBagConstraints.BOTH;

            // Add expMem2 to expMemCard2 to help with spacing (single memory view)
            expMem2 = new JLabel("Make Rooooooooom");
            expMem2.setSize(cornerImgDim);
            expMem2.setForeground(b1Reg);
            expMem2Header.add(expMem2, expMem2HeaderGBC);

            // Add memoryTitleLabel3 to expMemCard2 (single memory view)
            memoryTitleLabel3 = new JLabel("Memory Title");
            memoryTitleLabel3.setHorizontalAlignment(SwingConstants.CENTER);
            memoryTitleLabel3.setFont(titleFont);
            memoryTitleLabel3.setForeground(textColor);
            memoryTitleLabel3.setMinimumSize(cornerImgDim);
            expMem2HeaderGBC.gridx = 1;
            expMem2HeaderGBC.gridy = 0;
            expMem2HeaderGBC.weightx = 8;
            expMem2Header.add(memoryTitleLabel3, expMem2HeaderGBC);
            expMem2Header.setBorder(panelBorder);

            // Add memoryDateLabel3 to expMemCard2 (single memory view)
            memoryDateLabel3 = new JLabel("DD-MM-YYYY");
            memoryDateLabel3.setForeground(textColor);
            expMem2HeaderGBC.gridx = 2;
            expMem2HeaderGBC.gridy = 0;
            expMem2HeaderGBC.weightx = 1;
            expMem2Header.add(memoryDateLabel3, expMem2HeaderGBC);
            expMem2GBC.gridx = 0;
            expMem2GBC.gridy = 0;
            expMem2GBC.weightx = 1;
            expMem2GBC.weighty = 2;
            expMem2GBC.fill = GridBagConstraints.BOTH;
            expMemCard2.add(expMem2Header, expMem2GBC);

            // Add imgAudioTagsUploadPanel2 to expMemCard2 (single memory view)
            imgAudioTagsUploadPanel2 = new JPanel(new GridBagLayout());
            imgAudioTagsUploadPanel2.setBackground(b1Reg);
            imgAudioTagsUploadPanel2.setBorder(panelBorder);
            GridBagConstraints imgAudioTagsGBC2 = new GridBagConstraints();

            // Add audioTagsPanel to expMemCard2 (single memory view)
            addAudioPanel2 = new JPanel(new GridLayout(5, 1));
            addAudioPanel2.setBackground(b1Reg);
            Image recAudioScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Record btn\\record-reg.png", audioBtns);
            recAudioImg2 = new ImageIcon(recAudioScaledImg2);
            recAudio2 = new JLabel(recAudioImg2);
            time2 = new JLabel("Record Time: 00:00");
            time2.setFont(fieldFont);
            time2.setForeground(textColor);
            time2.setHorizontalAlignment(SwingConstants.CENTER);
            time2.setBorder(buttonBorder);

            // stopRec2 allows user to stop recording audio (single memory view)
            Image stopRecScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Stop btn\\stop-reg.png", audioBtns);
            stopRecImg2 = new ImageIcon(stopRecScaledImg2);
            stopRec2 = new JLabel(stopRecImg2);

            // recPanel2 allows user to add a new recording to an existing memory (single memory view)
            recPanel2 = new JPanel(new FlowLayout());
            recPanel2.add(recAudio2);
            recPanel2.setBackground(b1Reg);
            recPanel2.setBorder(buttonBorder);
            stopRecPanel2 = new JPanel(new FlowLayout());
            stopRecPanel2.add(stopRec2);
            stopRecPanel2.setBackground(b1Reg);
            stopRecPanel2.setBorder(buttonBorder);

            // playAudioPanel2 allows user to play audio (single memory view)
            playAudioPanel2 = new JPanel(new FlowLayout());
            playAudioPanel2.setBackground(b1Reg);
            Image playScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Play btn\\play-reg.png", audioBtns);
            playImg2 = new ImageIcon(playScaledImg2);
            playAudioPanel2.add(new JLabel(playImg2));
            playAudioPanel2.setBorder(buttonBorder);

            // removeAudioPanel2 allows user to remove audio from a memory (single memory view)
            removeAudioPanel2 = new JPanel(new FlowLayout());
            removeAudioPanel2.setBackground(b1Reg);
            Image remScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Remove btn\\remove-reg.png", audioBtns);
            remImg2 = new ImageIcon(remScaledImg2);
            remAudio2 = new JLabel(remImg2);
            removeAudioPanel2.add(remAudio2);
            removeAudioPanel2.setBorder(buttonBorder);
            imgAudioTagsGBC2.gridx = 0;
            imgAudioTagsGBC2.gridy = 0;
            imgAudioTagsGBC2.weightx = 1;
            imgAudioTagsGBC2.weighty = 1;
            imgAudioTagsGBC2.fill = GridBagConstraints.BOTH;
            addAudioPanel2.add(recPanel2);
            addAudioPanel2.add(time2);
            addAudioPanel2.add(playAudioPanel2);
            addAudioPanel2.add(stopRecPanel2);
            addAudioPanel2.add(removeAudioPanel2);
            imgAudioTagsUploadPanel2.add(addAudioPanel2, imgAudioTagsGBC2);

            // imgUploadImg2 holds the picture associated with a memory (single memory view)
            imgUploadScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Upload Image btn\\upload-image-reg.png", uploadGraphicDim);
            imgUploadImg2 = new ImageIcon(imgUploadScaledImg2);
            imgUploadPanel2 = new JPanel(new FlowLayout());
            imgUploadLabel2 = new JLabel(imgUploadImg2);
            imgUploadPanel2.setBorder(panelBorder);
            imgUploadPanel2.setBackground(b1Reg);
            imgUploadPanel2.add(imgUploadLabel2);
            imgUploadPanel2.setToolTipText("Click to Replace Image");
            imgUploadScroll2 = new JScrollPane(imgUploadPanel2);
            imgUploadScroll2.setBackground(b1Reg);
            imgUploadScroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            imgUploadScroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            imgUploadScroll2.setBorder(panelBorder);
            imgAudioTagsGBC2.gridx = 1;
            imgAudioTagsGBC2.weightx = 10;

            // imgFilterPanel2 holds all filter-related functionality (single memory view)
            imgFilterPanel2 = new JPanel(new GridBagLayout());
            imgFilterPanel2.setBackground(b1Reg);
            GridBagConstraints imgFilterGBC2 = new GridBagConstraints();
            imgFilterGBC2.gridx = 0;
            imgFilterGBC2.gridy = 0;
            imgFilterGBC2.weightx = 1;
            imgFilterGBC2.weighty = 9;
            imgFilterGBC2.fill = GridBagConstraints.BOTH;
            imgFilterPanel2.add(imgUploadScroll2, imgFilterGBC2);

            // filterPanel2 holds imgFilterCombo2 (single memory view)
            filterPanel2 = new JPanel();
            filterPanel2.setLayout(new BoxLayout(filterPanel2, BoxLayout.X_AXIS));
            filterPanel2.setBackground(b1Reg);
            filterPanel2.setBorder(panelBorder);
            filterLabel2 = new JLabel("   Apply Filter:   ");
            filterLabel2.setBackground(b1Reg);
            filterLabel2.setFont(fieldFont);
            filterLabel2.setForeground(textColor);
            filterPanel2.add(filterLabel2);

            // imgFilterCombo2 holds image filter options (single memory view)
            imgFilterCombo2 = new JComboBox(FILTER_OPTIONS);
            imgFilterCombo2.setMinimumSize(audioBtns);
            imgFilterCombo2.setFont(fieldFont);
            imgFilterCombo2.setBackground(b1Reg);
            imgFilterCombo2.setForeground(textColor);
            imgFilterCombo2.setEnabled(false);
            filterPanel2.add(imgFilterCombo2);

            // removeImgPanel2 allows user to remove an image associated with a memory (single memory view)
            removeImgPanel2 = new JPanel(new FlowLayout());
            removeImgPanel2.setBackground(b1Reg);
            removeImgPanel2.setBorder(buttonBorder);
            removeImgPanel2.setToolTipText("Click to Remove Image");
            Image removeImgScaledGraphic2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Remove btn\\remove-reg.png", audioBtns);
            removeImgGraphic2 = new ImageIcon(removeImgScaledGraphic2);
            removeImgLabel2 = new JLabel(removeImgGraphic2);
            removeImgPanel2.add(removeImgLabel2);
            filterPanel2.add(removeImgPanel2);
            imgFilterGBC2.gridy = 1;
            imgFilterGBC2.weighty = 1;
            imgFilterPanel2.add(filterPanel2, imgFilterGBC2);
            imgAudioTagsUploadPanel2.add(imgFilterPanel2, imgAudioTagsGBC2); // change to panel that holds image jscrollpane and filter panel

            // tagsPanel2 holds tag-related functionality (single memory view)
            tagsPanel2 = new JPanel(new GridBagLayout());
            tagsPanel2.setBackground(b1Reg);
            tagsPanel2.setMinimumSize(new Dimension(275, 200));
            GridBagConstraints tagsPanelGBC2 = new GridBagConstraints();
            tagsPanelGBC2.gridx = 0;
            tagsPanelGBC2.gridy = 0;
            tagsPanelGBC2.weightx = 1;
            tagsPanelGBC2.weighty = 1;
            tagsPanelGBC2.fill = GridBagConstraints.BOTH;

            // addTagsPanel2 allows user to add tags to a memory (single memory view)
            addTagsPanel2 = new JPanel();
            addTagsPanel2.setBackground(b1Reg);
            addTagsPanel2.setBorder(buttonBorder);
            Image addTagsScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Add Tags btn\\add-tags-reg.png", addTagsBtn);
            addTagsImg2 = new ImageIcon(addTagsScaledImg2);
            addTags2 = new JLabel(addTagsImg2);
            addTagsPanel2.add(addTags2);
            addTagsPanel2.setMaximumSize(audioBtns);
            tagsPanel2.add(addTagsPanel2, tagsPanelGBC2);

            // tagsListPanel2 holds all tags associated with a memory (single memory view)
            tagsListPanel2 = new JPanel();
            tagsListPanel2.setLayout(new BoxLayout(tagsListPanel2, BoxLayout.PAGE_AXIS));
            tagsListPanel2.setBackground(b1Reg);
            tagsListPanel2.setBorder(panelBorder);
            tagsListPanel2.setSize(250, 1000);
            tagsListScroll2 = new JScrollPane(tagsListPanel2);
            tagsListScroll2.setSize(250, 400);
            tagsListScroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            tagsPanelGBC2.gridy = 1;
            tagsPanelGBC2.weighty = 24;
            tagsPanel2.add(tagsListScroll2, tagsPanelGBC2);
            imgAudioTagsGBC2.gridx = 2;
            imgAudioTagsGBC2.weightx = 1;
            imgAudioTagsUploadPanel2.add(tagsPanel2, imgAudioTagsGBC2);
            expMem2GBC.gridx = 0;
            expMem2GBC.gridy = 1;
            expMem2GBC.weighty = 5;
            expMemCard2.add(imgAudioTagsUploadPanel2, expMem2GBC);

            // Add storyTextPanel to expMemCard2 (single memory view)
            storyTextPanel2 = new JPanel(new FlowLayout());
            storyTextPanel2.setBackground(b1Reg);
            storyTextPanel2.setMaximumSize(new Dimension(1000, 120));
            storyTextPanel2.setBorder(textfieldBorder);
            storyText2 = new JTextArea("It all started when...");
            storyText2.setFont(fieldFont);
            storyText2.setForeground(textColor);
            storyText2.setEnabled(false);
            storyText2.setLineWrap(true);
            storyText2.setPreferredSize(new Dimension(935, 1000));
            storyTextScroll2 = new JScrollPane(storyText2);
            storyTextScroll2.setPreferredSize(new Dimension(970, 200));
            storyTextPanel2.add(storyTextScroll2);
            expMem2GBC.gridx = 0;
            expMem2GBC.gridy = 2;
            expMem2GBC.weighty = 5;
            expMemCard2.add(storyTextPanel2, expMem2GBC);

            // Add prevCreatePanel to expMemCard2 (single memory view)
            prevEditSavePanel = new JPanel(new GridLayout(1, 2));
            prevPanel3 = new JPanel(new FlowLayout());
            prevPanel3.setBackground(b1Reg);
            prevPanel3.setSize(naviBtns);
            prevBtnImg3 = new ImageIcon();
            prev3 = new JLabel(prevBtnImg3);
            prev3.setSize(naviBtns);
            Image prevBtnScaledImg3 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Previous btn\\previous-reg.png", naviBtns);
            prevBtnImg3.setImage(prevBtnScaledImg3);
            prevPanel3.add(prev3);
            prevPanel3.setBorder(buttonBorder);

            // Create editCards and editPanel (single memory view)
            editCards = new JPanel(new CardLayout());
            editPanel = new JPanel(new FlowLayout());
            editPanel.setBackground(b1Reg);
            editPanel.setSize(naviBtns);
            editBtnImg = new ImageIcon();
            edit = new JLabel(editBtnImg);
            edit.setSize(naviBtns);
            Image editBtnScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Edit btn\\edit-reg.png", naviBtns);
            editBtnImg.setImage(editBtnScaledImg2);
            editPanel.add(edit);
            editPanel.setBorder(buttonBorder);
            editCards.add(editPanel, "Edit");
            delSavePanel = new JPanel();
            delSavePanel.setLayout(new BoxLayout(delSavePanel, BoxLayout.X_AXIS));
            delSavePanel.setBackground(b1Reg);

            // delMemPanel allows user to delete a memory (single memory view)
            delMemPanel = new JPanel(new FlowLayout());
            delMemPanel.setBackground(b1Reg);
            delMemPanel.setBorder(buttonBorder);
            Image delMemScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Delete Memory btn\\delete-memory-reg.png", naviBtns);
            delMemImg = new ImageIcon(delMemScaledImg);
            delMemLabel = new JLabel(delMemImg);
            delMemPanel.add(delMemLabel);
            delSavePanel.add(delMemPanel);

            // savePanel allows user to save edits to a memory (single memory view)
            savePanel = new JPanel(new FlowLayout());
            savePanel.setBackground(b1Reg);
            savePanel.setBorder(buttonBorder);
            Image saveScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Save btn\\save-reg.png", naviBtns);
            saveImg = new ImageIcon(saveScaledImg);
            saveLabel = new JLabel(saveImg);
            savePanel.add(saveLabel);
            delSavePanel.add(savePanel);
            editCards.add(delSavePanel, "Delete or Save");
            prevEditSavePanel.add(prevPanel3);
            prevEditSavePanel.add(editCards);
            expMem2GBC.gridx = 0;
            expMem2GBC.gridy = 3;
            expMem2GBC.weighty = 2;
            expMemCard2.add(prevEditSavePanel, expMem2GBC);

            // Create naviCard2 and add to naviCards
            naviCard2 = new JPanel();
            naviCard2.setLayout(new BoxLayout(naviCard2, BoxLayout.X_AXIS));
            naviCard2.setBackground(b1Reg);
            prev4Img = new ImageIcon(prevBtnScaledImg3);
            prev4 = new JLabel(prev4Img);
            prevPanel4 = new JPanel(new FlowLayout());
            prevPanel4.add(prev4);
            prevPanel4.setBackground(b1Reg);
            prevPanel4.setBorder(buttonBorder);
            naviCard2.add(prevPanel4);

            // Create exportSelPanel and exportAllPanel (share memories gallery)
            Image exportSelScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Export Selected btn\\export-selected-reg.png", naviBtns);
            exportSelImg = new ImageIcon(exportSelScaledImg);
            exportSel = new JLabel(exportSelImg);
            exportSelPanel = new JPanel(new FlowLayout());
            exportSelPanel.add(exportSel);
            exportSelPanel.setBackground(b1Reg);
            exportSelPanel.setBorder(buttonBorder);
            naviCard2.add(exportSelPanel);
            Image exportAllScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Export All btn\\export-all-reg.png", naviBtns);
            exportAllImg = new ImageIcon(exportAllScaledImg);
            exportAll = new JLabel(exportAllImg);
            exportAllPanel = new JPanel(new FlowLayout());
            exportAllPanel.add(exportAll);
            exportAllPanel.setBackground(b1Reg);
            exportAllPanel.setBorder(buttonBorder);
            exportAllPanel.setToolTipText("Export current memories to PDF.");
            naviCard2.add(exportAllPanel);
            naviCards.add(naviCard2, "naviCard2");

            // Create customizeApp card
            customizeApp = new JPanel();
            customizeApp.setLayout(new BoxLayout(customizeApp, BoxLayout.Y_AXIS));
            customizeApp.setBackground(b1Reg);
            Image customAppScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Graphics\\customize-appearance-reg.png", newMemTextTitleDim);
            customAppImg = new ImageIcon(customAppScaledImg);
            customAppLabel = new JLabel(customAppImg);
            customAppPanel = new JPanel(new FlowLayout());
            customAppPanel.add(customAppLabel);
            customAppPanel.setBackground(b3Reg);
            customAppPanel.setBorder(panelBorder);
            customAppPanel.setMaximumSize(new Dimension(2000, 100));
            customizeApp.add(customAppPanel);

            // Add appearanceSettings and all customize appearance options.
            appearanceSettings = new JPanel(new GridLayout(1, 2));
            appearanceSettings.setBackground(b1Reg);
            appearanceSettings.setBorder(panelBorder);
            appearanceLabelPanel = new JPanel();
            appearanceLabelPanel.setLayout(new BoxLayout(appearanceLabelPanel, BoxLayout.Y_AXIS));
            appearanceLabelPanel.setBackground(b1Reg);
            themePresetsLabel = new JLabel("Theme Presets: ");
            themePresetsLabel.setFont(titleFont);
            themePresetsLabel.setForeground(textColor);
            themePresetsLabel.setVerticalTextPosition(SwingConstants.CENTER);
            themePresetsPanel = new JPanel(new BorderLayout());
            themePresetsPanel.add(themePresetsLabel, BorderLayout.EAST);
            themePresetsPanel.setBackground(b1Reg);
            backgroundColor1Label = new JLabel("Background Color 1: ");
            backgroundColor1Label.setFont(titleFont);
            backgroundColor1Label.setForeground(textColor);
            backgroundColor1Label.setVerticalTextPosition(SwingConstants.CENTER);
            backgroundColor1Panel = new JPanel(new BorderLayout());
            backgroundColor1Panel.add(backgroundColor1Label, BorderLayout.EAST);
            backgroundColor1Panel.setBackground(b1Reg);
            backgroundColor2Label = new JLabel("Background Color 2: ");
            backgroundColor2Label.setFont(titleFont);
            backgroundColor2Label.setForeground(textColor);
            backgroundColor2Label.setVerticalTextPosition(SwingConstants.CENTER);
            backgroundColor2Panel = new JPanel(new BorderLayout());
            backgroundColor2Panel.add(backgroundColor2Label, BorderLayout.EAST);
            backgroundColor2Panel.setBackground(b1Reg);
            textColorLabel = new JLabel("Text Color: ");
            textColorLabel.setFont(titleFont);
            textColorLabel.setForeground(textColor);
            textColorLabel.setVerticalTextPosition(SwingConstants.CENTER);
            textColorPanel = new JPanel(new BorderLayout());
            textColorPanel.add(textColorLabel, BorderLayout.EAST);
            textColorPanel.setBackground(b1Reg);
            borderColorLabel = new JLabel("Border Color: ");
            borderColorLabel.setFont(titleFont);
            borderColorLabel.setForeground(textColor);
            borderColorLabel.setVerticalTextPosition(SwingConstants.CENTER);
            borderColorPanel = new JPanel(new BorderLayout());
            borderColorPanel.add(borderColorLabel, BorderLayout.EAST);
            borderColorPanel.setBackground(b1Reg);
            borderStyleLabel = new JLabel("Border Style: ");
            borderStyleLabel.setFont(titleFont);
            borderStyleLabel.setForeground(textColor);
            borderStyleLabel.setVerticalTextPosition(SwingConstants.CENTER);
            borderStylePanel = new JPanel(new BorderLayout());
            borderStylePanel.add(borderStyleLabel, BorderLayout.EAST);
            borderStylePanel.setBackground(b1Reg);
            appearanceLabelPanel.add(themePresetsPanel);
            appearanceLabelPanel.add(backgroundColor1Panel);
            appearanceLabelPanel.add(backgroundColor2Panel);
            appearanceLabelPanel.add(textColorPanel);
            appearanceLabelPanel.add(borderColorPanel);
            appearanceLabelPanel.add(borderStylePanel);
            appearanceSettings.add(appearanceLabelPanel);

            // Add comboBox column
            appearanceComboPanel = new JPanel();
            appearanceComboPanel.setLayout(new BoxLayout(appearanceComboPanel, BoxLayout.Y_AXIS));
            appearanceComboPanel.setBackground(b1Reg);
            themePresetCombo = new JComboBox(THEME_PRESETS);
            themePresetCombo.setForeground(textColor);
            themePresetCombo.setFont(titleFont);
            themePresetCombo.setMaximumSize(new Dimension(300, 400));
            themePresetComboPanel = new JPanel(new BorderLayout());
            themePresetComboPanel.setBackground(b1Reg);
            themePresetComboPanel.add(themePresetCombo, BorderLayout.WEST);
            backgroundColor1Combo = new JComboBox(COLOR_OPTIONS);
            backgroundColor1Combo.setForeground(textColor);
            backgroundColor1Combo.setFont(titleFont);
            backgroundColor1Combo.setMaximumSize(new Dimension(300, 400));
            backgroundColor1ComboPanel = new JPanel(new BorderLayout());
            backgroundColor1ComboPanel.setBackground(b1Reg);
            backgroundColor1ComboPanel.add(backgroundColor1Combo, BorderLayout.WEST);
            backgroundColor2Combo = new JComboBox(COLOR_OPTIONS);
            backgroundColor2Combo.setForeground(textColor);
            backgroundColor2Combo.setFont(titleFont);
            backgroundColor2Combo.setMaximumSize(new Dimension(300, 400));
            backgroundColor2ComboPanel = new JPanel(new BorderLayout());
            backgroundColor2ComboPanel.setBackground(b1Reg);
            backgroundColor2ComboPanel.add(backgroundColor2Combo, BorderLayout.WEST);
            textColorCombo = new JComboBox(COLOR_OPTIONS);
            textColorCombo.setForeground(textColor);
            textColorCombo.setFont(titleFont);
            textColorCombo.setMaximumSize(new Dimension(300, 400));
            textColorComboPanel = new JPanel(new BorderLayout());
            textColorComboPanel.setBackground(b1Reg);
            textColorComboPanel.add(textColorCombo, BorderLayout.WEST);
            borderColorCombo = new JComboBox(COLOR_OPTIONS);
            borderColorCombo.setForeground(textColor);
            borderColorCombo.setFont(titleFont);
            borderColorCombo.setMaximumSize(new Dimension(300, 400));
            borderColorComboPanel = new JPanel(new BorderLayout());
            borderColorComboPanel.setBackground(b1Reg);
            borderColorComboPanel.add(borderColorCombo, BorderLayout.WEST);
            borderStyleCombo = new JComboBox(BORDER_OPTIONS);
            borderStyleCombo.setForeground(textColor);
            borderStyleCombo.setFont(titleFont);
            borderStyleCombo.setMaximumSize(new Dimension(300, 400));
            borderStyleComboPanel = new JPanel(new BorderLayout());
            borderStyleComboPanel.setBackground(b1Reg);
            borderStyleComboPanel.add(borderStyleCombo, BorderLayout.WEST);
            appearanceComboPanel.add(themePresetComboPanel);
            appearanceComboPanel.add(backgroundColor1ComboPanel);
            appearanceComboPanel.add(backgroundColor2ComboPanel);
            appearanceComboPanel.add(textColorComboPanel);
            appearanceComboPanel.add(borderColorComboPanel);
            appearanceComboPanel.add(borderStyleComboPanel);
            appearanceSettings.add(appearanceComboPanel);
            customizeApp.add(appearanceSettings);

            // Add naviBtns
            prevDefaultConfirmPanel = new JPanel();
            prevDefaultConfirmPanel.setLayout(new BoxLayout(prevDefaultConfirmPanel, BoxLayout.X_AXIS));
            prevDefaultConfirmPanel.setBackground(b1Reg);
            prev5Img = new ImageIcon(prevBtnScaledImg);
            prev5 = new JLabel(prev5Img);
            prevPanel5 = new JPanel(new FlowLayout());
            prevPanel5.add(prev5);
            prevPanel5.setMaximumSize(new Dimension(1000, 100));
            prevPanel5.setBackground(b1Reg);
            prevPanel5.setBorder(buttonBorder);
            prevDefaultConfirmPanel.add(prevPanel5);

            // restDefaultPanel restores all default GUI colors and styles.
            Image restoreDefaultScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Restore Default btn\\restore-default-reg.png", naviBtns);
            restDefaultImg = new ImageIcon(restoreDefaultScaledImg);
            restDefaultLabel = new JLabel(restDefaultImg);
            restDefaultPanel = new JPanel(new FlowLayout());
            restDefaultPanel.add(restDefaultLabel);
            restDefaultPanel.setBackground(b1Reg);
            restDefaultPanel.setMaximumSize(new Dimension(1000, 100));
            restDefaultPanel.setBorder(buttonBorder);
            prevDefaultConfirmPanel.add(restDefaultPanel);

            // confirmPanel updates GUI colors and styles with user selections.
            Image confirmScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Confirm btn\\confirm-reg.png", naviBtns);
            confirmImg = new ImageIcon(confirmScaledImg);
            confirmLabel = new JLabel(confirmImg);
            confirmPanel = new JPanel(new FlowLayout());
            confirmPanel.add(confirmLabel);
            confirmPanel.setBackground(b1Reg);
            confirmPanel.setMaximumSize(new Dimension(1000, 100));
            confirmPanel.setBorder(buttonBorder);
            prevDefaultConfirmPanel.add(confirmPanel);
            customizeApp.add(prevDefaultConfirmPanel);

            // Add cards to cards collection
            cards.add(mainOptionsCard, "Main Options");
            cards.add(newMemoryCard1, "New Memory 1");
            cards.add(newMemoryCard2, "New Memory 2");
            cards.add(expMemCard1, "Explore Memories 1");
            cards.add(expMemCard2, "Explore Memories 2");
            cards.add(customizeApp, "Customize Appearance");

            /* - - - - - - - - - - - - - - \
             *                             |
             * END of GUI layout           |
             *                             |
             * - - - - - - - - - - - - - - | 
             *                             |
             * START of GUI mouseListeners |
             *                             |
             * - - - - - - - - - - - - - - /
             */
            // Activate newMemoryPanel as a button (on main menu)
            newMemoryPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the background color of newMemoryPanel on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    newMemoryPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to new memory creation on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "New Memory 1");
                    newMemoryPanel.setBackground(b1Reg);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes newMemoryPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    newMemoryPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes newMemoryPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    newMemoryPanel.setBackground(b1Reg);
                }
            });

            // Activate exploreMemoriesPanel as a button (on main menu)
            exploreMemoriesPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the exploreMemoriesPanel background on
                 * mouseClick.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    exploreMemoriesPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to memory gallery on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    exploreMemoriesPanel.setBackground(b1Reg);
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Explore Memories 1");
                    CardLayout cl2 = (CardLayout) naviCards.getLayout();
                    cl2.show(naviCards, "naviCard1");
                    dateFilterCombo.setSelectedIndex(0);
                    fillMemoryGallery("01/01/1990", searchTags);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the exploreMemoriesPanel background color
                 * on mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    exploreMemoriesPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the exploreMemoriesPanel background color
                 * on mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    exploreMemoriesPanel.setBackground(b1Reg);
                }
            });

            // Activate shareMemoriesPanel as a button (on main menu)
            shareMemoriesPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the shareMemoriesPanel background color
                 * on mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    shareMemoriesPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to share memories gallery on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    fillMemoryGallery("01/01/1900", searchTags);
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Explore Memories 1");

                    CardLayout cl2 = (CardLayout) naviCards.getLayout();
                    cl2.show(naviCards, "naviCard2");
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the shareMemoriesPanel background color
                 * on mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    shareMemoriesPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the shareMemoriesPanel background color
                 * on mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    shareMemoriesPanel.setBackground(b1Reg);
                }
            });

            // Activate customizeAppearancePanel as a button (on main menu)
            customizeAppearancePanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the customizeAppearancePanel background
                 * color on mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    customizeAppearancePanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to customize appearance
                 * options.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Customize Appearance");
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the customizeAppearancePanel background
                 * color on mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    customizeAppearancePanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the customizeAppearancePanel background
                 * color on mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    customizeAppearancePanel.setBackground(b1Reg);
                }
            });

            // Activate cancelPanel as a button (on new memory card 1)
            cancelPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the cancelPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    cancelPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to the main menu on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    setDataToDefault();
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Main Options");
                    cancelPanel.setBackground(b1Reg);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the cancelPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    cancelPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the cancelPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    cancelPanel.setBackground(b1Reg);
                }
            });

            // Activate nextPanel as a button (on new memory card 1)
            nextPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the nextPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    nextPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method checks for valid data input, and then navigates
                 * the user to new memory creation - part 2 on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (memoryLocTextfield.getText().matches("^([A-Za-z]{1,}\\s?){1,},\\s?([A-Za-z]{1,}\\s?){1,},\\s?([A-Za-z]{1,}\\s?){1,}$")) {
                        CardLayout cl = (CardLayout) cards.getLayout();
                        memoryTitleLabel2.setText(memoryTitleTextfield.getText());
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        memoryDateLabel2.setText(dateFormat.format(chooser.getDate()));
                        cl.show(cards, "New Memory 2");
                    } else {
                        JOptionPane.showMessageDialog(newMemoryCard1, "The location you"
                                + " entered is not valid. Please enter a location in "
                                + "the following format: <city>, <state>, <country>");
                    }
                    nextPanel.setBackground(b1Reg);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the nextPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    nextPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the nextPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    nextPanel.setBackground(b1Reg);
                }
            });

            // Activate recPanel as a button (on new memory card 2)
            recPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the recPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (currAudio == null && !canStop) {
                        recPanel.setBackground(b1Mouseclick);
                    }
                }

                /**
                 * This method starts recording audio from user's audio system
                 * on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currAudio == null && !canStop) {
                        canStop = true;
                        try {
                            captureAudio();
                            timer = new RecordTimer(time);
                            timer.start();
                            canStop = true;
                            recPanel.setBackground(b1Reg);
                        } catch (IllegalArgumentException iae) {
                            JOptionPane.showMessageDialog(null, "There was a problem with your microphone! Please try again.");
                        }
                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the recPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (currAudio == null && !canStop) {
                        recPanel.setBackground(b1Mouseover);
                    }
                }

                /**
                 * This method changes the recPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    if (currAudio == null && !canStop) {
                        recPanel.setBackground(b1Reg);
                    }
                }
            });

            // Activate playAudioPanel as a button (on new memory card 2)
            playAudioPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the playAudioPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (currAudio != null) {
                        playAudioPanel.setBackground(b1Mouseclick);
                    }
                }

                /**
                 * This method plays memory audio on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currAudio != null && !playing) {
                        playAudio(currAudio);
                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the playAudioPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (currAudio != null) {
                        playAudioPanel.setBackground(b1Mouseover);
                    }
                }

                /**
                 * This method changes the playAudioPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    if (currAudio != null) {
                        playAudioPanel.setBackground(b1Reg);
                    }
                }
            });

            // Activate stopRecPanel as a button (on new memory card 2)
            stopRecPanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    /**
                     * This method changes the stopRecPanel background color on
                     * mouseClicked.
                     */
                    if (canStop || playing) {
                        stopRecPanel.setBackground(b1Mouseclick);
                    }
                }

                /**
                 * This method stops audio recording on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {

                    try {
                        if (canStop) {
                            timer.cancel();
                            canStop = false;
                            running = false;
                            Thread.sleep(1000);
                        }

                        if (playing) {
                            newLine.stop();
                            newLine.drain();
                            newLine.close();
                        }
                        stopRecPanel.setBackground(b1Reg);

                    } catch (InterruptedException ex) {

                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the stopRecPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (canStop || playing) {
                        stopRecPanel.setBackground(b1Mouseover);
                    }
                }

                /**
                 * This method changes the stopRecPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    if (canStop || playing) {
                        stopRecPanel.setBackground(b1Reg);
                    }
                }
            });

            // Activate removeAudioPanel as a button (on new memory card 2)
            removeAudioPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the removeAudioPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (currAudio != null) {
                        removeAudioPanel.setBackground(b1Mouseclick);
                    }
                }

                /**
                 * This method removes audio from the memory on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currAudio != null) {
                        currAudio = null;
                        time.setText("Record Time: 00:00");
                        removeAudioPanel.setBackground(b1Reg);
                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the removeAudioPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (currAudio != null) {
                        removeAudioPanel.setBackground(b1Mouseover);
                    }
                }

                /**
                 * This method changes the removeAudioPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    if (currAudio != null) {
                        removeAudioPanel.setBackground(b1Reg);
                    }
                }
            });

            // Activate addTagsPanel as a button (on new memory card 2)
            addTagsPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the addTagsPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    addTagsPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method gets tags from user and adds them to the memory
                 * on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    addTagsPanel.setBackground(b1Reg);
                    getTags();
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the addTagsPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    addTagsPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the addTagsPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    addTagsPanel.setBackground(b1Reg);
                }
            });

            // Activate prevPanel as a button (on new memory card 2)
            prevPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the prevPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    prevPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to new memory - part 1 on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (canStop) {
                        int result = JOptionPane.showConfirmDialog(null, "You haven't stopped the recording, are you sure you want to leave? The current recording will be abruptly stopped!",
                                "Recording Stop Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            try {

                                timer.cancel();
                                canStop = false;
                                running = false;
                                currAudio = null;
                                stopRecPanel.setBackground(b1Reg);
                                Thread.sleep(1000);

                            } catch (InterruptedException ex) {

                            }
                        } else {
                            return;
                        }
                    }

                    if (playing) {
                        newLine.stop();
                        newLine.drain();
                        newLine.close();
                    }
                    prevPanel.setBackground(b1Reg);
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "New Memory 1");

                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the prevPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    prevPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the prevPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    prevPanel.setBackground(b1Reg);
                }
            });

            // Activate createPanel as a button (on new memory card 2)
            createPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the createPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    createPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method creates a memory and inserts it into the database
                 * on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (canStop) {
                        int result = JOptionPane.showConfirmDialog(null, "You haven't stopped the recording, are you sure you want to save the memory as it is right now?",
                                "Recording Stop Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            try {
                                timer.cancel();
                                canStop = false;
                                running = false;
                                remembaAudio = null;
                                stopRecPanel2.setBackground(b1Reg);
                                Thread.sleep(1000);

                            } catch (InterruptedException ex) {

                            }
                        } else {
                            return;
                        }
                    }

                    if (playing) {
                        newLine.stop();
                        newLine.drain();
                        newLine.close();
                    }

                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Main Options");
                    createMemory();
                    setDataToDefault();
                    memoriesFromDB.clear();
                    memoriesFromDB = m.loadAndGetMemories();
                    createPanel.setBackground(b1Reg);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the createPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    createPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the createPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    createPanel.setBackground(b1Reg);
                }
            });

            // Activate imgUploadPanel as a button (on new memory card 2)
            imgUploadPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the imgUploadPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    imgUploadPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method allows user to select an image to attach to the
                 * memory on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "JPG & PNG Images", "jpg", "png");
                    fileChooser.setFileFilter(filter);
                    fileChooser.setDialogTitle("Add an image");
                    int result = fileChooser.showOpenDialog(imgUploadPanel);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        if (fileChooser.getSelectedFile().getPath().toLowerCase().endsWith(".jpg") || fileChooser.getSelectedFile().getPath().toLowerCase().endsWith(".png")) {
                            currImg = new ImageIcon(fileChooser.getSelectedFile().getPath());
                            currImg = new ImageIcon(getScaledImage(currImg.getImage(), uploadImgDim));
                            originalImg = currImg;
                            currImgPath = fileChooser.getSelectedFile().getPath();
                            imgUploadPanel.removeAll();
                            imgUploadLabel = new JLabel(originalImg);
                            imgUploadPanel.add(imgUploadLabel);
                            imgUploadPanel.revalidate();
                            imgUploadPanel.repaint();
                            imgFilterCombo.setEnabled(true);
                        } else {
                            JOptionPane.showMessageDialog(imgUploadPanel, "Please upload either JPG or PNG image files.");
                        }

                    }
                    imgUploadPanel.setBackground(b1Reg);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the imgUploadPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    imgUploadPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the imgUploadPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    imgUploadPanel.setBackground(b1Reg);
                }
            });

            // Activate removeImgPanel as a button (on new memory card 2)
            removeImgPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the removeImgPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    removeImgPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method removes an image associated with a memory on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    imgUploadPanel.removeAll();
                    currImg = null;
                    imgFilterCombo.setSelectedIndex(0);
                    imgFilterCombo.setEnabled(false);
                    imgUploadImg = new ImageIcon(imgUploadScaledImg);
                    imgUploadLabel = new JLabel(imgUploadImg);
                    imgUploadPanel.add(imgUploadLabel);
                    imgUploadPanel.revalidate();
                    removeImgPanel.setBackground(b1Reg);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the removeImgPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    removeImgPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the removeImgPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    removeImgPanel.setBackground(b1Reg);
                }
            });

            // Activate prevPanel2 as a button (on expMemCard1)
            prevPanel2.addMouseListener(new MouseListener() {
                /**
                 * This method changes the prevPanel2 background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    prevPanel2.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to the main menu on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    prevPanel2.setBackground(b1Reg);
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Main Options");
                    selectedPanels.clear();
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the prevPanel2 background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    prevPanel2.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the prevPanel2 background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    prevPanel2.setBackground(b1Reg);
                }
            });

            // Activate imgFilterCombo options
            imgFilterCombo.addItemListener(new ItemListener() {
                /**
                 * This method applies filters to the current image.
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED || currImg == null) {
                        return;
                    }

                    imgUploadPanel.removeAll();

                    if (imgFilterCombo.getSelectedItem().equals("None")) {
                        imgUploadLabel = new JLabel(originalImg);
                        imgUploadPanel.add(imgUploadLabel);

                    } else if (imgFilterCombo.getSelectedItem().equals("Greyscale")) {
                        greyImg = new ImageIcon(getGray(originalImg));
                        imgUploadLabel = new JLabel(greyImg);
                        imgUploadPanel.add(imgUploadLabel);

                    } else if (imgFilterCombo.getSelectedItem().equals("Mirror Image")) {
                        mirrorImg = (ImageIcon) getMirrored(originalImg);
                        imgUploadLabel = new JLabel(mirrorImg);
                        imgUploadPanel.add(imgUploadLabel);
                    } else if (imgFilterCombo.getSelectedItem().equals("Invert Colors")) {
                        invertImg = (ImageIcon) getInvertedColors(originalImg);
                        imgUploadLabel = new JLabel(invertImg);
                        imgUploadPanel.add(imgUploadLabel);
                    } else if (imgFilterCombo.getSelectedItem().equals("Black & White")) {
                        bwImg = (ImageIcon) getBlackAndWhite(originalImg);
                        imgUploadLabel = new JLabel(bwImg);
                        imgUploadPanel.add(imgUploadLabel);
                    }

                    imgUploadPanel.repaint();
                    imgUploadPanel.revalidate();
                }
            });

            // Activate expAddTagsPanel as a button (on new memory card 2)
            expAddTagsPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the expAddTagsPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    expAddTagsPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method adds search tags and refreshes the gallery view
                 * on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    expAddTagsPanel.setBackground(b1Reg);
                    getSearchTags();
                    fillMemoryGallery(currDateChosen, searchTags);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the expAddTagsPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    expAddTagsPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the expAddTagsPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    expAddTagsPanel.setBackground(b1Reg);
                }
            });

            // Activate remembaPanel as a button
            remembaPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the remembaPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    remembaPanel.setBackground(b1Reg);
                }

                /**
                 * This method brings the user to the single memory view if one
                 * memory is selected. Otherwise, it lets the user know to
                 * select only one memory.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (selectedPanels.size() != 1) {
                        ImageIcon icon = new ImageIcon("CSC2620 - Final Project files\\Vector Art\\Graphics\\finger.png");
                        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaled);
                        JOptionPane.showMessageDialog(null, "To open a memory, please make sure one (and only one) memory is selected!", "Memory Selection Warning", JOptionPane.INFORMATION_MESSAGE, scaledIcon);
                    } else {
                        remembaPanel.setBackground(b1Mouseclick);
                        fillRemembaPanel(selectedPanels.get(0).getMemoryAssociated());
                        CardLayout cl = (CardLayout) cards.getLayout();
                        cl.show(cards, "Explore Memories 2");
                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the remembaPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    remembaPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the remembaPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    remembaPanel.setBackground(b1Reg);
                }
            });

            // Activate prevPanel3 as a button (on expMemCard2 [single mem view])
            prevPanel3.addMouseListener(new MouseListener() {
                /**
                 * This method changes the prevPanel3 background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    prevPanel3.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to the memory gallery and
                 * resets the single memory view on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (canStop) {
                        int result = JOptionPane.showConfirmDialog(null, "You haven't stopped the recording, are you sure you want to leave? You will lose the current recording!",
                                "Recording Loss Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            try {
                                if (canStop && currAudio == null && storyText2.isEnabled()) {
                                    timer.cancel();
                                    canStop = false;
                                    running = false;
                                    remembaAudio = null;
                                    stopRecPanel2.setBackground(b1Reg);
                                    Thread.sleep(1000);

                                }

                            } catch (InterruptedException ex) {

                            }
                        } else {
                            return;
                        }
                    }

                    if (playing) {
                        newLine.stop();
                        newLine.drain();
                        newLine.close();
                    }

                    setRemembaEditableOrNot(false);
                    currAudio = null;
                    imgFilterCombo2.setSelectedIndex(0);
                    prevPanel3.setBackground(b1Reg);
                    for (GalleryPanel gp : selectedPanels) {
                        gp.setSelected(false);
                        gp.setBackground(b1Reg);
                    }
                    selectedPanels.clear();
                    fillMemoryGallery("01/01/1990", searchTags);
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Explore Memories 1");
                    CardLayout cl2 = (CardLayout) editCards.getLayout();
                    cl2.show(editCards, "Edit");
                    editMemoryTags.clear();
                    tagsListPanel2.removeAll();
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the prevPanel3 background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    prevPanel3.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the prevPanel3 background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    prevPanel3.setBackground(b1Reg);
                }
            });

            // Activate editPanel as a button (expMemCard2)
            editPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the editPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    editPanel.setBackground(b1Reg);
                }

                /**
                 * This method changes all fields in single memory view to
                 * editable and activates all buttons on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    CardLayout cl = (CardLayout) editCards.getLayout();
                    cl.show(editCards, "Delete or Save");
                    tagsListPanel2.removeAll();

                    JLabel[] label = new JLabel[editMemoryTags.size()];
                    JPanel[] panel = new JPanel[editMemoryTags.size()];
                    Font tagFont = new Font("Bahnschrift", Font.PLAIN, 12);

                    for (int i = 0; i < editMemoryTags.size(); i++) {
                        label[i] = new JLabel(editMemoryTags.get(i));
                        label[i].setFont(tagFont);
                        panel[i] = new JPanel(new FlowLayout());
                        panel[i].add(label[i]);
                        panel[i].setBackground(b1Reg);
                        panel[i].setBorder(buttonBorder);
                        panel[i].setMaximumSize(new Dimension(225, 30));
                        panel[i].setToolTipText("Click to Remove");
                        TagMouseListener myListener = new TagMouseListener(tagsListPanel2, panel[i], label[i].getText(), editMemoryTags, b1Reg, b1Mouseover, b1Mouseclick);
                        panel[i].addMouseListener(myListener);
                        tagsListPanel2.add(panel[i]);
                        tagsListPanel2.repaint();
                        tagsListPanel2.revalidate();
                    }

                    remembaEditable = true;
                    setRemembaEditableOrNot(true);
                    editPanel.setBackground(b1Reg);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the editPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    editPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the editPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    editPanel.setBackground(b1Reg);
                }
            });

            delMemPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the delMemPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    delMemPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method asks user for confirmation and then deletes the
                 * selected memory from the database on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    List<Integer> ids = new ArrayList<>();
                    delMemPanel.setBackground(b1Reg);
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this memory? There is no way to restore it later!", "Message Deletion Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        if (canStop) {

                            try {
                                if (canStop && currAudio == null && storyText2.isEnabled()) {
                                    timer.cancel();
                                    canStop = false;
                                    running = false;
                                    remembaAudio = null;
                                    stopRecPanel2.setBackground(b1Reg);
                                    Thread.sleep(1000);

                                }

                            } catch (InterruptedException ex) {

                            }

                        }

                        if (playing) {
                            newLine.stop();
                            newLine.drain();
                            newLine.close();
                        }
                        ids.add(selectedPanels.get(0).getMemoryAssociated().getUniqueID());
                        m.deleteMemories(ids);
                        memoriesFromDB.remove(selectedPanels.get(0).getMemoryAssociated());
                        editMemoryTags.clear();
                        tagsListPanel2.removeAll();
                        selectedPanels.remove(0);
                        CardLayout cl = (CardLayout) cards.getLayout();
                        fillMemoryGallery("01/01/1990", searchTags);
                        cl.show(cards, "Explore Memories 1");
                        CardLayout cl2 = (CardLayout) editCards.getLayout();
                        cl2.show(editCards, "Edit");
                        setRemembaEditableOrNot(false);
                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the delMemPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    delMemPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the delMemPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    delMemPanel.setBackground(b1Reg);
                }
            });

            // Activate savePanel as a button (expMemCard2)
            savePanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the savePanel background on mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    savePanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method takes user edits and updates the associated
                 * memory in the database on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (canStop) {
                        int result2 = JOptionPane.showConfirmDialog(null, "You haven't stopped the recording, are you sure you want to save it as it is right now?",
                                "Recording Loss Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (result2 == JOptionPane.YES_OPTION) {
                            try {
                                if (canStop && currAudio == null && storyText2.isEnabled()) {
                                    timer.cancel();
                                    canStop = false;
                                    running = false;
                                    remembaAudio = null;
                                    stopRecPanel2.setBackground(b1Reg);
                                    Thread.sleep(1000);

                                }

                            } catch (InterruptedException ex) {

                            }
                        } else {
                            return;
                        }
                    }

                    if (playing) {
                        newLine.stop();
                        newLine.drain();
                        newLine.close();
                    }

                    savePanel.setBackground(b1Reg);
                    tagsListPanel2.removeAll();

                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to save this memory?", "Message Saving Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        setRemembaEditableOrNot(false);
                        byte[] data = null;
                        if (currImg2 != null) {
                            try {
                                Image imgIcon = ((ImageIcon) imgUploadLabel2.getIcon()).getImage();
                                BufferedImage bImage = new BufferedImage(imgIcon.getWidth(null), imgIcon.getHeight(null), BufferedImage.TYPE_INT_RGB);
                                Graphics2D g2 = bImage.createGraphics();
                                g2.drawImage(imgIcon, 0, 0, null);
                                g2.dispose();
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                ImageIO.write(bImage, "jpg", bos);
                                data = bos.toByteArray();
                            } catch (IOException ex) {

                            }
                        }

                        if (editMemoryTags != null) {

                            JLabel[] label = new JLabel[editMemoryTags.size()];
                            JPanel[] panel = new JPanel[editMemoryTags.size()];
                            Font tagFont = new Font("Bahnschrift", Font.PLAIN, 12);

                            for (int i = 0; i < editMemoryTags.size(); i++) {
                                label[i] = new JLabel(editMemoryTags.get(i));
                                label[i].setFont(tagFont);
                                panel[i] = new JPanel(new FlowLayout());
                                panel[i].add(label[i]);
                                panel[i].setBackground(b1Reg);
                                panel[i].setBorder(buttonBorder);
                                panel[i].setForeground(textColor);
                                panel[i].setMaximumSize(new Dimension(225, 30));
                                tagsListPanel2.add(panel[i]);
                                tagsListPanel2.repaint();
                                tagsListPanel2.revalidate();
                            }
                        }

                        String tagList = "";
                        for (int i = 0; i < editMemoryTags.size(); i++) {
                            tagList = tagList + editMemoryTags.get(i) + ",";
                        }

                        m.updateMemory(selectedPanels.get(0).
                                getMemoryAssociated(),
                                storyText2.getText(),
                                time2.getText().
                                        substring(time2.getText().length() - 5,
                                                time2.getText().length()),
                                tagList,
                                data,
                                currAudio);

                        currImg2 = null;
                        memoriesFromDB.clear();
                        memoriesFromDB = m.loadAndGetMemories();
                        CardLayout cl = (CardLayout) editCards.getLayout();
                        cl.show(editCards, "Edit");
                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the savePanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    savePanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the savePanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    savePanel.setBackground(b1Reg);
                }
            });

            deleteSelected.addMouseListener(new MouseListener() {
                /**
                 * This method changes the deleteSelected background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!selectedPanels.isEmpty()) {
                        deleteSelected.setBackground(b1Mouseclick);
                    }
                }

                /**
                 * This method asks the user for confirmation and then deletes
                 * selected memories from the database on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!selectedPanels.isEmpty()) {
                        String memoryString = "";
                        if (selectedPanels.size() > 1) {
                            memoryString = "Are you sure you want to delete these memories? There is no way to restore them later!";
                        } else {
                            memoryString = "Are you sure you want to delete this memory? There is no way to restore it later!";
                        }

                        List<Integer> ids = new ArrayList<>();
                        int result = JOptionPane.showConfirmDialog(null, memoryString, "Message Deletion Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

                        if (result == JOptionPane.YES_OPTION) {
                            for (GalleryPanel gp : selectedPanels) {
                                ids.add(gp.getMemoryAssociated().getUniqueID());
                                memoriesFromDB.remove(gp.getMemoryAssociated());
                            }
                            m.deleteMemories(ids);
                            CardLayout cl = (CardLayout) cards.getLayout();
                            fillMemoryGallery("01/01/1990", searchTags);
                            cl.show(cards, "Explore Memories 1");

                            selectedPanels.clear();
                            deleteSelected.setBackground(b1Reg);
                        }
                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the deleteSelected background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!selectedPanels.isEmpty()) {
                        deleteSelected.setBackground(b1Mouseover);
                    }
                }

                /**
                 * This method changes the deleteSelected background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!selectedPanels.isEmpty()) {
                        deleteSelected.setBackground(b1Reg);
                    }
                }
            });

            // Activate prev4Panel as a button (on expMemCard1)
            prevPanel4.addMouseListener(new MouseListener() {
                /**
                 * This method changes the prevPanel4 background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    prevPanel4.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to the main menu on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    prevPanel4.setBackground(b1Reg);
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Main Options");
                    selectedPanels.clear();
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the prevPanel4 background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    prevPanel4.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the prevPanel4 background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    prevPanel4.setBackground(b1Reg);
                }
            });

            exportSelPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the exportSelPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    exportSelPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method allows user to export selected memories to PDF on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (selectedPanels.isEmpty()) {
                        ImageIcon icon = new ImageIcon("CSC2620 - Final Project files\\Vector Art\\Graphics\\finger.png");
                        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaled);
                        JOptionPane.showMessageDialog(null, "To export memories"
                                + "please make sure at least one memory is "
                                + "selected!",
                                "PDF Export Warning",
                                JOptionPane.INFORMATION_MESSAGE, scaledIcon);

                    } else {
                        createDirectory(getFolderPathFromUser(), "selected");
                    }
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the exportSelPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    exportSelPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the exportSelPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    exportSelPanel.setBackground(b1Reg);
                }
            });

            exportAllPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the exportAllPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    exportAllPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method creates a directory based on user input so that
                 * all the memories can be exported to that directory in PDF
                 * format on mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    createDirectory(getFolderPathFromUser(), "all");
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the exportAllPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    exportAllPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the exportAllPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    exportAllPanel.setBackground(b1Reg);
                }
            });

            // Activate prevPanel5 as a button (on Customize Appearance)
            prevPanel5.addMouseListener(new MouseListener() {
                /**
                 * This method changes the prevPanel5 background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    prevPanel5.setBackground(b1Mouseclick);
                }

                /**
                 * This method navigates the user to the main menu on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, "Main Options");
                    prevPanel5.setBackground(b1Reg);
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the prevPanel5 background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    prevPanel5.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the prevPanel5 background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    prevPanel5.setBackground(b1Reg);
                }
            });

            // Activate restDefaultPanel as a button (on new memory card 2)
            restDefaultPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the restDefaultPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    restDefaultPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method sets all GUI colors and styles to default on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    restDefaultPanel.setBackground(b1Reg);
                    b1Reg = membaDef1;
                    b2Reg = membaDef2;
                    b3Reg = membaDef3;
                    textColor = membaTextColor;
                    b1Mouseover = membaMouseover;
                    b1Mouseclick = membaMouseclick;

                    borderStyleCombo.setSelectedItem("None");

                    buttonBorder = BorderFactory.createLineBorder(b2Reg);
                    panelBorder = BorderFactory.createLineBorder(b2Reg);
                    textfieldBorder = BorderFactory.createLineBorder(b2Reg);

                    repaintPanels();
                    redrawBorders();
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the restDefaultPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    restDefaultPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the restDefaultPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    restDefaultPanel.setBackground(b1Reg);
                }
            });

            // Activate confirmPanel as a button (on new memory card 2)
            confirmPanel.addMouseListener(new MouseListener() {
                /**
                 * This method changes the confirmPanel background color on
                 * mouseClicked.
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    confirmPanel.setBackground(b1Mouseclick);
                }

                /**
                 * This method grabs user input from customize appearance
                 * options and updates the GUI colors and styles on
                 * mousePressed.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Midnight Blue")) {
                        b1Reg = midnightBlue;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Bluetiful")) {
                        b1Reg = bluetiful;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Indigo")) {
                        b1Reg = indigo;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Misty Blue")) {
                        b1Reg = mistyBlue;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Electric Lime")) {
                        b1Reg = electricLime;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Green")) {
                        b1Reg = Color.green;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Screamin' Green")) {
                        b1Reg = screaminGreen;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Fern")) {
                        b1Reg = fern;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Pine Green")) {
                        b1Reg = pineGreen;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Carnation Pink")) {
                        b1Reg = carnationPink;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Vivid Violet")) {
                        b1Reg = vividViolet;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Orchid")) {
                        b1Reg = orchid;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Lavender")) {
                        b1Reg = lavender;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Yellow")) {
                        b1Reg = yellow;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Pastel Yellow")) {
                        b1Reg = pastelYellow;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Yellow Orange")) {
                        b1Reg = yellowOrange;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Orange")) {
                        b1Reg = Color.orange;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Red Orange")) {
                        b1Reg = redOrange;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Red")) {
                        b1Reg = Color.red;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("Black")) {
                        b1Reg = Color.black;
                    }
                    if (backgroundColor1Combo.getSelectedItem().toString().matches("White")) {
                        b1Reg = Color.white;
                    }

                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Midnight Blue")) {
                        b2Reg = midnightBlue;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Bluetiful")) {
                        b2Reg = bluetiful;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Indigo")) {
                        b2Reg = indigo;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Misty Blue")) {
                        b2Reg = mistyBlue;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Electric Lime")) {
                        b2Reg = electricLime;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Green")) {
                        b2Reg = Color.green;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Screamin' Green")) {
                        b2Reg = screaminGreen;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Fern")) {
                        b2Reg = fern;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Pine Green")) {
                        b2Reg = pineGreen;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Carnation Pink")) {
                        b2Reg = carnationPink;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Vivid Violet")) {
                        b2Reg = vividViolet;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Orchid")) {
                        b2Reg = orchid;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Lavender")) {
                        b2Reg = lavender;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Yellow")) {
                        b2Reg = yellow;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Pastel Yellow")) {
                        b2Reg = pastelYellow;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Yellow Orange")) {
                        b2Reg = yellowOrange;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Orange")) {
                        b2Reg = Color.orange;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Red Orange")) {
                        b2Reg = redOrange;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Red")) {
                        b2Reg = Color.red;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("Black")) {
                        b2Reg = Color.black;
                    }
                    if (backgroundColor2Combo.getSelectedItem().toString().matches("White")) {
                        b2Reg = Color.white;
                    }

                    if (textColorCombo.getSelectedItem().toString().matches("Midnight Blue")) {
                        textColor = midnightBlue;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Bluetiful")) {
                        textColor = bluetiful;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Indigo")) {
                        textColor = indigo;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Misty Blue")) {
                        textColor = mistyBlue;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Electric Lime")) {
                        textColor = electricLime;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Green")) {
                        textColor = Color.green;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Screamin' Green")) {
                        textColor = screaminGreen;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Fern")) {
                        textColor = fern;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Pine Green")) {
                        textColor = pineGreen;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Carnation Pink")) {
                        textColor = carnationPink;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Vivid Violet")) {
                        textColor = vividViolet;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Orchid")) {
                        textColor = orchid;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Lavender")) {
                        textColor = lavender;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Yellow")) {
                        textColor = yellow;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Pastel Yellow")) {
                        textColor = pastelYellow;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Yellow Orange")) {
                        textColor = yellowOrange;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Orange")) {
                        textColor = Color.orange;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Red Orange")) {
                        textColor = redOrange;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Red")) {
                        textColor = Color.red;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("Black")) {
                        textColor = Color.black;
                    }
                    if (textColorCombo.getSelectedItem().toString().matches("White")) {
                        textColor = Color.white;
                    }

                    if (borderColorCombo.getSelectedItem().toString().matches("Midnight Blue")) {
                        borderColor = midnightBlue;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Bluetiful")) {
                        borderColor = bluetiful;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Indigo")) {
                        borderColor = indigo;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Misty Blue")) {
                        borderColor = mistyBlue;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Electric Lime")) {
                        borderColor = electricLime;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Green")) {
                        borderColor = Color.green;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Screamin' Green")) {
                        borderColor = screaminGreen;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Fern")) {
                        borderColor = fern;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Pine Green")) {
                        borderColor = pineGreen;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Carnation Pink")) {
                        borderColor = carnationPink;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Vivid Violet")) {
                        borderColor = vividViolet;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Orchid")) {
                        borderColor = orchid;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Lavender")) {
                        borderColor = lavender;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Yellow")) {
                        borderColor = yellow;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Pastel Yellow")) {
                        borderColor = pastelYellow;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Yellow Orange")) {
                        borderColor = yellowOrange;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Orange")) {
                        borderColor = Color.orange;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Red Orange")) {
                        borderColor = redOrange;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Red")) {
                        borderColor = Color.red;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("Black")) {
                        borderColor = Color.black;
                    }
                    if (borderColorCombo.getSelectedItem().toString().matches("White")) {
                        borderColor = Color.white;
                    }

                    if (borderStyleCombo.getSelectedItem().toString().matches("None")) {
                        buttonBorder = BorderFactory.createLineBorder(borderColor);
                        panelBorder = BorderFactory.createLineBorder(borderColor);
                        textfieldBorder = BorderFactory.createLineBorder(borderColor);
                    }

                    b1Mouseclick = b2Reg;
                    header2ColorPicker();
                    mouseoverColorPicker();
                    repaintPanels();
                    redrawBorders();
                }

                /**
                 * This method is intentionally blank.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * This method changes the confirmPanel background color on
                 * mouseEntered.
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    confirmPanel.setBackground(b1Mouseover);
                }

                /**
                 * This method changes the confirmPanel background color on
                 * mouseExited.
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    confirmPanel.setBackground(b1Reg);
                }
            });

            // Add itemListener to themePresetCombo for color theme presets
            themePresetCombo.addItemListener(new ItemListener() {
                /**
                 * This method changes color/style combo box selections based on
                 * presets.
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (themePresetCombo.getSelectedItem().toString().matches("Nite Owl")) {
                        backgroundColor1Combo.setSelectedItem("Bluetiful");
                        backgroundColor2Combo.setSelectedItem("Midnight Blue");
                        textColorCombo.setSelectedItem("Black");
                        borderColorCombo.setSelectedItem("Midnight Blue");
                    }
                    if (themePresetCombo.getSelectedItem().toString().matches("Early Bird")) {
                        backgroundColor1Combo.setSelectedItem("Yellow");
                        backgroundColor2Combo.setSelectedItem("Orange");
                        textColorCombo.setSelectedItem("Red Orange");
                        borderColorCombo.setSelectedItem("Orange");
                    }
                    if (themePresetCombo.getSelectedItem().toString().matches("Slimed")) {
                        backgroundColor1Combo.setSelectedItem("Screamin' Green");
                        backgroundColor2Combo.setSelectedItem("Fern");
                        textColorCombo.setSelectedItem("Pine Green");
                        borderColorCombo.setSelectedItem("Fern");
                    }
                    if (themePresetCombo.getSelectedItem().toString().matches("Bouquet")) {
                        backgroundColor1Combo.setSelectedItem("Carnation Pink");
                        backgroundColor2Combo.setSelectedItem("Vivid Violet");
                        textColorCombo.setSelectedItem("Vivid Violet");
                        borderColorCombo.setSelectedItem("Vivid Violet");
                    }
                }

            });

        } catch (IOException | ParseException ex) {

        }
    }

    /* - - - - - - - - - - - - - - - - - \
     *                                   |
     * END of ViewController Constructor |
     *                                   |
     * - - - - - - - - - - - - - - - - - |
     *                                   |
     * START of class member functions   |
     *                                   |
     * - - - - - - - - - - - - - - - - - /

    /**
     * This method fills the memory gallery panel with all the memories in the database.
     * @param theDate The date used to filter memories.
     * @param listOfSearchTags The tags used to filter memories.
     */
    private void fillMemoryGallery(String theDate, List<String> listOfSearchTags) {
        try {
            // Revalidate the panel so that new panels can be added.
            memoryGalleryPanel.removeAll();
            memoryGalleryPanel.validate();
            memoryGalleryPanel.repaint();
            memoryGalleryPanel.revalidate();

            // Set the date display format and define component dimensions.
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdformat.parse(theDate);
            Dimension galleryThumbDim = new Dimension(260, 260);
            Dimension galleryPanelDim = new Dimension(300, 300);
            Dimension galleryDefaultDim = new Dimension(260, 60);

            // Sort memories chronologically.
            Collections.sort(memoriesFromDB);

            currentGalleryMemory.clear();
            // Add each memory to the gallery embedded in a GalleryPanel object.
            for (Memory mem : memoriesFromDB) {
                Date date2 = sdformat.parse(mem.getDate());
                List<String> memoryTags = new ArrayList<>();
                memoryTags = mem.getTagsAsList();

                if ((date2.after(date1) || date2.equals(date1)) && compareLists(memoryTags, listOfSearchTags)) {
                    currentGalleryMemory.add(mem);
                    GalleryPanel pan = new GalleryPanel(mem);
                    pan.setMaximumSize(galleryPanelDim);
                    String txt = mem.getTitle();
                    String dateOfMemory = mem.getDate();
                    JLabel label = new JLabel();
                    try {
                        ImageIcon imgg = new ImageIcon(getScaledImage(new ImageIcon(mem.getImage()).getImage(), galleryThumbDim));
                        label.setIcon(imgg);
                        pan.add(label);
                    } catch (NullPointerException n) {
                        try {
                            ImageIcon defImg = new ImageIcon(resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Graphics\\memba-logo-reg.png", galleryDefaultDim));
                            label.setIcon(defImg);
                            pan.add(label);
                        } catch (IOException ex) {
                            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    pan.setBorder(BorderFactory.createTitledBorder(panelBorder, txt + ": " + dateOfMemory, TitledBorder.CENTER, TitledBorder.TOP, fieldFont, textColor));
                    pan.setBackground(b1Reg);
                    pan.setToolTipText(txt + ": " + dateOfMemory);

                    // Add a mouseListener to each GalleryPanel object.
                    pan.addMouseListener(new MouseListener() {
                        /**
                         * This method is intentionally blank.
                         */
                        @Override
                        public void mouseClicked(MouseEvent e) {

                        }

                        /**
                         * This method sets a panel's selected status.
                         */
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (pan.isSelected()) {
                                pan.setBackground(b1Mouseclick);
                                pan.setSelected(false);
                                selectedPanels.remove(pan);
                            } else {
                                pan.setBackground(b2Reg);
                                pan.setSelected(true);
                                selectedPanels.add(pan);
                            }
                        }

                        /**
                         * This method is intentionally blank.
                         */
                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        /**
                         * This method changes the GalleryPanel background color
                         * on mouseEntered.
                         */
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            if (!pan.isSelected()) {
                                pan.setBackground(b1Mouseover);
                            } else {
                                pan.setBackground(b3Reg);
                            }
                        }

                        /**
                         * This method changes the GalleryPanel background color
                         * on mouseExited.
                         */
                        @Override
                        public void mouseExited(MouseEvent e) {
                            if (!pan.isSelected()) {
                                pan.setBackground(b1Reg);
                            } else {
                                pan.setBackground(b2Reg);
                            }
                        }
                    });

                    // Add GalleryPanel object to the memory gallery.
                    memoryGalleryPanel.add(pan);
                }

                // Resize the memory gallery based on the amount of memories displayed.
                if (memoryGalleryPanel.getComponentCount() > 5) {
                    memoryGalleryPanel.setPreferredSize(new Dimension((memoryGalleryPanel.getComponentCount() * 300), 300));
                }

                memoryGalleryPanel.validate();
                memoryGalleryPanel.repaint();
                memoryGalleryPanel.revalidate();
            }
        } catch (ParseException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method sets the create memory panels to default and resets the
     * lists, panels, and components used to create a memory.
     */
    public void setDataToDefault() {
        memoryTitleTextfield.setText("That time I...");
        memoryLocTextfield.setText("City, State, Country");
        memoryTitleLabel2.setText("Memory Title");
        time.setText("Record Time: 00:00");
        storyText.setText("It all started when...");
        chooser.setDate(new Date());
        currAudio = null;
        currImg = null;
        currImgPath = "";
        imgUploadPanel.removeAll();
        imgFilterCombo.setSelectedIndex(0);

        tagsListPanel.removeAll();
        tags = new ArrayList<>();

        imgUploadImg = new ImageIcon(imgUploadScaledImg);
        imgUploadLabel = new JLabel(imgUploadImg);

        imgUploadPanel.setBackground(b1Reg);
        imgUploadPanel.add(imgUploadLabel);
        imgUploadPanel.validate();
        imgUploadPanel.repaint();
    }

    /**
     * This method captures audio from user's microphone.
     */
    public void captureAudio() throws IllegalArgumentException {

        try {
            AudioFormat format = getFormat();
            DataLine.Info info = new DataLine.Info(
                    TargetDataLine.class, format);
            line = (TargetDataLine) AudioSystem.getLine(info);

            line.open(format);

            line.start();
            Runnable runner = new Runnable() {
                int bufferSize = (int) format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];

                @Override
                public void run() {
                    out = new ByteArrayOutputStream();
                    running = true;
                    try {
                        while (running) {
                            int count
                                    = line.read(buffer, 0, buffer.length);
                            if (count > 0) {
                                out.write(buffer, 0, count);
                            }
                        }
                        out.close();
                        currAudio = out.toByteArray();
                        line.stop();
                        line.close();
                        line = null;
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "No microphone detected! Please make sure your microphone is connected and try again.");
                    }
                }
            };
            Thread captureThread = new Thread(runner);

            captureThread.start();
        } catch (LineUnavailableException e) {
            JOptionPane.showMessageDialog(null, "There was a problem with your audio! Please try again.");
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(null, "There was a problem with your microphone! Please try again.");
        }
    }

    /**
     * This method plays the audio from the byte array passed as an argument.
     *
     * @param audioArray The byte array that stores audio.
     */
    public void playAudio(byte[] audioArray) {
        try {
            byte audio[] = audioArray;
            InputStream input
                    = new ByteArrayInputStream(audio);
            final AudioFormat format = getFormat();
            final AudioInputStream ais
                    = new AudioInputStream(input, format,
                            audio.length / format.getFrameSize());
            DataLine.Info info = new DataLine.Info(
                    SourceDataLine.class, format);
            newLine = (SourceDataLine) AudioSystem.getLine(info);
            newLine.open(format);
            newLine.start();

            Runnable runner = new Runnable() {
                int bufferSize = (int) format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];

                @Override
                public void run() {
                    try {
                        int count;
                        while ((count = ais.read(
                                buffer, 0, buffer.length)) != -1) {
                            playing = true;
                            if (count > 0) {
                                newLine.write(buffer, 0, count);
                            }
                        }
                        playing = false;
                        newLine.drain();
                        newLine.close();
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                        System.exit(-3);
                    }
                }
            };
            Thread playThread = new Thread(runner);
            playThread.start();
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e);
            System.exit(-4);
        }
    }

    /**
     * This method sets the audio format so that user can record audio and
     * attach it to a memory.
     *
     * @return The audio format used.
     */
    private AudioFormat getFormat() {
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate,
                sampleSizeInBits, channels, signed, bigEndian);
    }

    /**
     * This method resizes an Image object to the dimension passed by argument.
     *
     * @param filePath File path of image.
     * @param dmn Desired dimensions of image.
     * @return resizedImg The resized image.
     * @throws IOException Throws IOException when file cannot be found.
     */
    private Image resizeGraphic(String filePath, Dimension dmn) throws IOException {
        try {
            BufferedImage inputImg;
            Image resizedImg;
            File inFile;
            int width, height;

            // Get component dimensions for image resize
            width = (int) dmn.getWidth();
            height = (int) dmn.getHeight();

            // Read in the image and resize it
            inFile = new File(filePath);
            inputImg = ImageIO.read(inFile);
            resizedImg = inputImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return resizedImg;
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * This method takes user's input tags and associates them with a memory,
     * and adds them to a panel for display.
     */
    private void getSearchTags() {
        String tagHolder;
        String[] tagHolderSplit;
        Font tagFont = new Font("Bahnschrift", Font.PLAIN, 12);
        ArrayList<String> dedupedList = new ArrayList<>();

        // Get tags from user
        tagHolder = JOptionPane.showInputDialog("Enter tag keywords separated by commas:");

        // Apply comma delimiter and create unique list of entries
        if ((tagHolder != null) && (tagHolder.length() > 0)) {
            tagHolderSplit = tagHolder.split(",");

            // Remove whitespace 
            for (int i = 0; i < tagHolderSplit.length; i++) {
                tagHolderSplit[i] = tagHolderSplit[i].stripLeading();
                tagHolderSplit[i] = tagHolderSplit[i].stripTrailing();
                if (!dedupedList.contains(tagHolderSplit[i])) {
                    dedupedList.add(tagHolderSplit[i]);
                }
            }

            // Create as many panels and labels as unique entries
            JLabel[] label = new JLabel[dedupedList.size()];
            JPanel[] panel = new JPanel[dedupedList.size()];

            // Add new tags to the tagsListPanel
            for (int i = 0; i < dedupedList.size(); i++) {
                if (!searchTags.contains(dedupedList.get(i))) {
                    label[i] = new JLabel(dedupedList.get(i));
                    label[i].setFont(tagFont);
                    panel[i] = new JPanel(new FlowLayout());
                    panel[i].add(label[i]);
                    panel[i].setBackground(b1Reg);
                    panel[i].setForeground(textColor);
                    panel[i].setBorder(buttonBorder);
                    panel[i].setMaximumSize(new Dimension(200, 25));
                    panel[i].setToolTipText("Click to Remove");
                    TagMouseListener myListener = new TagMouseListener(searchTagsListPanel, panel[i], label[i].getText(), searchTags, dateFilterCombo, b1Reg, b1Mouseover, b1Mouseclick);
                    panel[i].addMouseListener(myListener);

                    searchTagsListPanel.add(panel[i]);
                    searchTagsListPanel.repaint();
                    searchTagsListPanel.revalidate();
                }
            }

            // Add all unique entries from all user inputs to memory's tags list
            for (int i = 0; i < dedupedList.size(); i++) {
                if (!searchTags.contains(dedupedList.get(i))) {
                    searchTags.add(dedupedList.get(i));
                }
            }
        }
    }

    /**
     * This method takes user's input tags and adds them to a new memory.
     */
    private void getTags() {
        String tagHolder;
        String[] tagHolderSplit;
        Font tagFont = new Font("Bahnschrift", Font.PLAIN, 12);
        ArrayList<String> dedupedList = new ArrayList<>();

        // Get tags from user
        tagHolder = JOptionPane.showInputDialog("Enter tag keywords separated by commas:");

        // Apply comma delimiter and create unique list of entries
        if ((tagHolder != null) && (tagHolder.length() > 0)) {
            tagHolderSplit = tagHolder.split(",");

            // Remove whitespace 
            for (int i = 0; i < tagHolderSplit.length; i++) {
                tagHolderSplit[i] = tagHolderSplit[i].stripLeading();
                tagHolderSplit[i] = tagHolderSplit[i].stripTrailing();
                if (!dedupedList.contains(tagHolderSplit[i])) {
                    dedupedList.add(tagHolderSplit[i]);
                }
            }

            // Create as many panels and labels as unique entries
            JLabel[] label = new JLabel[dedupedList.size()];
            JPanel[] panel = new JPanel[dedupedList.size()];

            // Add new tags to the tagsListPanel
            for (int i = 0; i < dedupedList.size(); i++) {
                if (!tags.contains(dedupedList.get(i))) {
                    label[i] = new JLabel(dedupedList.get(i));
                    label[i].setFont(tagFont);
                    panel[i] = new JPanel(new FlowLayout());
                    panel[i].add(label[i]);
                    panel[i].setBackground(b1Reg);
                    panel[i].setForeground(textColor);
                    panel[i].setBorder(buttonBorder);
                    panel[i].setMaximumSize(new Dimension(225, 30));
                    panel[i].setToolTipText("Click to Remove");
                    TagMouseListener myListener = new TagMouseListener(tagsListPanel, panel[i], label[i].getText(), tags, b1Reg, b1Mouseover, b1Mouseclick);
                    panel[i].addMouseListener(myListener);
                    tagsListPanel.add(panel[i]);
                    tagsListPanel.repaint();
                    tagsListPanel.revalidate();
                }
            }

            // Add all unique entries from all user inputs to memory's tags list
            for (int i = 0; i < dedupedList.size(); i++) {
                if (!tags.contains(dedupedList.get(i))) {
                    tags.add(dedupedList.get(i));
                }
            }
        }
    }

    /**
     * This method takes user's input tags and adds them to tag list associated
     * with a memory, then updates the display panel with the merged list.
     */
    private void getEditTags() {
        String tagHolder;
        String[] tagHolderSplit;
        Font tagFont = new Font("Bahnschrift", Font.PLAIN, 12);
        ArrayList<String> dedupedList = new ArrayList<>();

        // Get tags from user
        tagHolder = JOptionPane.showInputDialog("Enter tag keywords separated by commas:");

        // Apply comma delimiter and create unique list of entries
        if ((tagHolder != null) && (tagHolder.length() > 0)) {
            tagHolderSplit = tagHolder.split(",");

            // Remove whitespace 
            for (int i = 0; i < tagHolderSplit.length; i++) {
                tagHolderSplit[i] = tagHolderSplit[i].stripLeading();
                tagHolderSplit[i] = tagHolderSplit[i].stripTrailing();
                if (!dedupedList.contains(tagHolderSplit[i])) {
                    dedupedList.add(tagHolderSplit[i]);
                }
            }

            // Create as many panels and labels as unique entries
            JLabel[] label = new JLabel[dedupedList.size()];
            JPanel[] panel = new JPanel[dedupedList.size()];

            // Add new tags to the tagsListPanel
            for (int i = 0; i < dedupedList.size(); i++) {
                if (!editMemoryTags.contains(dedupedList.get(i))) {
                    label[i] = new JLabel(dedupedList.get(i));
                    label[i].setFont(tagFont);
                    panel[i] = new JPanel(new FlowLayout());
                    panel[i].add(label[i]);
                    panel[i].setBackground(b1Reg);
                    panel[i].setForeground(textColor);
                    panel[i].setBorder(buttonBorder);
                    panel[i].setMaximumSize(new Dimension(225, 30));
                    panel[i].setToolTipText("Click to Remove");
                    TagMouseListener myListener = new TagMouseListener(tagsListPanel2, panel[i], label[i].getText(), editMemoryTags, b1Reg, b1Mouseover, b1Mouseclick);
                    panel[i].addMouseListener(myListener);
                    tagsListPanel2.add(panel[i]);
                    tagsListPanel2.repaint();
                    tagsListPanel2.revalidate();
                }
            }

            // Add all unique entries from all user inputs to memory's tags list
            for (int i = 0; i < dedupedList.size(); i++) {
                if (!editMemoryTags.contains(dedupedList.get(i))) {
                    editMemoryTags.add(dedupedList.get(i));
                }
            }
        }
    }

    /**
     * This method returns a scaled Image object based on the dimension passed
     * by argument.
     *
     * @param srcImg The Image to be scaled.
     * @param dim The desired dimension.
     * @return resizedImg The resized Image.
     */
    private Image getScaledImage(Image srcImg, Dimension dim) {
        // Get width and height of image.
        int width = srcImg.getWidth(null);
        int height = srcImg.getHeight(null);

        // Fit the image to the panel without losing aspect ratio.
        while ((width > dim.width) || (height > dim.height)) {
            width = (int) (width * 0.9);
            height = (int) (height * 0.9);
        }

        // Resize the image and return to caller.
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();

        return resizedImg;
    }

    /**
     * This method creates a Memory object based on user input.
     */
    public void createMemory() {
        // create new memory with default data (title, text, date)
        Memory newMemory = new Memory(memoryTitleLabel2.getText(), memoryDateLabel2.getText(), storyText.getText());

        // look for additional data to add, such as image, audio, location and tags.
        if (currImg != null) {
            try {

                Image imgIcon = ((ImageIcon) imgUploadLabel.getIcon()).getImage();
                BufferedImage bImage = new BufferedImage(imgIcon.getWidth(null), imgIcon.getHeight(null), BufferedImage.TYPE_INT_RGB);

                Graphics2D g2 = bImage.createGraphics();
                g2.drawImage(imgIcon, 0, 0, null);
                g2.dispose();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                byte[] data = bos.toByteArray();
                newMemory.setImage(data);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not add image to database!");
            }

        }

        // If audio was added, store it with the memory.
        if (currAudio != null) {
            newMemory.setAudio(currAudio);
        }

        // If location was added, store it with the memory.
        if (!memoryLocTextfield.getText().equals("City, State, Country")) {
            newMemory.setLocation(memoryLocTextfield.getText());
        }

        // If tags were added, store with the memory.
        if (!tags.isEmpty()) {
            newMemory.setTags(tags);
        }

        // Store the record time in memory.
        newMemory.setRecordTime(time.getText().
                substring(time.getText().length() - 5, time.getText().length()));

        m.saveMemory(newMemory);
    }

    /**
     * This method converts an Image object to BufferedImage.
     *
     * @param im the Image object to be converted to BufferedImage.
     * @return the BufferedImage from the Image object.
     */
    public static BufferedImage imageToBufferedImage(Image im) {
        // Create new BufferedImage object using im (Image) width and height.
        BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Get graphics from the BufferedImage object and return it.
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    /**
     * This method converts an image to a grey image.
     *
     * @param icon the image to be converted to grey.
     * @return the greyscaled image.
     */
    public BufferedImage getGray(Icon icon) {
        // Convert the Icon object to BufferedImage and get its height and width.
        BufferedImage theImage = imageToBufferedImage(((ImageIcon) icon).getImage());
        int width = theImage.getWidth();
        int height = theImage.getHeight();

        // Loop over the pixels of the image and change the RGB values.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(theImage.getRGB(j, i));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);
                Color newColor = new Color(red + green + blue,
                        red + green + blue, red + green + blue);
                theImage.setRGB(j, i, newColor.getRGB());
            }
        }

        // Return the greyscaled image.
        return theImage;
    }

    /**
     * This method mirrors the imgLbl Icon over the Y-Axis.
     *
     * @param icon
     * @return the Icon object mirrored over the Y-Axis.
     */
    public Icon getMirrored(ImageIcon icon) {
        // Get imgLbl Icon as BufferedImage.
        BufferedImage bf = imageToBufferedImage(icon.getImage());

        // Mirror the image.
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-bf.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        // Transforms the BufferedImage object and stores the result in such object.
        bf = op.filter(bf, null);

        // Convert the BufferedImage to ImageIcon and return it.
        ImageIcon newIcon = new ImageIcon(bf);
        return newIcon;
    }

    /**
     * This method inverts the colors of the imgLbl Icon.
     *
     * @param theIcon
     * @return the Icon object with the colors inverted.
     */
    public Icon getInvertedColors(ImageIcon theIcon) {
        // Create new RescaleOp object which is responsible for inverting the colors.
        RescaleOp op = new RescaleOp(-1.0f, 255f, null);
        BufferedImage negative = op.filter(imageToBufferedImage(((ImageIcon) theIcon).getImage()), null); // Rescale the BufferedImage.
        ImageIcon icon = new ImageIcon(negative); // Get result as ImageIcon.
        return icon;
    }

    /**
     * This method converts the imgLbl Icon to black & white.
     *
     * @param theIcon
     * @return the Icon object as black & white.
     */
    public Icon getBlackAndWhite(ImageIcon theIcon) {
        // Get imgLbl Icon as BufferedImage.
        BufferedImage bi = imageToBufferedImage((theIcon).getImage());

        // Create new BufferedImage as black and white.
        BufferedImage result = new BufferedImage(
                bi.getWidth(),
                bi.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        // Use the Graphics2D class to create Graphics, then convert the 
        // BufferedImage to ImageIcon and return it.
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(bi, 0, 0, this);
        g2d.dispose();
        ImageIcon icon = new ImageIcon(result);
        return icon;
    }

    /**
     * This method gets the date based on its distance in days from today.
     *
     * @param daysAgo The number of days ago.
     * @return todate1 The date of daysAgo.
     */
    public static Date getDateFrom(int daysAgo) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String todate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -daysAgo);
        Date todate1 = cal.getTime();
        return todate1;
    }

    /**
     * This method compares two lists and returns if the second list is
     * contained in the first one.
     *
     * @param listOne The first list to be compared.
     * @param listTwo The second list to be compared.
     * @return
     */
    public boolean compareLists(List<String> listOne, List<String> listTwo) {
        int count = 0;

        for (String sOne : listOne) {
            for (String sTwo : listTwo) {
                if (sTwo.equals(sOne)) {
                    count++;
                }
            }
        }

        return count == listTwo.size();

    }

    /**
     * This method populates the Rememba panel.
     *
     * @param memory The memory displayed in the single memory view.
     */
    public void fillRemembaPanel(Memory memory) {
        editMemoryTags = new ArrayList<>();
        memoryTitleLabel3.setText(memory.getTitle());
        memoryDateLabel3.setText(memory.getDate());
        storyText2.setText(memory.getText());
        time2.setText("Record Time: " + memory.getRecordTime());

        // If memory has an image, upload it to imgUploadPanel2.
        if (memory.getImage() != null) {
            ImageIcon imgg = new ImageIcon(getScaledImage(new ImageIcon(memory.getImage()).getImage(), new Dimension(600, 350)));
            currImg2 = imgg;
            originalImg = imgg;
            imgUploadLabel2.setIcon(imgg);
        } // Otherwise, upload the default image to imgUploadPanel2.
        else {
            try {
                imgUploadScaledImg2 = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Buttons\\Upload Image btn\\upload-image-reg.png", new Dimension(200, 250));
                imgUploadImg2 = new ImageIcon(imgUploadScaledImg2);
                imgUploadPanel2.setBackground(b1Reg);
                imgUploadLabel2.setIcon(imgUploadImg2);
                imgUploadPanel2.add(imgUploadLabel2);
                imgUploadPanel2.setToolTipText("Click to Replace Image");
                originalImg = null;
                currImg2 = null;
            } catch (IOException ex) {
                Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // If memory has audio, then set current audio to the audio from the Memory object.
        if (memory.getAudio() != null) {
            currAudio = memory.getAudio();
        }

        // If memory has tags, then add them to the tags panel.
        if ((memory.getTags() != null) && (!memory.getTags().isBlank())) {

            String tagString = memory.getTags();
            String[] tagStringSplit = tagString.split(",");

            // Create as many panels and labels as unique entries
            JLabel[] label = new JLabel[tagStringSplit.length];
            JPanel[] panel = new JPanel[tagStringSplit.length];
            Font tagFont = new Font("Bahnschrift", Font.PLAIN, 12);

            // Add all tags from memory database entry to memory's tags list
            editMemoryTags.addAll(Arrays.asList(tagStringSplit));

            for (int i = 0; i < editMemoryTags.size(); i++) {
                label[i] = new JLabel(editMemoryTags.get(i));
                label[i].setFont(tagFont);
                panel[i] = new JPanel(new FlowLayout());
                panel[i].add(label[i]);
                panel[i].setBackground(b1Reg);
                panel[i].setForeground(textColor);
                panel[i].setBorder(buttonBorder);
                panel[i].setMaximumSize(new Dimension(225, 30));
                tagsListPanel2.add(panel[i]);
                tagsListPanel2.repaint();
                tagsListPanel2.revalidate();
            }
        }
    }

    /**
     * This method sets the whole Rememba panel to editable or not editable.
     *
     * @param bool The boolean variable that determines editable state.
     */
    public void setRemembaEditableOrNot(boolean bool) {
        storyText2.setEnabled(bool);
        imgFilterCombo2.setEnabled(bool);

        // Add mouseListener to recPanel2.
        recPanel2.addMouseListener(new MouseListener() {
            /**
             * This method changes the recPanel2 background color on
             * mouseClicked.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!canStop && currAudio == null && storyText2.isEnabled()) {
                    recPanel2.setBackground(b1Mouseclick);
                }
            }

            /**
             * This method starts capturing audio from user microphone on
             * mousePressed.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (!canStop && currAudio == null && storyText2.isEnabled()) {
                    try {
                        captureAudio();
                        timer = new RecordTimer(time2);
                        timer.start();
                        canStop = true;
                        recPanel2.setBackground(b1Reg);
                    } catch (IllegalArgumentException iae) {
                        JOptionPane.showMessageDialog(null, "There was a problem with your microphone! Please try again.");
                    }
                }
            }

            /**
             * This method is intentionally blank.
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * This method changes the recPanel2 background color on
             * mouseEntered.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!canStop && currAudio == null && storyText2.isEnabled()) {
                    recPanel2.setBackground(b1Mouseover);
                }
            }

            /**
             * This method changes the recPanel2 background color on
             * mouseExited.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                if (!canStop && currAudio == null && storyText2.isEnabled()) {
                    recPanel2.setBackground(b1Reg);
                }
            }
        });

        stopRecPanel2.addMouseListener(new MouseListener() {
            /**
             * This method changes the stopRecPanel2 background color on
             * mouseClicked.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((canStop) && (currAudio == null) && (storyText2.isEnabled()) || playing) {
                    stopRecPanel2.setBackground(b1Mouseclick);
                }
            }

            /**
             * This method stops recording on mousePressed.
             */
            @Override
            public void mousePressed(MouseEvent e) {

                try {
                    if (canStop && currAudio == null && storyText2.isEnabled()) {
                        timer.cancel();
                        canStop = false;
                        running = false;
                        remembaAudio = currAudio;
                        Thread.sleep(1000);
                    }

                    if (playing) {
                        newLine.stop();
                        newLine.drain();
                        newLine.close();
                    }

                    stopRecPanel2.setBackground(b1Reg);
                } catch (InterruptedException ex) {

                }
            }

            /**
             * This method is intentionally blank.
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * This method changes the stopRecPanel2 background color on
             * mouseEntered.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if ((canStop) && (currAudio == null) && (storyText2.isEnabled()) || playing) {
                    stopRecPanel2.setBackground(b1Mouseover);
                }
            }

            /**
             * This method changes the stopRecPanel2 background color on
             * mouseExited.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                if ((canStop) && (currAudio == null) && (storyText2.isEnabled()) || playing) {
                    stopRecPanel2.setBackground(b1Reg);
                }
            }
        });

        playAudioPanel2.addMouseListener(new MouseListener() {
            /**
             * This method changes the playAudioPanel2 background color on
             * mouseClicked.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currAudio != null && storyText2.isEnabled()) {
                    playAudioPanel2.setBackground(b1Mouseclick);
                }
            }

            /**
             * This method plays the memory's audio on mousePressed.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if ((currAudio != null) && (storyText2.isEnabled()) && !playing) {
                    playAudio(currAudio);
                }
            }

            /**
             * This method is intentionally blank.
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * This method changes the playAudioPanel2 background color on
             * mouseEntered.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (currAudio != null && storyText2.isEnabled()) {
                    playAudioPanel2.setBackground(b1Mouseover);
                }
            }

            /**
             * This method changes the playAudioPanel2 background color on
             * mouseExited.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                if (currAudio != null && storyText2.isEnabled()) {
                    playAudioPanel2.setBackground(b1Reg);
                }
            }
        });

        removeAudioPanel2.addMouseListener(new MouseListener() {
            /**
             * This method changes the removeAudioPanel2 background color on
             * mouseClicked.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currAudio != null && storyText2.isEnabled()) {
                    removeAudioPanel2.setBackground(b1Mouseclick);
                }
            }

            /**
             * This method removes the current audio of the memory and sets
             * recording time to default on mousePressed.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (currAudio != null && storyText2.isEnabled()) {
                    currAudio = null;
                    time2.setText("Record Time: 00:00");
                    removeAudioPanel2.setBackground(b1Reg);
                }
            }

            /**
             * This method is intentionally blank.
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * This method changes the removeAudioPanel2 background color on
             * mouseEntered.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (currAudio != null && storyText2.isEnabled()) {
                    removeAudioPanel2.setBackground(b1Mouseover);
                }
            }

            /**
             * This method changes the removeAudioPanel2 background color on
             * mouseExited.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                if (currAudio != null && storyText2.isEnabled()) {
                    removeAudioPanel2.setBackground(b1Reg);
                }
            }
        });

        imgUploadPanel2.addMouseListener(new MouseListener() {
            /**
             * This method is intentionally blank.
             */
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            /**
             * This method uploads a new image to the memory on mousePressed.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "JPG & PNG Images", "jpg", "png");
                    fileChooser.setFileFilter(filter);
                    fileChooser.setDialogTitle("Add an image");
                    int result = fileChooser.showOpenDialog(imgUploadPanel);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        if (fileChooser.getSelectedFile().getPath().toLowerCase().endsWith(".jpg") || fileChooser.getSelectedFile().getPath().toLowerCase().endsWith(".png")) {
                            currImg2 = new ImageIcon(fileChooser.getSelectedFile().getPath());
                            currImg2 = new ImageIcon(getScaledImage(currImg2.getImage(), new Dimension(375, 275)));
                            originalImg = currImg2;
                            currImgPath = fileChooser.getSelectedFile().getPath();
                            imgUploadPanel2.removeAll();
                            imgUploadLabel2 = new JLabel(originalImg);
                            imgUploadPanel2.add(imgUploadLabel2);
                            imgUploadPanel2.revalidate();
                            imgUploadPanel2.repaint();
                            imgFilterCombo2.setEnabled(true);
                        } else {
                            JOptionPane.showMessageDialog(imgUploadPanel2, "Please upload either JPG or PNG image files.");
                        }
                    }
                }
            }

            /**
             * This method is intentionally blank.
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * This method changes the imgUploadPanel2 background color on
             * mouseEntered.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    imgUploadPanel2.setBackground(b1Mouseover);
                }
            }

            /**
             * This method changes the imgUploadPanel2 background color on
             * mouseExited.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    imgUploadPanel2.setBackground(b1Reg);
                }
            }
        });

        removeImgPanel2.addMouseListener(new MouseListener() {
            /**
             * This method changes the removeImgPanel2 background color on
             * mouseClicked.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    removeImgPanel2.setBackground(b2Reg);
                }
            }

            /**
             * This method removes the current image of the memory on
             * mousePressed.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    imgUploadPanel2.removeAll();
                    currImg2 = null;
                    imgFilterCombo2.setSelectedIndex(0);
                    imgFilterCombo2.setEnabled(false);
                    imgUploadImg2 = new ImageIcon(imgUploadScaledImg2);
                    imgUploadLabel2 = new JLabel(imgUploadImg2);
                    imgUploadPanel2.add(imgUploadLabel2);
                    imgUploadPanel2.revalidate();
                }
            }

            /**
             * This method is intentionally blank.
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * This method changes the removeImgPanel2 background color on
             * mouseEntered.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    removeImgPanel2.setBackground(b1Mouseover);
                }
            }

            /**
             * This method changes the removeImgPanel2 background color on
             * mouseExited.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    removeImgPanel2.setBackground(b1Reg);
                }
            }
        });

        addTagsPanel2.addMouseListener(new MouseListener() {
            /**
             * This method changes the addTagsPanel2 background color on
             * mouseClicked.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    addTagsPanel2.setBackground(b2Reg);
                }
            }

            /**
             * This method gets tags from the user on mousePressed.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    getEditTags();
                }
            }

            /**
             * This method is intentionally blank.
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * This method changes the addTagsPanel2 background color on
             * mouseEntered.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    addTagsPanel2.setBackground(b1Mouseover);
                }
            }

            /**
             * This method changes the addTagsPanel2 background color on
             * mouseExited.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                if (storyText2.isEnabled()) {
                    addTagsPanel2.setBackground(b1Reg);
                }
            }
        });

        imgFilterCombo2.addItemListener(new ItemListener() {
            /**
             * This method applies a filter to an image based on the combo box
             * selection.
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED || originalImg == null) {
                    return;
                }

                imgUploadPanel2.removeAll();

                if (imgFilterCombo2.getSelectedItem().equals("None")) {
                    imgUploadLabel2 = new JLabel(originalImg);
                    imgUploadPanel2.add(imgUploadLabel2);

                } else if (imgFilterCombo2.getSelectedItem().equals("Greyscale")) {
                    greyImg = new ImageIcon(getGray(originalImg));
                    imgUploadLabel2 = new JLabel(greyImg);
                    imgUploadPanel2.add(imgUploadLabel2);

                } else if (imgFilterCombo2.getSelectedItem().equals("Mirror Image")) {
                    mirrorImg = (ImageIcon) getMirrored(originalImg);
                    imgUploadLabel2 = new JLabel(mirrorImg);
                    imgUploadPanel2.add(imgUploadLabel2);
                } else if (imgFilterCombo2.getSelectedItem().equals("Invert Colors")) {
                    invertImg = (ImageIcon) getInvertedColors(originalImg);
                    imgUploadLabel2 = new JLabel(invertImg);
                    imgUploadPanel2.add(imgUploadLabel2);
                } else if (imgFilterCombo2.getSelectedItem().equals("Black & White")) {
                    bwImg = (ImageIcon) getBlackAndWhite(originalImg);
                    imgUploadLabel2 = new JLabel(bwImg);
                    imgUploadPanel2.add(imgUploadLabel2);
                }

                imgUploadPanel2.repaint();
                imgUploadPanel2.revalidate();
            }
        });
    }

    /**
     * This method gets a path folder from user and returns it to caller.
     *
     * @return The path to the folder chosen by user.
     */
    public String getFolderPathFromUser() {
        JFileChooser fChooser = new JFileChooser();
        fChooser.setCurrentDirectory(new java.io.File("."));
        fChooser.setDialogTitle("Select Folder");
        fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fChooser.setAcceptAllFileFilterUsed(false);
        int result = fChooser.showOpenDialog(null);
        if (JFileChooser.OPEN_DIALOG == result) {
            return fChooser.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * This method creates a directory in the given path.
     *
     * @param path The path used to create the directory.
     * @param type The type of the PDF creation.
     */
    public void createDirectory(String path, String type) {
        int counter = 0;
        // if the path is not empty.
        if (!path.isEmpty()) {
            File file = new File(path);
            if (file.exists()) {
                String fileName = JOptionPane.showInputDialog(null, "Enter folder name where PDFs will be stored:", "Membook");
                if (fileName != null) {
                    // create file in directory.
                    File f = new File(path + "\\" + fileName);
                    boolean mkdir = f.mkdir();

                    // if only certain selected memories should be exported to PDF.
                    if (type.equals("selected")) {
                        for (GalleryPanel gp : selectedPanels) {
                            PDFCreator pdf = new PDFCreator(path + "\\" + fileName, gp.getMemoryAssociated(), getFormat());
                            counter += pdf.getSavedPDFs();
                        }
                    } // if all memories should be exported to PDF.
                    else if (type.equals("all")) {
                        for (Memory mem : currentGalleryMemory) {
                            PDFCreator pdf = new PDFCreator(path + "\\" + fileName, mem, getFormat());
                            counter += pdf.getSavedPDFs();
                        }
                    }

                    if (counter >= 1) {
                        String message = "Memory was";
                        if (counter > 1) {
                            message = "Memories were";
                        }
                        
                        // let user know that memory export to PDF was successful.
                        ImageIcon icon = new ImageIcon("CSC2620 - Final Project files\\Vector Art\\Graphics\\save-memory-check-mark.png");
                        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaled);
                        JOptionPane.showMessageDialog(null, message + " successfully exported to PDF!",
                                "Memory Exporting Confirmation", JOptionPane.INFORMATION_MESSAGE, scaledIcon);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "The folder name you entered is not avilable!", "Nonexistent Folder Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * This method repaints all panels based on user color choices in Customize
     * Appearance.
     */
    private void repaintPanels() {
        cards.setBackground(b1Reg);
        cards.repaint();
        cards.revalidate();
        mainOptionsCard.setBackground(b1Reg);
        mainOptionsCard.repaint();
        mainOptionsCard.revalidate();
        newMemoryPanel.setBackground(b1Reg);
        newMemoryPanel.repaint();
        newMemoryPanel.revalidate();
        exploreMemoriesPanel.setBackground(b1Reg);
        exploreMemoriesPanel.repaint();
        exploreMemoriesPanel.revalidate();
        shareMemoriesPanel.setBackground(b1Reg);
        shareMemoriesPanel.repaint();
        shareMemoriesPanel.revalidate();
        customizeAppearancePanel.setBackground(b1Reg);
        customizeAppearancePanel.repaint();
        customizeAppearancePanel.revalidate();
        newMemoryCard1.setBackground(b1Reg);
        newMemoryCard1.repaint();
        newMemoryCard1.revalidate();
        newMemoryCard2.setBackground(b1Reg);
        newMemoryCard2.repaint();
        newMemoryCard2.revalidate();
        newMemTitlePanel.setBackground(b1Reg);
        newMemTitlePanel.repaint();
        newMemTitlePanel.revalidate();
        newMemDatePanel.setBackground(b1Reg);
        newMemDatePanel.repaint();
        newMemDatePanel.revalidate();
        newMemLocPanel.setBackground(b1Reg);
        newMemLocPanel.repaint();
        newMemLocPanel.revalidate();
        cancelPanel.setBackground(b1Reg);
        cancelPanel.repaint();
        cancelPanel.revalidate();
        nextPanel.setBackground(b1Reg);
        nextPanel.repaint();
        nextPanel.revalidate();
        cancelNextPanel.setBackground(b1Reg);
        cancelNextPanel.repaint();
        cancelNextPanel.revalidate();
        newMem2Header.setBackground(b1Reg);
        newMem2Header.repaint();
        newMem2Header.revalidate();
        addAudioPanel.setBackground(b1Reg);
        addAudioPanel.repaint();
        addAudioPanel.revalidate();
        imgAudioTagsUploadPanel.setBackground(b1Reg);
        imgAudioTagsUploadPanel.repaint();
        imgAudioTagsUploadPanel.revalidate();
        addTagsPanel.setBackground(b1Reg);
        addTagsPanel.repaint();
        addTagsPanel.revalidate();
        storyTextPanel.setBackground(b1Reg);
        storyTextPanel.repaint();
        storyTextPanel.revalidate();
        prevCreatePanel.setBackground(b1Reg);
        prevCreatePanel.repaint();
        prevCreatePanel.revalidate();
        prevPanel.setBackground(b1Reg);
        prevPanel.repaint();
        prevPanel.revalidate();
        createPanel.setBackground(b1Reg);
        createPanel.repaint();
        createPanel.revalidate();
        recPanel.setBackground(b1Reg);
        recPanel.repaint();
        recPanel.revalidate();
        stopRecPanel.setBackground(b1Reg);
        stopRecPanel.repaint();
        stopRecPanel.revalidate();
        playAudioPanel.setBackground(b1Reg);
        playAudioPanel.repaint();
        playAudioPanel.revalidate();
        removeAudioPanel.setBackground(b1Reg);
        removeAudioPanel.repaint();
        removeAudioPanel.revalidate();
        tagsPanel.setBackground(b1Reg);
        tagsPanel.repaint();
        tagsPanel.revalidate();
        tagsListPanel.setBackground(b1Reg);
        tagsListPanel.repaint();
        tagsListPanel.revalidate();
        imgUploadPanel.setBackground(b1Reg);
        imgUploadPanel.repaint();
        imgUploadPanel.revalidate();
        expMemCard1.setBackground(b1Reg);
        expMemCard1.repaint();
        expMemCard1.revalidate();
        expMemCornerPanel.setBackground(b1Reg);
        expMemCornerPanel.repaint();
        expMemCornerPanel.revalidate();
        membaCortexPanel.setBackground(b1Reg);
        membaCortexPanel.repaint();
        membaCortexPanel.revalidate();
        cornerPanel.setBackground(b1Reg);
        cornerPanel.repaint();
        cornerPanel.revalidate();
        expFilterPanel.setBackground(b1Reg);
        expFilterPanel.repaint();
        expFilterPanel.revalidate();
        expAddTagsPanel.setBackground(b1Reg);
        expAddTagsPanel.repaint();
        expAddTagsPanel.revalidate();
        imgFilterPanel.setBackground(b1Reg);
        imgFilterPanel.repaint();
        imgFilterPanel.revalidate();
        filterPanel.setBackground(b1Reg);
        filterPanel.repaint();
        filterPanel.revalidate();
        removeImgPanel.setBackground(b1Reg);
        removeImgPanel.repaint();
        removeImgPanel.revalidate();
        datePanel.setBackground(b1Reg);
        datePanel.repaint();
        datePanel.revalidate();
        filterTagsPanel.setBackground(b1Reg);
        filterTagsPanel.repaint();
        filterTagsPanel.revalidate();
        searchTagsListPanel.setBackground(b1Reg);
        searchTagsListPanel.repaint();
        searchTagsListPanel.revalidate();
        memoryGalleryPanel.setBackground(b1Reg);
        memoryGalleryPanel.repaint();
        memoryGalleryPanel.revalidate();
        prevDelselRemPanel.setBackground(b1Reg);
        prevDelselRemPanel.repaint();
        prevDelselRemPanel.revalidate();
        prevPanel2.setBackground(b1Reg);
        prevPanel2.repaint();
        prevPanel2.revalidate();
        deleteSelected.setBackground(b1Reg);
        deleteSelected.repaint();
        deleteSelected.revalidate();
        remembaPanel.setBackground(b1Reg);
        remembaPanel.repaint();
        remembaPanel.revalidate();
        expMemCard2.setBackground(b1Reg);
        expMemCard2.repaint();
        expMemCard2.revalidate();
        expMem2Header.setBackground(b1Reg);
        expMem2Header.repaint();
        expMem2Header.revalidate();
        imgAudioTagsUploadPanel2.setBackground(b1Reg);
        imgAudioTagsUploadPanel2.repaint();
        imgAudioTagsUploadPanel2.revalidate();
        addAudioPanel2.setBackground(b1Reg);
        addAudioPanel2.repaint();
        addAudioPanel2.revalidate();
        recPanel2.setBackground(b1Reg);
        recPanel2.repaint();
        recPanel2.revalidate();
        stopRecPanel2.setBackground(b1Reg);
        stopRecPanel2.repaint();
        stopRecPanel2.revalidate();
        playAudioPanel2.setBackground(b1Reg);
        playAudioPanel2.repaint();
        playAudioPanel2.revalidate();
        removeAudioPanel2.setBackground(b1Reg);
        removeAudioPanel2.repaint();
        removeAudioPanel2.revalidate();
        imgUploadPanel2.setBackground(b1Reg);
        imgUploadPanel2.repaint();
        imgUploadPanel2.revalidate();
        imgFilterPanel2.setBackground(b1Reg);
        imgFilterPanel2.repaint();
        imgFilterPanel2.revalidate();
        filterPanel2.setBackground(b1Reg);
        filterPanel2.repaint();
        filterPanel2.revalidate();
        removeImgPanel2.setBackground(b1Reg);
        removeImgPanel2.repaint();
        removeImgPanel2.revalidate();
        tagsPanel2.setBackground(b1Reg);
        tagsPanel2.repaint();
        tagsPanel2.revalidate();
        addTagsPanel2.setBackground(b1Reg);
        addTagsPanel2.repaint();
        addTagsPanel2.revalidate();
        tagsListPanel2.setBackground(b1Reg);
        tagsListPanel2.repaint();
        tagsListPanel2.revalidate();
        storyTextPanel2.setBackground(b1Reg);
        storyTextPanel2.repaint();
        storyTextPanel2.revalidate();
        prevEditSavePanel.setBackground(b1Reg);
        prevEditSavePanel.repaint();
        prevEditSavePanel.revalidate();
        prevPanel3.setBackground(b1Reg);
        prevPanel3.repaint();
        prevPanel3.revalidate();
        editPanel.setBackground(b1Reg);
        editPanel.repaint();
        editPanel.revalidate();
        editCards.setBackground(b1Reg);
        editCards.repaint();
        editCards.revalidate();
        delSavePanel.setBackground(b1Reg);
        delSavePanel.repaint();
        delSavePanel.revalidate();
        delMemPanel.setBackground(b1Reg);
        delMemPanel.repaint();
        delMemPanel.revalidate();
        savePanel.setBackground(b1Reg);
        savePanel.repaint();
        savePanel.revalidate();
        naviCards.setBackground(b1Reg);
        naviCards.repaint();
        naviCards.revalidate();
        naviCard2.setBackground(b1Reg);
        naviCard2.repaint();
        naviCard2.revalidate();
        prevPanel4.setBackground(b1Reg);
        prevPanel4.repaint();
        prevPanel4.revalidate();
        exportSelPanel.setBackground(b1Reg);
        exportSelPanel.repaint();
        exportSelPanel.revalidate();
        exportAllPanel.setBackground(b1Reg);
        exportAllPanel.repaint();
        exportAllPanel.revalidate();
        customizeApp.setBackground(b1Reg);
        customizeApp.repaint();
        customizeApp.revalidate();
        appearanceSettings.setBackground(b1Reg);
        appearanceSettings.repaint();
        appearanceSettings.revalidate();
        appearanceLabelPanel.setBackground(b1Reg);
        appearanceLabelPanel.repaint();
        appearanceLabelPanel.revalidate();
        appearanceComboPanel.setBackground(b1Reg);
        appearanceComboPanel.repaint();
        appearanceComboPanel.revalidate();
        themePresetsPanel.setBackground(b1Reg);
        themePresetsPanel.repaint();
        themePresetsPanel.revalidate();
        backgroundColor1Panel.setBackground(b1Reg);
        backgroundColor1Panel.repaint();
        backgroundColor1Panel.revalidate();
        backgroundColor2Panel.setBackground(b1Reg);
        backgroundColor2Panel.repaint();
        backgroundColor2Panel.revalidate();
        textColorPanel.setBackground(b1Reg);
        textColorPanel.repaint();
        textColorPanel.revalidate();
        borderColorPanel.setBackground(b1Reg);
        borderColorPanel.repaint();
        borderColorPanel.revalidate();
        borderStylePanel.setBackground(b1Reg);
        borderStylePanel.repaint();
        borderStylePanel.revalidate();
        prevDefaultConfirmPanel.setBackground(b1Reg);
        prevDefaultConfirmPanel.repaint();
        prevDefaultConfirmPanel.revalidate();
        prevPanel5.setBackground(b1Reg);
        prevPanel5.repaint();
        prevPanel5.revalidate();
        restDefaultPanel.setBackground(b1Reg);
        restDefaultPanel.repaint();
        restDefaultPanel.revalidate();
        confirmPanel.setBackground(b1Reg);
        confirmPanel.repaint();
        confirmPanel.revalidate();
        themePresetComboPanel.setBackground(b1Reg);
        themePresetComboPanel.repaint();
        themePresetComboPanel.revalidate();
        backgroundColor1ComboPanel.setBackground(b1Reg);
        backgroundColor1ComboPanel.repaint();
        backgroundColor1ComboPanel.revalidate();
        backgroundColor2ComboPanel.setBackground(b1Reg);
        backgroundColor2ComboPanel.repaint();
        backgroundColor2ComboPanel.revalidate();
        textColorComboPanel.setBackground(b1Reg);
        textColorComboPanel.repaint();
        textColorComboPanel.revalidate();
        borderColorComboPanel.setBackground(b1Reg);
        borderColorComboPanel.repaint();
        borderColorComboPanel.revalidate();
        borderStyleComboPanel.setBackground(b1Reg);
        borderStyleComboPanel.repaint();
        borderStyleComboPanel.revalidate();
        membaLogoPanel.setBackground(b2Reg);
        membaLogoPanel.repaint();
        membaLogoPanel.revalidate();
        expMemCard1Header.setBackground(b3Reg);
        expMemCard1Header.repaint();
        expMemCard1Header.revalidate();
        expMemCornerImgLabel.setForeground(b3Reg);
        expMemCornerImgLabel.repaint();
        expMemCornerImgLabel.revalidate();
        expMemCornerPanel.setBackground(b3Reg);
        expMemCornerPanel.repaint();
        expMemCornerPanel.revalidate();
        membaCortexPanel.setBackground(b3Reg);
        membaCortexPanel.repaint();
        membaCortexPanel.revalidate();
        cornerPanel.setBackground(b3Reg);
        cornerPanel.repaint();
        cornerPanel.revalidate();
        cornerLabel.setForeground(b3Reg);
        cornerLabel.repaint();
        cornerLabel.revalidate();
        customAppPanel.setBackground(b3Reg);
        customAppPanel.repaint();
        customAppPanel.revalidate();
        memoryTitleTextfield.setForeground(textColor);
        memoryTitleTextfield.repaint();
        memoryTitleTextfield.revalidate();
        chooser.setForeground(textColor);
        for (Component cm : chooser.getComponents()) {
            ((JComponent) cm).setBackground(Color.white);
            ((JComponent) cm).setForeground(textColor);
        }
        chooser.repaint();
        chooser.revalidate();
        memoryLocTextfield.setForeground(textColor);
        memoryLocTextfield.repaint();
        memoryLocTextfield.revalidate();
        memoryTitleLabel2.setForeground(textColor);
        memoryTitleLabel2.repaint();
        memoryTitleLabel2.revalidate();
        memoryDateLabel2.setForeground(textColor);
        memoryDateLabel2.repaint();
        memoryDateLabel2.revalidate();
        time.setForeground(textColor);
        time.setBackground(b1Reg);
        time.repaint();
        time.revalidate();
        filterLabel.setForeground(textColor);
        filterLabel.repaint();
        filterLabel.revalidate();
        imgFilterCombo.setForeground(textColor);
        imgFilterCombo.setBackground(b1Reg);
        imgFilterCombo.repaint();
        imgFilterCombo.revalidate();
        storyText.setForeground(textColor);
        storyText.repaint();
        storyText.revalidate();
        dateLabel.setForeground(textColor);
        dateLabel.repaint();
        dateLabel.revalidate();
        dateFilterCombo.setForeground(textColor);
        dateFilterCombo.setBackground(b1Reg);
        dateFilterCombo.repaint();
        dateFilterCombo.revalidate();
        memoryTitleLabel3.setForeground(textColor);
        memoryTitleLabel3.repaint();
        memoryTitleLabel3.revalidate();
        memoryDateLabel3.setForeground(textColor);
        memoryDateLabel3.repaint();
        memoryDateLabel3.revalidate();
        expMem2.setForeground(b1Reg);
        expMem2.repaint();
        expMem2.revalidate();
        time2.setForeground(textColor);
        time2.setBackground(b1Reg);
        time2.repaint();
        time2.revalidate();
        filterLabel2.setForeground(textColor);
        filterLabel2.repaint();
        filterLabel2.revalidate();
        imgFilterCombo2.setForeground(textColor);
        imgFilterCombo2.setBackground(b1Reg);
        imgFilterCombo2.repaint();
        imgFilterCombo2.revalidate();
        storyText2.setForeground(textColor);
        storyText2.repaint();
        storyText2.revalidate();
        themePresetsLabel.setForeground(textColor);
        themePresetsLabel.repaint();
        themePresetsLabel.revalidate();
        backgroundColor1Label.setForeground(textColor);
        backgroundColor1Label.repaint();
        backgroundColor1Label.revalidate();
        backgroundColor2Label.setForeground(textColor);
        backgroundColor2Label.repaint();
        backgroundColor2Label.revalidate();
        textColorLabel.setForeground(textColor);
        textColorLabel.repaint();
        textColorLabel.revalidate();
        borderColorLabel.setForeground(textColor);
        borderColorLabel.repaint();
        borderColorLabel.revalidate();
        borderStyleLabel.setForeground(textColor);
        borderStyleLabel.repaint();
        borderStyleLabel.revalidate();
        themePresetCombo.setForeground(textColor);
        themePresetCombo.repaint();
        themePresetCombo.revalidate();
        backgroundColor1Combo.setForeground(textColor);
        backgroundColor1Combo.repaint();
        backgroundColor1Combo.revalidate();
        backgroundColor2Combo.setForeground(textColor);
        backgroundColor2Combo.repaint();
        backgroundColor2Combo.revalidate();
        textColorCombo.setForeground(textColor);
        textColorCombo.repaint();
        textColorCombo.revalidate();
        borderColorCombo.setForeground(textColor);
        borderColorCombo.repaint();
        borderColorCombo.revalidate();
        borderStyleCombo.setForeground(textColor);
        borderStyleCombo.repaint();
        borderStyleCombo.revalidate();
    }

    /**
     * This method creates a mouse-over color based on user's primary background
     * color choice.
     */
    private void mouseoverColorPicker() {
        int r, g, b;

        // Take b1Reg RGB and make it closer to white.
        r = b1Reg.getRed() + 30;
        g = b1Reg.getGreen() + 30;
        b = b1Reg.getBlue() + 30;

        // Set max RGB values.
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }

        // If the resulting color is white, change mouseover to grey.
        if (r == 255 && b == 255 && g == 255) {
            r = 200;
            b = 200;
            g = 200;
        }

        // Define new mouseover color.
        b1Mouseover = new Color(r, g, b);
    }

    /**
     * This method creates a new header2 color based on user's two choices of
     * background colors.
     */
    private void header2ColorPicker() {
        int avgR, avgG, avgB;

        // Take average of two background colors to find middle color.
        avgR = (b1Reg.getRed() + b2Reg.getRed()) / 2;
        avgG = (b1Reg.getGreen() + b2Reg.getGreen()) / 2;
        avgB = (b1Reg.getBlue() + b2Reg.getBlue()) / 2;

        // Create new b3Reg.
        b3Reg = new Color(avgR, avgG, avgB);
    }

    /**
     * This method refreshes all component borders based on user's preferences
     * in Customize Appearance.
     */
    private void redrawBorders() {
        // Set line borders.
        if (borderStyleCombo.getSelectedItem().toString().matches("Line Borders")) {
            buttonBorder = BorderFactory.createLineBorder(b2Reg);
            panelBorder = BorderFactory.createLineBorder(b2Reg);
            textfieldBorder = BorderFactory.createLineBorder(b2Reg);
        }

        // Set beveled borders.
        if (borderStyleCombo.getSelectedItem().toString().matches("Beveled Borders")) {
            buttonBorder = BorderFactory.createBevelBorder(1, b2Reg, b1Mouseover);
            panelBorder = BorderFactory.createBevelBorder(0, b2Reg, b1Mouseover);
            textfieldBorder = BorderFactory.createBevelBorder(0, b2Reg, b1Mouseover);
        }

        // Set soft beveled borders.
        if (borderStyleCombo.getSelectedItem().toString().matches("Soft Beveled Borders")) {
            buttonBorder = BorderFactory.createSoftBevelBorder(1, b2Reg, b1Mouseover);
            panelBorder = BorderFactory.createSoftBevelBorder(0, b2Reg, b1Mouseover);
            textfieldBorder = BorderFactory.createSoftBevelBorder(0, b2Reg, b1Mouseover);
        }

        // Set etched borders.
        if (borderStyleCombo.getSelectedItem().toString().matches("Etched Borders")) {
            buttonBorder = BorderFactory.createEtchedBorder(1, b2Reg, b1Mouseover);
            panelBorder = BorderFactory.createEtchedBorder(0, b2Reg, b1Mouseover);
            textfieldBorder = BorderFactory.createEtchedBorder(0, b2Reg, b1Mouseover);
        }

        // Refresh all component borders.
        newMemoryPanel.setBorder(buttonBorder);
        newMemoryPanel.repaint();
        newMemoryPanel.revalidate();
        exploreMemoriesPanel.setBorder(buttonBorder);
        exploreMemoriesPanel.repaint();
        exploreMemoriesPanel.revalidate();
        shareMemoriesPanel.setBorder(buttonBorder);
        shareMemoriesPanel.repaint();
        shareMemoriesPanel.revalidate();
        customizeAppearancePanel.setBorder(buttonBorder);
        customizeAppearancePanel.repaint();
        customizeAppearancePanel.revalidate();
        newMemTitlePanel.setBorder(panelBorder);
        newMemTitlePanel.repaint();
        newMemTitlePanel.revalidate();
        newMemDatePanel.setBorder(panelBorder);
        newMemDatePanel.repaint();
        newMemDatePanel.revalidate();
        newMemLocPanel.setBorder(panelBorder);
        newMemLocPanel.repaint();
        newMemLocPanel.revalidate();
        cancelPanel.setBorder(buttonBorder);
        cancelPanel.repaint();
        cancelPanel.revalidate();
        nextPanel.setBorder(buttonBorder);
        nextPanel.repaint();
        nextPanel.revalidate();
        newMem2Header.setBorder(panelBorder);
        newMem2Header.repaint();
        newMem2Header.revalidate();
        imgAudioTagsUploadPanel.setBorder(panelBorder);
        imgAudioTagsUploadPanel.repaint();
        imgAudioTagsUploadPanel.revalidate();
        addTagsPanel.setBorder(buttonBorder);
        addTagsPanel.repaint();
        addTagsPanel.revalidate();
        storyTextPanel.setBorder(textfieldBorder);
        storyTextPanel.repaint();
        storyTextPanel.revalidate();
        prevPanel.setBorder(buttonBorder);
        prevPanel.repaint();
        prevPanel.revalidate();
        createPanel.setBorder(buttonBorder);
        createPanel.repaint();
        createPanel.revalidate();
        recPanel.setBorder(buttonBorder);
        recPanel.repaint();
        recPanel.revalidate();
        stopRecPanel.setBorder(buttonBorder);
        stopRecPanel.repaint();
        stopRecPanel.revalidate();
        playAudioPanel.setBorder(buttonBorder);
        playAudioPanel.repaint();
        playAudioPanel.revalidate();
        removeAudioPanel.setBorder(buttonBorder);
        removeAudioPanel.repaint();
        removeAudioPanel.revalidate();
        tagsListPanel.setBorder(panelBorder);
        tagsListPanel.repaint();
        tagsListPanel.revalidate();
        imgUploadPanel.setBorder(buttonBorder);
        imgUploadPanel.repaint();
        imgUploadPanel.revalidate();
        expFilterPanel.setBorder(panelBorder);
        expFilterPanel.repaint();
        expFilterPanel.revalidate();
        filterPanel.setBorder(panelBorder);
        filterPanel.repaint();
        filterPanel.revalidate();
        removeImgPanel.setBorder(panelBorder);
        removeImgPanel.repaint();
        removeImgPanel.revalidate();
        searchTagsListPanel.setBorder(panelBorder);
        searchTagsListPanel.repaint();
        searchTagsListPanel.revalidate();
        memoryGalleryPanel.setBorder(panelBorder);
        memoryGalleryPanel.repaint();
        memoryGalleryPanel.revalidate();
        prevDelselRemPanel.setBorder(panelBorder);
        prevDelselRemPanel.repaint();
        prevDelselRemPanel.revalidate();
        prevPanel2.setBorder(buttonBorder);
        prevPanel2.repaint();
        prevPanel2.revalidate();
        deleteSelected.setBorder(buttonBorder);
        deleteSelected.repaint();
        deleteSelected.revalidate();
        remembaPanel.setBorder(buttonBorder);
        remembaPanel.repaint();
        remembaPanel.revalidate();
        expMem2Header.setBorder(panelBorder);
        expMem2Header.repaint();
        expMem2Header.revalidate();
        imgAudioTagsUploadPanel2.setBorder(panelBorder);
        imgAudioTagsUploadPanel2.repaint();
        imgAudioTagsUploadPanel2.revalidate();
        recPanel2.setBorder(buttonBorder);
        recPanel2.repaint();
        recPanel2.revalidate();
        stopRecPanel2.setBorder(buttonBorder);
        stopRecPanel2.repaint();
        stopRecPanel2.revalidate();
        playAudioPanel2.setBorder(buttonBorder);
        playAudioPanel2.repaint();
        playAudioPanel2.revalidate();
        removeAudioPanel2.setBorder(buttonBorder);
        removeAudioPanel2.repaint();
        removeAudioPanel2.revalidate();
        imgUploadPanel2.setBorder(panelBorder);
        imgUploadPanel2.repaint();
        imgUploadPanel2.revalidate();
        filterPanel2.setBorder(panelBorder);
        filterPanel2.repaint();
        filterPanel2.revalidate();
        removeImgPanel2.setBorder(buttonBorder);
        removeImgPanel2.repaint();
        removeImgPanel2.revalidate();
        addTagsPanel2.setBorder(buttonBorder);
        addTagsPanel2.repaint();
        addTagsPanel2.revalidate();
        tagsListPanel2.setBorder(panelBorder);
        tagsListPanel2.repaint();
        tagsListPanel2.revalidate();
        storyTextPanel2.setBorder(textfieldBorder);
        storyTextPanel2.repaint();
        storyTextPanel2.revalidate();
        prevPanel3.setBorder(buttonBorder);
        prevPanel3.repaint();
        prevPanel3.revalidate();
        editPanel.setBorder(buttonBorder);
        editPanel.repaint();
        editPanel.revalidate();
        delMemPanel.setBorder(buttonBorder);
        delMemPanel.repaint();
        delMemPanel.revalidate();
        savePanel.setBorder(buttonBorder);
        savePanel.repaint();
        savePanel.revalidate();
        prevPanel4.setBorder(buttonBorder);
        prevPanel4.repaint();
        prevPanel4.revalidate();
        exportSelPanel.setBorder(buttonBorder);
        exportSelPanel.repaint();
        exportSelPanel.revalidate();
        exportAllPanel.setBorder(buttonBorder);
        exportAllPanel.repaint();
        exportAllPanel.revalidate();
        appearanceSettings.setBorder(panelBorder);
        appearanceSettings.repaint();
        appearanceSettings.revalidate();
        prevPanel5.setBorder(buttonBorder);
        prevPanel5.repaint();
        prevPanel5.revalidate();
        restDefaultPanel.setBorder(buttonBorder);
        restDefaultPanel.repaint();
        restDefaultPanel.revalidate();
        confirmPanel.setBorder(buttonBorder);
        confirmPanel.repaint();
        confirmPanel.revalidate();
        membaLogoPanel.setBorder(BorderFactory.createBevelBorder(0));
        membaLogoPanel.repaint();
        membaLogoPanel.revalidate();
        expMemCard1Header.setBorder(panelBorder);
        expMemCard1Header.repaint();
        expMemCard1Header.revalidate();
        customAppPanel.setBorder(panelBorder);
        customAppPanel.repaint();
        customAppPanel.revalidate();
        memoryTitleTextfield.setBorder(textfieldBorder);
        memoryTitleTextfield.repaint();
        memoryTitleTextfield.revalidate();
        memoryLocTextfield.setBorder(textfieldBorder);
        memoryLocTextfield.repaint();
        memoryLocTextfield.revalidate();
        time.setBorder(buttonBorder);
        time.repaint();
        time.revalidate();
        time2.setBorder(buttonBorder);
        time2.repaint();
        time2.revalidate();
        expAddTagsPanel.setBorder(buttonBorder);
        expAddTagsPanel.repaint();
        expAddTagsPanel.revalidate();
    }
}
