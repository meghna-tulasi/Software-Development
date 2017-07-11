package views.panel;
 
import java.awt.EventQueue;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import project.commons.Favorites;
import project.commons.Result;
import java.awt.CardLayout;
 
// Main app to launch the application
/**
 * @author meghnatulasi
 *
 */
public class MainFrame extends JFrame {
 
    private JPanel contentPane;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	// creates the base frame
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                    frame.pack();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
    /**
     * Creates the frame.
     */
    /**
     * 
     */
    public MainFrame() {
 
        // default base panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1400, 900);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout(0, 0));        
        createAndAddGUIToBasePanel();
    }
 
    /**
     * // creatse a home page
     */
    public void createAndAddGUIToBasePanel () {
        
        HomePanel home = new HomePanel(contentPane);
        contentPane.add(home, "homePanel"); 
         
        /*creating landing search page
        Search starts at author search option
        SearchPanel does this*/
        SearchPanel SearchView = new SearchPanel(contentPane);
        contentPane.add(SearchView, "searchPanel");
         
        FavoritesPanel favoritesView = new FavoritesPanel(contentPane,SearchView);
        contentPane.add(favoritesView, "favoritesView");    
    }
}