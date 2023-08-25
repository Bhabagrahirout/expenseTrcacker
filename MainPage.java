package track;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainPage extends JFrame implements ActionListener 
{
    JLabel heading,date,curex,curval,last,addex,dateval,cat,amt,note,set;
    JTextField t1date, t2amt, t3note;
	JComboBox<String> cb1;
    JButton addbut, addcat,edit,editprf,lgot,view;
	DefaultTableModel model;
	JScrollPane scroll;
	JTable table;
	public String mobno,result,datevalue,catvalue,amount,notevalue,sysdate,res_tableinfo,result2,result3,sysdate1,sysdate2,result4;
    MainPage(String mobno) 
	{
		this.mobno=mobno;
		//name.setText(mobno);
        getContentPane().setBackground(new Color(5, 175, 172)); // setting frame background
        setLayout(null); // we don't need the default layout...we design our own
        setSize(900, 600);
        setLocation(300, 100);
		try 
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				// Driver Load...
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
				//retrive password from database
				String strinput = "select NAME from register where mob='" + mobno + "'";
				Statement smt = con.createStatement();
				ResultSet set = smt.executeQuery(strinput);
				if (set.next()) 
				{
					result = set.getString("NAME");					
				}
				
				//Retrieving tablename 
				String strinput1 = "select TABLEINFO from user_table where mob='" + mobno + "'";
				Statement smt1 = con.createStatement();
				ResultSet set1 = smt1.executeQuery(strinput1);
				if (set1.next()) 
				{
					res_tableinfo = set1.getString("TABLEINFO");				
				}
				
				//Displaying current date in the format: dd-MMM-yyyy
				LocalDate currentDate1 = LocalDate.now();
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
				sysdate1 = currentDate1.format(formatter1);
		 
				int monval=currentDate1.getMonthValue();
				String monthval=Integer.toString(monval);
		 
				LocalDate currentDate2 = LocalDate.now();
				DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("YYYY");
				sysdate2 = currentDate2.format(formatter2);
				
				
				//Retrieving total expense amount of current month
				String strinput3 = "select SUM(AMOUNT) AS TOTAMOUNT from "+ res_tableinfo +" where EXTRACT(MONTH FROM DATED)="+monval+" and EXTRACT(YEAR FROM DATED)="+sysdate2+"";
				Statement smt3 = con.createStatement();
				ResultSet set3 = smt1.executeQuery(strinput3);
				
				if (set3.next()) 
				{
					result3 = set3.getString("TOTAMOUNT");
				
				}			
				con.close();
			} 
			catch (Exception ob) 
			{
				System.out.println(ob);
			}
		// Split the full name by whitespace
        String[] nameParts = result.split("\\s+");

        // Extract the first name (assuming it's the first part of the split)
        String firstName = nameParts[0];

        heading = new JLabel("Welcome "+firstName+"!");
        heading.setBounds(365, 5, 300, 80);
        heading.setFont(new Font("Arial Bold", Font.BOLD, 20));
        heading.setForeground(Color.YELLOW);
        add(heading);
		
		LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
         sysdate = currentDate.format(formatter);
		
		date = new JLabel("Date: "+sysdate);
        date.setBounds(695, 25, 200, 80);
        date.setFont(new Font("OCR A Extended", Font.BOLD, 14));
        date.setForeground(Color.WHITE);
        add(date);
		
		curex = new JLabel("Total Expenditure this month");
        curex.setBounds(75, 65, 200, 80);
        curex.setFont(new Font("Calibri", Font.BOLD, 16));
        curex.setForeground(Color.BLACK);
        add(curex);
		
		curval = new JLabel("Rs."+result3);
        curval.setBounds(95, 95, 100, 80);
        curval.setFont(new Font("Calibri", Font.BOLD, 18));
        curval.setForeground(new Color(255, 64, 0));
		add(curval);
		
		last = new JLabel("Last Expenditure");
        last.setBounds(75, 135, 200, 80);
        last.setFont(new Font("Calibri", Font.BOLD, 16));
        last.setForeground(Color.BLACK);
        add(last);
		
		addex = new JLabel("Add new Expenditure");
        addex.setBounds(75, 225, 200, 80);
        addex.setFont(new Font("Calibri", Font.BOLD, 16));
        addex.setForeground(Color.BLACK);
        add(addex);
		
        dateval = new JLabel("Date: ");
        dateval.setBounds(75, 255, 200, 80);
        dateval.setFont(new Font("Calibri", Font.BOLD, 16));
        dateval.setForeground(Color.BLACK);
        add(dateval);
		
		t1date = new JTextField(sysdate);
        t1date.setBounds(183, 282, 150, 20);
		t1date.setFont(new Font("Arial Black", Font.BOLD, 14));
        add(t1date);
		t1date.setEnabled(false);
		
		cat = new JLabel("Category: ");
        cat.setBounds(75, 300, 200, 80);
        cat.setFont(new Font("Calibri", Font.BOLD, 16));
        cat.setForeground(Color.BLACK);
        add(cat);
		
        cb1 = new JComboBox<>();
        cb1.setBounds(183, 327, 150, 22);
		cb1.setBackground(Color.WHITE);
        add(cb1);
		
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Bhaba@123");
			
			//Retrieving existing category name associated with mobile number
			String strinput101 = "select NAME from category_track where mob="+ mobno +"";
			Statement smt101 = con.createStatement();
			ResultSet set101 = smt101.executeQuery(strinput101);
			//Adding category to drop down list
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
			while (set101.next()) 
			{
				String mobno01 = set101.getString("NAME");
				model.addElement(mobno01);
			}
			cb1.setModel(model);
			con.close();
		} 
		catch (Exception ob) 
		{
			System.out.println(ob);
		}
		
		amt = new JLabel("Amount: ");
        amt.setBounds(75, 345, 200, 80);
        amt.setFont(new Font("Calibri", Font.BOLD, 16));
        amt.setForeground(Color.BLACK);
        add(amt);
		
		t2amt = new JTextField();
        t2amt.setBounds(183, 369, 150, 23);
		t2amt.setFont(new Font("Arial Black", Font.BOLD, 14));
        add(t2amt);
		
		note = new JLabel("Add Note(opt): ");
        note.setBounds(75, 390, 200, 80);
        note.setFont(new Font("Calibri", Font.BOLD, 16));
        note.setForeground(Color.BLACK);
        add(note);
		
		t3note = new JTextField();
        t3note.setBounds(183, 415, 150, 23);
		t3note.setFont(new Font("Arial Black", Font.BOLD, 14));
        add(t3note);
		
		addbut = new JButton("ADD");
        addbut.setBounds(220, 457, 70, 30);
        addbut.setBackground(Color.ORANGE);
        addbut.setFont(new Font("Arial Black", Font.BOLD, 12));
        addbut.setForeground(Color.WHITE);
        addbut.addActionListener(this);
        add(addbut);
		
		set = new JLabel("Settings");
        set.setBounds(760, 340, 200, 80);
        set.setFont(new Font("Calibri", Font.BOLD, 16));
        set.setForeground(Color.WHITE);
        add(set);
		
		addcat = new JButton("Edit Category");
        addcat.setBounds(720, 390, 150, 30);
        addcat.setBackground(Color.ORANGE);
        addcat.setFont(new Font("Arial Black", Font.BOLD, 12));
        addcat.setForeground(Color.WHITE);
        addcat.addActionListener(this);
        add(addcat);
		
		edit = new JButton("Edit Expenditure");
        edit.setBounds(720, 430, 150, 30);
        edit.setBackground(Color.ORANGE);
        edit.setFont(new Font("Arial Black", Font.BOLD, 12));
        edit.setForeground(Color.WHITE);
        edit.addActionListener(this);
        add(edit);
		
		editprf = new JButton("Change Password");
        editprf.setBounds(720, 470, 150, 30);
        editprf.setBackground(Color.ORANGE);
        editprf.setFont(new Font("Arial Black", Font.BOLD, 11));
        editprf.setForeground(Color.WHITE);
        editprf.addActionListener(this);
        add(editprf);
		
		lgot = new JButton("Log out");
        lgot.setBounds(720, 510, 150, 30);
        lgot.setBackground(Color.ORANGE);
        lgot.setFont(new Font("Arial Black", Font.BOLD, 12));
        lgot.setForeground(Color.WHITE);
        lgot.addActionListener(this);
        add(lgot);
		
		view = new JButton("View Expenses");
        view.setBounds(720, 280, 150, 40);
        view.setBackground(Color.RED);
        view.setFont(new Font("Arial Black", Font.BOLD, 12));
        view.setForeground(Color.WHITE);
        view.addActionListener(this);
        add(view);
		
		//Creating a table structure where we display last expenditure 
		model = new DefaultTableModel();
		String[] columnNames = { "Date", "Category", "Amount", "Note" };
		model.setColumnIdentifiers(columnNames);

		table = new JTable();
		table.setModel(model);	

		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Bhaba@123");
			//retriving last expense on the basis of maximum serial no 
			String sql99 = "SELECT * FROM " + res_tableinfo + " where srNo=(select max(srNo) from "+res_tableinfo+")";
			Statement smt99 = con.createStatement();
			ResultSet rs99 = smt99.executeQuery(sql99);
			while(rs99.next()) {
				String d = rs99.getString("dated");
				String c = rs99.getString("category");
				String a = rs99.getString("amount");
				String n = rs99.getString("note");
				model.addRow(new Object[] { d, c, a, n });//Adding respective data to last expense
			}
			smt99.close();
			con.close();
		} 
		catch (Exception ee) 
		{
			ee.printStackTrace();
		}
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(55, 190, 800, 50);
		add(scroll);
		
		setVisible(true);
	}
	 public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() == addbut) //if user want to add expense
		{
			catvalue= cb1.getItemAt(cb1.getSelectedIndex());
			amount=t2amt.getText();
			notevalue=t3note.getText();
			try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
							
					String str = "select max(srNo) AS LASTSL from " +res_tableinfo+"";
					Statement smt3=con.createStatement();
					ResultSet set4 = smt3.executeQuery(str);
				//Retrieving last serial number
				if (set4.next()) 
				{
					result4 = set4.getString("LASTSL");
				}
				//if null then set zero
				if(result4==null)
				{
					result4="0";
				}
				int num=Integer.parseInt(result4);
				num=num+1;//adding one more serial number
				String sr=Integer.toString(num);
				
				//Adding one more expense to table					
				String strinput2 = "Insert into " + res_tableinfo + " values('"+sr+"','" + sysdate + "','" + catvalue + "','" + amount + "','" + notevalue + "')";
				Statement smt2 = con.createStatement();
				smt2.executeUpdate(strinput2);

				JOptionPane.showMessageDialog(this, "Successfully Added!");
				
				con.close();
				setVisible(false);
				new MainPage(mobno);
					
                } catch (Exception ob) {
                    System.out.println(ob);
                }
		}
		else if(e.getSource()==view)//if user want to see their total expense
		{
			setVisible(false);
			new viewExpense(mobno);
		}
		else if(e.getSource()==edit)//if user want to modify some existing expense details
		{
			setVisible(false);
			new EditExp(mobno);
		}
		else if(e.getSource()==lgot)//if user want to Log out
		{
			String options[]={"No","Yes"};
			int choice = JOptionPane.showOptionDialog(
                null,
                "Are you sure?",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
			);
			setVisible(false);
			if (choice == 0) 
			{
				new MainPage(mobno);
			} 
			else if (choice == 1) 
			{
				new HomePage();
			}
			
		}
		else if(e.getSource()==addcat)//if user want to modify category table 
		{
			setVisible(false);
			new EditCat(mobno);
		}
		else if(e.getSource()==editprf)//if user wants to change password
		{
			setVisible(false);
			new EditPrf(mobno);
		}
	}
	public static void main(String args[])
	{
		new MainPage("user");
	}
}