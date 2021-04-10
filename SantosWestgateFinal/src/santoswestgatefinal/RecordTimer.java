package santoswestgatefinal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JLabel;

/**
 * This method sets up a record timer to the audio recording functionality from
 * the GUI.
 * https://www.codejava.net/coding/how-to-develop-a-sound-recorder-program-in-java-swing
 *
 * @author Allen Westgate & Bernardo Santos
 */
public class RecordTimer extends Thread {

    private DateFormat dateFormater = new SimpleDateFormat("mm:ss"); // the date format of the recording string.
    private boolean isRunning = false; // boolean representing if the recording is running or not.
    private boolean isReset = false; // boolean representing if the recording is reset or not.
    private long startTime; // the start time of the recording.
    private JLabel labelRecordTime; // the JLabel containing the recording time.

    /**
     * The constructor.
     *
     * @param labelRecordTime the JLabel containing the record time.
     */
    RecordTimer(JLabel labelRecordTime) {
        this.labelRecordTime = labelRecordTime;
    }

    /**
     * This method starts the Thread responsible for counting the record time.
     */
    @Override
    public void run() {
        isRunning = true;

        startTime = System.currentTimeMillis();

        while (isRunning) {
            try {
                Thread.sleep(100);
                labelRecordTime.setText("Record Time: " + toTimeString());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                if (isReset) {
                    labelRecordTime.setText("Record Time: 00:00");
                    isRunning = false;
                    break;
                }
            }
        }
    }

    /**
     * This method cancels counting record/play time.
     */
    void cancel() {
        isRunning = false;
    }

    /**
     * This method resets counting to "00:00"
     */
    void reset() {
        isReset = true;
        isRunning = false;
    }

    /**
     * This method generates a String for time counter in the format of "mm:ss"
     *
     * @return the time counter
     */
    private String toTimeString() {
        long now = System.currentTimeMillis();
        Date current = new Date(now - startTime);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeCounter = dateFormater.format(current);
        return timeCounter;
    }
}
