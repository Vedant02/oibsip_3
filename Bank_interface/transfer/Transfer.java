package transfer;
import java.sql.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Transfer {
	Connection DB;
	Statement statement;
	ResultSet set;

	String Username;
	String Name;
	String status;
	double Balance;
	
	
	public Transfer(String Username,String Name,double Balance){
		this.Username = Username;
		this.Balance = Balance;
		this.Name = Name;
	}
	
	public void transfer(int ToAcc,double Amount) throws Exception{

	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root", "");
			
			statement = DB.createStatement();
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		long millis = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(millis);		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String Formated = sdf.format(timestamp);
		
		Balance-=Amount;
		status = "transfer";
		String username2;
		ResultSet temp = statement.executeQuery("select * from user_account where Acc_no='"+ToAcc+"'");
		if(temp.next() && ToAcc == temp.getInt(1)){
			username2 = temp.getString(2);
			statement.executeUpdate("Update user_account set savings = "+(Amount+temp.getDouble(5))+" where Acc_no='"+ToAcc+"'");
			statement.executeUpdate("Update user_account set savings = "+Balance+" where username='"+Username+"'");
			statement.executeUpdate("INSERT INTO transaction VALUES('"+Name+"','"+username2+"','"+status+"','"+Amount+"','"+Balance+"','"+Formated+"')");
		}
		else
			System.out.println("Invalid Account.");
	}

}
