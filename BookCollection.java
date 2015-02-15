// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;
import userinterface.MainFrame;

//==============================================================
public class BookCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Book";

	private Vector books;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public BookCollection() throws
		Exception
	{
		super(myTableName);
                Vector books = new Vector();
		
	}

	//----------------------------------------------------------------------------------
	private void addBook(Book b)
	{
		//users.add(u);
		int index = findIndexToAdd(b);
		books.insertElementAt(b,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(Book b)
	{
		//users.add(u);
		int low=0;
		int high = books.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Book midSession = (Book)books.elementAt(middle);

			int result = Book.compare(b,midSession);

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
		if (key.equals("Books"))
			return books;
		else
		if (key.equals("BookList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public Book retrieve(String bookNumber)
	{
		Book retValue = null;
		for (int cnt = 0; cnt < books.size(); cnt++)
		{
			Book nextBook = (Book)books.elementAt(cnt);
			String nextBookNum = (String)nextBook.getState("BookNumber");
			if (nextBookNum.equals(bookNumber) == true)
			{
				retValue = nextBook;
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
        public void findBooksOlderThanDate (String year)
        {
          String select = "SELECT * FROM Book WHERE (pubYear < " + year + ");";
          Vector v = this.getSelectQueryResult(select);
          print(v);
           
        }
       //-------------------------------------------------------------------------------------

        public void findBooksNewerThanDate (String year)
        {
          String select = "SELECT * FROM Book WHERE (pubYear > " + year + ");";
          Vector v = this.getSelectQueryResult(select);
          print(v);
           

        }

       //-------------------------------------------------------------------------------------

        public void findBooksWithTitleLike (String title)
        {
           String select = "SELECT * FROM Book WHERE (title LIKE '%" + title + "%');";
           Vector v = this.getSelectQueryResult(select);
           print(v);
        }       

       //-------------------------------------------------------------------------------------
        
        public void findBooksWithAuthorLike (String author)
        {
           String select = "SELECT * FROM Book WHERE (author LIKE '%" + author + "%');";
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
