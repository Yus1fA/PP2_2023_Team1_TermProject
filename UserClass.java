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
        if (username.length() < 8) {
            System.out.println(username.length());
            throw new InvalidLoginExceptions("Username must be containing at least 8 or more characters.");
        }
        else {
            this.username = username;
        }
    }

    public void setPassword(String password) throws InvalidLoginExceptions {
        if (password.length() < 8) {
            throw new InvalidLoginExceptions("Password must be at least 8 or more characters.");
        }
        else {
            this.password = password;
        }
    }

    public static void main(String[] args) {
        // Test part
        UserClass user1 = new UserClass("mark_pain", "123456789");
        UserClass user2 = new UserClass("john_smith", "15s16s17s");
        UserClass user3 = new UserClass("ted_johnson", "c7o8d9e");
        user1.login();
        user2.login();
        user3.login(); System.out.println();
        user1.register();
        user2.register();
        user3.register(); System.out.println();
        user1.login();
        user2.login();
        user3.login();
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
                    System.out.println("Faced error: " + e.getMessage());
                }
        } else {
            System.out.println("User -> " + getUsername() + " already existed.");
        }
    }
}
