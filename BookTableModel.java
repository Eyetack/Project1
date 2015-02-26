package userinterface;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

//==============================================================================
public class BookTableModel extends AbstractTableModel implements TableModel
{
	private Vector myState;

	public BookTableModel(Vector bookData)
	{
		myState = bookData;
	}

	//--------------------------------------------------------------------------
	public int getColumnCount()
	{
		return 5;
	}

	//--------------------------------------------------------------------------
	public int getRowCount()
	{
		return myState.size();
	}

	//--------------------------------------------------------------------------
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Vector book = (Vector)myState.elementAt(rowIndex);
		return "    " + book.elementAt(columnIndex);
	}

	//--------------------------------------------------------------------------
	public String getColumnName(int columnIndex)
	{
		if(columnIndex == 0)
			return "Book ID";
		else
		if(columnIndex == 1)
			return "Author";
		else
		if(columnIndex == 2)
			return "Title";
		else
		if(columnIndex == 3)
			return "Publication Year";

		else
		if(columnIndex == 4)
			return "Status";		

		else
			return "??";
	}
}
