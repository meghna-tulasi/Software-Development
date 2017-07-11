package project.commons;
import java.text.NumberFormat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/*JTextFieldLimit class is used to extend functionality of JTextField
This class helps limit the number of characters in the text field
and also developer can specify numeric restriction*/
public class JTextFieldLimit extends PlainDocument {
	private int limit;
	private boolean numeric;

	// constructor
	// Takes limit ie maximum number of characters and a boolean variable specifiying whether 
	// we need numeric restriction on the JTextField
	public JTextFieldLimit(int limit, boolean numeric) {
		super();
		this.limit = limit;
		this.numeric = numeric;
	}
}