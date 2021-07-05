import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * 需求文档编辑服务类
 */
public class DocService {
    private DocDAO docDAO = new DocDAO();

    /**
     * 增加文档, 调用数据库接口对象的addDoc方法
     * @param projectID 增加文档的项目编号
     * @param userID 增加文档的用户ID
     * @return 若成功增加文档, 返回true; 否则返回false
     */
    public boolean addDoc(String projectID, String userID) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要增加的文档ID: ");
        String docID = input.next();
        System.out.print("请输入文档描述: ");
        String docNote = input.next();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Doc doc = new Doc(docID, docNote, projectID, userID, simpleDateFormat.format(new java.util.Date()));
        if (!docDAO.addDoc(doc)) {
            System.out.println("addDoc Error!");
            return false;
        }
        System.out.println("已增加文档: " + docNote);
        return true;
    }

    /**
     * 修改文档描述, 调用数据库接口对象的modifyDoc方法
     * @return 若成功修改文档描述, 返回true; 否则返回false
     */
    public boolean modifyDoc() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要修改的文档ID: ");
        String docID = input.next();
        System.out.print("请输入新的文档描述: ");
        String docNote = input.next();
        if (!docDAO.modifyDoc(docID, docNote)) {
            System.out.println("modifyDoc Error!");
            return false;
        }
        System.out.println("已修改文档描述为: " + docNote);
        return true;
    }

    /**
     * 删除文档, 调用数据库接口对象的delDoc方法
     * @return 若成功删除文档, 返回true; 否则返回false
     */
    public boolean delDoc() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要删除的文档ID: ");
        String docID = input.next();
        if (!docDAO.delDoc(docID)) {
            System.out.println("delDoc Error!");
            return false;
        }
        System.out.println("已删除文档" + docID);
        return true;
    }

    /**
     * 获取并显示某一个项目的所有需求文档, 调用数据库接口对象的getDoc方法
     * @param projectID 项目编号
     * @return 若成功获取所有文档信息, 返回true; 否则返回false
     */
    public boolean getDoc(String projectID) {
        return docDAO.getDoc(projectID);
    }

    /**
     * 导入需求文档, 调用数据库接口对象的leadIn方法
     * @param projectID 当前所在的项目编号
     * @param userID 当前用户ID
     * @return 若成功导入文档, 返回true; 否则返回false
     */
    public boolean leadIn(String projectID, String userID) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入导入文件的路径: ");
        String filePath = input.next();
        System.out.print("请输入新建文档的ID: ");
        String docID = input.next();
        System.out.print("请输入新建文档的描述: ");
        String docNote = input.next();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Doc doc = new Doc(docID, docNote, projectID, userID, simpleDateFormat.format(new java.util.Date()));
        if (!docDAO.leadIn(doc, filePath)) {
            System.out.println("leadIn Error!");
            return false;
        }
        System.out.println("已将文件" + filePath + "导入需求文档" + docID);
        return true;
    }

    /**
     * 导出需求文档, 调用数据库接口对象的leadOut方法
     * @return 若成功导出文档, 返回true; 否则返回false
     */
    public boolean leadOut() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要导出的文档ID: ");
        String docID = input.next();
        System.out.print("请输入目标文件路径: ");
        String filePath = input.next();
        if (!docDAO.leadOut(docID, filePath)) {
            System.out.println("leadOut Error!");
            return false;
        }
        System.out.println("已将需求文档" + docID + "导出为" + filePath);
        return true;
    }
}