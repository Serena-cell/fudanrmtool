import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * 项目服务类
 */
public class ProjectServer {
    private ProjectDAO projectDAO = new ProjectDAO();

    /**
     * 增加项目
     */
    public boolean addProject(String userID) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要增加的项目ID: ");
        String projectID = input.next();
        System.out.print("请输入项目描述: ");
        String projectNote = input.next();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Project project = new Project(projectID, userID, projectNote, simpleDateFormat.format(new java.util.Date()));
        if (!projectDAO.addProject(project)) {
            System.out.println("addProject Error!");
            return false;
        }
        System.out.println("已增加项目: " + projectNote);
        return true;
    }

    /**
     * 修改项目描述
     */
    public boolean modifyProject() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要修改的项目ID: ");
        String projectID = input.next();
        System.out.print("请输入新的项目描述: ");
        String projectNote = input.next();
        if (!projectDAO.modifyProject(projectID, projectNote)) {
            System.out.println("modifyDoc Error!");
            return false;
        }
        System.out.println("已修改项目描述为: " + projectNote);
        return true;
    }

    /**
     * 删除项目
     */
    public boolean delProject() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要删除的项目ID: ");
        String projectID = input.next();
        if (!projectDAO.delProject(projectID)) {
            System.out.println("delProject Error!");
            return false;
        }
        System.out.println("已删除项目" + projectID);
        return true;
    }

    /**
     * 获取所有项目
     */
    public boolean getProject() {
        return projectDAO.getProject();
    }
}
