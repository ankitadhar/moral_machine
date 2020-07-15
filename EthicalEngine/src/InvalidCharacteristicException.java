
/**
 * Custom exception to be thrown when characteristics specified for creating instances of character
 * derived classes do not match characteristics list maintained in the program.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class InvalidCharacteristicException extends Exception {

    /**
     * Creates throwable exception instance for invalid characteristics while reading file with
     * line number to be shown in error message
     *
     * @param lineNumber indicates which line has invalid characteristics
     */
    public InvalidCharacteristicException(int lineNumber) {
        super("WARNING: invalid characteristic in config file in line " + lineNumber);
    }

    /**
     * Creates throwable exception instance for invalid characteristics.
     */
    public InvalidCharacteristicException() {
        super("WARNING: invalid characteristic in config file.");
    }
}
