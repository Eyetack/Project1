// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;
import java.util.*;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;
import userinterface.MainFrame;

//==============================================================
public class TransactionCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Transaction";

	private Vector transactions;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public TransactionCollection(String dateOfTrans, String patronId, String bookId) throws
		Exception
	{
		super(myTableName);
		String select="";
		if(dateOfTrans != null){
			if(patronId != null){
				if(bookId != null){
					 select = "SELECT * FROM Transaction WHERE (dateOfTrans =" + dateOfTrans + ", patronId =" + patronId + ", bookId=" + bookId + ");";
				}
				 select = "SELECT * FROM Transaction WHERE (dateOfTrans =" + dateOfTrans + ", patronId =" + patronId + ");";
			}
			if(bookId != null){
				 select = "SELECT * FROM Transaction WHERE (dateOfTrans =" + dateOfTrans + ", bookId=" + bookId + ");";
			}
			 select = "SELECT * FROM Transaction WHERE (dateOfTrans =" + dateOfTrans + ");";
		}
		else if(patronId != null){
			if(bookId != null){
				 select = "SELECT * FROM Transaction WHERE (patronId =" + patronId + ", bookId=" + bookId + ");";
			}
			 select = "SELECT * FROM Transaction WHERE (patronId =" + patronId + ");";
		}
		else if(bookId != null){
			 select = "SELECT * FROM Transaction WHERE (bookId=" + bookId + ");";
		}

		Vector transactions = new Vector();

		Vector v = this.getSelectQueryResult(select);
		for(int i=0; i < v.size(); i++){
			Properties nextRow = (Properties)v.elementAt(i);
		
			Transaction t = new Transaction (nextRow);
			transactions.add(t);
		}



		
               
		
	}

	//----------------------------------------------------------------------------------
	private void addTransaction(Transaction b)
	{
		//users.add(u);
		int index = findIndexToAdd(b);
		transactions.insertElementAt(b,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(Transaction b)
	{
		//users.add(u);
		int low=0;
		int high = transactions.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Transaction midSession = (Transaction)transactions.elementAt(middle);

			int result = Transaction.compare(b,midSession);

			if (result ==0)
			{
				return middle;
			}
			else if (result<0)
			{
				high=middle-1;
			}
			else
			{
				low=middle+1;
			}


		}
		return low;
	}


	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("Transactions"))
			return transactions;
		else
		if (key.equals("TransactionList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public Transaction retrieve(String transactionNumber)
	{
		Transaction retValue = null;
		for (int cnt = 0; cnt < transactions.size(); cnt++)
		{
			Transaction nextTransaction = (Transaction)transactions.elementAt(cnt);
			String nextTransactionNum = (String)nextTransaction.getState("TransactionNumber");
			if (nextTransactionNum.equals(transactionNumber) == true)
			{
				retValue = nextTransaction;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------
/*	protected void createAndShowView() //Don't need this method
	{

		View localView = (View)myViews.get("AccountCollectionView");

		if (localView == null)
		{
				// create our initial view
				localView = ViewFactory.createView("AccountCollectionView", this);

				myViews.put("AccountCollectionView", localView);

				// make the view visible by installing it into the frame
				swapToView(localView);
		}
		else
		{
			// make the view visible by installing it into the frame
			swapToView(localView);
		}
	}
*/
	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
 
       //------------------------------------------------------------------------------------
        public void findTransactionsOlderThanDate (String date)
        {
          String select = "SELECT * FROM Transaction WHERE (dateOfBirth < " + date + ");";
          Vector v = this.getSelectQueryResult(select);
          print(v);
           
        }
       //-------------------------------------------------------------------------------------

        public void print(Vector result)
        {        
          if (result == null)
		{
			System.out.println("No values returned from database for query");
		}
		else
		{
			int numResults = result.size();
			for (int cnt = 0; cnt < numResults; cnt++)
			{
				Properties nextRow = (Properties)result.elementAt(cnt);
	
				Enumeration columnNames = nextRow.propertyNames();
				while (columnNames.hasMoreElements() == true)
				{
					String columnName = (String)columnNames.nextElement();
					String columnValue = nextRow.getProperty(columnName);
	
					System.out.println(columnName + " = " + columnValue);
				}
			}
		}
          
        }
               


}
