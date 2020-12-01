import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Server
 *
 * Runs and spawns thread for each connected client, threads handle all transfer between server and client
 *
 * @author Conner McCormick , Seungjoon Woo
 * @version Nov 21, 2020
 *
 */

public class Server implements Runnable {
    private static final List<User> users = new ArrayList<User>();


    Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	static boolean isStopped = false;


    public Server(Socket socket) throws IOException {
    	//FileIO fileIO = new FileIO("file"); // need to make a file
		//this.users = (ArrayList<User>) Collections.synchronizedList(fileIO.getUsers());
		this.socket = socket;
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		isStopped = false;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4343);

        while (!isStopped) {
            Socket socket = serverSocket.accept();
            new Thread(new Server(socket)).start();

			System.out.println("connected");

        }
    }

    /*TODO: Fix the server exploding whenever client disconnects,
       EOF exception at read int on line 59 guess we should find a way to signal when client
       disconnects so we can kill the thread?
     */


	@Override
	public synchronized void run() {
		while(true) {                  //loops to receive instruction, server explodes when client disconnects though lol
			int clientMethod = -1;
			try {
				System.out.println("Users: " + users.size());
				System.out.println("reading...");
				clientMethod = ois.readInt();
				System.out.println("read int");
			} catch (IOException e) {
				e.printStackTrace();
			}

			switch (clientMethod) {
				case 0:
					this.stop();
					return;

				/*
				   This case is for when the login button is pressed

				   Checks if user with inputted username and pw is valid, then sends the user object to the client

				   Sends null if user does not exist or isn't valid
				 */

				case 1:
					try {
						String ID = (String)ois.readObject();

						String password = (String)ois.readObject();

						User currentUser = checkIDAndPassword(ID, password);


						oos.writeObject(currentUser);
						oos.flush();
						break;

					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}


				/*
				  Case for new account creation


 				 */
				case 2:
					try {
						String firstName = (String)ois.readObject();

						String lastName = (String)ois.readObject();

						int age = (int)ois.readObject();

						String password = (String)ois.readObject();

						String majors = (String)ois.readObject(); //Need to figure out format of majors to make an array

						String gradYear = (String)ois.readObject();

						String interests = (String)ois.readObject();

						String aboutMe = (String)ois.readObject();

						String email = (String)ois.readObject();

						String phoneNumber = (String)ois.readObject();

						String preferredName = (String)ois.readObject();

						String college = (String)ois.readObject();

						Profile currentProfile = new Profile(firstName, lastName, preferredName, age,
								college, gradYear, new String[2], interests, aboutMe, email, phoneNumber);

						User currentUser = new User(generateUsername(currentProfile), password);

						currentUser.setProfile(currentProfile);

						users.add(currentUser);



						System.out.println(currentUser.getUser());

						oos.writeObject(currentUser);  //writes user obj to client
						oos.flush();
						break;

					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}

				/*
				case 3 for edit profile
				 */
				case 3:
					try {
						System.out.println("Editing?????");
						String userName = (String)ois.readObject();


						String firstName = (String)ois.readObject();


						String lastName = (String)ois.readObject();

						int age = Integer.parseInt((String)ois.readObject());

						String majors = (String)ois.readObject(); //Need to figure out format of majors to make an array

						String gradYear = (String)ois.readObject();

						String interests = (String)ois.readObject();

						String aboutMe = (String)ois.readObject();

						String email = (String)ois.readObject();

						String phoneNumber = (String)ois.readObject();

						String preferredName = (String)ois.readObject();

						String college = (String)ois.readObject();

						Profile currentProfile = new Profile(firstName, lastName, preferredName, age,
								college, gradYear, new String[2], interests, aboutMe, email, phoneNumber);

						editProfile(userName, currentProfile);
						break;


					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}

					/*
					See all users case 4, writes users individually to the client side.
					had to do this because idk how to fix the issue with receiving an outdated
					list lol
					 */

				case 4:
					try {
						System.out.println("writing list");
						System.out.println(users.toString());

						oos.writeInt(users.size());
						oos.flush();


						ListIterator<User> listIterator = users.listIterator();

						while (listIterator.hasNext()) {
							oos.writeObject(listIterator.next());
							oos.flush();
						}
						break;
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				case 5:
					try {
						String username = (String)ois.readObject();

						oos.writeObject(findUser(username));
						oos.flush();


					} catch (IOException | ClassNotFoundException exception) {
						exception.printStackTrace();
					}
					break;
			}
		}
	}

	public synchronized String generateUsername(Profile profile) {
		StringBuilder sb = new StringBuilder();
		sb.append(profile.getFirstName());
		sb.append(profile.getLastName().substring(0 , 3));
		sb.append(String.valueOf(profile.getGradClass()));
		return sb.toString();
	}

	public synchronized User checkIDAndPassword(String id, String password) {
    	if (users != null && users.size() != 0) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getUser().equals(id)) {
					if (users.get(i).getPwd().equals(password)) {
						return users.get(i);
					} else {
						break;
					}
				}
			}
		}
    	return null;
	}

	public synchronized void editProfile(String username, Profile profile) {

		for (User u : users) {
			if(u.getUser().equals(username)) {
				u.setProfile(profile);
				break;
			}
		}
	}

	public synchronized User findUser(String username) {

		for (User u : users) {
			if(u.getUser().equals(username)) {
				return u;
			}
		}
		return null;
	}

	public synchronized void stop() {
		isStopped = true;
	}


	public synchronized void sendRequest(User sender, User receiver) {
    	if(sender.getFriends().contains(receiver) || sender.getSent().contains(receiver)) {
    		return;
    	}
    	if (sender.getReceived().contains(receiver)) {
    		sender.addFriend(receiver);
    		sender.removeReceived(receiver);
    		receiver.removeSent(sender);
    	} else {
    		sender.addSent(receiver);
    		receiver.addReceived(sender);
    	}
    	//refresh GUI
    }

    public synchronized void retractRequest(User sender, User receiver) {
    	if(!sender.getSent().contains(receiver)) {
    		return;
    	}
		sender.removeSent(receiver);
		receiver.removeReceived(sender);
    	//refresh GUI
    }

	public ArrayList<User> filterCollege(String college){
		ArrayList<User> results = new ArrayList<User>();
		for (User u : users) {
			if(u.getProfile().getCollege().equals(college)) {
				results.add(u);
			}
		}
		return results;
	}

	public ArrayList<User> filterGradYear(String gradYear){
		ArrayList<User> results = new ArrayList<User>();
		for (User u : users) {
			if(u.getProfile().getGradClass().equals(gradYear)) {
				results.add(u);
			}
		}
		return results;
	}
}
