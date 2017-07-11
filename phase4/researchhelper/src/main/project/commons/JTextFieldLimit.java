package project.commons;
import java.text.NumberFormat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/*JTextFieldLimit class is used to extend functionality of JTextField
This class helps limit the number of characters in the text field
and also developer can specify numeric restriction*/
/**
 * @author Neethu Prasad
 * @version 1.1
 */
/**
 * @author dell
 *
 */
@SuppressWarnings("serial")
public class JTextFieldLimit extends PlainDocument {
	private int limit;
	private boolean numeric;

	/**
	 * Constructor
	 * @param limit maximum number of characters and a boolean variable specifiying whether
	 * @param numeric if we need numeric restriction on the JtextField
	 */
	public JTextFieldLimit(int limit, boolean numeric) {
		super();
		this.limit = limit;
		this.numeric = numeric;
	}

	public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		if (str == null) return;

		if ((getLength() + str.length()) <= limit) {
			if(numeric && isNumeric(str) ) {
				super.insertString(offset, str, attr);
			} else if (!numeric){
				super.insertString(offset, str, attr);
			}
		}
	}

	/**
	 * @param value passed from the values entered from UI
	 * @return true/false depending on if the value contains numbers
	 */
	public boolean isNumeric(String value) {
		try {
			NumberFormat.getInstance().parse(value);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
}