
/**
 * Custom exception to be thrown when invalid number of data is provided for creating character
 * derived instances.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class InvalidDataFormatException extends Exception {

    /**
     * Creates throwable exception instance for invalid number of data provided to create
     * characters while reading file with line number to be shown in error message
     *
     * @param lineNumber indicates which line has invalid characteristics
     */
    public InvalidDataFormatException(int lineNumber) {
        super("WARNING: invalid data format in config file in line " + lineNumber);
    }

    /**
     * Creates throwable exception instance for invalid number of data for creating characters.
     */
    public InvalidDataFormatException() {
        super("WARNING: invalid data format in config file");
    }

}
