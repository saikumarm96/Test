package world;

import java.sql.*;
import java.util.*;

public class Bank extends Exception {
	public Bank(String message) {
		super(message);
	}

	static Scanner d = new Scanner(System.in);
	static int j;

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection a = DriverManager.getConnection("jdbc:mysql://172.17.15.40:3306/sai kumar", "itgusr5",
					"itgusr5");
			System.out.println("enter username:");
			String e = d.nextLine();
			PreparedStatement b = a.prepareStatement("select * from bank where acc_name=?");
			b.setString(1, e);
			ResultSet c = b.executeQuery();
			if (c.next()) {
				System.out.println("enter pin:");
				int f = d.nextInt();
				int choice;
				if (f == c.getInt("Password")) {
					do {
						System.out.println("Welcome to ABC Bank");
						System.out.println("Select from the below options:" + "\n" + "1.Balance enquiry" + "\n"
								+ "2.Withdrawl" + "\n" + "3.Deposit" + "\n" + "4.Transfer" + "\n" + "5.Change pin");
						int i = d.nextInt();
						switch (i) {
						case 1:
							j = c.getInt("balance");
							System.out.println("Your availble balance is:" + j);
							break;
						case 2:
							System.out.print("Enter the amount you want to withdraw:");
							int bal = d.nextInt();
							try {
								if (bal >= c.getInt("balance"))
									throw new Bank("Insufficient funds");
								else {
									System.out.println("Amount withdrawn" + "\n" + "Your remaining balance is:"
											+ (c.getInt("balance") - bal));
									int w = c.getInt("balance") - bal;
									String sq = "update bank set balance=" + w + " where acc_name='" + e + "'";
									int wi = b.executeUpdate(sq);
								}
							} catch (Bank x) {
								System.out.println(x);
							}
							break;
						case 3:
							System.out.print("Enter the amount you want to deposit:");
							int dep = d.nextInt();
							try {
								if (dep < 100) {
									throw new Bank("Min deposit amount should be >100");
								} else {
									int up = dep + c.getInt("balance");
									System.out.println("Deposit sucessfull" + "\n" + "Your latest balance is:" + up);
									String sql = "update bank set balance=" + up + " where acc_name='" + e + "'";
									int depo = b.executeUpdate(sql);

								}
							} catch (Bank v) {
								System.out.println(v);
							}
							break;
						case 4:
							System.out.println("Enter account number you want to send:");
							int in = d.nextInt();
							PreparedStatement b1 = a.prepareStatement("select * from bank where acc_number=?");
							b1.setInt(1, in);
							ResultSet rs = b1.executeQuery();
							if (rs.next()) {
								if (in == rs.getInt("acc_number")) {
									System.out.println("Enter the amount you want to send:");
									int en = d.nextInt();
									int sub = c.getInt("balance") - en;
									String st = "update bank set balance=" + sub + " where acc_name='" + e + "'";
									int wit = b.executeUpdate(st);
									int add = rs.getInt("balance") + en;
									System.out.println("Your updated balance is:" + add + "");
									String st1 = "update bank set balance=" + add + " where acc_number=" + in + "";
									int as = b1.executeUpdate(st1);

								}
							} else {
								System.out.println("enter valid account number");
							}
							break;
						case 5:
							System.out.println("Enter new pin");
							int pin = d.nextInt();
							String ch = "update bank set Password=" + pin + " where acc_name='" + e + "'";
							int cha = b.executeUpdate(ch);
							System.out.println("Pin change successfull");
						}
						System.out.println("Do you want to continue" + "\n" + "1.YES" + "\n" + "2.NO");
						choice = d.nextInt();
					} while (choice == 1);
					{
						if (choice == 2) {
							System.out.println("Thank you");
						}
					}
				} else {
					System.out.println("invalid credentials");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
}