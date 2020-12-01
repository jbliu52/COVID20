import java.io.Serializable;
import java.util.HashSet;
/**
 * @author 
 * @version Nov 17, 2020
 * 
 */

public class User implements Serializable {
	private String user;
	private String pwd;
	private Profile profile;
	private HashSet<User> friends;
	private HashSet<User> sent;
	private HashSet<User> received;

	public User(String user, String pwd) {
		this.user = user;
		this.pwd = pwd;
		this.profile = null;
		this.friends = new HashSet<User>();
		this.sent = new HashSet<User>();
		this.received = new HashSet<User>();
	}
	
	public User(String user, String pwd, Profile profile, HashSet<User> friends, HashSet<User> sent,
			HashSet<User> recieved) {
		this.user = user;
		this.pwd = pwd;
		this.profile = profile;
		this.friends = friends;
		this.sent = sent;
		this.received = recieved;
	}
	
	public void addFriend(User friend) {
		friends.add(friend);
	}
	
	public void removeFriend(User friend) {
		friends.remove(friend);
	}
	
	public void addSent(User friend) {
		sent.add(friend);
	}
	
	public void addReceived(User friend) {
		received.add(friend);
	}
	
	public void removeSent(User friend) {
		sent.remove(friend);
	}
	
	public void removeReceived(User friend) {
		received.remove(friend);
	}
	
	public Profile getProfile() {
		return profile;
	}

	public HashSet<User> getFriends() {
		return friends;
	}

	public HashSet<User> getSent() {
		return sent;
	}

	public HashSet<User> getReceived() {
		return received;
	}

	public synchronized String getUser() {
		return user;
	}

	public String getPwd() {
		return pwd;
	}
	public String getUserName() {
		return user;
	}

	public synchronized void setProfile(Profile profile) {
		this.profile = profile;
	}
}
