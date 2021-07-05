import java.sql.*;

/**
 * 项目相关操作数据库接口
 */
public class ProjectDAO {
    /**
     * 增加项目
     */
    public boolean addProject(Project project) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "INSERT INTO Project VALUES " + "('" + project.projectID + "', '" + project.userID + "', '" +
                    project.projectNote + "', '" + project.projectTime + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 修改项目描述
     */
    public boolean modifyProject(String projectID, String projectNote) {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "UPDATE Project SET ProjectNote = '" + projectNote + "' WHERE ProjectID = '" + projectID + "'" ;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除项目, 同时删除所有相关文档和相关需求条目
     */
    public boolean delProject(String projectID) {
        DocDAO docDAO = new DocDAO();
        try {
            Statement stmt = Main.conn.createStatement();
            //事务开始
            String sql = "begin";
            stmt.execute(sql);
            //删除项目
            sql = "DELETE FROM Project WHERE ProjectID = '" + projectID + "'";
            stmt.execute(sql);
            //删除所有相关文档
            sql = "SELECT * FROM Doc WHERE ProjectID = '" + projectID + "'";
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()) {
                String docID = resultSet.getString("DocID");
                if (!docDAO.delDoc(docID)) { //删除文档时也会删除所有相关需求条目
                    //回滚
                    sql = "rollback";
                    stmt.execute(sql);
                    return false;
                }
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
     * 获取所有项目
     */
    public boolean getProject() {
        try {
            Statement stmt = Main.conn.createStatement();
            String sql = "SELECT * FROM Project";
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("ProjectID\tUserID\tProjectNote\t\tProjectTime");
            while(resultSet.next()) {
                String projectID = resultSet.getString("ProjectID");
                String userID = resultSet.getString("UserID");
                String projectNote = resultSet.getString("ProjectNote");
                String projectTime = resultSet.getString("ProjectTime");
                // 输出数据
                System.out.print(projectID + "\t\t\t");
                System.out.print(userID + "\t\t");
                System.out.print(projectNote + "\t\t");
                System.out.println(projectTime);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
