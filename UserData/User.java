package UserData;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        try {
            setUsername(username);
            setPassword(password);
        } catch (InvalidLoginExceptions e) {
            e.printStackTrace(); 
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) throws InvalidLoginExceptions {
        if (username == null || username.length() < 8) {
            throw new InvalidLoginExceptions("Username must be at least 8 characters long.");
        }
        this.username = username;
    }

    public void setPassword(String password) throws InvalidLoginExceptions {
        if (password == null || password.length() < 8) {
            throw new InvalidLoginExceptions("Password must be at least 8 characters long.");
        }
        this.password = password;
    }
  
    public HashMap<String, String> readUserDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"))) {
            HashMap<String, String> users = new HashMap<>();
            String readLine;

            while ((readLine = reader.readLine()) != null) {
                String[] parts = readLine.split(":");
                users.put(parts[0], parts[1]);
            }

            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public void writeUserDatabase(HashMap<String, String> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("database.txt"))) {
            for (String keys : map.keySet()) {
                String values = map.get(keys);
                writer.write(keys + ":" + values);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        HashMap<String, String> users = readUserDatabase();
        if (getUsername() == null || getPassword() == null) {
            System.out.println("Username or password was been written incorrectly.");
            return;
        }
        if (users.containsKey(getUsername())) {
            if (users.get(getUsername()).equals(getPassword())) {
                System.out.println("User -> " + getUsername() + " logged in.");
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("User -> " + getUsername() + " is not registered. Before logging in, please register!");
        }
    }

    public void register() {
        HashMap<String, String> users = readUserDatabase();

        if (!users.containsKey(getUsername())) {
            try {
                setPassword(getPassword());
                users.put(getUsername(), getPassword());
                writeUserDatabase(users);
                System.out.println("User -> " + getUsername() + " registered.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("User -> " + getUsername() + " already exists.");
        }
    }

    public static void main(String[] args) {
        User user = new User("jane_doe", "securepassword123");
    
        user.login();
        user.register();
        user.login();
    }
}
