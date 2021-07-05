/**
 * 项目类
 */
public class Project {
    public String projectID;
    public String userID;
    public String projectNote;
    public String projectTime;

    Project(String projectID, String userID, String projectNote, String projectTime) {
        this.projectID = projectID;
        this.userID = userID;
        this.projectNote = projectNote;
        this.projectTime = projectTime;
    }
}
