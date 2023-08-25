package track;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;

public class EditExp extends JFrame implements ActionListener{
	String mobileno,res_tableinfo,tempamt,tempcat,tempdate,tempnote,tempsl;
	JLabel l1date,l2category,l3amt,l4note;
	JTextField t1date,t2category,t3amt,t4note;
	JButton update,back;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	EditExp(String mobileno)
	{
		this.mobileno=mobileno;
		try 
		{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Bhaba@123");
			
			//Retrieving table name
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
		
		//creating a table strusture where we display all expenditure
		model = new DefaultTableModel();
        String[] columnNames = { "Sl.","Date", "Category", "Amount", "Note" };
        model.setColumnIdentifiers(columnNames);

        table = new JTable();
        table.setModel(model);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Bhaba@123");
			
			//Retrieving data 
            String sql = "SELECT * FROM " + res_tableinfo + "";
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery(sql);
            while (rs.next()) 
			{
				String sr= rs.getString("srNo");
                String d = rs.getString("dated");
                String c = rs.getString("category");
                String a = rs.getString("amount");
                String n = rs.getString("note");
                model.addRow(new Object[] {sr, d, c, a, n });//Displaying on display table
            }
            smt.close();
            con.close();
        } 
		catch (Exception ee) 
		{
            ee.printStackTrace();
        }
		
		l2category = new JLabel("Category: ");
        l2category.setBounds(640, 500, 200, 50);
        l2category.setFont(new Font("Arial Black", Font.BOLD, 12));
        l2category.setForeground(Color.RED);
        add(l2category);
		
		t2category= new JTextField();
        t2category.setBounds(720, 510, 200, 30);
        t2category.setFont(new Font("Arial black", Font.BOLD, 14));
        add(t2category);
		
		l1date = new JLabel("Date: ");
        l1date.setBounds(60, 500, 200, 50);
        l1date.setFont(new Font("Arial Black", Font.BOLD, 12));
        l1date.setForeground(Color.RED);
        add(l1date);
		
		t1date= new JTextField();
        t1date.setBounds(150, 510, 200, 30);
        t1date.setFont(new Font("Arial black", Font.BOLD, 14));
        add(t1date);
		
		l3amt = new JLabel("Amount: ");
        l3amt.setBounds(60, 550, 200, 50);
        l3amt.setFont(new Font("Arial Black", Font.BOLD, 12));
        l3amt.setForeground(Color.RED);
        add(l3amt);
		
		t3amt= new JTextField();
        t3amt.setBounds(150, 560, 200, 30);
        t3amt.setFont(new Font("Arial black", Font.BOLD, 14));
        add(t3amt);
		
		l4note = new JLabel("Note: ");
        l4note.setBounds(640, 550, 200, 50);
        l4note.setFont(new Font("Arial Black", Font.BOLD, 12));
        l4note.setForeground(Color.RED);
        add(l4note);
		
		t4note= new JTextField();
        t4note.setBounds(720, 560, 200, 30);
        t4note.setFont(new Font("Arial black", Font.PLAIN, 14));
        add(t4note);
		
		update = new JButton("Update");
        update.setBounds(520, 610, 80, 30);
        update.setBackground(new Color(30, 144, 254));
        update.setFont(new Font("Arial Black", Font.BOLD, 10));
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        add(update);
		
		back = new JButton("Back");
        back.setBounds(420, 610, 80, 30);
        back.setBackground(new Color(30, 144, 254));
        back.setFont(new Font("Arial Black", Font.BOLD, 10));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
		
		//While user click one row on the table, these respected data will store on respected textfield
		table.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e)
			{
				int i=table.getSelectedRow();
				tempsl=model.getValueAt(i,0).toString();
				tempdate=model.getValueAt(i,1).toString();
				tempcat=model.getValueAt(i,2).toString();
				tempamt=model.getValueAt(i,3).toString();
				tempnote=model.getValueAt(i,4).toString();
				t1date.setText(tempdate);
				t2category.setText(tempcat);
				t3amt.setText(tempamt);
				t4note.setText(tempnote);
			}
		});		

		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(0, 0, 1000, 480);
		add(scroll);
        setVisible(true);
		t1date.setEnabled(false);
	}
	
	 public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() == update) 
		{			
		String datevalue=t1date.getText();
		String catvalue=t2category.getText();
		String amountvalue=t3amt.getText();
		String notevalue=t4note.getText();
		
		  try 
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				// Driver Load...
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
				
				//updating expense
				String strinput = "update "+ res_tableinfo +" set CATEGORY='"+ catvalue +"',AMOUNT='"+ amountvalue +"',NOTE='"+ notevalue +"' where srNo='"+ tempsl +"' and CATEGORY='"+ tempcat +"' and AMOUNT='"+ tempamt +"' and NOTE='"+ tempnote +"'";
				Statement smt = con.createStatement();
				ResultSet set = smt.executeQuery(strinput);
				 JOptionPane.showMessageDialog(this, "Updated Successfully!");
				 setVisible(false);
				 new EditExp(mobileno);
				con.close();
			}
			catch(Exception ob)
			{
				System.out.println(ob);
			}
		}
		else if(e.getSource() == back)
		{
			setVisible(false);
			new MainPage(mobileno);
		}
	}
	public static void main(String args[])
	{
		new EditExp("");
	}
}