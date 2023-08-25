package track;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
public class PopForAddCat extends JFrame implements ActionListener 
{
	JLabel enter;
	JTextField t1;
	JButton ok;
	String mob;
	PopForAddCat(String mob)
	{
		this.mob=mob;
		
		getContentPane().setBackground(Color.GRAY); // setting frame background
        setLayout(null); // we don't need the default layout...we design our own
        setSize(400, 100);
        setLocation(550, 350);
		
		enter = new JLabel("Enter:");
		enter.setBounds(10, 15, 100, 30);
		enter.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
		enter.setForeground(Color.BLACK);
		add(enter);
		
		t1 = new JTextField();
        t1.setBounds(60, 15, 250, 30);
        add(t1);
		
		ok = new JButton("ok");
		ok.setBounds(320, 20, 50, 20);
		ok.setBackground(new Color(30, 144, 254));
		ok.setFont(new Font("Arial Black", Font.BOLD, 10));
		ok.setForeground(Color.WHITE);
		ok.addActionListener(this);
		add(ok);
		
		setVisible(true);
	}
	 public void actionPerformed(ActionEvent e) 
	{
		String gettext = t1.getText();
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
			String strinput = "Insert into category_track values('" + gettext + "','" + mob + "')";//inserting new category
			Statement smt = con.createStatement();
			smt.executeUpdate(strinput);
			
			JOptionPane.showMessageDialog(this, "Successfully Added!");
			setVisible(false);
			new MainPage(mob);
		}
		catch(Exception ob)
		{
			System.out.println(ob);
		}
		
	}
	public static void main(String args[])
	{
		new PopForAddCat("");
	}
}