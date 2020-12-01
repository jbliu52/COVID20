import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * GUI New Account Frame for app
 *
 * <p>Purdue University -- CS18000 -- Fall 2020</p>
 *
 * @author Katherine Yi
 * @version 11/20/2020
 */

public class NewAccountFrame extends JComponent implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //fields
    User currentUser;
    ObjectInputStream ois;
    ObjectOutputStream oos;
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

    JLabel password;
    JTextField passwordField;

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new NewAccountFrame());
    }

     */
    
    @Override
    public void run() {
        NewAccountFrame frame = new NewAccountFrame(oos, ois);
        frame.setVisible(true);
    }

    public NewAccountFrame(ObjectOutputStream oos, ObjectInputStream ois) {
        this.oos = oos;
        this.ois = ois;
        System.out.println("into run of new account");

        JFrame frame = new JFrame("Corona Connect Create New Account");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

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

        password= new JLabel("* Password: ");
        passwordField = new JTextField();
        mPanel.add(password);
        mPanel.add(passwordField);


        firstName = new JLabel("* First name: ");
        firstNameField = new JTextField();
        mPanel.add(firstName);
        mPanel.add(firstNameField);

        lastName = new JLabel("* Last name: ");
        lastNameField = new JTextField();
        mPanel.add(lastName);
        mPanel.add(lastNameField);

        preferredName = new JLabel("Preferred name: ");
        preferredNameField = new JTextField();
        mPanel.add(preferredName);
        mPanel.add(preferredNameField);

        age = new JLabel("* Age: ");
        ageField = new JTextField();
        mPanel.add(age);
        mPanel.add(ageField);

        college = new JLabel("* College: ");
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

        gradClass = new JLabel("* Graduation Year:");
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

        majors = new JLabel("* Major:");
        JTextField majorsField = new JTextField(); // this is supposed to be dropdown?
        mPanel.add(majors);
        mPanel.add(majorsField);

        interests = new JLabel("* Interests:");
        interestsField = new JTextField();
        mPanel.add(interests);
        mPanel.add(interestsField);

        aboutMeSection = new JLabel("* About Me:");
        aboutMeField = new JTextField();
        mPanel.add(aboutMeSection);
        mPanel.add(aboutMeField);

        email = new JLabel("* Email:");
        emailField = new JTextField();
        mPanel.add(email);
        mPanel.add(emailField);

        phoneNumber = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        mPanel.add(phoneNumber);
        mPanel.add(phoneNumberField);

        content.add(jsp, BorderLayout.CENTER);

        JPanel sPanel = new JPanel();
        saveButton = new JButton("Save New Profile");
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check to see if all fields are valid in another method 
                boolean valid = false;
                if (firstNameField.getText().equals("")
                || passwordField.getText().equals("") || lastNameField.getText().equals("")
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
                    JOptionPane.showMessageDialog(null, "Profile is being created.", "Corona Connect",
                        JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("pog");

                    try {
                        System.out.println("pog2");
                        oos.writeInt(2);
                        oos.flush();

                        oos.writeObject(firstNameField.getText());
                        oos.flush();

                        oos.writeObject(lastNameField.getText());
                        oos.flush();

                        oos.writeObject(age);
                        oos.flush();

                        oos.writeObject(passwordField.getText());
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

                        currentUser = (User)ois.readObject();


                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }

                    // create profile here  
                    frame.dispose();
                    launchMainFrame();       
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
        MainFrame mf = new MainFrame(oos, ois, currentUser);
    }

}
