package santoswestgatefinal;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static java.lang.System.exit;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * This method deals with the data of the program and connects to the database.
 *
 * @author Allen Westgate & Bernardo Santos
 */
public class Model {

    private List<Memory> memories = new ArrayList<>(); // the list of memories.
    private String host; // the host of the database.
    private Connection con; // the connection to the database.

    /**
     * The constructor.
     */
    public Model() {
        try {
            this.host = "jdbc:derby://localhost:1527/memba;create=true;user=root;password=root";
            this.con = DriverManager.getConnection(this.host);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problem connecting to database! Start your JavaDB server and try again.");
            exit(0);
        }
    }

    /**
     * This method loads the memories from the database and returns to caller.
     *
     * @return the memories as a list.
     */
    public List<Memory> loadAndGetMemories() {

        try {
            // connect to database and retrieve the data it contains.
            Statement stmt = this.con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            String[] types = {"TABLE"};
            List<String> listOfTables = new ArrayList<>();
            ResultSet tables = dbm.getTables(null, null, "%", types);

            // iterate over the list of tables of the database and add it to 
            // listOfTables.
            while (tables.next()) {
                listOfTables.add(tables.getString("TABLE_NAME"));
            }

            // if table exists in database, then retrieve its data.
            if (listOfTables.contains("MEMBA_TABLE")) {
                byte[] audio = null; // the audio of the memory.
                byte[] image = null; // the image of the memory.
                String audioString = "", imageString = "";
                String SQL = "SELECT * FROM MEMBA_TABLE"; // SQL query to select all from the table.
                ResultSet rs = stmt.executeQuery(SQL); // the result set of the query.

                // iterate over the rows of the table found in database.
                while (rs.next()) {

                    // get each of the Memory attributes from the table row.
                    int uniqueID = rs.getInt("un");
                    String title = rs.getString("title").stripTrailing();
                    String date = rs.getString("date").stripTrailing();
                    String text = rs.getString("text").stripTrailing();
                    String location = rs.getString("location").stripTrailing();
                    String recTime = rs.getString("record_time").stripTrailing();
                    String tags = rs.getString("tags").stripTrailing();

                    // Initialize the Memory object.
                    Memory memory = null;
                    memory = new Memory(title, date, text);

                    // try to get the image byte array from the table.
                    try {
                        image = rs.getBlob("image").getBinaryStream().readAllBytes();
                        memory.setImage(image);
                    } catch (NullPointerException n) {
                        imageString = "";
                    }

                    // try to get the audio byte array from the table.
                    try {
                        audio = rs.getBlob("audio").getBinaryStream().readAllBytes();
                        memory.setAudio(audio);
                    } catch (NullPointerException n) {
                        audioString = "";
                    }

                    // if location string is not empty, set the Memory object 
                    // location to the one found in the table.
                    if (!location.isEmpty()) {
                        memory.setLocation(location);
                    }

                    // if the tags string is not empty, set the Memory object
                    // tags list to the one found in the table.
                    if (!tags.isEmpty()) {
                        List<String> listOfTags = new ArrayList<>();
                        String[] tagsSplit = tags.split(",");
                        listOfTags.addAll(Arrays.asList(tagsSplit));
                        memory.setTags(listOfTags);
                    }

                    // set the memory's unique id and record time, and add the 
                    // Memory object to the list of memories.
                    memory.setUniqueID(uniqueID);
                    memory.setRecordTime(recTime);
                    memories.add(memory);
                }
            } else {

                // create table in database.
                String sql = "CREATE TABLE ROOT.MEMBA_TABLE ("
                        + "un int primary key generated always as identity, "
                        + "title char(100) not null,"
                        + "date char(40) not null,"
                        + "text varchar(1000) not null,"
                        + "location char(100),"
                        + "image blob, "
                        + "audio blob,"
                        + "record_time varchar(5),"
                        + "tags varchar(200))";
                stmt.execute(sql);
            }

            // return the memories to caller.
            return this.memories;
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not load data from database!");
        }
        return null;
    }

    /**
     * This method saves a memory to the database.
     *
     * @param m the Memory to be saved to database.
     */
    public void saveMemory(Memory m) {
        try {

            // try to insert the memories' attributes to the database table.
            PreparedStatement st = con.prepareStatement(
                    "INSERT INTO ROOT.MEMBA_TABLE("
                    + "TITLE, DATE, TEXT, LOCATION, IMAGE, AUDIO, RECORD_TIME, TAGS) "
                    + "VALUES (?,?,?,?,?,?,?,?)");

            // set each of the attributes to the statement from above.
            st.setString(1, m.getTitle());
            st.setString(2, m.getDate());
            st.setString(3, m.getText());
            st.setString(4, m.getLocation());

            if (m.getImage() == null) {
                st.setNull(5, java.sql.Types.BLOB);
            } else {
                st.setBlob(5, new ByteArrayInputStream(m.getImage()));
            }

            if (m.getAudio() == null) {
                st.setNull(6, java.sql.Types.BLOB);
            } else {
                st.setBlob(6, new ByteArrayInputStream(m.getAudio()));
            }

            st.setString(7, m.getRecordTime());
            st.setString(8, m.getTags());

            // execute the statement.
            st.executeUpdate();

            // tell user that memory creation was successfull.
            ImageIcon icon = new ImageIcon("CSC2620 - Final Project files\\Vector Art\\Graphics\\save-memory-check-mark.png");
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaled);

            JOptionPane.showMessageDialog(null, "Memory was successfully created!",
                    "Memory creation confirmation", JOptionPane.INFORMATION_MESSAGE, scaledIcon);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Could not save data to database!");
        }
    }

    /**
     * This method deletes a group of memories from the database based on the
     * ids passed as argument.
     *
     * @param ids the ids of the memories to be removed from database.
     */
    public void deleteMemories(List<Integer> ids) {
        String memoriesIDs = ""; // the ids of the memories as a String.
        String message = ""; // the message to user.

        // for each of the ids in the list, add it to a string followed by a comma.
        for (Integer id : ids) {
            memoriesIDs += String.valueOf(id) + ",";
        }

        // remove the last character of the string, which is a comma.
        memoriesIDs = memoriesIDs.substring(0, memoriesIDs.length() - 1);

        // set message to user according to the number of memories removed.
        if (ids.size() > 1) {
            message = "Memories were succesfully deleted!";
        } else {
            message = "Memory was succesfully deleted!";
        }

        try {
            // delete the memories from the table.
            Statement stmt = con.createStatement();
            String SQL = "DELETE FROM MEMBA_TABLE WHERE UN IN (" + memoriesIDs + ")";
            stmt.executeUpdate(SQL);

            // let user know that deletion was successful.
            ImageIcon icon = new ImageIcon("CSC2620 - Final Project files\\Vector Art\\Graphics\\trash-bin.png");
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaled);
            JOptionPane.showMessageDialog(null, message,
                    "Memory deletion confirmation", JOptionPane.INFORMATION_MESSAGE, scaledIcon);
        } catch (SQLException ex) {
            if (message.contains("memories")) {
                JOptionPane.showMessageDialog(null,
                        "There was a problem deleting the memories from database!");
            } else if(message.contains("memory")){
                JOptionPane.showMessageDialog(null,
                        "There was a problem deleting the memory from database!");
            }
        }

    }

    /**
     * This method updates a Memory object in the database.
     *
     * @param mem the Memory object.
     * @param text the memory's text.
     * @param recordTime the memory's record time.
     * @param tags the memory's tags.
     * @param imageByte the memory's image as a byte array.
     * @param audioByte the memory's audio as a byte array.
     */
    public void updateMemory(Memory mem, String text, String recordTime,
            String tags, byte[] imageByte, byte[] audioByte) {
        try {

            // update the table in database .
            PreparedStatement ps = con.prepareStatement("UPDATE MEMBA_TABLE SET "
                    + "TITLE =?, "
                    + "DATE =?, "
                    + "TEXT =?, "
                    + "LOCATION =?, "
                    + "RECORD_TIME =?, "
                    + "TAGS =?, "
                    + "IMAGE =?, "
                    + "AUDIO =? "
                    + "WHERE UN =?");

            // set the staement attributes to be the ones passed by argument.
            ps.setString(1, mem.getTitle());
            ps.setString(2, mem.getDate());
            ps.setString(3, text);
            ps.setString(4, mem.getLocation());
            ps.setString(5, recordTime);
            ps.setString(6, tags);
            if (imageByte == null) {
                ps.setNull(7, java.sql.Types.BLOB);
            } else {
                ps.setBlob(7, new ByteArrayInputStream(imageByte));
            }

            if (audioByte == null) {
                ps.setNull(8, java.sql.Types.BLOB);
            } else {
                ps.setBlob(8, new ByteArrayInputStream(audioByte));
            }

            ps.setInt(9, mem.getUniqueID());

            // execute the statement.
            ps.execute();
            ps.close();

            // Let user know that update was successful.
            ImageIcon icon = new ImageIcon("CSC2620 - Final Project files\\Vector Art\\Graphics\\save-memory-check-mark.png");
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaled);

            JOptionPane.showMessageDialog(null, "Memory was succesfully updated!",
                    "Memory update confirmation", JOptionPane.INFORMATION_MESSAGE, scaledIcon);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
