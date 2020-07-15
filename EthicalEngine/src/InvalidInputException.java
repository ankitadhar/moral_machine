
/**
 * Custom exception to be thrown when invalid input is provided by the user to the console.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class InvalidInputException extends Exception {

    /**
     * Creates throwable exception instance for invalid input by user with a suffix message of
     * "Invalid response".
     *
     * @param message is the message to be shown to the user.
     */
    public InvalidInputException(String message) {
        super("Invalid response. " + message);
    }
}
