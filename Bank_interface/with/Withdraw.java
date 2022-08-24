package with;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.sql.*;

public class Withdraw {
	
	String Username;
	String Name;
	String status;
	Scanner sc = new Scanner(System.in);
	double Balance;
	
	Connection DB;
	Statement statement;
	ResultSet set;

	
	public Withdraw(String Username,String Name,double Balance){
		this.Username = Username;
		this.Balance = Balance;
		this.Name = Name;
	}
	
	public void withdraw(double Amount) throws Exception{


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
		status = "withdraw";
		statement.executeUpdate("Update user_account set savings = "+Balance+" where username='"+Username+"'");
		statement.executeUpdate("INSERT INTO transaction VALUES('"+Name+"','','"+status+"','"+Amount+"','"+Balance+"','"+Formated+"')");
		System.out.println("Rs. "+Amount+" Withdrawed From Your Account.");
		System.out.println("Your Current Balance : Rs. "+Balance);
	}
	
}
