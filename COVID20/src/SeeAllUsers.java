import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * GUI New Account Frame for app
 *
 * <p>Purdue University -- CS18000 -- Fall 2020</p>
 *
 * @author Katherine Yi
 * @version 11/20/2020
 */

public class SeeAllUsers extends JComponent implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //fields
    JButton closeButton;

    List<User> allUsers;

    ObjectInputStream ois;
    ObjectOutputStream oos;
    User currentUser;

   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new SeeAllUsers());  
    }*/
    
    @Override
    public void run() {
        SeeAllUsers frame = new SeeAllUsers(ois, oos, currentUser);
        frame.setVisible(true);
    }

    public SeeAllUsers(ObjectInputStream ois, ObjectOutputStream oos, User currentUser) {
        this.ois = ois;
        this.oos = oos;
        this.currentUser = currentUser;

        System.out.println("into run of seeing all users");
        JFrame frame = new JFrame("Corona Connect See All Users");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel panel = new JPanel(); // top panel
        JLabel labelAllUsers = new JLabel("All Corona Connect Users Listed Below");
        panel.add(labelAllUsers);
        content.add(panel, BorderLayout.NORTH);   

        /*
        Content of labels and fields for user to fill in
        */
        JPanel mPanel = new JPanel(); // panel in the middle
        mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
        JScrollPane jsp = new JScrollPane(mPanel);
        // TODO: add loop for users and display of them

        try {
            oos.writeInt(4);
            oos.flush();
            allUsers = new ArrayList<User>();
            int numUsers = ois.readInt();

            System.out.println("Number of users in array is: " + numUsers);

            for (int i = 0; i < numUsers; i++) {

                User addUser = (User)ois.readObject();
                allUsers.add(addUser);
            }

            System.out.println("got list");

            System.out.println(allUsers.toString());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ListIterator<User> listIterator = allUsers.listIterator();

        while (listIterator.hasNext()) {
            User currUser = listIterator.next();
            JButton userButton = new JButton(currUser.getProfile().getFirstName() + " " +
                    currUser.getProfile().getLastName());
            userButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        oos.writeInt(5);
                        oos.flush();

                        oos.writeObject(currUser.getUser());
                        oos.flush();

                        User receivedUser = (User)ois.readObject();
                        SeeOtherUserProfile otherUserProfile = new SeeOtherUserProfile(ois, oos, receivedUser);
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            mPanel.add(userButton);
        }

        content.add(jsp, BorderLayout.CENTER);

        JPanel sPanel = new JPanel();
        closeButton = new JButton("Return to Main Page");
        closeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check to see if all fields are valid in another method 
                    
                frame.dispose();
                System.out.println("Close button pressed");
            }
        });
        sPanel.add(closeButton);
        content.add(sPanel, BorderLayout.SOUTH);

        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
