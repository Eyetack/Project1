// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainFrame;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

/** The class containing the Librarian  for the ATM application */
//==============================================================
public class Librarian implements IView, IModel, ISlideShow
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;

	// private Patron myPatron;

	// GUI Components
	private Hashtable myViews;
	private JFrame		myFrame;

	private String loginErrorMessage = "";
	private String transactionErrorMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public Librarian()
	{
		myFrame = MainFrame.getInstance();
		myViews = new Hashtable();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Librarian");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Librarian",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		createAndShowLibrarianView();
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
	//	dependencies.setProperty("Login", "LoginError");
		dependencies.setProperty("Book", "TransactionError");
		dependencies.setProperty("Patron", "TransactionError");
		dependencies.setProperty("Transaction", "TransactionError");
		dependencies.setProperty("Search", "TransactionError");
		

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
	/*	if (key.equals("LoginError") == true)
		{
			return loginErrorMessage;
		}
		else */
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
	/*	else
		if (key.equals("Name") == true)
		{
			if (myPatron != null)
			{
				return myPatron.getState("Name");
			}
			else
				return "Undefined";
		} */
		else
			return "";
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// STEP 4: Write the sCR method component for the key you
		// just set up dependencies for
		// DEBUG System.out.println("Librarian.sCR: key = " + key);

	/*	if (key.equals("Login") == true)
		{
			if (value != null)
			{
				loginErrorMessage = "";

				boolean flag = loginPatron((Properties)value);
				if (flag == true)
				{
					createAndShowTransactionChoiceView();
				}
			}
		}
		else
		if (key.equals("CancelTransaction") == true)
		{
			createAndShowTransactionChoiceView();
		}
		else */
		if ((key.equals("Book") == true) || (key.equals("Patron") == true) ||
			(key.equals("Transaction") == true) || (key.equals("Search") == true) ||)
		{
			String transType = key;

		//	if (myAccountHolder != null)
		//	{
		//		doTransaction(transType);
		//	}
		//	else
		//	{
		//		transactionErrorMessage = "Transaction impossible: Customer not identified";
		//	}

		}
	/*	else
		if (key.equals("Logout") == true)
		{
			myAccountHolder = null;

			createAndShowTellerView();
		} */

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// DEBUG System.out.println("Teller.updateState: key: " + key);

		stateChangeRequest(key, value);
	}

	/**
	 * Login AccountHolder corresponding to user name and password.
	 */
	//----------------------------------------------------------
/*	public boolean loginAccountHolder(Properties props)
	{
		try
		{
			myAccountHolder = new AccountHolder(props);
			return true;
		}
		catch (InvalidPrimaryKeyException ex)
		{
				loginErrorMessage = "ERROR: " + ex.getMessage();
				return false;
		}
		catch (PasswordMismatchException exec)
		{

				loginErrorMessage = "ERROR: " + exec.getMessage();
				return false;
		}
	}

*/
	/**
	 * Create a Transaction depending on the Transaction type (deposit,
	 * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
	 * create.
	 */
	//----------------------------------------------------------
	/*public void doTransaction(String transactionType)
	{
		try
		{
			Transaction trans = TransactionFactory.createTransaction(
				transactionType);

			trans.subscribe("CancelTransaction", this);
			trans.stateChangeRequest("DoYourJob", "");
		}
		catch (Exception ex)
		{
			transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
			new Event(Event.getLeafLevelClassName(this), "createTransaction",
					"Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
					Event.ERROR);
		}
	}
*/
	//----------------------------------------------------------
/*	private void createAndShowTransactionChoiceView()
	{
		View localView = (View)myViews.get("TransactionChoiceView");

		if (localView == null)
		{
				// create our initial view
				localView = ViewFactory.createView("TransactionChoiceView", this); // USE VIEW FACTORY

				myViews.put("TransactionChoiceView", localView);

				// make the view visible by installing it into the frame
				swapToView(localView);
		}
		else
		{
			swapToView(localView);
		}
	}
*/
	//------------------------------------------------------------
	private void createAndShowLibrarianView()
	{
		View localView = (View)myViews.get("LibrarianView");

		if (localView == null)
		{
				// create our initial view
				localView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY

				myViews.put("LibrarianView", localView);

				// make the view visible by installing it into the frame
				myFrame.getContentPane().add(localView); // just the main panel in this case
				myFrame.pack();
		}
		else
		{
			swapToView(localView);
		}
	}


	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}

	//----------------------------------------------------------------------------
	protected void swapToPanelView(JPanel otherView)
	{
		JPanel currentView = (JPanel)myFrame.getContentPane().getComponent(0);
		// and remove it
		myFrame.getContentPane().remove(currentView);
		// add our view
		myFrame.getContentPane().add(otherView);
		//pack the frame and show it
		myFrame.pack();
		//Place in center
		WindowPosition.placeCenter(myFrame);
	}

	//-----------------------------------------------------------------------------
	public void swapToView(IView otherView)
	{

		if (otherView == null)
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		if (otherView instanceof JPanel)
		{
			swapToPanelView((JPanel)otherView);
		}//end of SwapToView
		else
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Non-displayable view object sent ", Event.ERROR);
		}

	}
	
	public void createNewBook() {
	
	Book b = new Book();
	BookView v = new BookView(b);
	swapToView(v);
	
	}
	
	public void createNewPatron() {
	
	Patron p = new Patron();
	PatronView v = new PatronView(p);
	swapToView(v);
	
	}
	
	public void createNewTransaction() {
	
	Transaction t = new Transaction();
	TransactionView v = new TransactionView(t);
	swapToView(v);
	
	}

}

