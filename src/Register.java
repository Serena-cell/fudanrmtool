import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Register {
	/**
	 * 判断在新增一个用户信息时，其账号是否唯一
	 * 
	 * @param userID	 
	 * @return 唯一时返回true，否则返回false
	 * @throws SQLException
	 */
	public static boolean isuserIDAddUnique(String userID) throws SQLException {
		PreparedStatement prep = null;
		ResultSet rs = null;
		int count;
		try {
			String sql = "SELECT count(*) as count FROM User WHERE UserID = ?";
			prep = Main.conn.prepareStatement(sql);
			prep.setString(1, userID);
			rs = prep.executeQuery();
			rs.next();
			count = rs.getInt("count");
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (prep != null) {
				prep.close();
			}
		}
		return (count == 0);
	}
	

    public static boolean rigister() throws IOException, SQLException {
    	//--------从控制台获取用户输入的信息-----
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("欢迎注册！！！");
        System.out.println("请输入用户名和密码进行注册");
        System.out.println("请输入用户名：");
        String name = br.readLine();
        System.out.println("请输入密码：");
        String password = br.readLine();
        String userID;

        while(true) {
            Random r = new Random();
            Long l = r.nextLong();
            userID = l.toString();
            if (isuserIDAddUnique(userID))
                break;
        }

        Statement stmt = null;
        int count = 0;
        try {
            stmt = Main.conn.createStatement();
            String sql_insert = "insert into User values ('"+userID+"', '"+name+"', '"+password+"')";
            count = stmt.executeUpdate(sql_insert);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	 //关闭资源
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if(count == 1) {
            System.out.println("恭喜注册成功！！！");
            return true;
        } else {
            System.out.println("对不起，您注册失败！！！");
            return false;
        }
    }
}
