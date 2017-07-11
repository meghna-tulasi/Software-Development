package views.panel;

import javax.swing.GroupLayout;
import javax.swing.JButton;

// interface for AuthorSearchPanel, KeywordsSearchPanel and PapersSearchPanel
/**
 * @author meghnatulasi
 *
 */
public interface PanelInterface {

	void addGUI();
	JButton createCancelButton();
	JButton createGoToFavoritesButton();
	JButton createAuthorSearchButton();
	JButton createPapersSearchButton();
	JButton createKeywordsSearchButton();
	JButton createSearchButton();
	GroupLayout createLayout();
}
