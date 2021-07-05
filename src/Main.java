import java.sql.*;
import java.util.Scanner;

public class Main {
	// JDBC驱动名 & 数据库URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/FudanRMTool?" +
            "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // 数据库的用户名与密码
    static final String USER = "root";
    static final String PASS = "lyf20000619";

    public static Connection conn = null;

    private final static ProjectServer projectService = new ProjectServer(); // 项目编辑服务对象
    private final static DocService docService = new DocService(); //文档编辑服务对象
    private final static EditService editService = new EditService(); //需求编辑服务对象

    /**
     * 项目编辑模块
     */
    private static void ProjectInterface(String userID) {
        Scanner input = new Scanner(System.in);
        boolean doQuit = false;
        while (!doQuit) {
            projectService.getProject(); //显示所有项目
            System.out.println("1.增加项目\t2.修改项目描述\t3.删除项目\t4.项目查看与编辑\t5.返回");
            switch (input.nextInt()) {
                case 1:
                    projectService.addProject(userID);
                    break;
                case 2:
                    projectService.modifyProject();
                    break;
                case 3:
                    projectService.delProject();
                    break;
                case 4:
                    System.out.print("请输入要编辑的项目ID: ");
                    String projectID = input.next();
                    docInterface(userID, projectID);
                    break;
                case 5:
                    doQuit = true;
                    break;
            }
        }
    }

    /**
     * 文档编辑界面, 显示所有文档, 进行文档编辑
     */
    private static void docInterface(String userID, String projectID) {
        Scanner input = new Scanner(System.in);
        boolean doQuit = false;
        while (!doQuit) {
            docService.getDoc(projectID); //显示所有文档
            System.out.println("1.增加文档\t2.修改文档描述\t3.删除文档\t4.文档查看与编辑\t5.导入\t6.导出\t7.返回");
            switch (input.nextInt()) {
                case 1:
                    docService.addDoc(projectID, userID);
                    break;
                case 2:
                    docService.modifyDoc();
                    break;
                case 3:
                    docService.delDoc();
                    break;
                case 4:
                    System.out.print("请输入要编辑的文档ID: ");
                    String docID = input.next();
                    reqInterface(docID);
                    break;
                case 5:
                    docService.leadIn(projectID, userID);
                    break;
                case 6:
                    docService.leadOut();
                    break;
                case 7:
                    doQuit = true;
                    break;
            }
        }
    }

    /**
     * 需求编辑界面, 显示所有需求, 进行需求编辑
     */
    private static void reqInterface(String docID) {
        Scanner input = new Scanner(System.in);
        boolean doQuit = false;
        while (!doQuit) {
            editService.get(docID); //显示所有需求
            System.out.println("1.增加需求\t2.修改需求\t3.删除需求\t4.增加链接\t5.跳转到链接\t6.返回");
            switch (input.nextInt()) {
                case 1:
                    editService.add(docID);
                    break;
                case 2:
                    editService.modify();
                    break;
                case 3:
                    editService.del();
                    break;
                case 4:
                    editService.setLink();
                    break;
                case 5:
                    editService.getLink();
                    break;
                case 6:
                    doQuit = true;
                    break;
            }
        }
    }

    /**
     * 主函数
     */
    public static void main(String[] args) {
        try {
        	// 注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            // 打开连接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 登录界面
            System.out.println("\nWelcome to FudanRMTool!\n");
            System.out.println("选择注册（输入1）还是登录（输入0）");
            Scanner input = new Scanner(System.in);
            int i = input.nextInt();
            if (i == 1) { //注册, 若注册失败则退出, 若注册成功则让用户登录
                if (!Register.rigister()) //注册失败
                    return;
            }
            System.out.println("欢迎登录！！！");
            System.out.println("请输入用户名和密码进行登录");
            System.out.println("请输入用户名：");
            String username = input.next();
            System.out.println("请输入密码：");
            String pwd = input.next();
            String userID = Login.getuserID(username, pwd);
            // 进入项目界面
            ProjectInterface(userID);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	// 关闭连接
            try {
                if(conn != null)
                    conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("连接已关闭");
    }
}