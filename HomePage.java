/*
Before executing all the program make sure you have installed Oracle Database and properly set your classpath
Then you have to create following tables:
1. register (name,mob,work,password)
2. user_table (mob,name,tableinfo)
3. category_track(name,mob)
*/
package track;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class HomePage extends JFrame implements ActionListener 
{
    JLabel heading, user, password, image;
    JTextField t1user;
    JPasswordField t2pass;
    JButton login, signup;
	String result;

    HomePage() 
	{
        getContentPane().setBackground(Color.yellow);
        setLayout(null);
        setSize(1000, 500);
        setLocation(250, 150);
		
		//inserting image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("homepage.jpg"));
        image = new JLabel(i1);
        image.setBounds(0, 0, 500, 500);
        add(image);
		
		heading = new JLabel("EXPENSE TRACKER");
        heading.setBounds(600, 50, 300, 50);
        heading.setFont(new Font("ALGERIAN", Font.BOLD, 28));
        add(heading);

        user = new JLabel("MOBILE NO.");
        user.setBounds(675, 135, 200, 50);
        user.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
        user.setForeground(Color.RED);
        add(user);

        password = new JLabel("PASSWORD");
        password.setBounds(675, 228, 200, 50);
        password.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
        password.setForeground(Color.RED);
        add(password);

        t1user = new JTextField();
        t1user.setBounds(627, 178, 205, 25);
        t1user.setFont(new Font("Arial Black", Font.BOLD, 20));
        add(t1user);

        t2pass = new JPasswordField();
        t2pass.setBounds(627, 275, 205, 25);
        t2pass.setFont(new Font("Arial Black", Font.BOLD, 20));
        add(t2pass);

        signup = new JButton("SignUp");
        signup.setBounds(630, 330, 100, 30);
        signup.setBackground(new Color(30, 144, 254));
        signup.setFont(new Font("Arial Black", Font.BOLD, 15));
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);

        login = new JButton("LogIn");
        login.setBounds(736, 330, 100, 30);
        login.setBackground(new Color(30, 144, 254));
        login.setFont(new Font("Arial Black", Font.BOLD, 15));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        setVisible(true);
		
		//Tooltips
		login.setToolTipText("Click to Track your expenses");
		signup.setToolTipText("New Account! SignUp here");
    }

    public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() == login) 
		{
            String struser = t1user.getText();
            String pass1 = t2pass.getText();

			 try 
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				// Driver Load...
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
				//retrive password from database
				String strinput = "select PASSWORD from register where mob='" + struser + "'";
				Statement smt = con.createStatement();
				ResultSet set = smt.executeQuery(strinput);
				if (set.next()) 
				{
				   result = set.getString("PASSWORD");                        
				}
				con.close();
				//If result is null then there is not present registered mobile number
				if(result==null)
				{
					JOptionPane.showMessageDialog(this,"Invalid Username");
					setVisible(false);
					new HomePage();
				}
				else
				{
					//check the inputed password is same as database password or not
					if (pass1.equals(result)) 
					{
						setVisible(false);
						new MainPage(struser); // directing towards Main page
					} 
					//If not same
					else 
					{
						JOptionPane.showMessageDialog(this, "Invalid Password!");
						t2pass.setText("");
					}
				}
				
			} 
			catch (Exception ob) 
			{
				System.out.println(ob);
			} 
			
        }
        
        //for newly registration
        else if (e.getSource() == signup) 
		{
			setVisible(false);
			new Registration();
        }
        
    }

    public static void main(String args[]) 
	{
        new HomePage();
    }
}