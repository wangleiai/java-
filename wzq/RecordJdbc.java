package wzq;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.BreakIterator;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.log.Log;

public class RecordJdbc {
	public static void main(String[] args) {
		RecordJdbc recordJdbc = new RecordJdbc();
		recordJdbc.select("a");
		System.out.println(ReLook.eArrayList.size());
		recordJdbc.insert("aa", "b", 1, "c");
		System.out.println(ReLook.eArrayList.size());

	}

	static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";

		String url = "jdbc:mysql://47.93.14.42:3306/java";
		String username = "root";
		String password = "wl19980805";
		Connection connection = null;
		try {
			connection = (Connection) DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;

	}

	static boolean insert(String trace, String name, int win, String enemy) {
		Connection connection = getConn();
		String sql = "insert into record(trace, name, win, enemy) values(?, ?, ?, ?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, trace);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, win);
			preparedStatement.setString(4, enemy);

			int a = preparedStatement.executeUpdate();
//			System.out.println("a:  " + a);
			// 关闭
			preparedStatement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO: handle exception
			return false;
		}

	}

	static boolean select(String name) {

		Connection connection = getConn();
		
		String sql = "select * from record where name= '" + name +"'";
		PreparedStatement preparedStatement;

		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			System.out.println(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			int col = resultSet.getMetaData().getColumnCount();
//			System.out.println(col);
			int count=0;
			while (resultSet.next()) {
//				System.out.println("=========" + resultSet.getInt(1) + "===========" + resultSet.getInt(4) + "========="
//						+ resultSet.getInt(5));

				String trace = resultSet.getString(1);
				String mString = resultSet.getString(2);
				int i = resultSet.getInt(3);
				String enemy = resultSet.getString(4);
				ReLook.tArrayList.add(trace);
				ReLook.nArrayList.add(mString);
				ReLook.wArrayList.add(i);
				ReLook.eArrayList.add(enemy);
				count ++;
				if(count==5)
					break;
				
				
				
//				preparedStatement.close();
//				connection.close();

//				return true;
			} 

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return true;
	}

}
