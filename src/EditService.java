import java.util.Scanner;

/**
 * 需求编辑服务类
 */
public class EditService {
    private RequirementDAO reqDAO = new RequirementDAO();

    /**
     * 增加需求, 调用数据库接口对象的addRequirements方法
     * @param docID 要增加需求条目的文档编号
     * @return 若成功增加需求, 返回true; 否则返回false
     */
    public boolean add(String docID) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要增加的需求条目ID: ");
        String requirementID = input.next();
        System.out.print("请输入需求条目内容: ");
        String requirementNote = input.next();
        Requirements requirements = new Requirements(requirementID, docID, requirementNote);
        if (!reqDAO.addRequirements(requirements)) {
            System.out.println("addRequirement Error!");
            return false;
        }
        System.out.println("已增加需求: " + requirementNote);
        return true;
    }

    /**
     * 修改需求描述, 调用数据库接口对象的modifyRequirements方法
     * @return 若成功修改需求描述, 返回true; 否则返回false
     */
    public boolean modify() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要修改的需求条目ID: ");
        String reqID = input.next();
        System.out.print("请输入新的需求条目内容: ");
        String reqNote = input.next();
        if (!reqDAO.modifyRequirements(reqID, reqNote)) {
            System.out.println("addRequirement Error!");
            return false;
        }
        System.out.println("已修改需求" + reqID + "为: " + reqNote);
        return true;
    }

    /**
     * 删除需求条目, 调用数据库接口对象的delRequirements方法
     * @return 若成功删除需求条目, 返回true; 否则返回false
     */
    public boolean del() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要删除的需求条目ID: ");
        String reqID = input.next();
        if (!reqDAO.delRequirements(reqID)) {
            System.out.println("addRequirement Error!");
            return false;
        }
        System.out.println("已删除需求" + reqID);
        return true;
    }

    /**
     * 获取某一文档中的所有需求条目, 调用数据库接口对象的getRequirement方法
     * @param docID 文档编号
     * @return 若成功获取所有需求条目, 返回true; 否则返回false
     */
    public boolean get(String docID) {
        return reqDAO.getRequirement(docID);
    }

    /**
     * 当某一文档被删除时, 删除其中的所有需求条目, 调用数据库接口对象的delAll方法
     * @param docID 文档编号
     * @return 若成功删除所有需求条目, 返回true; 否则返回false
     */
    public boolean delAll(String docID) {
        return reqDAO.delAll(docID);
    }

    /**
     * 跳转到需求条目的链接条目, 调用数据库接口对象的getLink方法
     * @return 若成功跳转, 返回true; 否则返回false
     */
    public boolean getLink() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要跳转到链接需求的需求条目ID: ");
        String reqID = input.next();
        System.out.println("链接需求条目为:");
        if (!reqDAO.getLink(reqID)) {
            System.out.println("getLink Error!");
            return false;
        }
        return true;
    }

    /**
     * 设置需求链接, 调用数据库接口对象的setLink方法
     * @return 若成功创建链接, 返回true; 否则返回false
     */
    public boolean setLink() {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入要增加链接的需求条目ID: ");
        String reqID = input.next();
        System.out.print("请输入要链接到的需求条目ID: ");
        String linkID = input.next();
        if (!reqDAO.setLink(reqID, linkID)) {
            System.out.println("setLink Error!");
            return false;
        }
        System.out.println("已将需求" + reqID + "链接到需求" + linkID);
        return true;
    }
}