//Student UI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
class Order
{
    float cost=0;
    int t=1;
    int qn=1;
    int fd=0;
    Order(String text,String reg)
    {
        JFrame f4=new JFrame();
        f4.setTitle("Place Order");
        f4.setLayout(null);
        f4.setSize(375,812);
        f4.setVisible(true);
        JLabel q=new JLabel("Qty.");
        q.setBounds(100,100,100,50);
        f4.add(q);
        JTextField qty=new JTextField(30);
        qty.setText("1");
        qty.setBounds(100,200,100,50);
        f4.add(qty);
        JButton ord=new JButton("Order");
        ord.setBounds(100,300,100,20);
        f4.add(ord);
        ord.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
                    Statement stmt=con.createStatement();
                    PreparedStatement ps = con.prepareStatement("SELECT foodid,price FROM menu WHERE fname = ?");
                    ps.setString(1, text);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next())
                    {
                        qn=Integer.parseInt(qty.getText());
                        fd=rs.getInt(1);
                        cost=qn*rs.getFloat(2);
                        break;
                    }
                    JButton pay=new JButton("Pay : ₹ "+String.valueOf(cost));
                    pay.setBounds(20,400,300,20);
                    f4.add(pay);
                    f4.revalidate();
                    f4.repaint();
                    pay.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ee)
                        {
                            pay.setEnabled(false);
                            try 
                            {
                                Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc", "root", "Anamika12F");
                                Statement stmt2 = con2.createStatement();
                                ResultSet rs2 = stmt2.executeQuery("SELECT MAX(token_no) FROM orders");
                                while(rs2.next())
                                {
                                    t=rs2.getInt(1)+1;
                                    JLabel tok=new JLabel("Token no. "+String.valueOf(t));
                                    tok.setBounds(10,500,350,50);
                                    tok.setFont(new Font("Arial", Font.BOLD, 30));
                                    tok.setForeground(Color.RED); 
                                    f4.add(tok);
                                    f4.revalidate();
                                    f4.repaint();
                                    try
                                    {
                                        Class.forName("com.mysql.jdbc.Driver");
                                        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
                                        String sql = "insert into orders(TOKEN_NO, REGNO, FOODID, QTY, AMT_PAID) values(?, ?, ?, ?, ?);"; 
                                        PreparedStatement statement = conn.prepareStatement(sql); 
                                        statement.setInt(1,t); 
                                        statement.setString(2,reg);
                                        statement.setInt(3,fd);
                                        statement.setInt(4,qn); 
                                        statement.setFloat(5,cost);
                                        int rowsInserted = statement.executeUpdate();
                                        conn.close();
                                    }
                                    catch(Exception ex)
                                    {
                                        //System.out.println(ex);
                                    }
                                    break;
                                }
                                con2.close();
                            }
                            catch (Exception ex2) 
                            {
                                //System.out.println(ex2);
                            }
                        }
                    });
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
class Menu implements ActionListener
{
    JFrame f3=new JFrame();
    String reg;    
    Menu(String n)
    {
        this.reg=n;
        f3.setTitle("Menu");
        f3.setLayout(null);
        f3.setSize(375,812);
        f3.setVisible(true);
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
                f3.add(x);
                x.addActionListener(this);
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
        Object src=e.getSource();
        JButton btn = (JButton) src;
        String text = btn.getText();
        new Order(text,reg);
    }
}
class SignUp
{
    SignUp()
    {
        JFrame f2=new JFrame();
        f2.setTitle("Sign Up");
        f2.setLayout(null);
        f2.setSize(375,812);
        f2.setVisible(true);
        JTextField id=new JTextField(30);
        id.setText("Reg.No.");
        id.setBounds(120,200,100,50);
        f2.add(id);
        JTextField name=new JTextField(100);
        name.setText("Name");
        name.setBounds(120,300,100,50);
        f2.add(name);
        JTextField pass=new JTextField(100);
        pass.setText("Password");
        pass.setBounds(120,400,100,50);
        f2.add(pass);
        JButton don=new JButton("Done");
        don.setBounds(120,500,100,20);
        f2.add(don);
        don.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int f=-1;
                    String i=id.getText();
                    String n=name.getText();
                    String p=pass.getText();
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery("select * from students");
                    while(rs.next())
                    {
                        if(rs.getString(1).equals(i))
                        {
                             id.setText("Reg.No.");
                             name.setText("Name");
                             pass.setText("Password");
                             f=1;
                             break;
                        }
                    }
                    if(f==-1)
                    {
                        String sql = "insert into students(regno, name, password) values(?, ?, ?)"; 
                        PreparedStatement statement = con.prepareStatement(sql); 
                        statement.setString(1,i); 
                        statement.setString(2,n); 
                        statement.setString(3,p);
                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) 
                        {
                            f2.dispose();
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
class Login
{
    Login()
    {
        JFrame f1=new JFrame();
        f1.setTitle("Student Login");
        JTextField name=new JTextField(30);
        name.setText("Reg.No.");
        name.setBounds(120,200,100,50);
        f1.add(name);
        JTextField pass=new JTextField(100);
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
                int f=-1;
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bfc","root","Anamika12F");
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery("select * from students");
                    while(rs.next())
                    {
                        if(rs.getString(1).equals(n) && rs.getString(3).equals(p))
                        {
                             new Menu(n);
                             f=1;
                             break;
                        }
                    }
                    con.close();
                }
                catch(Exception ex)
                {
                    //System.out.println(ex);
                }
                if(f==-1)
                {
                    name.setText("Reg.No.");
                    pass.setText("Password");
                }
            }
         });
        JButton sign=new JButton("Sign Up");
        sign.setBounds(120,700,100,20);
        f1.add(sign);
        sign.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new SignUp();
            }
         });
        f1.setLayout(null);
        f1.setSize(375,812);
        f1.setVisible(true);
     }
}
public class Student 
{
    public static void main(String args[])
    {
        new Login();
    }
}