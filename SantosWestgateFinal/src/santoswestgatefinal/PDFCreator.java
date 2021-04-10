package santoswestgatefinal;

import ch.aplu.util.SoundRecorder;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.swing.JOptionPane;

/**
 * This method creates a PDF Document in memory and writes to it.
 *
 * Inspired by: https://github.com/amdegregorio/PDFBox-Example
 *
 * @author Allew Westgate & Bernardo Santos
 */
public class PDFCreator {

    private int counter = 0;
    
    /**
     * The constructor.
     *
     * @param path the path used to store the newly created PDF.
     * @param memory the memory used to create the PDF.
     * @param af the audio format.
     */
    public PDFCreator(String path, Memory memory, AudioFormat af) {

        File f = new File(path + "\\Memory-" + String.valueOf(memory.getUniqueID()) + ".pdf");
        int result = 0;
        if (f.exists()) {
            result = JOptionPane.showConfirmDialog(null, "There is already a file named " + path + "Memory-" + String.valueOf(memory.getUniqueID()) + ".pdf, are you sure you want to override it?");
        }

        if (result == JOptionPane.YES_OPTION) {
            counter++;
            // creates new PDFWriter object so that it is possible to write to a 
            // new PDF document.
            PDFWriter pdfWriter = new PDFWriter(path, "Memory-" + String.valueOf(memory.getUniqueID()) + ".pdf");
            pdfWriter.createPdfFile();

            // get the title of the memory and create a new StringBuffer.
            String heading = memory.getTitle();
            StringBuffer pageText = new StringBuffer();

            // if memory has audio, create new audio file in the directory.
            if (memory.getAudio() != null) {
                SoundRecorder sd = new SoundRecorder(af);
                sd.writeWavFile(memory.getAudio(), path + "\\" + "Memory-" + String.valueOf(memory.getUniqueID()) + ".wav");

            }

            // if memory has location, write the location to PDF.
            if (!memory.getLocation().isEmpty()) {
                pageText.append(memory.getLocation());
                pageText.append(System.getProperty("line.separator"));
            }

            // write the memory's date and text to PDF.
            pageText.append(memory.getDate());
            pageText.append(System.getProperty("line.separator"));
            pageText.append(memory.getText().replaceAll("\\n", System.getProperty("line.separator")).replaceAll("\\t", " "));
            pageText.append(System.getProperty("line.separator"));
            pageText.append(System.getProperty("line.separator"));
            pdfWriter.addPage(heading, pageText, memory.getImage(), memory.getAudio());
            pdfWriter.saveAndClose();

        }
    }
    
    public int getSavedPDFs(){
        return counter;
    }

}
