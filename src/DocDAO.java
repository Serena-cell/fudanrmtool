import java.sql.*;
import java.io.*;

/**
 * 需求文档相关操作的数据库接口类
 */
public class DocDAO {
    /**
     * 增加文档
     * @param src 文档对象
     * @return 若成功增加文档, 返回true; 否则返回false
     */
    public boolean addDoc(Doc src) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "INSERT INTO Doc VALUES " + "('" + src.docID + "', '" + src.userID + "', '" +
                    src.projectID + "', '" + src.docNote + "', '" + src.docTime + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 修改文档描述
     * @param docID 要修改的文档编号
     * @param docNote 新的文档描述内容
     * @return 若成功修改文档描述, 返回true; 否则返回false
     */
    public boolean modifyDoc(String docID, String docNote) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "UPDATE Doc SET DocNote = '" + docNote + "' WHERE DocID = '" + docID + "'" ;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除文档, 并删除需求条目表中该文档的所有需求条目
     * @param docID 要删除的文档编号
     * @return 若成功删除文档, 返回true; 否则返回false
     */
    public boolean delDoc(String docID) {
        EditService editService = new EditService();
        try {
            Statement stmt = Main.conn.createStatement();
            //事务开始
            String sql = "begin";
            stmt.execute(sql);
            //删除文档
            sql = "DELETE FROM Doc WHERE docID = '" + docID + "'";
            stmt.executeUpdate(sql);
            //删除相关需求条目
            if (!editService.delAll(docID)) {
                //回滚
                sql = "rollback";
                stmt.execute(sql);
                return false;
            }
            //事务提交
            sql = "commit";
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取并显示某一个项目的所有需求文档
     * @param projectID 项目编号
     * @return 若成功获取所有文档信息, 返回true; 否则返回false
     */
    public boolean getDoc(String projectID) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "SELECT * FROM Doc WHERE projectID = '" + projectID + "'";
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("DocID\tUserID\tprojectID\tDocNote\t\tDocTime");
            while(resultSet.next()) {
                // 通过字段搜索
                String docID = resultSet.getString("DocID");
                String userID = resultSet.getString("UserID");
                String docNote = resultSet.getString("DocNote");
                String docTime = resultSet.getString("DocTime");
                // 输出数据
                System.out.print(docID + "\t\t");
                System.out.print(userID + "\t\t");
                System.out.print(projectID + "\t\t\t");
                System.out.print(docNote + "\t");
                System.out.println(docTime);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 导入需求文档
     * 导入文档的格式要求: 每行写一项需求, 需求的写法为 需求ID + 空格 + 需求描述
     * @param src 导入到的目的Doc对象
     * @param filePath 要导入的源文件路径
     * @return 若成功导入文档, 返回true; 否则返回false
     */
    public boolean leadIn(Doc src, String filePath) {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String input = null;
            Statement stmt = Main.conn.createStatement();
            //开始事务
            String sql = "begin";
            stmt.execute(sql);
            //创建文档
            if (!addDoc(src)) {
                System.out.println("addDoc Error!");
                return false;
            }
            //创建需求条目
            while((input = bufReader.readLine()) != null)
            {
                String[] str = input.split(" ");
                Requirements requirements = new Requirements(str[0], src.docID, str[1]);
                RequirementDAO reqDAO = new RequirementDAO();
                if (!reqDAO.addRequirements(requirements)) {
                    //回滚
                    sql = "rollback";
                    stmt.execute(sql);
                    bufReader.close();
                    return false;
                }
            }
            //提交事务
            sql = "commit";
            stmt.execute(sql);
            bufReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 导出需求文档
     * @param docID 要导出的文档编号
     * @param filePath 目的文件路径
     * @return 若成功导出文档, 返回true; 否则返回false
     */
    public boolean leadOut(String docID, String filePath) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "SELECT * FROM Requirements WHERE DocID = '" + docID + "'";
            ResultSet resultSet = stmt.executeQuery(sql);
            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            while(resultSet.next()) {
                String reqID = resultSet.getString("RequirementsID");
                String reqNote = resultSet.getString("RequirementsNote");
                bufWriter.write(reqID + " " + reqNote);
                bufWriter.newLine();
            }
            bufWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
