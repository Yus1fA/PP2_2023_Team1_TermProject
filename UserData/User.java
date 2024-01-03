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
        if (isAdmin(username, password)) {
            this.username = username;
            this.password = password;
        } else {
            try {
                setUsername(username);
                setPassword(password);
            } catch (UsernameLengthException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) throws UsernameLengthException {
        if (username == null || username.length() < 8) {
            throw new UsernameLengthException("Username must be at least 8 characters long.");
        }
        this.username = username;
    }

    public void setPassword(String password) throws UsernameLengthException {
        if (password == null || password.length() < 8) {
            throw new UsernameLengthException("Password must be at least 8 characters long.");
        }
        this.password = password;
    }
  
    public HashMap<String, String> readUserDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Resources/database.txt"))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources/database.txt"))) {
            for (String keys : map.keySet()) {
                String values = map.get(keys);
                writer.write(keys + ":" + values);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int login() {
        HashMap<String, String> users = readUserDatabase();
        if (getUsername() == null || getPassword() == null) {
            return -1;
        }
        if (!users.containsKey(getUsername())) {
            return -2;
        }
        if (!users.get(getUsername()).equals(getPassword())) {
            return -3;
        }
        return 1;
    }

    public int register() {
        HashMap<String, String> users = readUserDatabase();
        if (users.containsKey(getUsername())) {
            return -1;
        }
        try {
            setPassword(getPassword());
            users.put(getUsername(), getPassword());
            writeUserDatabase(users);
            return 1;
        } catch (Exception e) {
            return -2;
        }
    }

    private boolean isAdmin(String username, String password) {
        return "admin".equals(username) && "1223334444".equals(password);
    }
    // public static void main(String[] args) {
    //     User user = new User("jane_doe", "securepassword123");
    
    //     user.login();
    //     user.register();
    //     user.login();
    // }
}
