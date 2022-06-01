

import java.sql.*;
import java.util.*;

public class Bank {


	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("loaded");
			Connection a = DriverManager.getConnection("jdbc:mysql://172.17.15.40:3306/sai kumar", "itgusr5",
					"itgusr5");
			System.out.println("connected");
			PreparedStatement b = a.prepareStatement("select * from bank where acc_name=?");
			ResultSet c = b.executeQuery();
			Scanner d = new Scanner(System.in);
			System.out.println("enter username:");
			String e=d.nextLine();
			//System.out.println("enter pin:");
			//int f = d.nextInt();
			while (c.next()) {
				System.out.println(c.getString("acc_name"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("fail");
		}

	}
}

