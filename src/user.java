/**
 * 这个类是关于用户的抽象,这是一个JavaBean
 *
 */
public class user {
    private String name;
    private String userID;
    private String password;
    
	public String getname() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getuserID() {
		return userID;
	}
	public void setuserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

