package track;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
public class EditCat extends JFrame implements ActionListener 
{
	JLabel heading,avlcat;
	JTextField textField;
	JComboBox<String> cb1;
	String mob,catvalue;
	JButton addcat,delete,back,okButton;
	EditCat(String mob) {
		this.mob=mob;
        setLayout(null); // we don't need the default layout...we design our own
        setSize(500, 400);
        setLocation(500, 200);

        heading = new JLabel("Edit Category");
        heading.setBounds(165, 0, 200, 80);
        heading.setFont(new Font("ERAS BOLD ITC", Font.BOLD, 18));
        heading.setForeground(Color.RED);
        add(heading);
		
		avlcat=new JLabel("Available Category: ");
		avlcat.setBounds(10, 50, 250, 80);
        avlcat.setFont(new Font("Calibri", Font.BOLD, 16));
        avlcat.setForeground(Color.BLUE);
        add(avlcat);
		cb1 = new JComboBox<>();
		cb1.setBounds(180, 75, 200, 30);
		add(cb1);
		
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
			
			//Retrieving existing category
			String strinput = "select  NAME from category_track where mob="+ mob +"";
			Statement smt = con.createStatement();
			ResultSet set = smt.executeQuery(strinput);
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
			while (set.next()) 
			{
				String name1 = set.getString("NAME");
				model.addElement(name1);//adding category to dropdown list
			}
			cb1.setModel(model);
			con.close();
		}
		catch(Exception ob)
		{
			System.out.println(ob);
		}
		
		addcat = new JButton("ADD +");
		addcat.setBounds(200, 300, 90, 30);
		addcat.setBackground(new Color(10,240,40));
		addcat.setFont(new Font("Arial Black", Font.BOLD, 10));
		addcat.setForeground(Color.WHITE);
		addcat.addActionListener(this);
		add(addcat);
		
		delete = new JButton("Delete -");
		delete.setBounds(335, 300, 90, 30);
		delete.setBackground(Color.RED);
		delete.setFont(new Font("Arial Black", Font.BOLD, 10));
		delete.setForeground(Color.WHITE);
		delete.addActionListener(this);
		add(delete);
		
		back = new JButton("< Back");
		back.setBounds(60, 300, 90, 30);
		back.setBackground(new Color(30, 144, 254));
		back.setFont(new Font("Arial Black", Font.BOLD, 10));
		back.setForeground(Color.WHITE);
		back.addActionListener(this);
		add(back);
		
		setVisible(true);		
	}
	
	 public void actionPerformed(ActionEvent e) 
	 {
        if (e.getSource() == addcat)//If user want to add some new category 
		{
            setVisible(false);
            new PopForAddCat(mob);
        } 
		else if (e.getSource() == delete) //if user wants to delete some existing category
		{
            catvalue = cb1.getItemAt(cb1.getSelectedIndex());
            try 
			{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
                String strinput99 = "Delete from category_track where name='" + catvalue + "' and mob='" + mob + "'";//deleting
                Statement smt99 = con.createStatement();
                smt99.executeUpdate(strinput99);

                JOptionPane.showMessageDialog(this, "Removed!");
                setVisible(false);
                new MainPage(mob);
                con.close();
            } catch (Exception ob) 
			{
                System.out.println(ob);
            }
        } 
		else 
		{
			setVisible(false);
			new MainPage(mob);
		}

	 }
	
	public static void main(String args[])
	{
		new EditCat("");
	}
}