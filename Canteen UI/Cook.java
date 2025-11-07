//Cook UI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
class Deliv implements ActionListener
{
    int t=0;
    Deliv()
    {
        JFrame f5=new JFrame();
        f5.setTitle("Deliver Order");
        f5.setLayout(null);
        f5.setSize(375,812);
        f5.setVisible(true);
        try
        {
            int i=15+2;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT  o.TOKEN_NO, m.FNAME, o.QTY, o.DELIVERED FROM orders o JOIN menu m ON o.FOODID = m.FOODID ORDER BY o.TOKEN_NO;");
            rs.next();
            while(rs.next())
            {
                if(String.valueOf(rs.getString(4)).equals("NO"))
                {
                    t=rs.getInt(1);
                    JLabel b1=new JLabel("Token No. "+String.valueOf(t));
                    JLabel b2=new JLabel(rs.getString(2)+" ("+String.valueOf(rs.getInt(3))+")");
                    JButton b5=new JButton(String.valueOf(t));
                    f5.add(b1);
                    f5.add(b2);
                    f5.add(b5);
                    b5.setSize(15,15);
                    b1.setBounds(5,5+i,350,15);
                    i+=(15+2);
                    b2.setBounds(5,5+i,350,15);
                    i+=(15+2);
                    b5.setBounds(5,5+i,50,15);
                    i+=(15+10);
                    b5.addActionListener(this);
                }
            }
            con.close();
        }
        catch(Exception ex)
        {
            //System.out.println(ex);
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            Object src=e.getSource();
            JButton btn = (JButton) src;
            String text = btn.getText();
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
            String sql = "UPDATE orders SET DELIVERED='YES' WHERE TOKEN_NO=?"; 
            PreparedStatement statement = conn.prepareStatement(sql); 
            statement.setInt(1,Integer.parseInt(text));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) 
            {
                btn.setText("✔");
                btn.setEnabled(false);
            }                       
            conn.close();
        }
        catch(Exception ex)
        {
            //System.out.println(ex);
        }
    }
}
class Login
{
    Login()
    {
        JFrame f1=new JFrame();
        f1.setTitle("Cook Login");
        JTextField name=new JTextField(50);
        name.setText("Name");
        name.setBounds(120,200,100,50);
        f1.add(name);
        JTextField pass=new JTextField(30);
        pass.setText("Password");
        pass.setBounds(120,300,100,50);
        f1.add(pass);
        JButton log=new JButton("Login");
        log.setBounds(120,500,100,20);
        f1.add(log);
        log.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String n=name.getText();
                String p=pass.getText();
                JLabel err=new JLabel("Error");
                err.setBounds(120,400,100,20);
                if(n.equals("Cook") && p.equals("000"))
                {
                    new Deliv();
                }
                else
                {
                    name.setText("Name");
                    pass.setText("Password");
                }
            }
         });
        f1.setLayout(null);
        f1.setSize(375,812);
        f1.setVisible(true);
     }
}
public class Cook 
{
    public static void main(String args[])
    {
        new Login();
    }
}