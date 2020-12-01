import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * GUI Edit Account Frame for app
 *
 * <p>Purdue University -- CS18000 -- Fall 2020</p>
 *
 * @author Katherine Yi
 * @version 11/20/2020
 */

public class EditProfileFrame extends JComponent implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //fields
    User user;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    JLabel firstName;
    JTextField firstNameField;
    JLabel lastName;
    JTextField lastNameField;
    JLabel preferredName;
    JTextField preferredNameField;
    JLabel age;
    JTextField ageField;
    JLabel college;
    JComboBox<String> collegeField;
    JLabel gradClass;
    JComboBox<String> gradClassField; 
    JLabel majors;
    JComboBox<String> majorsField; 
    JLabel interests;
    JTextField interestsField;
    JLabel aboutMeSection;
    JTextField aboutMeField;
    JLabel email;
    JTextField emailField;
    JLabel phoneNumber;
    JTextField phoneNumberField;
    JButton saveButton;

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new EditProfileFrame());
    }*/
    
    @Override
    public void run() {
        EditProfileFrame frame = new EditProfileFrame(oos, ois, user);
        frame.setVisible(true);
    }

    public EditProfileFrame(ObjectOutputStream oos, ObjectInputStream ois, User currentUser) {


        System.out.println("into run of edit profile");
        JFrame frame = new JFrame("Corona Connect Edit Account");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        this.oos = oos;
        this.ois = ois;
        this.user = currentUser;

        JPanel panel = new JPanel(); // top panel
        JLabel instructions = new JLabel("Fill out the fields below for your profile. Required to fill out fields with a *.");
        panel.add(instructions);
        content.add(panel, BorderLayout.NORTH);   

        /*
        Content of labels and fields for user to fill in
        */
        JPanel mPanel = new JPanel(); // panel in the middle 
        JScrollPane jsp = new JScrollPane(mPanel);

        mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
        firstName = new JLabel("* First name: ");
        firstNameField = new JTextField(); // TODO: //get name from profile
        mPanel.add(firstName);
        mPanel.add(firstNameField);

        lastName = new JLabel("* Last name: ");
        lastNameField = new JTextField(); // TODO: //get name from profile
        mPanel.add(lastName);
        mPanel.add(lastNameField);

        preferredName = new JLabel("Preferred name: ");
        preferredNameField = new JTextField(); // TODO: //get name from profile
        mPanel.add(preferredName);
        mPanel.add(preferredNameField);

        age = new JLabel("* Age: ");
        ageField = new JTextField(); // TODO: //get age from profile
        mPanel.add(age);
        mPanel.add(ageField);

        college = new JLabel("* College: "); // TODO: //get college from profile
        collegeField = new JComboBox<String>();
        collegeField.addItem("College of Agriculture");
        collegeField.addItem("College of Education");
        collegeField.addItem("Exploratory Studies");
        collegeField.addItem("College of Health and Human Sciences");
        collegeField.addItem("College of Liberal Arts");
        collegeField.addItem("Krannert School of Management");
        collegeField.addItem("College of Pharmacy");
        collegeField.addItem("Purdue Polytechnic Institute");
        collegeField.addItem("College of Science");
        collegeField.addItem("College of Veterinary Medicine");
        collegeField.addItem("Honors College");
        collegeField.addItem("The Graduate School");        
        mPanel.add(college);
        mPanel.add(collegeField);

        gradClass = new JLabel("Graduation Year:"); // TODO: //get gradYear from profile
        gradClassField = new JComboBox<String>();
        gradClassField.addItem("2020");
        gradClassField.addItem("2021");
        gradClassField.addItem("2022");
        gradClassField.addItem("2023");
        gradClassField.addItem("2024");
        gradClassField.addItem("2025");
        gradClassField.addItem("2026");
        gradClassField.addItem("2027");
        gradClassField.addItem("2028");
        gradClassField.addItem("2029");
        mPanel.add(gradClass);
        mPanel.add(gradClassField);

        majors = new JLabel("* Major:"); // TODO: //get name from profile
        JTextField majorsField = new JTextField(); // this is supposed to be dropdown?
        mPanel.add(majors);
        mPanel.add(majorsField);

        interests = new JLabel("* Interests:");
        interestsField = new JTextField(); // TODO: //get interest from profile
        mPanel.add(interests);
        mPanel.add(interestsField);

        aboutMeSection = new JLabel("* About Me:");
        aboutMeField = new JTextField(); // TODO: //get abtMe from profile
        mPanel.add(aboutMeSection);
        mPanel.add(aboutMeField);

        email = new JLabel("* Email:");
        emailField = new JTextField(); // TODO: //get email from profile
        mPanel.add(email);
        mPanel.add(emailField);

        phoneNumber = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(); // TODO: //get phone from profile
        mPanel.add(phoneNumber);
        mPanel.add(phoneNumberField);

        content.add(jsp, BorderLayout.CENTER);

        firstNameField.setText(currentUser.getProfile().getFirstName());
        lastNameField.setText(currentUser.getProfile().getLastName());
        preferredNameField.setText(currentUser.getProfile().getPreferredName());
        ageField.setText(String.valueOf(currentUser.getProfile().getAge()));
        interestsField.setText(currentUser.getProfile().getInterests());
        aboutMeField.setText(currentUser.getProfile().getAboutMeSection());

        emailField.setText(currentUser.getProfile().getEmail());
        phoneNumberField.setText(currentUser.getProfile().getPhoneNumber());

        JPanel sPanel = new JPanel();
        saveButton = new JButton("Save New Profile");
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check to see if all fields are valid in another method 
                boolean valid = false;
                if (firstNameField.getText().equals("") || lastNameField.getText().equals("")
                        || ageField.getText().equals("") || majorsField.getText().equals("")
                        || interestsField.getText().equals("") || aboutMeField.getText().equals("")
                        || emailField.getText().equals("")) {
                    valid = false;
                } else valid = true;
                int age = 0;
                try {
                    age = Integer.parseInt(ageField.getText());
                } catch (NumberFormatException numberFormatException) {
                    valid = false;
                }
                if (age == 0) {
                    valid = false;
                }
                if (valid) { // fields are valid
                    JOptionPane.showMessageDialog(null, "Profile is being updated.", "Corona Connect",
                        JOptionPane.INFORMATION_MESSAGE);

                    try {

                        oos.writeInt(3);
                        oos.flush();

                        oos.writeObject(user.getUser());
                        oos.flush();


                        oos.writeObject(firstNameField.getText());
                        oos.flush();

                        oos.writeObject(lastNameField.getText());
                        oos.flush();

                        oos.writeObject(String.valueOf(age));
                        oos.flush();

                        oos.writeObject(majorsField.getText());
                        oos.flush();

                        oos.writeObject(String.valueOf(gradClassField.getSelectedItem()));
                        oos.flush();

                        oos.writeObject(interestsField.getText());
                        oos.flush();

                        oos.writeObject(aboutMeField.getText());
                        oos.flush();

                        oos.writeObject(emailField.getText());
                        oos.flush();

                        oos.writeObject(phoneNumberField.getText());
                        oos.flush();

                        oos.writeObject(preferredNameField.getText());
                        oos.flush();

                        oos.writeObject(String.valueOf(collegeField.getSelectedItem()));
                        oos.flush();

                        user.getProfile().setFirstName(firstNameField.getText());
                        user.getProfile().setLastName(lastNameField.getText());
                        user.getProfile().setAge(Integer.parseInt(ageField.getText()));
                        user.getProfile().setAboutMeSection(aboutMeField.getText());
                        user.getProfile().setPreferredName(preferredNameField.getText());
                        user.getProfile().setCollege(String.valueOf(collegeField.getSelectedItem()));
                        user.getProfile().setGradClass(String.valueOf(gradClassField.getSelectedItem()));
                        user.getProfile().setEmail(emailField.getText());
                        user.getProfile().setPhoneNumber(phoneNumberField.getText());
                        user.getProfile().setInterests(interestsField.getText());


                        frame.dispose();
                        launchMainFrame();


                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    // create profile here         
                } 
                    
                if (!valid) { // fields are not valid
                    JOptionPane.showMessageDialog(null, "Missing or invalid field.", "Corona Connect",
                        JOptionPane.ERROR_MESSAGE);            
                }    
                System.out.println("Save New Profile button pressed");

            }
        });
        sPanel.add(saveButton);
        content.add(sPanel, BorderLayout.SOUTH);

        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void launchMainFrame() {
        MainFrame main = new MainFrame(oos, ois, user);
    }
}
