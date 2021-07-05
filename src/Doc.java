/**
 * 需求文档类
 */
public class Doc {
    public String docID;
    public String docNote;
    public String projectID;
    public String userID;
    public String docTime;

    /**
     * 构造函数, 对Doc对象的成员进行初始化
     * @param docID 文档编号
     * @param note 文档描述
     * @param projectID 该文档属于的模块编号
     * @param userID 创建该文档的用户ID
     * @param time 文档创建时间
     */
    Doc(String docID, String note, String projectID, String userID, String time) {
        this.docID = docID;
        docNote = note;
        docTime = time;
        this.projectID = projectID;
        this.userID = userID;
    }
}