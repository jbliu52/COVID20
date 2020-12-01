import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * Project 5 - FileIO.java
 * 
 * Class that reads/writes to file
 * 
 * a list of your sources of help (if any)
 *
 * @author Seungjoon Woo
 * @version November 18, 2020
 */
public class FileIO {
    String fileName;
    ArrayList<User> users;

    public FileIO(String fileName) throws IOException {
        users = new ArrayList<>();
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        while (true) {
            try {
                users.add((User)ois.readObject());

            } catch (EOFException e) {
                break;
            } catch (ClassNotFoundException ce) {
                System.out.println("File corrupted");
            }
        }

    }

    public ArrayList<User> getUsers() {
        return users;
    }

    

    public static void fileWrite(String fileName, ArrayList<User> users) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (int i = 0; i < users.size(); i++) {
            oos.writeObject(users.get(i));
        }
    }

}
