package trans;
import java.sql.*;

public class Transaction_history {
	
	String Name;
	Connection DB;
	Statement statement;
	ResultSet set;	
	
	public Transaction_history(String Name){
		this.Name = Name;  
	}
	
	public void transaction_history() throws Exception{

	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root", "");
			
			statement = DB.createStatement();
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		set = statement.executeQuery("select count(*) from transaction where username1=\""+Name+"\"");
		set.next();
		int count = set.getInt("count(*)");
		set = statement.executeQuery("select * from transaction where username1=\""+Name+"\"");
		while(count>0)
		{
			set.next();
			if(set.getString("username2").equals(""))
			{
				System.out.println(set.getString(1)+" "+set.getString(3)+" Amount : "+set.getInt(4)+"\nBalance :"+set.getInt(5)+"  "+set.getString(6)+"\n");				
			}
			else
			{
				System.out.println(set.getString(1)+" "+set.getString(3)+" to "+set.getString(2)+" Amount : "+set.getInt(4)+" "+set.getString(6)+"\nBalance : "+set.getInt(5)+"\n");
			}
	
			count--;
		}
		set = statement.executeQuery("select count(*) from transaction where username2=\""+Name+"\"");
		set.next();
		count = set.getInt("count(*)");
		set = statement.executeQuery("select * from transaction where username2=\""+Name+"\"");
		while(count>0)
		{
			set.next();
			
			System.out.println(set.getString(2)+" recived"+" from "+set.getString(1)+" Amount : "+set.getInt(4)+" "+set.getString(6)+"\nBalance : "+set.getInt(5)+"\n");
			
	
			count--;
		}

	}

}
