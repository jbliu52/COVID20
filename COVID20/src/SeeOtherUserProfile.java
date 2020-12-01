import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * GUI see other user profile for app
 *
 * <p>Purdue University -- CS18000 -- Fall 2020</p>
 *
 * @author Katherine Yi
 * @version 11/20/2020
 */

public class SeeOtherUserProfile extends JComponent implements Runnable {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    User currentUser;
   
    private static final long serialVersionUID = 1L;

    // (ObjectInputStream ois, ObjectOutputStream oos, User currentUser)
    @Override
    public void run() {
        SeeOtherUserProfile frame = new SeeOtherUserProfile(ois, oos, currentUser);
        frame.setVisible(true);
    }

    public SeeOtherUserProfile(ObjectInputStream ois, ObjectOutputStream oos, User currentUser) {
        this.ois = ois;
        this.oos = oos;
        this.currentUser = currentUser;
        JTextArea firstName; // or preferred name if it is not null
        JTextArea lastName;
        JTextArea userName;
        JTextArea age;
        JTextArea college;
        JTextArea gradYear;
        JTextArea majors;
        JTextArea email;
        JTextArea phoneNumber;
        //private BufferedImage image;

        System.out.println("into run of seeing other users profile");
        JFrame frame = new JFrame("Corona Connect See Other User");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel panel = new JPanel(); // top panel
        JLabel labelAllUsers = new JLabel("Protential Connection's Profile: ");
        panel.add(labelAllUsers);
        content.add(panel, BorderLayout.NORTH);   

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
        userName.setText(currentUser.getUser());
        age.setText(String.valueOf(currentUser.getProfile().getAge()));
        college.setText(currentUser.getProfile().getCollege());
        gradYear.setText(currentUser.getProfile().getGradClass());
        email.setText(currentUser.getProfile().getEmail());
        phoneNumber.setText(currentUser.getProfile().getPhoneNumber());

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
}
