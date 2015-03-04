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
import java.util.*;

// project imports
import impresario.IModel;

/** The class containing the Transaction View  for the Librarian application */
//==============================================================
public class TransactionView extends View
{

	// GUI components
	protected JTextField bookId;
	protected JTextField patronId;
	protected JComboBox status;
	protected JTextField dateOfTrans;

	protected JButton cancelButton;
	protected JButton submitButton;
	
	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public TransactionView(IModel transaction)
	{
		super(transaction, "TransactionView");

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

		JLabel lbl_1 = new JLabel("        Insert New Transaction       ");
		Font myFont_1 = new Font("Helvetica", Font.BOLD, 18);
		lbl_1.setFont(myFont_1);
		temp_1.add(lbl_1);

		container.add(temp_1);

		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		String title = "TRANSACTION INFORMATION";
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
		JPanel temp0 = new JPanel();
		temp0.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel bookIdLabel = new JLabel("  Book ID          : ");
		temp0.add(bookIdLabel);

		bookId = new JTextField(20);
		temp0.add(bookId);

		temp.add(temp0);

		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel patronIdLabel = new JLabel("  Patron ID               : ");
		temp1.add(patronIdLabel);

		patronId = new JTextField(20);
		temp1.add(patronId);

		temp.add(temp1);

		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel transLabel = new JLabel("  Transaction Type               : ");
		temp2.add(transLabel);

		String [] transType = {"Rent", "Return"};
		JComboBox status = new JComboBox(transType);

		temp.add(temp2);

		JPanel temp3 = new JPanel();
		temp3.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel dateOfTransLabel = new JLabel("  Date of Transaction         : ");
		temp3.add(dateOfTransLabel);

		dateOfTrans = new JTextField(20);
		temp3.add(dateOfTrans);

		temp.add(temp3);
		
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
		 Properties p = new Properties();
		boolean num1 = isNumeric(bookId.getText());
        boolean num2 = 	isNumeric(patronId.getText());
        String date1 = "2000-01-01";		
		String date2 = "2014-02-01";
		if(num1 && num2 && dateOfTrans.getText().compareTo(date1) >= 0 && dateOfTrans.getText().compareTo(date2) <= 0))
		{
		   p.setProperty("bookId", bookId.getText());
		   p.setProperty("patronId", patronId.getText());
		   p.setProperty("dateOfTrans", dateOfTrans.getText());
		   p.setProperty("status", status.getSelectedItem().toString());
		   myModel.stateChangeRequest("InsertBook", p);
		}
		
		else
		{
		   statusLog.displayMessage("Please enter correct data.");
		}
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

		if (key.equals("InsertTransactionResult") == true)
		{
			displayMessage("Insert new transaction success.");
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
//----------------------------------------------------------
	public static boolean isNumeric(String str)  
{  
  try  
  {  
    double d = Double.parseDouble(str);  
  }  
  catch(NumberFormatException nfe)  
  {  
    return false;  
  }  
  return true;  
}
}

//---------------------------------------------------------------
//	Revision History:
//


