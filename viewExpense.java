package track;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class viewExpense extends JFrame implements ActionListener
{
	String mobileno,res_tableinfo,sorting;
	JLabel sort;
	JComboBox<String> cb1;
	JTable table;
	JButton ok,back;
	DefaultTableModel model;
	JScrollPane scroll;
	viewExpense(String mobileno)
	{
		this.mobileno=mobileno;
		try 
		{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Bhaba@123");
            String strinput1 = "select TABLEINFO from user_table where mob='" + mobileno + "'";
            Statement smt1 = con.createStatement();
            ResultSet set1 = smt1.executeQuery(strinput1);
            if (set1.next()) 
			{
                res_tableinfo = set1.getString("TABLEINFO");
            }
            con.close();
        } 
		catch (Exception ob) 
		{
            System.out.println(ob);
        }
		
		getContentPane().setBackground(Color.GRAY);
        setTitle("Your Expense Details:");
		setLayout(null);
        setLocation(300, 100);
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		sort = new JLabel("Sort by:  ");
        sort.setBounds(600, 0, 200, 50);
        sort.setFont(new Font("Arial Black", Font.BOLD, 12));
        sort.setForeground(Color.RED);
        add(sort);
		String br[] = { "Category","Date" };
        cb1 = new JComboBox<>(br);
        cb1.setBounds(699, 11, 150, 22);
		cb1.setBackground(Color.WHITE);
        add(cb1);
		
		ok = new JButton("ok");
        ok.setBounds(870, 10, 50, 20);
        ok.setBackground(new Color(30, 144, 254));
        ok.setFont(new Font("Arial Black", Font.BOLD, 10));
        ok.setForeground(Color.WHITE);
        ok.addActionListener(this);
        add(ok);
		
		back= new JButton("Back");
		back.setBounds(10, 10, 68, 20);
		back.setBackground(new Color(30, 144, 254));
		back.setFont(new Font("Arial Black", Font.BOLD, 10));
		back.setForeground(Color.WHITE);
		back.addActionListener(this);
        add(back);
		
		model = new DefaultTableModel();
		String[] columnNames = { "Date", "Category", "Amount", "Note" };
		model.setColumnIdentifiers(columnNames);

		table = new JTable();
		table.setModel(model);			
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(0, 50, 1000, 610);
		add(scroll);
        setVisible(true);

	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==ok)//operation on ok button
		{
			sorting= cb1.getItemAt(cb1.getSelectedIndex());
			//erasing table
			model.getDataVector().removeAllElements();
			revalidate();
			if(sorting.equals("Category"))//if user select sort by category
			{
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Bhaba@123");
					String sql = "SELECT * FROM " + res_tableinfo + " order by CATEGORY";//retrieving data order by category
					Statement smt = con.createStatement();
					ResultSet rs = smt.executeQuery(sql);
					while (rs.next()) {
						String d = rs.getString("dated");
						String c = rs.getString("category");
						String a = rs.getString("amount");
						String n = rs.getString("note");
						model.addRow(new Object[] { d, c, a, n });
					}
					smt.close();
					con.close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			else  //if user select sort by date
			{
				try 
				{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Bhaba@123");
					String sql = "SELECT * FROM " + res_tableinfo + "";
					Statement smt = con.createStatement();
					ResultSet rs = smt.executeQuery(sql);
					while (rs.next()) 
					{
						String d = rs.getString("dated");
						String c = rs.getString("category");
						String a = rs.getString("amount");
						String n = rs.getString("note");
						model.addRow(new Object[] { d, c, a, n });
					}
					smt.close();
					con.close();
				} 
				catch (Exception ee) 
				{
					ee.printStackTrace();
				}
			}
		}
		else
		{
			setVisible(false);
			new MainPage(mobileno);
		}
		
	}
	public static void main(String args[])
	{
		new viewExpense("");
	}
}