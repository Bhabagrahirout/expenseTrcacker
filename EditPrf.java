package track;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class EditPrf extends JFrame implements ActionListener {
    JLabel mobile, pass, newpass, conpass;
    JTextField t1mob, t2pass, t3newpass, t4conpass;
    JButton enable, update;
    static String mob, result = "", passin, newin, newcon, mobin, strtonum, str, result1;

    EditPrf(String mob) {
        this.mob = mob;
        setLayout(null); // we don't need the default layout...we design our own
        setSize(450, 350);
        setLocation(500, 200);
		
		mobile = new JLabel("Change Password");
        mobile.setBounds(150, 20, 200, 30);
        mobile.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
        mobile.setForeground(Color.RED);
        add(mobile);

        pass = new JLabel("Old Password:");
        pass.setBounds(10, 80, 200, 30);
        pass.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        pass.setForeground(Color.BLACK);
        add(pass);

        t2pass = new JTextField();
        t2pass.setBounds(150, 80, 200, 30);
        add(t2pass);

        newpass = new JLabel("New Password:");
        newpass.setBounds(10, 145, 200, 30);
        newpass.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        newpass.setForeground(Color.BLACK);
        add(newpass);

        t3newpass = new JTextField();
        t3newpass.setBounds(150, 145, 200, 30);
        add(t3newpass);

        conpass = new JLabel("Confirm Password:");
        conpass.setBounds(10, 215, 200, 30);
        conpass.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        conpass.setForeground(Color.BLACK);
        add(conpass);

        t4conpass = new JTextField();
        t4conpass.setBounds(150, 215, 200, 30);
        add(t4conpass);

        update = new JButton("Update");
        update.setBounds(210, 260, 80, 30);
        update.setBackground(new Color(30, 144, 254));
        update.setFont(new Font("Arial Black", Font.BOLD, 10));
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        add(update);

        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) 
	{                  
		passin = t2pass.getText();
		newin = t3newpass.getText();
		newcon = t4conpass.getText();
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","Bhaba@123");
			
			//Retrieving password 
			String strinput = "SELECT PASSWORD FROM register WHERE mob=?";
			PreparedStatement pstmt = con.prepareStatement(strinput);
			pstmt.setString(1, mob);
			ResultSet set = pstmt.executeQuery();
			if (set.next()) 
			{
				result = set.getString("PASSWORD");
			}
			con.close();
		} 
		catch (Exception ob) 
		{
			System.out.println(ob);
		}
		if (passin.equals(result))//check for old password is correct or not 
		{
			if (newin.equals(newcon)) //check for new password and confirm password are same or not
			{
				try 
				{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Bhaba@123");
					
					//Updating password
					String strinput99 = "UPDATE register SET password=? WHERE mob=?";
					PreparedStatement pstmt99 = con.prepareStatement(strinput99);
					pstmt99.setString(1, newcon);
					pstmt99.setString(2, mob);
					pstmt99.executeUpdate();
					con.close();
				} 
				catch (Exception ob) 
				{
					System.out.println(ob);
				}
				JOptionPane.showMessageDialog(this, "Password updated successfully!");
				setVisible(false);
				new MainPage(mob);
			} 
			else//if new password and confirm password are not same 
			{
				JOptionPane.showMessageDialog(this, "Passwords must be the same!");
				t3newpass.setText("");
				t4conpass.setText("");
			}
		} 
		else //if the old password is not correct one
		{
			JOptionPane.showMessageDialog(this, "Invalid Password!");
			t2pass.setText("");
		}        
    }

    public static void main(String args[]) {
        new EditPrf("");
    }
}