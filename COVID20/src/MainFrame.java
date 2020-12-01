import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * GUI mainFrame for app
 *
 * <p>
 * Purdue University -- CS18000 -- Fall 2020
 * </p>
 *
 * @author Katherine Yi
 * @version 11/20/2020
 */

public class MainFrame extends JComponent implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // fields
    // add field for image
    User currentUser;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    JTextArea firstName; // or preferred name if it is not null
    JTextArea lastName;
    JTextArea userName;
    JTextArea age;
    JTextArea college;
    JTextArea gradYear;
    JTextArea majors;
    JTextArea email;
    JTextArea interests;
    JTextArea aboutMeSection;
    JTextArea phoneNumber;
    private BufferedImage image;
    JButton deleteAccount;
    JButton seeAllUsers;
    JButton seeRecievedRequests;
    JButton seeCurrentFriends;
    JButton seeSentRequests;
    JButton editProfile;
    JButton logOut;
    int yesDelete;

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new MainFrame());
    }

     */

    @Override
    public void run() {
        MainFrame frame = new MainFrame(oos, ois, currentUser);
        frame.setVisible(true);
        System.out.println("into run of MainFrame");
    }

    public MainFrame(ObjectOutputStream oos, ObjectInputStream ois, User currentUser) {
        JFrame frame = new JFrame("Corona Connect Main Page");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        this.oos = oos;
        this.ois = ois;
        this.currentUser = currentUser;

        /**
         * Content Here
         */

        Panel nPanel = new Panel();
        JScrollPane jsp = new JScrollPane(nPanel);
        nPanel.setLayout(new FlowLayout());
        nPanel.setPreferredSize(new Dimension(175, 175));
        // add content to North here
        firstName = new JTextArea("This is first name"); // or preferred name if it is not null
        nPanel.add(firstName);
        lastName = new JTextArea("This is last name"); // or preferred name if it is not null
        nPanel.add(lastName);
        userName = new JTextArea("This is user name"); // or preferred name if it is not null
        nPanel.add(userName);
        age = new JTextArea("This is age"); // or preferred name if it is not null
        nPanel.add(age);
        college = new JTextArea("This is college"); // or preferred name if it is not null
        nPanel.add(college);
        gradYear = new JTextArea("This is gradName"); // or preferred name if it is not null
        nPanel.add(gradYear);
        majors = new JTextArea("This is majors"); // or preferred name if it is not null
        nPanel.add(majors);
        email = new JTextArea("This is email"); // or preferred name if it is not null
        nPanel.add(email);
        phoneNumber = new JTextArea("This is phone#"); // or preferred name if it is not null
        nPanel.add(phoneNumber);
        content.add(jsp, BorderLayout.NORTH);

        firstName.setText(currentUser.getProfile().getFirstName());
        lastName.setText(currentUser.getProfile().getLastName());
        userName.setText(currentUser.getUserName());
        age.setText(String.valueOf(currentUser.getProfile().getAge()));
        college.setText(currentUser.getProfile().getCollege());
        gradYear.setText(currentUser.getProfile().getGradClass());

        email.setText(currentUser.getProfile().getEmail());
        phoneNumber.setText(currentUser.getProfile().getPhoneNumber());

        Panel wPanel = new Panel();
        wPanel.setLayout(new BoxLayout(wPanel, BoxLayout.Y_AXIS));
        JScrollPane jspW = new JScrollPane(wPanel);
        // TODO: //add picture HERE
                
        deleteAccount = new JButton("Delete Account");
        deleteAccount.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    yesDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Corona Connect", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE); 
                    if (yesDelete == JOptionPane.YES_OPTION) {
                        System.out.println("User confirmed delete");
                        // method to delete account
                        JOptionPane.showMessageDialog(null, "Account has been deleted", "Corona Connect", 
                            JOptionPane.INFORMATION_MESSAGE); 
                        frame.setVisible(false);
                        launchLoginFrame();
                    } else {
                        System.out.println("User rejected delete");
                    }

                }
            }
        );
        seeAllUsers = new JButton("See All Users");
        seeAllUsers.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    launchSeeAllUsers();
                } 
            }
        );
        seeRecievedRequests = new JButton("See Recieved Friend Requests");
        seeRecievedRequests.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    launchSeeRecievedRequests();
                } 
            }
        );
        seeCurrentFriends = new JButton("See Current Friends");
        seeCurrentFriends.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    launchSeeCurrentFriends();
                } 
            }
        );
        seeSentRequests = new JButton("See Sent Friend Requests");
        seeSentRequests.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    launchSeeSentRequests();
                } 
            }
        );
        editProfile = new JButton("Edit Profile");
        editProfile.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    launchEditFrame();
                    frame.dispose();
                } 
            }
        );
        logOut = new JButton("Logout");
        logOut.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add method to disconnect user
                frame.dispose(); 
            } 
        });
        wPanel.add(deleteAccount);
        wPanel.add(seeAllUsers);
        wPanel.add(seeCurrentFriends);
        wPanel.add(seeSentRequests);
        wPanel.add(seeRecievedRequests);
        wPanel.add(editProfile);
        wPanel.add(logOut);
        //add buttons to West here
        content.add(jspW, BorderLayout.WEST);

        Panel centerPanel = new Panel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel interests = new JLabel("Interests:");
        centerPanel.add(interests);
        JTextArea interestsField = new JTextArea("This is where the interests of profile will be called and go");
        centerPanel.add(interestsField);
        JLabel abtMe = new JLabel("About Me:");
        centerPanel.add(abtMe);
        JTextArea abtMeField = new JTextArea("This is where the about me of profile will be called and go");
        centerPanel.add(abtMeField);
        content.add(centerPanel, BorderLayout.CENTER);

        abtMeField.setLineWrap(true);
        abtMeField.setWrapStyleWord(true);
        interestsField.setWrapStyleWord(true);
        interestsField.setLineWrap(true);

        abtMeField.setText(currentUser.getProfile().getAboutMeSection());
        interestsField.setText(currentUser.getProfile().getInterests());

        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void launchLoginFrame() {
        LoginFrame loginFrame = new LoginFrame(oos, ois);
    }

    public void launchEditFrame() {
        EditProfileFrame editProfFrame = new EditProfileFrame(oos, ois, currentUser);
    }

    public void launchSeeAllUsers() {
        SeeAllUsers seeAllUsers = new SeeAllUsers(ois, oos, currentUser);
    }

    public void launchSeeRecievedRequests() {
        SeeRecievedRequests seeRecievedRequests = new SeeRecievedRequests();
    }

    public void launchSeeCurrentFriends() {
        SeeCurrentFriends currFriends = new SeeCurrentFriends();
    }

    public void launchSeeSentRequests() {
        SeeSentRequests sentReq = new SeeSentRequests();
    }
}
