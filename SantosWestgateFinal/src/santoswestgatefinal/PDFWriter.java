package santoswestgatefinal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;

/**
 * This method writes directly to a new PDF document. Inspired by:
 * https://github.com/amdegregorio/PDFBox-Example
 *
 * @author Bernardo Santos & Allen Westgate
 */
public class PDFWriter {

    private String pdfOutputDirectory = ""; // the directory in which the PDF will be saved at.
    private String pdfFileName = ""; // the name of the PDF file.
    private PDDocument doc = null; // the document object.
    private PDFont font = null; // the font used in the PDF.

    /**
     * The constructor.
     *
     * @param pdfOutputDirectory the directory in which the PDF will be saved
     * at.
     * @param pdfFileName the name of the PDF file.
     */
    public PDFWriter(String pdfOutputDirectory, String pdfFileName) {
        this.pdfOutputDirectory = pdfOutputDirectory;

        // add the extensions to the strings if they do not have it.
        if (!this.pdfOutputDirectory.endsWith("/")) {
            this.pdfOutputDirectory += "/";
        }
        if (!pdfFileName.endsWith(".pdf")) {
            pdfFileName = pdfFileName + ".pdf";
        }
        this.pdfFileName = pdfFileName;
    }

    /**
     * This method creates a PDF document and sets the font to Helvetica.
     */
    public void createPdfFile() {
        doc = new PDDocument();
        font = PDType1Font.HELVETICA;
    }

    /**
     * This method adds a page to the PDF document recnetly created.
     *
     * @param pageHeader the header of the page.
     * @param pageText the text of the page.
     * @param image the image of the page.
     * @param audio the audio associated to the memory.
     * @return true if page could be added, false otherwise.
     */
    public boolean addPage(String pageHeader, StringBuffer pageText, byte[] image, byte[] audio) {
        try {

            // create a new page of the PDF document.
            boolean ok = false;
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream contents = null; // the contents of the page.

            // set the font size, the positioning of the text and the offset.
            float fontSize = 12;
            float leading = 1.5f * fontSize;
            PDRectangle mediabox = page.getMediaBox();
            float margin = 75;
            float width = mediabox.getWidth() - 2 * margin;
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin - 100;
            float yOffset = startY;

            // get Memba logo and convert it to byte array.
            Image membaLogoScaledImg = resizeGraphic("CSC2620 - Final Project files\\Vector Art\\Graphics\\memba-logo-reg.png", new Dimension(321, 75));
            BufferedImage bImage = new BufferedImage(membaLogoScaledImg.getWidth(null), membaLogoScaledImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bImage.createGraphics();
            g2.drawImage(membaLogoScaledImg, 0, 0, null);
            g2.dispose();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            byte[] data = bos.toByteArray();

            // create a PDImageXObject containing the Memba logo.
            PDImageXObject mimg = PDImageXObject.createFromByteArray(doc, data, pdfFileName);

            try {

                // initialize the contents stream of the PDF.
                contents = new PDPageContentStream(doc, page);

                // draw the Memba logo to the top of the PDF.
                contents.drawImage(mimg, startX + 70, startY + 100, 321, 75);
                startY += 80;

                // begin text and set the font of the contents stream.
                contents.beginText();
                contents.setFont(font, 14);

                // set new line start.
                contents.newLineAtOffset(startX, startY);
                yOffset -= leading;

                // add text to the PDF document.
                contents.showText(pageHeader);
                contents.newLineAtOffset(0, -leading);
                yOffset -= leading;

                // parse the individual lines so that the text is rightly 
                // displayed in several lines.
                List<String> lines = new ArrayList<>();
                parseIndividualLines(pageText, lines, fontSize, font, width);
                contents.setFont(font, fontSize);

                // for each line in lines String, add it to the PDF page.
                for (String line : lines) {
                    contents.showText(line);
                    contents.newLineAtOffset(0, -leading);
                    yOffset -= leading;

                    if (yOffset <= 0) {
                        contents.endText();
                        try {
                            if (contents != null) {
                                contents.close();
                            }
                        } catch (IOException e) {
                            ok = false;
                            e.getMessage();
                        }
                        page = new PDPage();
                        doc.addPage(page);
                        contents = new PDPageContentStream(doc, page);
                        contents.beginText();
                        contents.setFont(font, fontSize);
                        yOffset = startY;
                        contents.newLineAtOffset(startX, startY);
                    }
                }

                // contents stream end.
                contents.endText();

                // set the scale used to draw the image.
                float scale = 1f;

                // if the memory has an image, draw it to the PDF document.
                if (image != null) {
                    PDImageXObject pdImage = PDImageXObject.createFromByteArray(doc, image, pdfFileName);
                    scale = width / pdImage.getWidth();
                    yOffset -= (225);

                    // if image cannot be drawn in the page, create a new page
                    // and draw it there.
                    if (yOffset <= 0) {
                        try {
                            if (contents != null) {
                                contents.close();
                            }
                        } catch (IOException e) {
                            ok = false;
                            e.getMessage();
                        }
                        page = new PDPage();
                        doc.addPage(page);
                        contents = new PDPageContentStream(doc, page);
                        yOffset = startY - (pdImage.getHeight() * scale);
                    }

                    // scale image down.
                    float pdImageHeight, pdImageWidth;
                    pdImageHeight = pdImage.getHeight();
                    pdImageWidth = pdImage.getWidth();
                    while (pdImageHeight > 225 && pdImageWidth > 200) {
                        pdImageHeight = (float)(pdImageHeight * 0.95);
                        pdImageWidth = (float)(pdImageWidth * 0.95);
                    }
                    
                    // draw image.
                    contents.drawImage(pdImage, startX + (462 - pdImageWidth)/2, yOffset, pdImageWidth, pdImageHeight);
                }

                // if memory has audio, add a link in the PDF that opens the 
                // audio file.
                if (audio != null) {
                    final PDAnnotationLink txtLink = new PDAnnotationLink();

                    // set border style
                    final PDBorderStyleDictionary linkBorder = new PDBorderStyleDictionary();
                    linkBorder.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE);
                    linkBorder.setWidth(10);
                    txtLink.setBorderStyle(linkBorder);

                    // set order color
                    final Color color = Color.GREEN;
                    final float[] components = new float[]{color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f};
                    txtLink.setColor(new PDColor(components, PDDeviceRGB.INSTANCE));

                    // set destination URI.
                    final PDActionURI action = new PDActionURI();
                    action.setURI(pdfOutputDirectory + pdfFileName.replace(".pdf", ".wav"));
                    txtLink.setAction(action);

                    // position of the link.
                    final PDRectangle position = new PDRectangle();
                    position.setLowerLeftX(startX);
                    position.setLowerLeftY(yOffset - 40);
                    position.setUpperRightX(300);
                    position.setUpperRightY(yOffset - 10);
                    txtLink.setRectangle(position);
                    page.getAnnotations().add(txtLink);

                    // add link content.
                    contents.beginText();
                    contents.newLineAtOffset(startX, yOffset - 20);
                    contents.setFont(font, 12);
                    contents.showText("Click here to listen to the audio attached to this image!");
                    contents.endText();

                }
                ok = true;
            } catch (IOException e) {
                e.getMessage();
                ok = false;
            } finally {
                try {
                    if (contents != null) {
                        contents.close();
                    }
                } catch (IOException e) {
                    ok = false;
                    e.getMessage();
                }
            }

            return ok;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Problem saving memory to PDF! Please try again.");
        }
        return false;
    }

    /**
     * This method closes the document and saves it.
     */
    public void saveAndClose() {
        try {
            doc.save(pdfOutputDirectory + pdfFileName);
        } catch (IOException e) {
            e.getMessage();
        } finally {
            try {
                doc.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.getMessage();
            }
        }
    }

    /**
     * This method parses individual lines of a text.
     *
     * @param wholeLetter the StringBuffer containing the text.
     * @param lines the lines of the text.
     * @param fontSize the size of the font.
     * @param pdfFont the PDF font type.
     * @param width the width of the page.
     */
    private void parseIndividualLines(StringBuffer wholeLetter, List<String> lines, float fontSize, PDFont pdfFont, float width) {
        String[] paragraphs = wholeLetter.toString().split(System.getProperty("line.separator"));
        for (int i = 0; i < paragraphs.length; i++) {
            int lastSpace = -1;
            lines.add(" ");
            while (paragraphs[i].length() > 0) {
                try {
                    int spaceIndex = paragraphs[i].indexOf(' ', lastSpace + 1);
                    if (spaceIndex < 0) {
                        spaceIndex = paragraphs[i].length();
                    }
                    String subString = paragraphs[i].substring(0, spaceIndex);
                    float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
                    if (size > width) {
                        if (lastSpace < 0) {
                            lastSpace = spaceIndex;
                        }
                        subString = paragraphs[i].substring(0, lastSpace);
                        lines.add(subString);
                        paragraphs[i] = paragraphs[i].substring(lastSpace).trim();
                        lastSpace = -1;
                    } else if (spaceIndex == paragraphs[i].length()) {
                        lines.add(paragraphs[i]);
                        paragraphs[i] = "";
                    } else {
                        lastSpace = spaceIndex;
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Problem parsing lines of PDF! Please try again.");
                }
            }
        }
    }

    /**
     * Resize an image from a file to a new dimension.
     *
     * @param filePath the path of the file.
     * @param dmn the new dimension.
     * @return the resized Image object.
     */
    private Image resizeGraphic(String filePath, Dimension dmn) {
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
            JOptionPane.showMessageDialog(null, "Problem resizing image from memory, please try again.");
        }
        return null;
    }
}
