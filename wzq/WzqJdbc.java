package wzq;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.log.Log;


public class WzqJdbc {
	
	
	static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";
		// 更改的服务器的时候要改ip和密码 47.93.14.42
//		String url = "jdbc:mysql://localhost:3306/java";
		String url = "jdbc:mysql://47.93.14.42:3306/java";
		
		String username = "root";
		String password = "wl19980805";
		
		Connection connection = null;
		try {
			connection = (Connection) DriverManager.getConnection(url,username,password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return connection;
	}
	
	static boolean insert(User user) {
		Connection connection = getConn();
		String sql = "insert into wuziqi(id, name, password, win, fail) values(?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement;//预编译
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getPassword());			
			preparedStatement.setInt(4, 0);
			preparedStatement.setInt(5, 0);

			// 重要的一步
			int a = preparedStatement.executeUpdate();
			System.out.println("a:  "  + a);
			// 关闭
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {

//			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	 static boolean update(User user) {
		Connection connection = getConn();
		String sql = "update wuziqi set win=" + user.getWin() + ", fail=" + user.getFail() + " where id=" + user.getId();
		System.out.println(sql);
		PreparedStatement preparedStatement ;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			int i = preparedStatement.executeUpdate();
			System.out.println("updata: " + i);
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			return false;
//			e.printStackTrace();
		}
		return true;
	}
	
	/*
	 * 这里只返回查询成功与否就可以，顺便改变一下user的win和fail
	 * */
	static boolean select(User user) {
		
		Connection connection = getConn();
//		上面是错误的方法，正确的方法写到了下面
//		String sql = "select * from wuziqi where name=" + user.getName() + " and password="+user.getPassword() ;
		String sql = "select * from wuziqi where name= '"+ user.getName() +"'  and password= '"+user.getPassword()+"'" ;
		PreparedStatement preparedStatement;

		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			System.out.println(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			int col = resultSet.getMetaData().getColumnCount();
			System.out.println(col);
			if (resultSet.next()) {
				System.out.println("=========" + resultSet.getInt(1) + "===========" + resultSet.getInt(4) + "=========" + resultSet.getInt(5));
				
				user.setId(resultSet.getInt(1));
				user.setWin(resultSet.getInt(4));
				user.setFail(resultSet.getInt(5));
				
				Login.user.setId(resultSet.getInt(1));
				Login.user.setWin(resultSet.getInt(4));
				Login.user.setFail(resultSet.getInt(5));
				Login.user.setName(resultSet.getString(2));
				preparedStatement.close();
				connection.close();
				
				return true;
			}
			else {
				preparedStatement.close();
				connection.close();
				return false;
			}
			
		

			
			
		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
//		return true;
	}
	
	public static void main(String[] args) {
//		User user = new User(0, "a", "c");
//		// 执行成功
//		System.out.println(select(user));
		
		User user2 = new User(1,"a","a");
//		System.out.println(insert(user2));
//		user2.setFail(1);
//		System.out.println(update(user2));
		System.out.println(select(user2));
	}
	
}
