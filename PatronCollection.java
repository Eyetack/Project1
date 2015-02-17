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
public class PatronCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Patron";

	private Vector patrons;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public PatronCollection() throws
		Exception
	{
		super(myTableName);
                Vector patrons = new Vector();
		
	}

	//----------------------------------------------------------------------------------
	private void addPatron(Patron b)
	{
		//users.add(u);
		int index = findIndexToAdd(b);
		patrons.insertElementAt(b,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(Patron b)
	{
		//users.add(u);
		int low=0;
		int high = patrons.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Patron midSession = (Patron)patrons.elementAt(middle);

			int result = Patron.compare(b,midSession);

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
		if (key.equals("Patrons"))
			return patrons;
		else
		if (key.equals("PatronList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public Patron retrieve(String patronNumber)
	{
		Patron retValue = null;
		for (int cnt = 0; cnt < patrons.size(); cnt++)
		{
			Patron nextPatron = (Patron)patrons.elementAt(cnt);
			String nextPatronNum = (String)nextPatron.getState("PatronNumber");
			if (nextPatronNum.equals(patronNumber) == true)
			{
				retValue = nextPatron;
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
        public void findPatronsOlderThanDate (String date)
        {
          String select = "SELECT * FROM Patron WHERE (dateOfBirth < " + date + ");";
          Vector v = this.getSelectQueryResult(select);
          print(v);
           
        }
       //-------------------------------------------------------------------------------------

        public void findPatronsYoungerThanDate (String date)
        {
          String select = "SELECT * FROM Patron WHERE (dateOfBirth > " + date + ");";
          Vector v = this.getSelectQueryResult(select);
          print(v);
           

        }

       //-------------------------------------------------------------------------------------

        public void findPatronsWithNameLike (String name)
        {
           String select = "SELECT * FROM Patron WHERE (name LIKE '%" + name + "%');";
           Vector v = this.getSelectQueryResult(select);
           print(v);
        }       

       //-------------------------------------------------------------------------------------
        
        public void findPatronsatZipcode (String zip)
        {
           String select = "SELECT * FROM Patron WHERE (zip =" + zip + ");";
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
