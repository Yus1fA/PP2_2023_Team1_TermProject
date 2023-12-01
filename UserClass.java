import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserClass {
    private String username;
    private String password;

    public UserClass(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) {
        // Test part
        UserClass user = new UserClass("mark_pain", "12345679");
        UserClass user2 = new UserClass("john_smith", "15s16s17s");
        user.login();
        user2.login();
        user.register();
        user2.register();
        user.login();
        user2.login();
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
            users.put(getUsername(), getPassword());
            writeUserDatabase(users);
            System.out.println("User -> " + getUsername() + " registered.");
        } else {
            System.out.println("User -> " + getUsername() + " already existed.");
        }
    }
}
