import trans.Transaction_history;
import transfer.Transfer;
import dep.Deposit;
import with.Withdraw;
import java.util.Scanner;
import java.sql.*;


class Bank{
	
	Connection DB;
	Statement statement;
	ResultSet set;
	Scanner sc = new Scanner(System.in);
	String Username,Name;
	String Password;
	String status;
	double Balance;
	double Amount;
	int AccNo;
	
	
	
	public Bank()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root", "");
			
			statement = DB.createStatement();
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void cls()
	{
		try
		{
			new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor(); 
		}catch(Exception E)
		{
			System.out.print(E);
		}
	}


	void login() throws Exception{
		cls();
		System.out.print("Enter User Id :");
		Username = sc.nextLine();
		
		System.out.print("Enter Password :");
		Password = sc.nextLine();
		
		set = statement.executeQuery("select * from User_account where username=\""+Username+"\" AND Password=\""+Password+"\"");
		
		if(set.next() && Username.equals(set.getString(3)) &&  Password.equals(set.getString(4))){
			Name = set.getString(2);
			System.out.println("Welcome "+Name);
			Balance = set.getInt(5);
			menu();
		}
		else
		{
			System.out.println("Incorrect Username and Password. Retry again...");
			login();
			cls();			
		}		
	}
	


	void menu() throws Exception{
		int choice,ToAcc;
		
		do{
			System.out.println("1. Transaction History.\n2. Withdraw.\n3. Deposit.\n4. Transfer Money.\n5. Quit (Logout)");
			System.out.print("Enter Your Choice :");
			choice = sc.nextInt();
			switch(choice){
			
				case 1:
					cls();
					Transaction_history t = new Transaction_history(Name);
					t.transaction_history();
					break;
					
				case 2:
					cls();
					Withdraw w = new Withdraw(Username,Name,Balance);
					System.out.print("Enter Amount To Withdraw : ");
					Amount = sc.nextDouble();
					if(Amount <= Balance)
					{
						w.withdraw(Amount);
						Balance-=Amount;
					}
					else
					{
						System.out.println("!!!Insufficent Balance in Acoount!!!");
					}
					break;
					
				case 3:
					cls();
					System.out.print("Enter Amount To Deposit : ");
					Deposit d = new Deposit(Username,Name,Balance);
					Amount = sc.nextDouble();
					if(Amount<=0)
					{
						System.out.println("!!!Invalid Amount!!!");
						break;
					}
					d.deposit(Amount);
					Balance+=Amount;
					break;
					
				case 4:
					cls();
					System.out.print("Enter Account No. Of Reciever : ");
					ToAcc = sc.nextInt();
					Transfer tr = new Transfer(Username,Name,Balance);
					System.out.print("Enter Amount To Transfer : ");
					Amount = sc.nextDouble();
					if(Amount <= Balance)
					{
						tr.transfer(ToAcc,Amount);
						Balance-= Amount; 
					}
					else
					{
						System.out.println("!!!Insufficent Balance in Acoount!!!");
					}
					break;
					
				case 5:
					System.exit(0);
					break;
					
				default:
					System.out.println("Enter Valid Choice.");
			}
		}while(choice != 5);
	}


	public static void main(String[] args) throws Exception{
		Bank b = new Bank();
		b.login();
}
}
