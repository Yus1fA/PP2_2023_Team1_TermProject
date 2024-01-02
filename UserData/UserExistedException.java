package UserData;

public class UserExistedException extends Exception {
    public UserExistedException(String message) {
        super(message);
    }
}