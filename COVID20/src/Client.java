import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Basic client setup
 *
 * it just connects to the server, idk
 *
 * @author Conner McCormick
 * @version Nov 21, 2020
 *
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 4343);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        SwingUtilities.invokeLater(new LoginFrame(oos, ois));
    }
}
