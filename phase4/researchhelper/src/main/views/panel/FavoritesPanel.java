package views.panel;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import project.commons.Favorites;
import project.commons.Result;
import views.table.FavoritesTable;
import views.table.Table;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
 

/**
 * @author neethuprasad
 */ //Favorites panel for displaying all the author data added to favorites list in table format
 
@SuppressWarnings("serial")
public class FavoritesPanel extends JSplitPane {
 
    private JPanel contentPane;
    JButton btnGoToSearch;
    JLabel lblFavoritesList;
 
    // constructor
    /**
     * @param contentPane holds the selected author resultset
     * @param parent takes in the parent pane 
     */
    public FavoritesPanel(JPanel contentPane, JSplitPane parent) {
        this.setContentPane(contentPane);

        // panel for displaying the heading
        JPanel headingPane = new JPanel();
        JScrollPane scrollPane = new JScrollPane();
         
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setContinuousLayout(true);
        setLeftComponent(headingPane);
        setRightComponent(scrollPane);
 
        Table  t = new FavoritesTable(Favorites.getInstance().getFavorites());
        scrollPane.setViewportView(t.getTable());
 
        // label 
        lblFavoritesList = new JLabel("Favorites List");
        lblFavoritesList.setFont(new Font("Tahoma", Font.BOLD, 20));
        // back button
        btnGoToSearch = new JButton("Go to search page");
        btnGoToSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// display search panel on clicking back button
                CardLayout card = (CardLayout)contentPane.getLayout();
                card.show(contentPane, "searchPanel");          
            }
        });
        
        // layout for favorites panel
        GroupLayout gl_panel_2 = new GroupLayout(headingPane);
        gl_panel_2.setHorizontalGroup(
                gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                        .addGap(130)
                        .addComponent(lblFavoritesList)
                        .addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addComponent(btnGoToSearch))
                );
        gl_panel_2.setVerticalGroup(
                gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblFavoritesList)
                                .addComponent(btnGoToSearch))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        headingPane.setLayout(gl_panel_2);
        setOneTouchExpandable(true);
        setDividerLocation(50);
        setResizeWeight(0.3);
         
    }
 
   
    /**
     * @return  //getFavoritesPanel() displays the current panel
     */
    public JSplitPane getFavoritesPanel() {
        return this;
    }
 
  
    /**
     * @return   // return the base pane
     */
    public JPanel getContentPane() {
        return contentPane;
    }
 
   
    /**
     * @param contentPane
     */ // sets the base pane
    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

   
	/**
	 * @return  // return the back button
	 */
	public JButton getGotoSearch() {
		return this.btnGoToSearch;
	}

	
	/**
	 * @return // returns the favorites label
	 */
	public JLabel getFavLabel() {
		return this.lblFavoritesList;
	}

}