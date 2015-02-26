// specify the package
package userinterface;

// system imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Book View  for the Librarian application */
//==============================================================
public class SearchBookView extends View
{

	// GUI components
	protected JTextField author;
	protected JTextField title;
	protected JTextField pubYear;
	protected JTextField status;

	protected JButton cancelButton;
	protected JButton submitButton;
	
	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SearchBookView(IModel book)
	{
		super(book, "BookView");

		// set the layout for this panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create our GUI components, add them to this panel
		add(createTitle());
		add(createDataEntryFields());
		add(createNavigationButtons());

		// Error message area
		add(createStatusLog("                                            "));

		//populateFields();

		//myModel.subscribe("ServiceCharge", this);
		//myModel.subscribe("UpdateStatusMessage", this);
	}

	// Overide the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);
		//bannerID.requestFocusInWindow();
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	protected JPanel createTitle()
	{
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(Box.createRigidArea(new Dimension(250,10)));

		JPanel temp_1 = new JPanel();
		temp_1.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lbl_1 = new JLabel("        Search Books       ");
		Font myFont_1 = new Font("Helvetica", Font.BOLD, 18);
		lbl_1.setFont(myFont_1);
		temp_1.add(lbl_1);

		container.add(temp_1);

		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		String title = "BOOK INFORMATION";
		JLabel lbl = new JLabel(title);
		Font myFont = new Font("Helvetica", Font.BOLD, 15);
		lbl.setFont(myFont);
		temp.add(lbl);

		container.add(temp);
		return container;
	}

	// Create the main data entry fields
	//-------------------------------------------------------------
	protected JPanel createDataEntryFields()
	{

		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

		temp.add(Box.createRigidArea(new Dimension(0,20)));

		// data entry fields

		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel titleLabel = new JLabel("  Title               : ");
		temp1.add(titleLabel);

		title = new JTextField(20);
		temp1.add(title);

		temp.add(temp1);


		return temp;
	}

	// Create the navigation buttons
	//-------------------------------------------------------------
	protected JPanel createNavigationButtons()
	{
		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

		temp.add(Box.createRigidArea(new Dimension(0,10)));

		JPanel temp1 = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(1);
		f1.setHgap(25);
		temp1.setLayout(f1);

		// create the buttons, listen for events, add them to the panel

		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		temp1.add(submitButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		temp1.add(cancelButton);

		temp.add(temp1);

		return temp;
	}

	// Create the status log field
	//-------------------------------------------------------------
	protected JPanel createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	/*public void populateFields()
	{
		accountNumber.setText((String)myModel.getState("AccountNumber"));
		acctType.setText((String)myModel.getState("Type"));
		balance.setText((String)myModel.getState("Balance"));
	 	serviceCharge.setText((String)myModel.getState("ServiceCharge"));
	} */

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(EventObject evt)
	{

		clearErrorMessage();

		if (evt.getSource() == cancelButton)
		{
			// cancel the deposit
			processCancel();
		}
		else
		if (evt.getSource() == submitButton)
		{
			
			processData();
		}
	}

	/**
	 * Process
	 */
	//----------------------------------------------------------
	protected void processData()
	{
		myModel.stateChangeRequest("ServiceCharge", serviceCharge.getText());
	}

	/**
	 * Process the Cancel button.
	 */
	//----------------------------------------------------------
	protected void processCancel()
	{
		myModel.stateChangeRequest("AccountCancelled", null);
	}

	/**
	 * Update method
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		clearErrorMessage();

		if (key.equals("ServiceCharge") == true)
		{
			String val = (String)value;
			serviceCharge.setText(val);
			displayMessage("Service Charge Imposed: $ " + val);
		}
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

}

//---------------------------------------------------------------
//	Revision History:
//


