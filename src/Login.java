import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * 通过name和password得到userID
 * param: String name,String password
 * return: userID
 */
public class Login {
	public static String getuserID(String name,String password) throws SQLException {
		PreparedStatement prep = null;
		ResultSet rs = null;
		user user = new user();
		try {
			String sql = " SELECT * FROM User where name=? AND password=?";
			prep = Main.conn.prepareStatement(sql);
			prep.setString(1, name);
			prep.setString(2, password);
			rs = prep.executeQuery();
			if (rs.next()) {
				user.setName(rs.getString("name"));
				user.setuserID(rs.getString("UserID"));
				user.setPassword(rs.getString("password"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (prep != null) {
				prep.close();
			}
		}
		return user.getuserID();
	}

}
