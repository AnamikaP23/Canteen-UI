//Manager UI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
class Add
{
    Add()
    {
        JFrame f3=new JFrame();
        f3.setTitle("Add Food");
        f3.setLayout(null);
        f3.setSize(375,812);
        f3.setVisible(true);
        JTextField id=new JTextField(30);
        id.setText("ID");
        id.setBounds(120,200,100,50);
        f3.add(id);
        JTextField name=new JTextField(50);
        name.setText("Name");
        name.setBounds(120,300,100,50);
        f3.add(name);
        JTextField cost=new JTextField(50);
        cost.setText("Price");
        cost.setBounds(120,400,100,50);
        f3.add(cost);
        JButton don=new JButton("Done");
        don.setBounds(120,500,100,20);
        f3.add(don);
        don.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int f=-1;
                    int i=Integer.parseInt(id.getText());
                    String n=name.getText();
                    float c=Float.parseFloat(cost.getText());
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery("select * from menu");
                    while(rs.next())
                    {
                        if((rs.getInt(1)==i) || (rs.getString(2).equalsIgnoreCase(n)))
                        {
                             id.setText("ID");
                             name.setText("Name");
                             cost.setText("Price");
                             f=1;
                             break;
                        }
                    }
                    if(f==-1)
                    {
                        String sql = "insert into menu(foodid, fname, price) values(?, ?, ?)"; 
                        PreparedStatement statement = con.prepareStatement(sql); 
                        statement.setInt(1,i); 
                        statement.setString(2,n); 
                        statement.setFloat(3,c);
                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) 
                        {
                            f3.dispose();
                        }                       
                    }
                    con.close();
                }
                catch(Exception ex)
                {
                    //System.out.println(ex);
                }
            }
         });
    }
}
class Update
{
    Update(String text)
    {
        JFrame f4=new JFrame();
        f4.setTitle("Update Food");
        f4.setLayout(null);
        f4.setSize(375,812);
        f4.setVisible(true);
        JTextField id=new JTextField(30);
        id.setBounds(120,200,100,50);
        f4.add(id);
        JTextField name=new JTextField(50);
        name.setBounds(120,300,100,50);
        f4.add(name);
        JTextField cost=new JTextField(50);
        cost.setBounds(120,400,100,50);
        f4.add(cost);
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from menu");
            while(rs.next())
            {
                if(rs.getString(2).equals(text))
                {
                    id.setText(String.valueOf(rs.getInt(1)));
                    name.setText(text);
                    cost.setText(String.valueOf(rs.getFloat(3)));
                    break;
                }
            }
            con.close();
        }
        catch(Exception ex)
        {
            //System.out.println(ex);
        }
        JButton don=new JButton("Done");
        don.setBounds(120,500,100,20);
        f4.add(don);
        don.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int i=Integer.parseInt(id.getText());
                    String n=name.getText();
                    float c=Float.parseFloat(cost.getText());
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
                    String sql = "UPDATE menu SET foodid=?,fname=?,price=? WHERE fname=?"; 
                    PreparedStatement statement = con.prepareStatement(sql); 
                    statement.setInt(1,i); 
                    statement.setString(2,n); 
                    statement.setFloat(3,c);
                    statement.setString(4,text);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) 
                    {
                        f4.dispose();
                    }                       
                    con.close();
                }
                catch(Exception ex)
                {
                    //System.out.println(ex);
                }
            }
         });
    }
}
class Order
{
    Order()
    {
        JFrame f5=new JFrame();
        f5.setTitle("Order Details");
        f5.setLayout(null);
        f5.setSize(375,812);
        f5.setVisible(true);
        try
        {
            int i=15+2;
            JLabel a1=new JLabel("TOKEN_NO - NAME");
            JLabel a2=new JLabel("FNAME - QTY - AMT_PAID");
            JLabel a3=new JLabel("ORDER_TIME");
            JLabel a4=new JLabel("DELIVERED");
            JLabel a5=new JLabel("");
            f5.add(a1);
            f5.add(a2);
            f5.add(a3);
            f5.add(a4);
            f5.add(a5);
            a1.setBounds(5,15+2,350,15);
            i+=(15+2);
            a2.setBounds(5,5+i,350,15);
            i+=(15+2);
            a3.setBounds(5,5+i,350,15);
            i+=(15+2);
            a4.setBounds(5,5+i,350,15);
            i+=(15+2);
            a5.setBounds(5,5+i,350,15);
            i+=(15+2);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT  o.TOKEN_NO, s.NAME, m.FNAME, o.QTY, o.AMT_PAID, o.ORDER_TIME, o.DELIVERED FROM orders o JOIN students s ON o.REGNO = s.REGNO JOIN menu m ON o.FOODID = m.FOODID ORDER BY o.TOKEN_NO;");
            rs.next();
            while(rs.next())
            {
                JLabel b1=new JLabel(String.valueOf(rs.getInt(1))+" - "+rs.getString(2));
                JLabel b2=new JLabel(rs.getString(3)+" - "+String.valueOf(rs.getInt(4))+" - "+String.valueOf(rs.getFloat(5)));
                JLabel b3=new JLabel(rs.getString(6));
                JLabel b4=new JLabel(rs.getString(7));
                JLabel b5=new JLabel("");
                f5.add(b1);
                f5.add(b2);
                f5.add(b3);
                f5.add(b4);
                f5.add(b5);
                b1.setBounds(5,5+i,350,15);
                i+=(15+2);
                b2.setBounds(5,5+i,350,15);
                i+=(15+2);
                b3.setBounds(5,5+i,350,15);
                i+=(15+2);
                b4.setBounds(5,5+i,350,15);
                i+=(15+2);
                b5.setBounds(5,5+i,350,15);
                i+=(15+2);
            }
            con.close();
        }
        catch(Exception ex)
        {
            //System.out.println(ex);
        }
    }
}
class Menu implements ActionListener
{
    JFrame f2=new JFrame();
    JButton ad=new JButton("ADD");
    JButton o=new JButton("ORDERS");
    Menu()
    {
        ad.setBounds(10,10,100,20);
        f2.add(ad);
        ad.addActionListener(this);
        o.setBounds(150,10,100,20);
        f2.add(o);
        o.addActionListener(this);
        try
        {
            int i=2;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from menu");
            while(rs.next())
            {
                JButton x=new JButton(rs.getString(2));
                x.setBounds(10,10+(20*i),300,20);
                i++;
                f2.add(x);
                x.addActionListener(this);
            }
            con.close();
        }
        catch(Exception ex)
        {
            //System.out.println(ex);
        }
        f2.setTitle("Menu Change");
        f2.setLayout(null);
        f2.setSize(375,812);
        f2.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        Object src=e.getSource();
        JButton btn = (JButton) src;
        String text = btn.getText(); 
        if(text.equals("ADD"))
        {
            new Add();
        }
        else if(text.equals("ORDERS"))
        {
            new Order();
        }
        else
        {
            new Update(text);
        }
    }
}
class Login
{
    Login()
    {
        JFrame f1=new JFrame();
        f1.setTitle("Manager Login");
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
                if(n.equals("Manager") && p.equals("123"))
                {
                    new Menu();
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
public class Manager {
    public static void main(String args[])
    {
        new Login();
    }
}