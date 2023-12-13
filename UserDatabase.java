import java.io.*;
import java.util.HashMap;

public class UserDatabase {

    // HashMap to store registered users
    private HashMap<String, String> users;

    // Constructor to initialize the user database
    public UserDatabase() {
        users = new HashMap<>();
    }

    // Method to register a new user
    public void registerUser(String username, String password) {
        users.put(username, password);
        saveToFile();
    }

    // Method to check if a user is registered
    public boolean isUserRegistered(String username) {
        return users.containsKey(username);
    }

    // Method to validate login credentials
    public boolean validateLogin(String username, String password) {
        return users.getOrDefault(username, "").equals(password);
    }

    // Method to read user data from a file
    private void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to save user data to a file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_database.txt"))) {
            for (HashMap.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase();

        // Register a new user
        userDatabase.registerUser("john_doe", "password123");

        // Check if a user is registered
        System.out.println("Is john_doe registered? " + userDatabase.isUserRegistered("john_doe"));

        // Validate login credentials
        System.out.println("Login successful? " + userDatabase.validateLogin("john_doe", "password123"));
    }
}
