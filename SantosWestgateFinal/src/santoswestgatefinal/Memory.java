package santoswestgatefinal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a memory added to the program by the user.
 *
 * @author Allen Westgate & Bernardo Santos
 */
public class Memory implements Comparable<Memory> {

    private int uniqueID; // the unique id of the object.
    private String title, date, location = "", text, recordTime = "00:00"; // all the strings.
    private List<String> tags = new ArrayList<>(); // a list containing the tags.
    private byte[] audio = null, image = null; // the audio and image of the memory as byte arrays.

    /**
     * The constructor.
     *
     * @param title the title of the memory.
     * @param date the date of the memory.
     * @param text the text of the memory.
     */
    public Memory(String title, String date, String text) {
        this.title = title;
        this.date = date;
        this.text = text;
    }

    /**
     * This method sets the unique id of the memory.
     *
     * @param uniqueID the unique id.
     */
    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    /**
     * This method sets the title of the memory.
     *
     * @param title the title of the memory.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method sets the date of the memory.
     *
     * @param date the date of the memory.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * This method sets the location of the memory.
     *
     * @param location the location of the memory.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method sets the text of the memory.
     *
     * @param text the text of the memory.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * This method sets the tags list of the memory.
     *
     * @param tags the tags associated to the memory.
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * This method sets the audio byte array of the memory.
     *
     * @param audio the audio byte array.
     */
    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    /**
     * This method sets the image byte array of the memory.
     *
     * @param image the image bte array of the memory.
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * This method sets the record time of the audio associated to the memory.
     *
     * @param recordTime the record time.
     */
    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * This method returns the unique id of the memory.
     *
     * @return the unique id.
     */
    public int getUniqueID() {
        return uniqueID;
    }

    /**
     * This method returns the title of the memory.
     *
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method returns the date of the memory.
     *
     * @return the date.
     */
    public String getDate() {
        return date;
    }

    /**
     * This method returns the location of the memory.
     *
     * @return the location of the memory.
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method returns the text of the memory.
     *
     * @return the text.
     */
    public String getText() {
        return text;
    }

    /**
     * This method returns the list of tags as a string.
     *
     * @return the tagas as string.
     */
    public String getTags() {
        String returnTags = "";
        for (String tag : tags) {
            returnTags += tag + ",";
        }

        if (returnTags.length() > 1) {
            returnTags = returnTags.substring(0, returnTags.length() - 1);
        }

        return returnTags;
    }

    /**
     * This method returns the audio byte array.
     *
     * @return the audio byte array.
     */
    public byte[] getAudio() {
        return audio;
    }

    /**
     * This method returns the image byte array of the memory.
     *
     * @return the image byte array.
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * This method returns the record time of the audio.
     *
     * @return the record time of the audio.
     */
    public String getRecordTime() {
        return recordTime;
    }

    /**
     * This method returns the tags as a list.
     *
     * @return the tags as a list.
     */
    public List<String> getTagsAsList() {
        return tags;
    }

    /**
     * This method compares the dates of two Memory objects.
     *
     * @param o the Memory object to be compared.
     * @return 0 or 1.
     */
    @Override
    public int compareTo(Memory o) {
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdformat.parse(this.getDate());
            Date date2 = sdformat.parse(o.getDate());
            return date1.compareTo(date2);

        } catch (ParseException ex) {
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
