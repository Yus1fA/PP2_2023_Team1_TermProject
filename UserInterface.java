import java.awt.BorderLayout;
import javax.swing.*;

public class UserInterface extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JLabel welcome = new JLabel("Welcome to Movie Database!");
        frame.add(welcome, BorderLayout.NORTH);

        JTabbedPane tabbed = new JTabbedPane();

        JPanel browse = new JPanel(new BorderLayout());
        JScrollPane movieList = new JScrollPane(new JList<>());

        movieList.setBorder(BorderFactory.createTitledBorder("Movies"));
        browse.add(movieList, BorderLayout.CENTER);
        tabbed.addTab("Browse Movies", browse);

        JPanel watchlist = new JPanel(new BorderLayout());
        JScrollPane watchlistScroll = new JScrollPane(new JList<>());

        watchlistScroll.setBorder(BorderFactory.createTitledBorder("Watchlist"));
        watchlist.add(watchlistScroll, BorderLayout.CENTER);
        tabbed.addTab("Watchlist", watchlist);

        JPanel registration = new JPanel(new BorderLayout());
        JButton register = new JButton("Register");
        registration.add(register, BorderLayout.CENTER);
        tabbed.addTab("Registration", registration);

        frame.add(tabbed, BorderLayout.CENTER);

        JButton addWatchlist = new JButton("Add to Watchlist");
        watchlist.add(addWatchlist, BorderLayout.SOUTH);

        JButton removeWatchlist = new JButton("Remove from Watchlist");
        watchlist.add(removeWatchlist, BorderLayout.NORTH);

        frame.setTitle("Movie Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 600); 
        frame.setVisible(true);
    }
}
