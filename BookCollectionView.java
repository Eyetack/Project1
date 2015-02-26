package userinterface;

import impresario.IModel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import model.Book;
import model.BookCollection;

//==============================================================================
public class BookCollectionView extends View implements ActionListener, MouseListener
{
	protected JTable tableOfBooks;
	protected JButton cancelButton;
	protected JButton submitButton;

	protected MessageView statusLog;

	protected Vector booksVector;

	//--------------------------------------------------------------------------
	public BookCollectionView(IModel wsc)
	{
		super(wsc, "BookCollectionView");

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		booksVector = new Vector();

		add(createTitle());
		add(createDataEntryFields());
		add(createNavigationButtons());

		// Error message area
		add(createStatusLog("                                     "));

		populateFields();
	}

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}

	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{
		booksVector.removeAllElements();

		try
		{
			BookCollection bookCollection = (BookCollection)myModel.getState("BookList");

	 		Vector entryList = (Vector)bookCollection.getState("Books");
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true)
			{
				Book nextBook = (Book)entries.nextElement();
				Vector view = nextBook.getEntryListView();

				// add this list entry to the list
				booksVector.add(view);
			}
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
		}
	}

	//--------------------------------------------------------------------------
	protected JPanel createTitle()
	{
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(Box.createRigidArea(new Dimension(250,10)));

		JPanel temp_0 = new JPanel();
		temp_0.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel title_0 = new JLabel("Library System");
		Font myFont_1 = new Font("Helvetica", Font.BOLD, 18);
		title_0.setFont(myFont_1);
		temp_0.add(title_0);

		container.add(temp_0);

		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel title = new JLabel("LIST OF BOOKS");
		Font myFont = new Font("Helvetica", Font.BOLD, 15);
		title.setFont(myFont);
		temp.add(title);

		container.add(temp);
		return container;
	}

	//--------------------------------------------------------------------------
	protected JPanel createDataEntryFields()
	{
		JPanel entries = new JPanel();
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));

		JPanel tablePan = new JPanel();
		tablePan.setLayout(new FlowLayout(FlowLayout.CENTER));

		TableModel myData = new BookTableModel(booksVector);

		tableOfBooks = new JTable(myData);
		tableOfBooks.addMouseListener(this);
		tableOfBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableOfBooks.setPreferredScrollableViewportSize(new Dimension(300,100));

		TableColumn column;
		for(int i = 0; i < myData.getColumnCount(); i++)
		{
			column = tableOfBooks.getColumnModel().getColumn(i);
			column.setPreferredWidth(40);
		}
		// Renter First Name

		tablePan.add(tableOfBooks);

		JScrollPane scrollPane = new JScrollPane(tableOfBooks);
		tablePan.add(scrollPane);

		entries.add(tablePan);
		return entries;
	}

	//--------------------------------------------------------------------------
	protected JPanel createNavigationButtons()
	{
		JPanel temp = new JPanel();
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(5);
		f1.setHgap(25);
		temp.setLayout(f1);

		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		temp.add(submitButton);

		cancelButton = new JButton("Done");
		cancelButton.addActionListener(this);
		temp.add(cancelButton);

		return temp;
	}

	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}

	//--------------------------------------------------------------------------
	protected void processAction(EventObject evt)
	{
		statusLog.clearErrorMessage();
		if(evt.getSource() == cancelButton)
		{
			myModel.stateChangeRequest("CancelAccountList", null);
		}
		if(evt.getSource() == submitButton)
		{
			processAccountSelected();
		}
	}

	//--------------------------------------------------------------------------
	protected void processAccountSelected()
	{
		int selectedIndex = tableOfAccounts.getSelectedRow();
		if(selectedIndex >= 0)
		{
			Vector selectedAccount = (Vector)accountsVector.elementAt(selectedIndex);

			myModel.stateChangeRequest("AccountSelected", selectedAccount.elementAt(0));
		}
	}

	//--------------------------------------------------------------------------
	protected JPanel createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processAccountSelected();
		}
	}

	//----------- These are not used ------------------------------
	public void mousePressed(MouseEvent click) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
