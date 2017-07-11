package views;
 
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
 
public class HomePanel extends JPanel{
    private JPanel contentPane;
    private JLabel heading;
    private JLabel image;
    private GroupLayout homeLayout;
    private JButton startSearchButton;
 
    public HomePanel(JPanel contentPane) {
        this.contentPane = contentPane;
        addGUIElements();
    }
 
    public void addGUIElements() {
        this.heading = createHeading();
        this.image = createImage();     
        this.startSearchButton = createButton();        
        this.homeLayout = createLayout(heading, image, startSearchButton);      
        setLayout(homeLayout);
    }
     
    public JLabel getHeading() {
        return this.heading;
    }
     
    public JLabel getImage() {
        return this.image;
    }
     
    public JButton getStartSearchButton() {
        return this.startSearchButton;
    }
     
    private GroupLayout createLayout(JLabel heading, JLabel image, JButton startSearchButton) {
        GroupLayout homeLayout = new GroupLayout(this);
        homeLayout.setHorizontalGroup(
                homeLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(homeLayout.createSequentialGroup()
                        .addGroup(homeLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(homeLayout.createSequentialGroup()
                                        .addGap(188)
                                        .addComponent(image, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                        .addGap(75)
                                        .addComponent(heading, GroupLayout.PREFERRED_SIZE, 535, GroupLayout.PREFERRED_SIZE))
                                .addGroup(homeLayout.createSequentialGroup()
                                        .addGap(501)
                                        .addComponent(startSearchButton, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(370, Short.MAX_VALUE))
                );
        homeLayout.setVerticalGroup(
                homeLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(homeLayout.createSequentialGroup()
                        .addGap(141)
                        .addComponent(image, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(515, Short.MAX_VALUE))
                .addGroup(Alignment.TRAILING, homeLayout.createSequentialGroup()
                        .addGap(179)
                        .addComponent(heading, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                        .addComponent(startSearchButton, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                        .addGap(317))
                );
        return homeLayout;
    }
 
    private JLabel createHeading() {
        JLabel label = new JLabel("Committee Helper");
        label.setFont(new Font("Arial Black", Font.ITALIC, 50));
        return label;
    }
 
    private JLabel createImage() {
        JLabel label = new JLabel("");
       // label.setIcon(new ImageIcon(MainFrame.class.getResource("/project/resources/searchIcon.png")));
        return label;
    }
     
    private JButton createButton() {
        JButton btnStartSearch = new JButton("Start Search");
        btnStartSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)contentPane.getLayout();
                card.show(contentPane, "AuthorSearchPane");
                card.next(contentPane);
            }
        });
         
         
        btnStartSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
        return btnStartSearch;
    }

	public HomePanel getHomePanel() {
		return this;
	}
}