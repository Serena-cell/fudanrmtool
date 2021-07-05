/**
 * 需求条目类
 */
public class Requirements {
    public String requirementID;
    public String docID;
    public String requirementNote;
    public String linkID;

    Requirements(String reqID, String docID, String note) {
        requirementID = reqID;
        this.docID = docID;
        requirementNote = note;
    }
}