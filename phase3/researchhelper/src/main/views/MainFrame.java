package views;
 
import java.awt.EventQueue;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import project.commons.Result;
import java.awt.CardLayout;
 
public class MainFrame extends JFrame {
 
    private JPanel contentPane;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
    /**
     * Create the frame.
     */
    public MainFrame() {
 
        // default base panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1328, 839);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout(0, 0));        
        createAndAddGUIToBasePanel();
    }
 
    public void createAndAddGUIToBasePanel () {
        // create a home page 
        HomePanel home = new HomePanel(contentPane);
        contentPane.add(home, "homePanel"); 
         
        /*creating landing search page
        Search starts at author search option
        SearchPanel does this*/
        SearchPanel SearchView = new SearchPanel(contentPane);
        contentPane.add(SearchView, "searchPanel");
         
        HashSet<Result> favorites = new HashSet<Result>();
        FavoritesPanel favoritesView = new FavoritesPanel(contentPane,favorites,SearchView);
        contentPane.add(favoritesView, "favoritesView");    
    }
}