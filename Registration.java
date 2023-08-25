package track;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Registration extends JFrame implements ActionListener {
    JLabel heading, id, name, work, pass, conpass;
    JTextField t1id, t2name, t3pass, t4pass;
    JComboBox<String> cb1;
    JButton submit, reset, back;

    Registration() {
        getContentPane().setBackground(new Color(5, 80, 130)); // setting frame background
        setLayout(null); // we don't need the default layout...we design our own
        setSize(700, 500);
        setLocation(400, 200);

        heading = new JLabel("Registration");
        heading.setBounds(225, 5, 300, 80);
        heading.setFont(new Font("ENGRAVERS MT", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        add(heading);

        id = new JLabel("Enter Name: ");
        id.setBounds(135, 79, 200, 80);
        id.setFont(new Font("Arial Black", Font.BOLD, 12));
        id.setForeground(Color.WHITE);
        add(id);

        t1id = new JTextField();
        t1id.setBounds(270, 105, 200, 30);
        add(t1id);

        name = new JLabel("Mobile Number: ");
        name.setBounds(135, 120, 200, 80);
        name.setFont(new Font("Arial Black", Font.BOLD, 12));
        name.setForeground(Color.WHITE);
        add(name);

        t2name = new JTextField();
        t2name.setBounds(270, 145, 200, 30);
        add(t2name);

        work = new JLabel("Work As: ");
        work.setBounds(135, 160, 200, 80);
        work.setFont(new Font("Arial Black", Font.BOLD, 12));
        work.setForeground(Color.WHITE);
        add(work);
		
        String br[] = { "Business", "Govt.Job", "Pvt. Job", "Home Maker", "Student", "Other" };
        cb1 = new JComboBox<>(br);
        cb1.setBounds(270, 190, 200, 25);
        add(cb1);

        pass = new JLabel("Enter Password: ");
        pass.setBounds(135, 200, 200, 80);
        pass.setFont(new Font("Arial Black", Font.BOLD, 12));
        pass.setForeground(Color.WHITE);
        add(pass);

        t3pass = new JTextField();
        t3pass.setBounds(270, 225, 200, 30);
        add(t3pass);
		t3pass.setToolTipText("Password must have 8 character & mixture of alphabet and number");

        conpass = new JLabel("Confirm Password: ");
        conpass.setBounds(135, 240, 200, 80);
        conpass.setFont(new Font("Arial Black", Font.BOLD, 12));
        conpass.setForeground(Color.WHITE);
        add(conpass);

        t4pass = new JTextField();
        t4pass.setBounds(270, 265, 200, 30);
        add(t4pass);
		t4pass.setToolTipText("Password must be same");

        submit = new JButton("Submit >");
        submit.setBounds(385, 310, 100, 30);
        submit.setBackground(Color.YELLOW);
        submit.setFont(new Font("Arial Black", Font.BOLD, 12));
        submit.setForeground(Color.BLACK);
        submit.addActionListener(this);
        add(submit);

        reset = new JButton("Reset");
        reset.setBounds(320, 360, 100, 30);
        reset.setBackground(Color.RED);
        reset.setFont(new Font("Arial Black", Font.BOLD, 12));
        reset.setForeground(Color.WHITE);
        reset.addActionListener(this);
        add(reset);
		
		back = new JButton("< Back");
        back.setBounds(255, 310, 100, 30);
        back.setBackground(new Color(30, 144, 254));
        back.setFont(new Font("Arial Black", Font.BOLD, 12));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
        setVisible(true);
		
		//Tooltips
		submit.setToolTipText("Click to submit the registration form");
		reset.setToolTipText("Click to reset the form");
		back.setToolTipText("Click to return to the Login Page");

    }
    
    public void actionPerformed(ActionEvent e) 
	{
		//If user clicks reset button
        if (e.getSource() == reset) 
		{
            setVisible(false);
            new Registration();
        } 
		//If user clicks back button
		else if(e.getSource() == back)
		{
			setVisible(false);
			new HomePage();
		}
		//If user clicks submit button
		else 
		{
            String userName = t1id.getText().toUpperCase();
            String mobilenum = t2name.getText();
            String tableinfo = "A" + mobilenum;
            String wrk = cb1.getItemAt(cb1.getSelectedIndex());
            String pass1 = t3pass.getText();
			int lengthpass=pass1.length();//calculating the length of password
			int lengthmob=mobilenum.length();//calculating the length of mobile number
			if(lengthmob!=10)
			{
				JOptionPane.showMessageDialog(this,"Mobile number should be 10 number!");
				t3pass.setText("");
			}
			else if(lengthpass<8)
			{
				JOptionPane.showMessageDialog(this,"Password must have 8 character!");
				t3pass.setText("");
				t4pass.setText("");
			}
			else
			{
				boolean containsalpha=false;
				boolean containsdigit=false;
				
				for(int i=0;i<lengthpass;i++)
				{
					char ch=pass1.charAt(i);
					if(Character.isLetter(ch))//check password contains letter or not
					{
						containsalpha=true;
					}
					else if(Character.isDigit(ch))//check password contains number or not
					{
						containsdigit=true;
					}
					if(containsalpha && containsdigit)
					{
						break;
					}
				}
				if(containsalpha && containsdigit)//if password contains both alphabet and number
				{
					String conpass1 = t4pass.getText();
					if (pass1.equals(conpass1)) //checking password with confirm password
					{
						try 
						{
							Class.forName("oracle.jdbc.driver.OracleDriver");
							Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
							//inserting into register_abhi where we store name,mobile,work and password		
							String strinput = "Insert into register values('" + userName + "','" + mobilenum + "','" + wrk + "','" + pass1 + "')";
							Statement smt = con.createStatement();
							smt.executeUpdate(strinput);

							JOptionPane.showMessageDialog(this, "Successfully Registered!");
							
							//Creating a personal table using mobile number where we store all expense details
							String str = "Create table " + tableinfo + " (srNo number default 0 ,dated date not null,category varchar2(20) not null,amount number(7,2) not null,note varchar2(50))";
							PreparedStatement st = con.prepareStatement(str);
							st.executeUpdate();
							
							//After creating personal table that table info keep in another table named user_table where we store mobile no with respective table
							String info="insert into user_table values('" + mobilenum +"','" + userName + "','" + tableinfo + "')";
							PreparedStatement strinfo=con.prepareStatement(info);
							strinfo.executeUpdate();
							
							//Creating Category table using mobile number
							String strinput1 = "Insert into category_track values('Others',"+ mobilenum +")";
							Statement smt1 = con.createStatement();
							smt1.executeUpdate(strinput1);
							
							con.close();
							//After successfully registering redirect towards Homepage
							setVisible(false);
							new HomePage();
						} 
						catch (Exception ob) 
						{
							System.out.println(ob);
						}
					} 
					//If password and confirm password are not same
					else 
					{
						JOptionPane.showMessageDialog(this, "Passwords must be the same!");
						t3pass.setText("");
						t4pass.setText("");
					}
				}
				//If password doesnot contain alphabet or number
				else
				{
					JOptionPane.showMessageDialog(this,"Password must contain atleat one alphabet and one number!");
					t3pass.setText("");
					t4pass.setText("");
				}
			}
        }
    }

    public static void main(String args[]) {
        new Registration();
    }
}